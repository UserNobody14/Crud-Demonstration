/*import {
  BrowserRouter as Router,
  Route,
  Link,
  Redirect,
  withRouter
} from "react-router-dom";
import {
  Button,
  Card,
  Container,
  Col,
  FormGroup,
  Label,
  Input,
  Form
} from 'reactstrap';
import React, { Component } from "react";
import Auth from '../Auth';

//Add a title screen into base?

class RatingForm extends React.Component {
  render() {
    //add key back in later.
    const ratings = this.props.ratings.map(rating => <Rating rating={rating} key={getRatingKey(rating.ratingID)}/>); //Replace these with a reactstrap version.?
    return (
      <div>
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

const EditOwnRating = withRouter(
  ({ history }) =>
    Auth.isUserAuthenticated() ? (
      <Card>
        Welcome!{" "}
        <Button
          onClick={() => {
            Auth.deauthenticateUser();
            history.push("/");
          }}>
          Sign out
        </Button>
      </Card>
    ) : (
      <Card>You are not logged in.</Card>
    )
);

export default EditOwnRating; */
