'use strict';
// 'react-hot-loader/root';
//const React = require('react');
import React from 'react';
//import RatingDropdown from './RatingDropdown.jsx';
//import Property from './Property.jsx';
import Property from './Property.jsx';
const axios = require('axios');
//const RatingDropdown = require('./RatingDropdown.jsx');
//const Property = require('./Property.jsx');
import { Button, Form, FormGroup, Label, Input} from 'reactstrap';
//import 'bootstrap/dist/css/bootstrap.min.css';
import Auth from '../Auth';


class PropertyList extends React.Component {
	componentDidMount() {
		console.log("Mounting PropertyList...")
	}
	render() {
		const properties = this.props.properties.map(property =>
			<Property key={property._links.self.href} property={property}
			backlink={Number(property._links.self.href.replace("http://localhost:8080/api/properties/", ""))}/>
		);//Replace these with a reactstrap version.?
		console.log("Rendering PropertyList...")
		return (<div>{properties}</div>);
	}
}
export default PropertyList;
