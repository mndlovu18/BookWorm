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
  console.log(req.headers['content-type']);
  //find all posts in the database
  Post.find({}, (err, posts) => {
    if (err) {
      res.type("html").status(200);
      res.write("uh oh: " + err);
      res.write(err);
    } else {
      // if (posts.length === 0) {
      //   res.type("html").status(200);
      //   res.write("There are no posts in the database");
      //   res.end();
      //   return;
      // } else {
        // res.type("html").status(200);
        // res.write("Here are all the posts in the database: ");
        // res.write("<ul>");
        //show each post in the database
        
        res.format({
          text () {
            res.send('hey')z
          },
        
          html : function () {
            res.render('viewPosts',{posts:posts});
          },
        
          json : function () {
            res.json(posts);
          }
        })
        // posts.forEach((post) => {
        //   res.render('postView',{ title:post.title});
          // res.write("<h3> Book title: " + post.title + "</h3>");
          // res.write("<p> Author: " + post.name + "</p>");
          // res.write("<p> Summary: " + post.content + "</p>");
          // res.write("<p> Date posted: " + post.created + "</p>");
          // res.write("<p>" + "Post id: " + post._id + "</p>");
          // res.write('<a href="/delete?_id=' + post._id + '">delete</a>');
          // res.write('&nbsp <a href="/edit?_id=' + post._id + '">edit</a>');
          // res.write("<br>");
        // });
        // res.write("</ul>");
        // res.end();
      // }
    }
  });
});

//endpoint for delete
app.use("/delete", (req, res) => {
  let id = req.query._id;
  let deleted = Post.findByIdAndDelete(id).exec();
  deleted.then(data =>{
    console.log(deleted);
  // res.type("html").status(200);
  //     res.write(id + " has been deleted." );
  //     res.end();
  })
  //redirect to the homepage
  res.redirect("/posts");
});

//endpoint for edit
app.use("/edit", (req, res) => {
  let id = req.query._id;
  Post.findById(id).exec(function(err,post){
    if(post){
      res.render('edit', { title: post.title, content: post.content, author:post.name, _id:id })
    }
    else{
      console.log("Post not found");
    }
  });
});

//endpoint for submitting the edits
app.use("/submitEdit", (req,res) => {
  var filter = { 'title' : req.body.title };
  console.log(filter)
	var action = { '$set' : { 'content' : req.body.content } };
  console.log(action)

	Post.findOneAndUpdate( filter, action, (err, post) => {
		if (err) { 
		   res.json( { 'status' : err } ); 
		}
		else if (!post) {
		   res.json( { 'status' : 'no post' } ); 
		}
		else {
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
