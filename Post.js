var mongoose = require("mongoose");

mongoose.connect("mongodb://localhost/posts");
var Schema = mongoose.Schema;

var postSchema = new Schema({
  title: String,
  content: String,
  name: { type: String, required: true },
  date: { type: Date, default: Date.now },
});

//export the Post class
module.exports = mongoose.model("Post", postSchema);
