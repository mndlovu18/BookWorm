// set up Express
var express = require("express");
var app = express();

// set up BodyParser
var bodyParser = require("body-parser");
app.use(bodyParser.urlencoded({ extended: true }));

// import the Post class from Post.js
var Post = require("./Post.js");

app.use("/public", express.static("public"));

app.use("/", (req, res) => {
  res.redirect("/public/postform.html");
});

app.listen(3000, () => {
  console.log("Listening on port 3000");
});
