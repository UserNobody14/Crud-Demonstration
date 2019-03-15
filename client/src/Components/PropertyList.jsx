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
	linkFind(property) {
		if (typeof property._links !== 'undefined') {
			if (typeof property._links.self !== 'undefined'){
			var realLink = property._links.self.href;
		var backlink = Number(realLink.replace(Auth.urlGet() + '/api/properties/', ""));
		var back2 = realLink.replace(Auth.urlGet() + '/api/properties/', "");
		return [realLink, backlink, back2];
	}
		}
		else if (typeof property.propID !== 'undefined') {
			var backlink = property.propID;
			var realLink = Auth.urlGet() + "/api/properties/" + backlink;
			console.log(backlink);
			console.log(realLink);
			return [realLink, backlink];
		}
	}
	render() {
		const properties = this.props.properties.map(property =>
			<Property key={this.linkFind(property)[0]} property={property}
				backlink={this.linkFind(property)[1]}/>
		);//Replace these with a reactstrap version.?
		console.log("Rendering PropertyList...")
		return (<div>{properties}</div>);
	}
}
export default PropertyList;
