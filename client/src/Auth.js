import axios from 'axios';

/*
    const username = encodeURIComponent(this.state.username);
		//console.log(username);
    const password = encodeURIComponent(this.state.password);
    const formData = `username=${username}&password=${password}`;
		const altData = JSON.stringify({username: username, password: password});
		/////////////////////////////////////////////////////////

		function authentify(authorization) {
			Auth.authenticateUser(authorization);
			return authorization;
		}
		fetch('/login', {
		  					method: 'POST',
		  					headers: {'Content-Type': 'application/json'},
		  body: JSON.stringify({
		    username: this.state.username,
		    password: this.state.password,
		  })
		}).then(checkStatus)
			.then(response => response.headers.get('Authorization'))
			.then(d => authentify(d))
			.then(function(data) {
					alert('A name was submitted: ' + username + '\nThis is the json web token: ' + data);
    			console.log('request succeeded with JSON response', data);
  		}).catch(function(error) {
    			console.log('request failed', error)
  		})
			//TODO: it should update the page.

      */
class Auth {
  static makeAuthenticatedRequest(postMethod, url, data) {
    //run the delivered config through axios with relevant token based authentication.
    return axios({data: data, url: url, method: postMethod,
      headers:
      {'Content-Type': 'application/json',
    'Authorization': Auth.getToken()}});

  }
  static authenticateUserName(username, password) {
    var z = true;
    axios({
      url: Auth.urlGet() + '/login',
      headers: {'Content-Type': 'application/json'},
    data: {username: username, password: password},
  method: 'post'})
    .then(response => {
      Auth.authenticateUser(response.headers.authorization);
      console.log(JSON.stringify(response.headers.authorization))
      console.log(response.headers);
      })
    .catch(() => {z = false});
    console.log(z);
    return z;
  }

  static authenticatedHeaders() {
    if (Auth.isUserAuthenticated()) {
      return {'Authorization': Auth.getToken(), 'Content-Type': 'application/json'};
    }
    else {
      return {'Content-Type': 'application/json'};
    }

  }

  static urlGet() {
    //return "http://localhost:8080";
    return "https://" + window.location.host;
  }

  // static makeAuthenticatedPost(url, data) {
  //   Auth.makeAuthenticatedPost('post', url, data)
  // }

  static authenticateThrough(token) {
    localStorage.setItem('token', token);
    return localStorage.getItem('token');
  }
  //TODO: make it set whether user is a host or a guest when they register?
  /**
   * Authenticate a user. Save a token string in Local Storage
   *
   * @param {string} token
   */
  static authenticateUser(token) {
    localStorage.setItem('token', token);
  }

  /**
   * Check if a user is authenticated - check if a token is saved in Local Storage
   *
   * @returns {boolean}
   */
  static isUserAuthenticated() {
    return localStorage.getItem('token') !== null;
  }

  static isUserHost() {
    return true;
  }

  /**
   * Deauthenticate a user. Remove a token from Local Storage.
   *
   */
  static deauthenticateUser() {
    localStorage.removeItem('token');
  }

  /**
   * Get a token value.
   *
   * @returns {string}
   */

  static getToken() {
    return localStorage.getItem('token');
  }

}

export default Auth;
