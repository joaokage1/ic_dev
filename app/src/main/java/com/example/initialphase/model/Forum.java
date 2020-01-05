package com.example.initialphase.model;

import com.google.firebase.database.ServerValue;

public class Forum {

    private String title, description, userID, userPhoto, forumKey;
    private Object timestamp;

    public Forum(String title, String description, String userID, String userPhoto) {
        this.title = title;
        this.description = description;
        this.userID = userID;
        this.userPhoto = userPhoto;
        this.timestamp = ServerValue.TIMESTAMP;
    }

    public Forum(){

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getForumKey() {
        return forumKey;
    }

    public void setForumKey(String forumKey) {
        this.forumKey = forumKey;
    }

    public Object getTimestamp() {
        return timestamp;
    }

}
