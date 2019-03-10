'use strict'
import React, { PropTypes } from 'react';
import { Link, IndexLink } from 'react-router';
import Auth from '../Auth';
//// 'react-hot-loader/root';
//add in Reactstrap.

const Base = ({ children }) => (
  <div>
    <div className="top-bar">
      <div className="top-bar-left">
        <IndexLink to="/">React App</IndexLink>
      </div>

      {Auth.isUserAuthenticated() ? (
        <div className="top-bar-right">
          <Link to="/logout">Log out</Link>
        </div>
      ) : (
        <div className="top-bar-right">
          <Link to="/login">Log in</Link>
          <Link to="/signup">Sign up</Link>
        </div>
      )}

    </div>

    //{ /* child component will be rendered here */ }
    {children}

  </div>
);

// Base.propTypes = {
//   children: PropTypes.object.isRequired
// };

export default Base;
