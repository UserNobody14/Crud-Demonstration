'use strict'
import React from 'react';
//import { browserHistory, Router } from 'react-router';
import { BrowserRouter as Router, Route, Link } from "react-router-dom";
import routes from './routes.js';
import { hot } from 'react-hot-loader/root';
class MainApp extends React.Component {
  render() {
    return(<Router>
       <AuthExample />
      </Router>)
  }
}
export default hot(MainApp);
