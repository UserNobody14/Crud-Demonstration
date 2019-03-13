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
  ListGroupItemText
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
    ratings: []
  }
  componentDidMount() {
    const headers = Auth.authenticatedHeaders();
    axios({
      method: 'get',
      url: Auth.urlGet() + '/properties/' + this.props.backlink + '/ratings',
      headers: Auth.authenticatedHeaders()
    }).then(response => this.setState({ratings: response.data}));
    //.catch(() => this.setState({ratings: testData.data}));
  }

  toggle = () => {
    this.setState(state => ({
      collapse: !state.collapse,
      ratings: this.state.ratings
    }));
  }

  render() {
    return (<div>
            <Button color="primary" onClick={this.toggle} style={{
                marginBottom: '1rem'
              }}>Ratings</Button>
                <Collapse isOpen={this.state.collapse}>
              <RatingList ratings={this.state.ratings}></RatingList>
            </Collapse>
          </div>);
  }
}
const myF = link => Number(link.replace("http://localhost:8080/api/ratings/", ""));

class RatingList extends React.Component {
  render() {
    //add key back in later.
    const ratings = this.props.ratings.map(rating => <Rating rating={rating} backlink={rating.ratingID}/>); //Replace these with a reactstrap version.?
    return (<ListGroup>
      {ratings}
    </ListGroup>)
  }
}

class Rating extends React.Component {
  render() {
    return (<ListGroupItem>
      <ListGroupItemHeading>Rating: {this.props.rating.rating}</ListGroupItemHeading>
      <ListGroupItemText>
        {this.props.rating.comment}
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
*/
export default RatingDropdown;
