/**
 * This is a simple nodejs server that exposes a json file as an API
 * To run this server, you need to have nodejs installed on your machine
 * To install nodejs, visit https://nodejs.org/en/download/
 * To run this server, open a terminal and navigate to the directory where this file is located
 * before running the server, you need to install the express module by running the following command:npm install express
 * Run the following command: node mockapi.js
 * This will start the server on port 3000
 * You can access the json file by navigating to http://localhost:3000/api/v1/users
 * This will return the contents of the users.json file
 */
var express = require('express');
var app = express();
//expose users.json file with nodejs as an API
app.get('/api/v1/usuarios', function (req, res) {
    res.sendFile(__dirname + '/users.json');
});
app.listen(3000, function () {
    console.log('Mock API listening on port 3000!');
});




