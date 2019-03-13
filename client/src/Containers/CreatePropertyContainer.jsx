import axios from 'axios';
import React, { Component } from "react";
import CreatePropertyForm from '../Components/CreatePropertyForm.jsx';
import {
  BrowserRouter as Router,
  Route,
  Link,
  Redirect,
  withRouter
} from "react-router-dom";
import Auth from '../Auth';

class CreatePropertyContainer extends Component {
  state = {
    propname: '',
    address: '',
    poolsize: '',
    description: '',
    price: 0
  }
  handleChange = (event) => {
    const name = event.target.name;
    const val = event.target.value;
    this.setState({[name]: val});
  }
  handleSubmit = (event) => {
    event.preventDefault();
    axios({
      url: Auth.urlGet() + '/properties',
      method: 'post',
      headers: Auth.authenticatedHeaders(),
      data: {
        propname: this.state.propname,
        address: this.state.address,
        poolsize: this.state.poolsize,
        description: this.state.description,
        price: this.state.price
        }
      })
    //TODO: catch bad usernames, similar.
  }
  render() {
    return(
      <CreatePropertyForm
        onSubmit={this.handleSubmit}
        onChange={this.handleChange} />
    )
  }
}
export default CreatePropertyContainer;
