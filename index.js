// set up Express
var express = require("express");
var app = express();

//load views using pug
app.set('views', './views')
app.set('view engine', 'pug')


// set up BodyParser
var bodyParser = require("body-parser");
app.use(bodyParser.urlencoded({ extended: true }));

// import the Post class from Post.js
var Post = require("./Post.js");
const { redirect } = require("express/lib/response");

/***************************************/

//endpoint for creating a new post
//this is the action of the "create new post" form
app.use("/create", (req, res) => {
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

//endpoint for showing all posts
app.use("/posts", (req, res) => {
  //find all posts in the database
  Post.find({}, (err, posts) => {
    if (err) {
      res.type("html").status(200);
      res.write("uh oh: " + err);
      res.write(err);
    } else {
      if (posts.length === 0) {
        res.type("html").status(200);
        res.write("There are no posts in the database");
        res.end();
        return;
      } else {
        res.type("html").status(200);
        res.write("Here are all the posts in the database: ");
        res.write("<ul>");
        //show each post in the database
        posts.forEach((post) => {
          res.write("<h3>" + post.title + "</h3>");
          res.write("<p>" + post.title + "</p>");
          res.write("<p>" + post.content + "</p>");
          res.write("<p>" + post.created + "</p>");
          res.write("<p>" + "id: " + post._id + "</p>");
          res.write('<a href="/delete?_id=' + post._id + '">delete</a>');
          res.write('&nbsp <a href="/edit?_id=' + post._id + '">edit</a>');
          res.write("<br>");
        });
        res.write("</ul>");
        res.end();
      }
    }
  });
});

//endpoint for delete
app.use("/delete", (req, res) => {
  let id = req.query._id;
  let deleted = Post.findByIdAndDelete(id).exec();
  deleted.then(data =>{
    console.log(deleted);
  res.type("html").status(200);
      res.write("This is the delete endpoint" + id + deleted.title);
      res.end();
  })
});

//endpoint for edit
app.use("/edit", (req, res) => {
  let id = req.query._id;
  let post = Post.findById(id).exec();
  res.render('edit', { title: 'Hey', message: 'Hello there!' })

});








app.use("/public", express.static("public"));

app.use("/", (req, res) => {
  res.redirect("/public/postform.html");
});

app.listen(3000, () => {
  console.log("Listening on port 3000");
});
