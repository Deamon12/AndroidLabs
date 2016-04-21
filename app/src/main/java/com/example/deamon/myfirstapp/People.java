package com.example.deamon.myfirstapp;


public class People {

    private String name;
    private String gender;
    private int id;




    public People(String name) {
        this.name = name;
    }

    public People(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public People(int id, String name, String gender) {
        this.id = id;
        this.name = name;
        this.gender = gender;

    }

    //--Getter/Setters--//
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "People{" +
                "name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", id=" + id +
                '}';
    }
}
