'use strict'
import React from 'react';
import Auth from '../Auth';
import Dashboard from '../Components/Dashboard.jsx';
import CreatePropertyContainer from './CreatePropertyContainer.jsx'
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

  }

  /**
   * Render the component.
   */
  render() {
    return (<CreatePropertyContainer />);
  }

}

export default HostDashboard;
