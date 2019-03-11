import React from 'react';
const MainApp = require('./MainApp.js').default;
import ReactDom from 'react-dom';
import '!style-loader!css-loader!bootstrap/dist/css/bootstrap.min.css';
console.log("blah")
ReactDom.render((
    <MainApp />), document.getElementById('react'));
