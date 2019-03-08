'use strict';

// tag::vars[]
const React = require('react');
const ReactDOM = require('react-dom');
const axios = require('axios')
const client = require('./client');
const RatingDropdown = require('./RatingDropdown.jsx');
import { Button, Form, FormGroup, Label, Input} from 'reactstrap';
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
const checkStatus = function (response) {
		  if (response.status >= 200 && response.status < 300) {
		    return response
		  } else {
		    var error = new Error(response.statusText)
		    error.response = response
		    throw error
		  }
		}
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
			<Property key={property._links.self.href} property={property}
			backlink={Number(property._links.self.href.replace("http://localhost:8080/api/properties/", ""))}/>
		);//Replace these with a reactstrap version.?
		return (
			// <div>
			// {properties}
			// </div>
			<div>
			<table>
				<tbody>
					<tr>
						<th>Name</th>
						<th>Address</th>
						<th>Poolsize</th>
            <th>Average Rating</th>
						<th>backlinked Id</th>
						<th>create rating</th>
					</tr>
					{properties}
				</tbody>
			</table>
			</div>
		)
	}
}
// end::property-list[]

// tag::property[]
class Property extends React.Component{
	constructor(props) {
		super(props);
		this.handleChange = this.handleChange.bind(this);
		this.handleSubmit = this.handleSubmit.bind(this);
		this.state = {rating: 0};
	}
	handleChange(event) {
		const name = event.target.name;
		const value = event.target.value;
		this.setState({[name]: value});
	}
	handleSubmit(event) {
		event.preventDefault();
		//allows a user to post ratings.
		//Make this a separate block from
		console.log("gotten this far.")
		function authentify(authorization) {
			Auth.authenticateUser(authorization);
			return authorization;
		}
		console.log(this.props.backlink);
		fetch('/properties/' + this.props.backlink + '/ratings', {
		  					method: 'POST',
		  					headers: {'Content-Type': 'application/json',
													'Authorization': Auth.getToken()},
		  body: JSON.stringify({
				propID: this.props.backlink,
		    rating: 3,
		    comment: this.state.comment
		  })
		}).then(checkStatus)
			//.then(response => response.headers,get('Authorization'))
			//.then(d => authentify(d))
			.then(function(data) {
					alert('A rating was submitted: ' + data.json());
    			console.log('request succeeded with JSON response', data.json());
  		}).catch(function(error) {
    			console.log('request failed', error)
  		})
	}
	render() {
		const properlink = this.props.backlink;
		return (
			//maybe instead of rows & columns just have a bunch of divs.
			/*
					<div>
						<div>
							<span>{this.props.property.propName}</span>
							<span>{this.props.property.address}</span>
							<span>{this.props.property.poolsize}</span>
							<span>{this.props.property.avgrating}</span>
						</div>
						<div>
							<RatingDropdown backlink={properlink}/>
						</div>
					</div>
					*/
			<tr>
				<td>{this.props.property.propName}</td>
				<td>{this.props.property.address}</td>
				<td>{this.props.property.poolsize}</td>
				<td>{this.props.property.avgrating}</td>
				<td>{this.props.backlink}</td>
				<td>
				<form onSubmit={this.handleSubmit}>
        	<label>
          	Rate your stay:
          	<input type="text" value={this.state.comment} name="comment" onChange={this.handleChange} />
        	</label>
					<label>
          		Pick your favorite flavor:
          	<select value={this.state.rating} onChange={this.handleChange}>
            	<option value=1>Grapefruit</option>
            	<option value=2>Lime</option>
            	<option value=3>Coconut</option>
            	<option value=4>Mango</option>
          	</select>
        	</label>
        	<input type="submit" value="Submit" />
      	</form>
			</td>

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

ReactDOM.render(
  <NameForm />,
  document.getElementById('login')
);

ReactDOM.render(
	<App />,
	document.getElementById('react')
);