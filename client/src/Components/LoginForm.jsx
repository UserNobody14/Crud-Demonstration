'use strict'
const React = require('react');
//const ReactDOM = require('react-dom');
const axios = require('axios')
const RatingDropdown = require('./RatingDropdown.jsx');
import { Button, Form, FormGroup, Label, Input} from 'reactstrap';
// import LoginForm from './LoginForm.jsx';
// import injectTapEventPlugin from 'react-tap-event-plugin';
// import getMuiTheme from 'material-ui/styles/getMuiTheme';
// import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
// import { PropTypes } from 'react';
import Auth from '../Auth';
// 'react-hot-loader/root'

const checkStatus = function (response) {
		  if (response.status >= 200 && response.status < 300) {
		    return response
		  } else {
		    var error = new Error(response.statusText)
		    error.response = response
		    throw error
		  }
		}

class LoginForm extends React.Component {
  constructor(props) {
    super(props);
    this.state = {username: '', password: ''}; //use class properties?

    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  handleChange(event) {
		const name = event.target.name;
		const value = event.target.value;
    this.setState({[name]: value});
  }

  handleSubmit(event) {

    event.preventDefault();
		    // prevent default action. in this case, action is the form submission event
    // create a string for an HTTP body message

		//send this.state.username and this.state.password to the Auth function.
		//maybe just use the handleSubmit and handleChange from the loginPage?
  }

  render() {
    return (
      <form onSubmit={this.props.onSubmit}>
        <label>
          Username:
          <input type="text" value={this.props.user.username} name="username" onChange={this.props.onChange} />
        </label>
				<label>
          Password:
          <input type="text" value={this.props.user.password} name="password" onChange={this.props.onChange} />
        </label>
        <input type="submit" value="Submit" />
      </form>
    );
  }
}
export default LoginForm;
