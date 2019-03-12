import axios from 'axios';
import React, { Component } from "react";
import SignUpForm from '../Components/SignUpForm.jsx';
import {
  BrowserRouter as Router,
  Route,
  Link,
  Redirect,
  withRouter
} from "react-router-dom";

class SignupPage extends Component {
  state = {
    username: '',
    password: '',
    isHost: false
  }
  handleChange = (event) => {
    const name = event.target.name;
    const val = event.target.value;
    this.setState({[name]: val});
  }
  handleSubmit = (event) => {
    event.preventDefault();
    axios({
      url: '/users/sign-up',
      method: 'post',
      data: {
        username: this.state.username,
        password: this.state.password,
        authorityr: this.state.isHost ? "ROLE_HOST" : "ROLE_GUEST"}
      })
    //TODO: catch bad usernames, similar.
  }
  handleHost = (isHost) => {
    this.setState({ isHost })
  }
  render() {
    return(
      <SignUpForm onSubmit={this.handleSubmit} onChange={this.handleChange} onRadioBtnClick={this.handleHost} hostValue={this.state.isHost}/>
    )
  }
}
export default SignupPage;
