// set up Express
var express = require("express");
var app = express();

// set up BodyParser
var bodyParser = require("body-parser");
app.use(bodyParser.urlencoded({ extended: true }));

// import the Person class from Person.js
var Post = require("./Post.js");
