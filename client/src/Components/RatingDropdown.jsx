'use strict'
import React, {Component} from 'react';
// 'react-hot-loader/root';
import {
  Collapse,
  Button,
  CardBody,
  Card,
  ListGroup,
  ListGroupItem,
  ListGroupItemHeading,
  ListGroupItemText,
  Form,
  FormGroup,
  Input,
  Badge,
  Label
} from 'reactstrap';
const axios = require('axios');
import Auth from '../Auth';

const testData = {data:
  [
    {
        "comment": "badPlace2Stay",
        "propID": 1490,
        "rating": 2,
        "ratingID": 1903,
        "userCanEdit": false
    },
    {
        "comment": "sucks",
        "propID": 1490,
        "rating": 3,
        "ratingID": 1904,
        "userCanEdit": false
    }
]
}

class RatingDropdown extends Component {

  state = {
    collapse: false,
    ratings: [],
    submission: {
      rating: 0,
      comment: ""
    }
  }
   handleChange = (event) => {
    const name = event.target.name;
    const submission = this.state.submission;
		submission[name] = event.target.value;
    this.setState({
      submission
    });
   }
  //Definitely change location of the authorized request issuer.
  handleSubmit = (event) => {
    event.preventDefault();
    console.log("gotten this far.")
    console.log(this.props.backlink);
    axios({
      url: Auth.urlGet() + `/properties/${this.props.backlink}/ratings`,
      method: 'post',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': Auth.getToken()
      },
      data: {
        propID: this.props.backlink,
        rating: Number(this.state.submission.rating),
        comment: this.state.submission.comment
      }
    }).then(function(data) {
      alert('A rating was submitted: ' + data.data);
      console.log('request succeeded with JSON response', data.data);
    }).catch(function(error) {
      console.log('request failed', error)
    })
    this.handleGet();
  }
   componentDidMount() {
  //   const headers = Auth.authenticatedHeaders();
  //   axios({
  //     method: 'get',
  //     url: Auth.urlGet() + '/properties/' + this.props.backlink + '/ratings',
  //     headers: Auth.authenticatedHeaders()
  //   }).then(response => this.setState({ratings: response.data}));
  //   //.catch(() => this.setState({ratings: testData.data}));
  }
  handleGet = () => {
        const headers = Auth.authenticatedHeaders();
    axios({
      method: 'get',
      url: Auth.urlGet() + '/properties/' + this.props.backlink + '/ratings',
      headers: Auth.authenticatedHeaders()
    }).then(response => this.setState({ratings: response.data}));

  }

  toggle = () => {
    if (!this.state.collapse) {this.handleGet()};
    this.setState(state => ({
      collapse: !state.collapse,
      ratings: this.state.ratings
    }));
  }

  render() {
    return (<div>
      <Button block color="primary" onClick={this.toggle} style={{
        marginBottom: '1rem'
      }}>Ratings</Button>
      <Collapse isOpen={this.state.collapse}>
        <RatingList ratings={this.state.ratings} handleChange={this.handleChange} handleSubmit={this.handleSubmit} submission={this.state.submission}></RatingList>
            </Collapse>
          </div>);
  }
}
const myF = link => Number(link.replace("http://localhost:8080/api/ratings/", ""));
const getRatingKey = ratingID => Auth.urlGet() + "/api/ratings/" + ratingID;

class RatingList extends React.Component {
  render() {
    //add key back in later.
    const ratings = this.props.ratings.map(rating => <Rating rating={rating} key={getRatingKey(rating.ratingID)}/>); //Replace these with a reactstrap version.?
    return (
      <div>
        <ListGroup>
          {ratings}
        </ListGroup>
        <Card>
          <Form inline onSubmit={this.props.handleSubmit} onChange={this.props.handleChange}>
            <FormGroup>
              <Label for="exampleText">Text Area</Label>
              <Input type="textarea" name="comment" id="exampleText" />
            </FormGroup>
            <FormGroup>
              <Label for="exampleSelect">Select</Label>
              <Input type="select" name="rating" id="exampleSelect">
                <option>1</option>
                <option>2</option>
                <option>3</option>
                <option>4</option>
                <option>5</option>
              </Input>
            </FormGroup>
            <Button>Submit</Button>
          </Form>
        </Card>
      </div>)
  }
}

class Rating extends React.Component {
  render() {
    return (<ListGroupItem>
      <ListGroupItemHeading>Rating: {this.props.rating.rating} out of 5</ListGroupItemHeading>
      <ListGroupItemText>
        <p>{this.props.rating.comment}</p>
        <Badge>{this.props.rating.userCanEdit ? "Yours" : "Not yours"}</Badge>
      </ListGroupItemText>
    </ListGroupItem>);
  }
}

/*
        <br />
        <InputGroup>
          <Input />
          <InputGroupButtonDropdown addonType="append" isOpen={this.state.dropdownOpen} toggle={this.toggleDropDown}>
            <DropdownToggle caret>
              Button Dropdown
            </DropdownToggle>
            <DropdownMenu>
              <DropdownItem header>Header</DropdownItem>
              <DropdownItem disabled>Action</DropdownItem>
              <DropdownItem>Another Action</DropdownItem>
              <DropdownItem divider />
              <DropdownItem>Another Action</DropdownItem>
            </DropdownMenu>
          </InputGroupButtonDropdown>
        </InputGroup>
        <br />
                <form onSubmit={this.props.handleSubmit}>
          <label>
            Leave a comment:
            <input type="text" value={this.props.submission.comment} name="comment" onChange={this.props.handleChange}/>
          </label>
          <label>
            Rate your stay:
            <select value={this.props.submission.rating} onChange={this.props.handleChange}>
              <option value="1">"1"</option>
              <option value="2">"2"</option>
              <option value="3">"3"</option>
              <option value="4">"4"</option>
            </select>
          </label>
          <input type="submit" value="Submit"/>
        </form>

        <FormGroup tag="fieldset">
          <legend>Rating</legend>
          <FormGroup check>
            <Label check>
              <Input type="radio" name="rating1" />{' '}
              Option one is this and that—be sure to include why it's great
            </Label>
          </FormGroup>
          <FormGroup check>
            <Label check>
              <Input type="radio" name="rating2" />{' '}
              Option two can be something else and selecting it will deselect option one
            </Label>
          </FormGroup>
          <FormGroup check>
            <Label check>
              <Input type="radio" name="rating3" disabled />{' '}
              Option three is disabled
            </Label>
          </FormGroup>
        </FormGroup>

        <FormGroup>
          <Label for="exampleText">Text Area</Label>
          <Input type="textarea" name="text" id="exampleText" />
        </FormGroup>
*/
export default RatingDropdown;
