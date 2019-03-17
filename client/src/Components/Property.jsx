'use strict';

// 'react-hot-loader/root';
//const React = require('react');
import React from 'react';
const axios = require('axios');
//const RatingDropdown = require('./RatingDropdown.jsx');
import RatingDropdown from './RatingDropdown.jsx';
import {Button, Form, FormGroup, Label, Input, Container, Row, Col, Card, CardBody } from 'reactstrap';
import Auth from '../Auth.js';

const checkStatus = function(response) {
  if (response.status >= 200 && response.status < 300) {
    return response
  } else {
    var error = new Error(response.statusText)
    error.response = response
    throw error
  }
}

class Property extends React.Component {
  render() {
    return (
    <Container>
      <Row>
        <Col>{this.props.property.propname}</Col>
        <Col>{this.props.property.address}</Col>
        <Col>{this.props.property.poolsize}</Col>
        <Col>{this.props.property.description}</Col>
        <Col>{this.props.property.avgrating}</Col>
        <Col>{this.props.backlink}</Col>
    </Row>
    <Row>
        <RatingDropdown backlink={ this.props.backlink }/>
    </Row>
  </Container>)
}//// NOTE: to self, change 2 more aesthetic architecture.
}
/*
<Container>
      <Row>
        <Col>{this.props.property.propname}</Col>
        <Col>{this.props.property.address}</Col>
        <Col>{this.props.property.poolsize}</Col>
        <Col>{this.props.property.description}</Col>
        <Col>{this.props.property.avgrating}</Col>
        <Col>{this.props.backlink}</Col>
    </Row>
    <Row>
        <RatingDropdown backlink={ this.props.backlink }/>
    </Row>
  </Container>
*/

export default Property;
