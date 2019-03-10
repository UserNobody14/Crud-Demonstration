import React from 'react';
const MainApp = require('./MainApp.js');
import ReactDom from 'react-dom';
import '!style-loader!css-loader!bootstrap/dist/css/bootstrap.min.css';
const App = a => ReactDom.render((
    <MainApp />), document.getElementById('react'));

export default App;
