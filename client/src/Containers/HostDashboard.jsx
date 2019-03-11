'use strict'
import React from 'react';
import Auth from '../Auth';
import Dashboard from '../Components/Dashboard.jsx';
// 'react-hot-loader/root';

class HostDashboard extends React.Component {

  /**
   * Class constructor.
   */
  constructor(props) {
    super(props);

    this.state = {
      myProperties: ''
    };
  }

  /**
   * This method will be executed after initial rendering.
   */
  componentDidMount() {
    //change to axios
    const xhr = new XMLHttpRequest();
    xhr.open('get', '/api/my-properties');
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    // set the authorization HTTP header
    xhr.setRequestHeader('Authorization', `Bearer ${Auth.getToken()}`);
    // note: my Auth token has bearer already in it.
    xhr.responseType = 'json';
    xhr.addEventListener('load', () => {
      if (xhr.status === 200) {
        this.setState({
          myProperties: xhr.response.message
        });
      }
    });
    xhr.send();
  }

  /**
   * Render the component.
   */
  render() {
    return (<Dashboard myProperties={this.state.myProperties} />);
  }

}

export default HostDashboard;
