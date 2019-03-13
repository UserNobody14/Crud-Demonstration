'use strict'
const React = require('react');
//const ReactDOM = require('react-dom');
const axios = require('axios')
const RatingDropdown = require('./RatingDropdown.jsx');
import { Button, Form, FormGroup, Label, Input, Card, Col, ButtonGroup } from 'reactstrap';
// import LoginForm from './LoginForm.jsx';
// import injectTapEventPlugin from 'react-tap-event-plugin';
// import getMuiTheme from 'material-ui/styles/getMuiTheme';
// import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
// import { PropTypes } from 'react';
import Auth from '../Auth';
// 'react-hot-loader/root'
/*
"address": "EasyBeds",
                "avgrating": 0.0,
                "description": "EasyBeds",
                "poolsize": "EasyBeds",
                "price": 8,
                "propname": "EasyBeds"
                */

class CreatePropertyForm extends React.Component {
	//add forms for email and host vs. guest.
  render() {
    //add a Col element?
    return (
			<Card>
				<Form onSubmit={this.props.onSubmit} onChange={this.props.onChange}>
          <FormGroup className="mb-2 mr-sm-2 mb-sm-0">
	          <Label for="propname" className="mr-sm-2">Property Name</Label>
	          <Input type="propname" name="propname" id="propname" placeholder="property name" />
	        </FormGroup>
          <FormGroup className="mb-2 mr-sm-2 mb-sm-0">
	          <Label for="description" className="mr-sm-2">Property Description</Label>
	          <Input type="description" name="description" id="description" placeholder="property name" />
	        </FormGroup>
          <FormGroup className="mb-2 mr-sm-2 mb-sm-0">
	          <Label for="poolsize" className="mr-sm-2">Pool Size</Label>
	          <Input type="poolsize" name="poolsize" id="poolsize" placeholder="property name" />
	        </FormGroup>
          <FormGroup className="mb-2 mr-sm-2 mb-sm-0">
	          <Label for="price" className="mr-sm-2">Price of Stay</Label>
	          <Input type="price" name="price" id="price" placeholder="property name" />
	        </FormGroup>
	        <FormGroup className="mb-2 mr-sm-2 mb-sm-0">
	          <Label for="address" className="mr-sm-2">Address</Label>
	          <Input type="address" name="address" id="address" placeholder="address" />
	        </FormGroup>
	        <Button>Submit</Button>
	      </Form>
			</Card>
    );
  }
}
export default CreatePropertyForm;
