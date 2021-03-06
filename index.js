// set up Express
var express = require("express");
var app = express();

//load views using pug
app.set("views", "./views");
app.set("view engine", "pug");

// set up BodyParser
var bodyParser = require("body-parser");
app.use(bodyParser.urlencoded({ extended: true }));

// import the Post class from Post.js
var Post = require("./Post.js");
const { redirect } = require("express/lib/response");
/***************************************/

//endpoint for creating a new post from url
app.use("/create", (req, res) => {
  console.log(req.url);
  //create the new post
  var newPost = new Post({
    title: req.body.title,
    content: req.body.content,
    name: req.body.name,
    //assign a timestamp to each post
    created: Date.now(),
    //assign an id to each post
    _id: Math.floor(Math.random() * 1000000),
  });

  //save the new post to the database
  newPost.save((err) => {
    if (err) {
      res.type("html").status(200);
      res.write("uh oh: " + err);
      console.log(err);
      res.end();
    } else {
      //redirect to the homepage
      res.redirect("/posts");
    }
  });
});

//endpoin to create a new post from the form
app.use("/createpost", (req, res) => {
  console.log(req.url);
  //create the new post
  var newPost = new Post({
    title: req.query.title,
    content: req.query.content,
    name: req.query.name,
    //assign a timestamp to each post
    created: Date.now(),
    //assign an id to each post
    _id: Math.floor(Math.random() * 1000000),
  });

  //save the new post to the database
  newPost.save((err) => {
    if (err) {
      res.type("html").status(200);
      res.write("uh oh: " + err);
      console.log(err);
      res.end();
    } else {
      //redirect to the homepage
      res.redirect("/posts");
    }
  });
});

//endpoint for showing all posts
app.use("/posts", (req, res) => {
  console.log(req.headers["content-type"]);
  //find all posts in the database
  Post.find({}, (err, posts) => {
    if (err) {
      res.type("html").status(200);
      res.write("uh oh: " + err);
      res.write(err);
    } else {
      res.format({
        text() {
          res.send("hey");
        },

        html: function () {
          res.render("viewPosts", { posts: posts });
        },

        json: function () {
          res.json(posts);
        },
      });
    }
  });
});

//endpoint for delete
app.use("/delete", (req, res) => {
  let id = req.query._id;
  let deleted = Post.findByIdAndDelete(id).exec();
  deleted.then((data) => {
    console.log(deleted);
  });
  res.redirect("/posts");
});

//endpoint for edit
app.use("/edit", (req, res) => {
  let id = req.query._id;
  Post.findById(id).exec(function (err, post) {
    if (post) {
      res.render("edit", {
        title: post.title,
        content: post.content,
        author: post.name,
        _id: id,
      });
    } else {
      console.log("Post not found");
    }
  });
});

//endpoint for submitting the edits
app.use("/submitEdit", (req, res) => {
  var filter = { title: req.body.title };
  console.log(filter);
  var action = { $set: { content: req.body.content } };
  console.log(action);

  Post.findOneAndUpdate(filter, action, (err, post) => {
    if (err) {
      res.json({ status: err });
    } else if (!post) {
      res.json({ status: "no post" });
    } else {
      //redirect to the homepage
      res.redirect("/posts");
    }
  });
});

app.use("/public", express.static("public"));

app.use("/", (req, res) => {
  res.redirect("/public/postform.html");
});

app.listen(3000, () => {
  console.log("Listening on port 3000");
});
