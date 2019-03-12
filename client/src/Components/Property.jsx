'use strict';

// 'react-hot-loader/root';
//const React = require('react');
import React from 'react';
const axios = require('axios');
//const RatingDropdown = require('./RatingDropdown.jsx');
import RatingDropdown from './RatingDropdown.jsx';
import {Button, Form, FormGroup, Label, Input, Container, Row, Col } from 'reactstrap';
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
  constructor(props) {
    super(props);
    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.state = {
      rating: 0,
      comment: "no commment"
    };
  }
  handleChange(event) {
    const name = event.target.name;
    const value = event.target.value;
    this.setState({[name]: value});
  }
  //Definitely change location of the authorized request issuer.
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
    axios({
      url: `/properties/${this.props.backlink}/ratings`,
      headers: {
        'Content-Type': 'application/json',
        'Authorization': Auth.getToken()
      },
      data: {
        propID: this.props.backlink,
        rating: Number(this.state.rating),
        comment: this.state.comment
      }
    }).then(function(data) {
      alert('A rating was submitted: ' + data.json());
      console.log('request succeeded with JSON response', data.json());
    }).catch(function(error) {
      console.log('request failed', error)
    })
  }
  render() {
    const properlink = this.props.backlink;
    return (
    <Container>
      <Row>
        <Col>{this.props.property.propname}</Col>
        <Col>{this.props.property.address}</Col>
        <Col>{this.props.property.poolsize}</Col>
        <Col>{this.props.property.avgrating}</Col>
        <Col>{this.props.backlink}</Col>
        <Col>
          <form onSubmit={this.handleSubmit}>
            <label>
              Leave a comment:
              <input type="text" value={this.state.comment} name="comment" onChange={this.handleChange}/>
            </label>
            <label>
              Rate your stay:
              <select value={this.state.rating} onChange={this.handleChange}>
                <option value="1">"1"</option>
                <option value="2">"2"</option>
                <option value="3">"3"</option>
                <option value="4">"4"</option>
              </select>
            </label>
            <input type="submit" value="Submit"/>
          </form>
        </Col>
    </Row>
    <Row>
        <RatingDropdown backlink={ this.props.backlink }/>
    </Row>
  </Container>)
}//// NOTE: to self, remove the big rating submit form and place it elsewhere.
}

export default Property;
