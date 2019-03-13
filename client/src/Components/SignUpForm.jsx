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
class SignUpForm extends React.Component {
	//add forms for email and host vs. guest.
  render() {
    //add a Col element?
    return (
			<Card>
				<Form onSubmit={this.props.onSubmit} onChange={this.props.onChange}>
	        <FormGroup className="mb-2 mr-sm-2 mb-sm-0">
	          <Label for="username" className="mr-sm-2">Username</Label>
	          <Input type="username" name="username" id="username" placeholder="username" />
	        </FormGroup>
	        <FormGroup className="mb-2 mr-sm-2 mb-sm-0">
	          <Label for="password" className="mr-sm-2">Password</Label>
	          <Input type="password" name="password" id="password" placeholder="password" />
	        </FormGroup>
        <ButtonGroup>
          <Button color="primary" onClick={() => this.props.onRadioBtnClick(1)} active={this.props.hostValue === false}>Host</Button>
          <Button color="primary" onClick={() => this.props.onRadioBtnClick(2)} active={this.props.hostValue === true}>Guest</Button>
        </ButtonGroup>
	        <Button>Submit</Button>
	      </Form>
			</Card>
    );
  }
}
export default SignUpForm;
