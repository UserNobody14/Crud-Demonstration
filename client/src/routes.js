//import Base from './Components/BasePage.jsx';
const PropertyContainer = require('./Containers/PropertyContainer.jsx').default;
import HostDashboard from './Containers/HostDashboard.jsx';
import LoginPage from './Containers/LoginPage.jsx';
//import SignUpPage from './Containers/SignUpPage.jsx';
import AuthCard from './Base/AuthCard.jsx'
import Auth from './Auth';
import {
  BrowserRouter as Router,
  Route,
  Link,
  Redirect,
  withRouter
} from "react-router-dom";

 /*
 Sometimes you need to render whether the path matches the location or not.
 In these cases, you can use the function children prop.
 It works exactly like render except that it gets called whether there is a match or not.

The children render prop receives all the same route props as the component and render methods,
except when a route fails to match the URL, then match is null.
This allows you to dynamically adjust your UI based on whether or not the route matches.
Here we're adding an active class if the route matches

<ul>
  <ListItemLink to="/somewhere" />
  <ListItemLink to="/somewhere-else" />
</ul>;

const ListItemLink = ({ to, ...rest }) => (
  <Route
    path={to}
    children={({ match }) => (
      <li className={match ? "active" : ""}>
        <Link to={to} {...rest} />
      </li>
    )}
  />
);
       <div>
        <nav>
          <ul>
            <li>
              <Link to="/">Home</Link>
            </li>
            <li>
              <Link to="/about/">About</Link>
            </li>
            <li>
              <Link to="/users/">Users</Link>
            </li>
          </ul>
        </nav>

        <Route path="/" exact component={Index} />
        <Route path="/about/" component={About} />
        <Route path="/users/" component={Users} />
      </div>
 */

import React, { Component } from "react";
//////////////////////////////////////////////////////////
//remember to add LoginPage, Logout redirect, and SignUpPage

////////////////////////////////////////////////////////////
// 1. Click the public page
// 2. Click the protected page
// 3. Log in
// 4. Click the back button, note the URL each time

function AuthExample() {
  return (
    <Router>
      <div>
        <AuthCard />
        <ul>
          <li>
            <Link to="/property-view">View Properties</Link>
          </li>
          <li>
            <Link to="/host-dashboard">Host Dashboard</Link>
          </li>
        </ul>
        <Route path="/property-view" component={Public} />
        <Route path="/login-page" component={LoginPage} />
        <PrivateRoute path="/host-dashboard" component={Protected} />
      </div>
    </Router>
  );
}
//Check whether {() => <PropertyContainer />} works?
//TODO: get private route working on (for instance) the add comment buttons under properties.
//TODO: add a page for individual properties

function PrivateRoute({ component: Component, ...rest }) {
  return (
    <Route
      {...rest}
      render={props =>
        Auth.isUserAuthenticated() ? (
          <Component {...props} />
        ) : (
          <Redirect
            to={{
              pathname: "/login-page",
              state: { from: props.location }
            }}
          />
        )
      }
    />
  );
}

function Public() {
  return <PropertyContainer />;
}

function Protected() {
  return <HostDashboard />;
}

export default AuthExample;
