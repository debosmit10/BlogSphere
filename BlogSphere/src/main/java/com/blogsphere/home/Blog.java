package com.blogsphere.home;

public class Blog {
	private int blogId;
	private String userId;
    private String username;
    private String pfp;
    private String topic;
    private String body;
    private String image;
    private String time;
    private int likes;

    // Getters and Setters
    public int getBlogId() {
        return blogId;
    }

    public void setBlogId(int blogId) {
        this.blogId = blogId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPfp() {
        return pfp;
    }

    public void setPfp(String profilePicture) {
        this.pfp = profilePicture;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topicName) {
        this.topic = topicName;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String bodyText) {
        this.body = bodyText;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String postImage) {
        this.image = postImage;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likeCount) {
        this.likes = likeCount;
    }
}
