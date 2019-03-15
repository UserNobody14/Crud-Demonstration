'use strict'
const React = require('react');
const axios = require('axios');
//const client = require('./client');
//const PropertyList = require('../Components/PropertyList.jsx').default;
import PropertyList from '../Components/PropertyList.jsx';
import {Button, Form, FormGroup, Label, Input} from 'reactstrap';
//import 'bootstrap/dist/css/bootstrap.min.css';
import Auth from '../Auth';

const testData = require('../testData.json');
var newTest = {data: testData};
newTest = {data: {_embedded: {properties: [
	{"_links": {
                    "property": {
                        "href": "http://localhost:8080/api/properties/1490"
                    },
                    "self": {
                        "href": "http://localhost:8080/api/properties/1490"
                    }
                },
    "description": "Buy watermelon",
	"address": "buy",
"propName": "e",
"avg_rating": 0.0,
"price": 3,
"poolsize": "bid"
}
]}}};

class PropertyContainer extends React.Component {

	state = {
		properties: []
	}
	setPropertyElements = (responseElement) => {
		//console.log(JSON.stringify(responseElement))
		let dt = responseElement.data;
		let rt = (Array.isArray(dt)) ? {properties: dt}
		: {properties: dt._embedded.properties};
		this.setState(rt)
	}
	generalQuery = (append) => {
		const retData = {url: Auth.urlGet() + append, method: 'get', headers: Auth.authenticatedHeaders()};
		console.log(retData);
		return retData;
	}
	queryParams = () => {
		if(this.props.propID != null) {
			return this.generalQuery(`/api/properties/${this.props.propID}`);
		}
		else if(this.props.searchString != null) {
			return this.generalQuery(`/properties/search/${this.props.searchString}`);
		}
		else {
			return this.generalQuery('/api/properties');
		}
	}
  componentDidMount() {
		//if this.props.individualize == true add this.props.propID to the url
		axios(this.queryParams()).then(this.setPropertyElements)
		//.catch(nonimportant => this.setPropertyElements(testData));
		//this.setState({properties: testData._embedded.properties});
		console.log("Mounting PropertyContainer...");
		console.log(testData);
	}

  render() {
		console.log(this.state.properties)
    return (<PropertyList properties={this.state.properties}/>)
  }
}

export default PropertyContainer;
