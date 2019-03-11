'use strict'
import React, { PropTypes } from 'react';
import { Card, CardTitle, CardText, CardSubtitle, CardBody } from 'reactstrap';
// 'react-hot-loader/root';


const Dashboard = ({ myProperties }) => (
  <Card className="container">
	    <CardBody>
          <CardTitle>HostDashboard</CardTitle>
          <CardSubtitle>Need authentication to get here.</CardSubtitle>
		  {myProperties && <CardText style={{ fontSize: '16px', color: 'green' }}>{myProperties}</CardText>}
        </CardBody>
  </Card>
);

// Dashboard.propTypes = {
//   secretData: PropTypes.string.isRequired
// };

export default Dashboard;
