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
  withRouter,
  Switch
} from "react-router-dom";
import SignUpPage from './Containers/SignUpPage.jsx';
import SearchContainer from './Containers/SearchContainer.jsx';
import React, { Component } from "react";
//////////////////////////////////////////////////////////
//remember to add LoginPage, Logout redirect, and SignUpPage

////////////////////////////////////////////////////////////
// 1. Click the public page
// 2. Click the protected page
// 3. Log in
// 4. Click the back button, note the URL each time

const routesX = [
  {
    path: "/",
    exact: true,
    sidebar: () => <div>home!</div>,
    main: () => <h2>Home</h2>
  },
  {
    path: "/bubblegum",
    sidebar: () => <div>bubblegum!</div>,
    main: () => <h2>Bubblegum</h2>
  },
  {
    path: "/shoelaces",
    sidebar: () => <div>shoelaces!</div>,
    main: () => <h2>Shoelaces</h2>
  }
];

function AuthExample() {
  return (
    <Router>
      <div>
        <AuthCard />
        <ul>
          <li>
            <Link to="/property-view">View All Properties</Link>
          </li>
          <li>
            <Link to="/host-dashboard">Host Dashboard</Link>
          </li>
          <li>
            <Link to="/sign-up-page">Sign Up</Link>
          </li>
          <li>
            <Link to="/">Home</Link>
          </li>
        </ul>
        <Route exact path="/" component={() => <SearchContainer />} />
        <Switch>
          <Route path="/property-view" component={() => <PropertyContainer propID={null} searchString={null} />} />
          <Route path="/sign-up-page" component={() => <SignUpPage />} />
          <Route path="/login-page" component={ LoginPage } />
          <PrivateRoute path="/host-dashboard" component={() => <HostDashboard />} />
          <Route path="individual/:propID" component={ LoginPage } />
        </Switch>
      </div>
    </Router>
  );
}
//Check whether {() => <PropertyContainer />} works?
//TODO: get private route working on (for instance) the add comment buttons under properties.
//TODO: add a page for individual properties

//match.params.propID
function IndividualPropertyItem({ match }) {
  return null;
}

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
export default AuthExample;
