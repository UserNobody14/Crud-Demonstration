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
newTest = {entity: {_embedded: {properties: [
	{
    "description": "Buy watermelon",
	"address": "buy",
"propName": "e",
"avg_rating": 0.0,
"price": 3,
"poolsize": "bid"
}
]}}};

class PropertyContainer extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      properties: []
    };
    //this.setPropertyElements = this.setPropertyElements.bind(this)
  }

  componentDidMount() {
    //axios.get('/api/properties').then(nonimportant => this.setPropertyElements(nonimportant)).catch(nonimportant => this.setPropertyElements(newTest));
		this.setState({properties: testData._embedded.properties});
		console.log("Mounting PropertyContainer...");
		console.log(testData);
	}
	setPropertyElements(responseElement) {
		this.setState({properties: responseElement._embedded.properties});
	}
  render() {
		console.log(this.state.properties)
    return (<PropertyList properties={this.state.properties}/>)
  }
}

export default PropertyContainer;
