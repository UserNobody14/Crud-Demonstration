//'use strict';
// 'react-hot-loader/root';
const React = require('react');
const axios = require('axios');
const RatingDropdown = require('./RatingDropdown.jsx');
const Property = require('./Property.jsx');
import { Button, Form, FormGroup, Label, Input} from 'reactstrap';
//import 'bootstrap/dist/css/bootstrap.min.css';
import Auth from '../Auth';


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
export default PropertyList;
