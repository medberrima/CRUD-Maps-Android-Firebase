package com.dsi32.androidapp;

public class Resto {


    private String nom, location, services,  desc,imageResto ;
    private int rank;

    public Resto( String nom, String location, String services, int rank , String imageResto, String desc) {
        this.nom = nom;
        this.location = location;
        this.services = services;
        this.rank = rank;
       this.desc = desc;
       this.imageResto = imageResto;
    }
    public Resto() {

    }

    public String getImageResto() {
        return imageResto;
    }

    public void setImageResto(String imageResto) {
        this.imageResto = imageResto;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
