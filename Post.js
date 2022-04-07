var mongoose = require("mongoose");

mongoose.connect("mongodb://localhost:27017/posts");
var Schema = mongoose.Schema;

var postSchema = new Schema({
  title: { type: String, required: true },
  name: { type: String, required: true },
  content: { type: String, required: true },
  //assign a timestamp to each post
  created: { type: Date, default: Date.now },
  //assign an id to each post
  _id: { type: Number, required: true },
});

//export the Post class
module.exports = mongoose.model("Post", postSchema);
