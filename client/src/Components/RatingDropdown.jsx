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
  Label,
  Col
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
   handleEdit = (input, ratingID, event) => {
     event.preventDefault();
     this.handleSubmitGeneral({rating: input.rating, comment: input.comment, propID: this.props.backlink}, ratingID);
   }
  //Maybe make the server reply to 'post' requests with the updated view?.
  //change 2 data: this.state.submission?
  handleSubmit = (event) => {
    event.preventDefault();
    this.handleSubmitGeneral({
      propID: this.props.backlink,
      rating: Number(this.state.submission.rating),
      comment: this.state.submission.comment
    });


  }
  handleSubmitGeneral = (input, ratingID=null) => {
    const newrl = (ratingID != null) ? `/ratings/${ratingID}` : `/properties/${this.props.backlink}/ratings`;
    let newMethod = (ratingID != null) ? 'put' : 'post'
    axios({
      url: Auth.urlGet() + newrl,
      method: newMethod,
      headers: Auth.authenticatedHeaders(),
      data: {
        propID: input.propID,
        rating: input.rating,
        comment: input.comment
      }
    })
    .then(this.handleGet)
    .then(data => console.log('request succeeded', data.data))
    .catch(error => console.log('request failed', error))
  }
  handleGet = () => {
        const headers = Auth.authenticatedHeaders();
    axios({
      method: 'get',
      url: Auth.urlGet() + '/properties/' + this.props.backlink + '/ratings',
      headers: Auth.authenticatedHeaders()
    }).then(response => this.setState({ratings: response.data}));

  }
  toggle = (state) => {
    if (!state.collapse) {this.handleGet()};
    return {collapse: !state.collapse, ratings: state.ratings, submission: state.submission};
  }

  render() {
    return (
      <Col>
        <Button block color="primary" onClick={() => this.setState(this.toggle)} style={{
          marginBottom: '1rem'
        }}>Ratings</Button>
        <Collapse isOpen={this.state.collapse}>
          <RatingList
            ratings={this.state.ratings}
            handleChange={this.handleChange}
            handleSubmit={this.handleSubmit}
            submission={this.state.submission}
            handleEdit={this.handleEdit}>
          </RatingList>
        </Collapse>
      </Col>);
  }
}
const myF = link => Number(link.replace("http://localhost:8080/api/ratings/", ""));
const getRatingKey = ratingID => Auth.urlGet() + "/api/ratings/" + ratingID;

class RatingList extends React.Component {
  render() {
    //add key back in later.
    const ratings = this.props.ratings.map(rating => {
      if (rating.userCanEdit) {
        return (<EditableRating rating={rating} backlink={rating.ratingID} key ={getRatingKey(rating.ratingID)} handleEdit={this.props.handleEdit}></EditableRating>);
      }
      else {return(<Rating rating={rating} key={getRatingKey(rating.ratingID)} />)}
    }); //Replace these with a reactstrap version.?
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
    return (
      <ListGroupItem>
        <ListGroupItemHeading>Rating: {this.props.rating.rating} out of 5 {this.props.rating.userCanEdit ? <Badge>Yours</Badge> : " " }</ListGroupItemHeading>
        <ListGroupItemText>{this.props.rating.comment}</ListGroupItemText>
      </ListGroupItem>);
  }
}

const privateHandleEdit = (input, ratingID, event) => {
     event.preventDefault();
     handleSubmitAltGeneral({rating: input.rating, comment: input.comment, propID: input.propID}, ratingID);
   };

const handleSubmitAltGeneral = (input, ratingID=null) => {
    let newrl = `/ratings/${ratingID}`;
    let newMethod = 'put';
    axios({
      url: Auth.urlGet() + newrl,
      method: newMethod,
      headers: Auth.authenticatedHeaders(),
      data: {
        propID: input.propID,
        rating: input.rating,
        comment: input.comment
      }
    }).then(data => console.log('request succeeded', data.data))
    .catch(error => console.log('request failed', error))
  };

class EditableRating extends React.Component {
  state = {
    submission: {propID: this.props.backlink,
    rating: this.props.rating.rating,
    comment: this.props.rating.comment},
    editing: false
  }
  toggle = () => {
    this.setState({editing: !this.state.editing});
  }
   handleChange = (event) => {
    const name = event.target.name;
    const submission = this.state.submission;
		submission[name] = event.target.value;
    this.setState({ submission });
   }
  handleEditReturn = (event) => privateHandleEdit(this.state.submission, this.props.rating.ratingID, event);

  render() {
    return (
      <ListGroupItem>
        <ListGroupItemHeading>Rating: {this.props.rating.rating} out of 5 {this.props.rating.userCanEdit ? <Badge>Yours</Badge> : " " }</ListGroupItemHeading>
        <ListGroupItemText>{this.props.rating.comment}
          <Button block color="secondary" onClick={() => this.setState(this.toggle)} style={{
            marginBottom: '1rem'
          }}>Ratings</Button>
          <Collapse isOpen={this.state.editing}>
            <RatingForm
              handleChange={this.handleChange}
              handleSubmit={this.handleEditReturn}
              comment={this.state.submission.comment}
              handleEdit={this.handleEdit}
              rating={this.state.submission.rating}>
            </RatingForm>
          </Collapse>
        </ListGroupItemText>
      </ListGroupItem>);
}
}

class RatingForm extends React.Component {
  render() {
    //add key back in later.
    //const ratings = this.props.ratings.map(rating => <Rating rating={rating} key={getRatingKey(rating.ratingID)}/>); //Replace these with a reactstrap version.?
    return (
      <div>
        <Card>
          <Form inline onSubmit={this.props.handleSubmit} onChange={this.props.handleChange}>
            <FormGroup>
              <Label for="exampleText">Text Area</Label>{' '}
              <Input type="textarea" name="comment" id="exampleText2" defaultValue={this.props.comment}></Input>
            </FormGroup>
            <FormGroup>
              <Label for="exampleSelect">Select</Label>{' '}
              <Input type="select" name="rating" id="exampleSelect2">
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
