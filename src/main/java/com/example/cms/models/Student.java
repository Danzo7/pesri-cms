package com.example.cms.models;

public class Student {
    private int id=-1 ,age,profID;
    private String fName;
    private String lName;

    public Student(int id, int age, int profID, String fName, String lName) {
        this.id = id;
        this.age = age;
        this.profID = profID;
        this.fName = fName;
        this.lName = lName;
    }
    // the id is Auto_increment //
    // please use same order  as first constructor next time =bad code :/.
    public Student( String fName, String lName,int age, int profID) {
        this.age = age;
        this.profID = profID;
        this.fName = fName;
        this.lName = lName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setProfID(int profID) {
        this.profID = profID;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public int getId() {
        return id;
    }

    public int getAge() {
        return age;
    }

    public int getProfID() {
        return profID;
    }

    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }


}
