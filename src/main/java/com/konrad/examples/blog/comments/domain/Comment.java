package com.konrad.examples.blog.comments.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Comment {
    public String postId;
    public String name;
    public String date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZZ").format(new Date());
    public String text;
    public String email;
    public String ip;

    public Comment() {

    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }


}
