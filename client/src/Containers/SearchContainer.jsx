'use strict'
const React = require('react');
//const ReactDOM = require('react-dom');
const axios = require('axios')
import { Button, Form, FormGroup, Label, Input, Card} from 'reactstrap';
// import LoginForm from './LoginForm.jsx';
// import injectTapEventPlugin from 'react-tap-event-plugin';
// import getMuiTheme from 'material-ui/styles/getMuiTheme';
// import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
// import { PropTypes } from 'react';
import Auth from '../Auth';
import PropertyContainer from './PropertyContainer.jsx'
import PropertyList from '../Components/PropertyList.jsx'
// 'react-hot-loader/root'
class SearchContainer extends React.Component {

  /**
   * Class constructor.
   */
  state = {
      searchString: '',
      properties: []
  }
  setPropertyElements = (responseElement) => {
		//console.log(JSON.stringify(responseElement))
		let dt = responseElement.data;
		let rt = (Array.isArray(dt)) ? dt : dt._embedded.properties;
		this.setState({properties: rt, searchString: this.state.searchString})
	}
	generalQuery = () => {
		const retData = {
      url: Auth.urlGet() + `/properties/search/${this.state.searchString}`,
      method: 'get',
      headers: Auth.authenticatedHeaders()
    };
		console.log(retData);
		return retData;
	}

  runSearch = (event) => {

    event.preventDefault();
    const valString = event.target.searchString.value;
    console.log(valString);
    console.log(this.state.searchString);
    const searchString = valString;
    this.setState({ searchString });
    console.log(this.state.searchString);
    axios(this.generalQuery()).then(this.setPropertyElements)
    // const value = event.target.value;
    // this.setState({[name]: value});
     //this.setState()
     //TODO figure out how to make it work via callbacks?
   }
   //or should it be more like login = (event) => ...

  handleChange = (event) => {
      const name = event.target.name;
      const value = event.target.value;
      this.setState({[name]: value});
  }
  render() {
    // let { from } = this.props.location.state || { from: { pathname: "/" } };
    // let { redirectToReferrer } = this.state;
    //
    // if (redirectToReferrer) return <Redirect to={from} />;
    return (
      <div>
        <Card>
          <Form inline onSubmit={this.runSearch} onChange={this.handleChange}>
            <FormGroup className="mb-2 mr-sm-2 mb-sm-0">
              <Label for="search" className="mr-sm-2">Search Properties</Label>
              <Input type="search" name="searchString" id="searchString" placeholder="search properties" />
            </FormGroup>
            <Button>Submit</Button>
          </Form>
        </Card>
        <PropertyList properties={this.state.properties} />
      </div>
    );
  }
}
//onChange = {this.handleChange}
//onChange={this.props.onChange}
// class SearchForm extends React.Component {
//
//   render() {
//     return (
//
//     );
//   }
// }
export default SearchContainer;
