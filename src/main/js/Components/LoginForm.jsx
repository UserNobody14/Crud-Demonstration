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
    this.state = {username: '', password: ''};

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
    const username = encodeURIComponent(this.state.username);
		//console.log(username);
    const password = encodeURIComponent(this.state.password);
    const formData = `username=${username}&password=${password}`;
		const altData = JSON.stringify({username: username, password: password});
		/////////////////////////////////////////////////////////

		function authentify(authorization) {
			Auth.authenticateUser(authorization);
			return authorization;
		}
		fetch('/login', {
		  					method: 'POST',
		  					headers: {'Content-Type': 'application/json'},
		  body: JSON.stringify({
		    username: this.state.username,
		    password: this.state.password,
		  })
		}).then(checkStatus)
			.then(response => response.headers.get('Authorization'))
			.then(d => authentify(d))
			.then(function(data) {
					alert('A name was submitted: ' + username + '\nThis is the json web token: ' + data);
    			console.log('request succeeded with JSON response', data);
  		}).catch(function(error) {
    			console.log('request failed', error)
  		})
			//TODO: it should update the page.
  }

  render() {
    return (
      <form onSubmit={this.handleSubmit}>
        <label>
          Username:
          <input type="text" value={this.state.username} name="username" onChange={this.handleChange} />
        </label>
				<label>
          Password:
          <input type="text" value={this.state.password} name="password" onChange={this.handleChange} />
        </label>
        <input type="submit" value="Submit" />
      </form>
    );
  }
}
export default LoginForm;
