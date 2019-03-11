'use strict'
import React, {
  PropTypes
} from 'react';
// 'react-hot-loader/root';
import Auth from '../Auth';
import LoginForm from '../Components/LoginForm.jsx';

import { Redirect } from 'react-router-dom'
/*
class Login extends Component {
  state = { redirectToReferrer: false };

  login = () => {
    fakeAuth.authenticate(() => {
      this.setState({ redirectToReferrer: true });
    });
  };

  render() {
    let { from } = this.props.location.state || { from: { pathname: "/" } };
    let { redirectToReferrer } = this.state;

    if (redirectToReferrer) return <Redirect to={from} />;

    return (
      <div>

        <button onClick={this.login}>Log in</button>
      </div>
    );
  }
}
*/

class LoginPage extends React.Component {

  /**
   * Class constructor.
   */
  state = {
      errors: {},
      successMessage: 'Success!',
      user: {username: '',
      password: ''},
    redirectToReferrer: false
  }

   login = (event) => {
     event.preventDefault();
     //this.setState()
     this.setState({redirectToReferrer: Auth.authenticateUserName(this.state.user.username, this.state.user.password)});
   }
   //or should it be more like login = (event) => ...

   handleChange = (event) => {
    const name = event.target.name;
    const user = this.state.user;
		user[name] = event.target.value;
    this.setState({
      user
    });
   }
   //figure out how to add this somewhere at the top if the user came from trying to do something they shouldn't.
   //also: consider making it so the
   //<p>You must log in to view the page at {from.pathname}</p>
  render() {
    let { from } = this.props.location.state || { from: { pathname: "/" } };
    let { redirectToReferrer } = this.state;

    if (redirectToReferrer) return <Redirect to={from} />;
    return ( <
      LoginForm onSubmit = {
        this.login
      }
      onChange = {
        this.handleChange
      }
      errors = {
        this.state.errors
      }
      successMessage = {
        this.state.successMessage
      }
      user = {
        this.state.user
      }
      />
    );
  }
}
// LoginPage.contextTypes = {
//   router: PropTypes.object.isRequired
// };

export default LoginPage;
