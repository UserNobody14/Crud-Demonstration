import {
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
  Col
} from 'reactstrap';
import React, { Component } from "react";
import Auth from '../Auth';

const AuthCard = withRouter(
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

export default AuthCard;
