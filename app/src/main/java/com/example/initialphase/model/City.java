package com.example.initialphase.model;

import com.google.firebase.database.ServerValue;

public class City {

    private String name, cityFlag, countryFlag, cityKey;
    private String bairro, contatos, curiosidades, custo, transporte, universidades, photo, lugares;
    private Object timestamp;

    public City(String name, String cityFlag, String countryFlag) {
        this.name = name;
        this.cityFlag = cityFlag;
        this.countryFlag = countryFlag;
        this.timestamp = ServerValue.TIMESTAMP;
    }

    public City(String name, String cityFlag, String countryFlag, String bairro, String contatos, String curiosidades, String custo, String transporte, String universidades, String photo, String lugares) {
        this.name = name;
        this.cityFlag = cityFlag;
        this.countryFlag = countryFlag;
        this.bairro = bairro;
        this.contatos = contatos;
        this.curiosidades = curiosidades;
        this.custo = custo;
        this.transporte = transporte;
        this.universidades = universidades;
        this.photo = photo;
        this.timestamp = ServerValue.TIMESTAMP;
        this.lugares = lugares;
    }

    public City() {
    }

    public String getLugares() {
        return lugares;
    }

    public void setLugares(String lugares) {
        this.lugares = lugares;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getContatos() {
        return contatos;
    }

    public void setContatos(String contatos) {
        this.contatos = contatos;
    }

    public String getCuriosidades() {
        return curiosidades;
    }

    public void setCuriosidades(String curiosidades) {
        this.curiosidades = curiosidades;
    }

    public String getCusto() {
        return custo;
    }

    public void setCusto(String custo) {
        this.custo = custo;
    }

    public String getTransporte() {
        return transporte;
    }

    public void setTransporte(String transporte) {
        this.transporte = transporte;
    }

    public String getUniversidades() {
        return universidades;
    }

    public void setUniversidades(String universidades) {
        this.universidades = universidades;
    }

    public String getCityKey() {
        return cityKey;
    }

    public void setCityKey(String cityKey) {
        this.cityKey = cityKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCityFlag() {
        return cityFlag;
    }

    public void setCityFlag(String cityFlag) {
        this.cityFlag = cityFlag;
    }

    public String getCountryFlag() {
        return countryFlag;
    }

    public void setCountryFlag(String countryFlag) {
        this.countryFlag = countryFlag;
    }

    public Object getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Object timestamp) {
        this.timestamp = timestamp;
    }
}
