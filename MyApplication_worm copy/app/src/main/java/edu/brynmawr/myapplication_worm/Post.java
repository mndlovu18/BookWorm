package edu.brynmawr.myapplication_worm;

public class Post {
    private  Long id;
    private  String title;
    private  String name;
    private  String content;
    private  String created;

    public Post(Long id,  String title, String name, String content, String created) {
        this.id = id;
        this.title = title;
        this.name = name;
        this.content = content;
        this.created = created;
    }

    public  Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public  String getCreated() {
        return created;
    }

    public String toString() {
        String result = "";
        result += "id: " + id + "\n";
        result += "Title: " + title + "\n";
        result += "Name: " + name + "\n";
        result += "Content: " + content + "\n";
        result += "Created: " + created + "\n";
        return result;

    }

}
