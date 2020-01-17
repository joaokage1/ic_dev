package com.example.initialphase.model;

import com.google.firebase.database.ServerValue;

public class Experiencia {

    private String nome, curso, desc, picture, userID, userPhoto, experienciaKey;
    private Object timestamp;

    public Experiencia(String nome, String curso, String desc, String picture, String userID, String userPhoto) {
        this.nome = nome;
        this.curso = curso;
        this.desc = desc;
        this.picture = picture;
        this.userID = userID;
        this.userPhoto = userPhoto;
        this.timestamp = ServerValue.TIMESTAMP;
    }

    public Experiencia(){

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
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

    public String getExperienciaKey() {
        return experienciaKey;
    }

    public void setExperienciaKey(String experienciaKey) {
        this.experienciaKey = experienciaKey;
    }

    public Object getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Object timestamp) {
        this.timestamp = timestamp;
    }
}
