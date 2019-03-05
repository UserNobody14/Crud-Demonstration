'use strict';

// tag::vars[]
const React = require('react');
const ReactDOM = require('react-dom');
const client = require('./client');
// import LoginForm from './LoginForm.jsx';
// import injectTapEventPlugin from 'react-tap-event-plugin';
// import getMuiTheme from 'material-ui/styles/getMuiTheme';
// import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
// import { PropTypes } from 'react';
import Auth from './Auth';
// import { Link } from 'react-router';
// import { Card, CardText } from 'material-ui/Card';
// import RaisedButton from 'material-ui/RaisedButton';
// import TextField from 'material-ui/TextField';
// end::vars[]
// tag::app[]
//const xhrSend = function
class App extends React.Component {

	constructor(props) {
		super(props);
		this.state = {properties: []};
	}
//rename properties to properties.
	componentDidMount() {
		client({method: 'GET', path: '/api/properties'}).done(response => {
			this.setState({properties: response.entity._embedded.properties});
		});
	}

	render() {
		return (
			<PropertyList properties={this.state.properties}/>
		)
	}
}
// end::app[]

// tag::property-list[]
class PropertyList extends React.Component{
	render() {
		const properties = this.props.properties.map(property =>
			<Property key={property._links.self.href} property={property}/>
		);//Replace these with a reactstrap version.
		return (
			<table>
				<tbody>
					<tr>
						<th>Name</th>
						<th>Address</th>
						<th>Poolsize</th>
            <th>Average Rating</th>
					</tr>
					{properties}
				</tbody>
			</table>
		)
	}
}
// end::property-list[]

// tag::property[]
class Property extends React.Component{
	render() {
		return (
			<tr>
				<td>{this.props.property.propName}</td>
				<td>{this.props.property.address}</td>
				<td>{this.props.property.poolsize}</td>
				<td>{this.props.property.avgrating}</td>
			</tr>
		)
	}
}
// end::property[]
class NameForm extends React.Component {
  constructor(props) {
    super(props);
    this.state = {username: '',
									password: ''};

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
		function checkStatus(response) {
		  if (response.status >= 200 && response.status < 300) {
		    return response
		  } else {
		    var error = new Error(response.statusText)
		    error.response = response
		    throw error
		  }
		}
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
			.then(response => response.headers,get('Authorization'))
			.then(d => authentify(d))
			.then(function(data) {
    			console.log('request succeeded with JSON response', data)
  		}).catch(function(error) {
    			console.log('request failed', error)
  		})
		alert('A name was submitted: ' + this.state.username + ' ' + this.state.password);
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

ReactDOM.render(
  <NameForm />,
  document.getElementById('login')
);

ReactDOM.render(
	<App />,
	document.getElementById('react')
);
