import axios from 'axios';
import React, { Component } from "react";
import {
  BrowserRouter as Router,
  Route,
  Link,
  Redirect,
  withRouter
} from "react-router-dom";

class SignupPage extends Component {
  state = {username: '',
password: '',
isHost: false}
handleChange = (event) => {
  const name = event.target.name;
  const val = event.target.value;
  this.setState({[name]: val});
}
handleSubmit = (event) => {
  axios.post('/users/signup', {data: {username: this.state.username, password: this.state.password}})
}
}
