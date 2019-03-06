import React, { Component } from 'react';
const client = require('./client');
import { Collapse, Button, CardBody, Card, ListGroup, ListGroupItem, ListGroupItemHeading, ListGroupItemText } from 'reactstrap';

class RatingDropdown extends Component {
  constructor(props) {
    super(props);
    this.toggle = this.toggle.bind(this);
    this.state = { collapse: false, ratings: []};
  }
  componentDidMount() {
		client({method: 'GET', path: '/api/properties/' + this.props.backlink + '/ratings'}).done(response => {
			this.setState({properties: response.entity._embedded.properties});
		});
	}

  toggle() {
    this.setState(state => ({ collapse: !state.collapse, ratings: this.state.ratings }));
  }

  render() {
    return (
      <div>
        <Button color="primary" onClick={this.toggle} style={{ marginBottom: '1rem' }}>Ratings</Button>
        <Collapse isOpen={this.state.collapse}>
          <RatingList ratings={this.state.ratings}>
          </RatingList>
        </Collapse>
      </div>
    );
  }
}
const myF = link => Number(link.replace("http://localhost:8080/api/ratings/", ""));

class RatingList extends React.Component{
	render() {
		const ratings = this.props.ratings.map(rating =>
			<Rating key={rating._links.self.href} rating={rating} backlink={myF(rating._links.self.href)}/>
		);//Replace these with a reactstrap version.?
		return (
			<ListGroup>
					{ratings}
			</ListGroup>
		)
	}
}

class Rating extends React.Component {
  render() {
    return (
        <ListGroupItem>
          <ListGroupItemHeading>Rating: {this.props.rating.rating}</ListGroupItemHeading>
          <ListGroupItemText>
          {this.props.rating.comment}
          </ListGroupItemText>
        </ListGroupItem>
    );
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
