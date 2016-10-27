package com.givemepass.allenwu.firenews.model;

/**
 * Created by Admin on 2016/9/21.
 */
public class Member {
    String name;
    String address;
    int id;
    boolean gender;

    public Member(String name, String address, int id, boolean gender) {
        this.name = name;
        this.address = address;
        this.id = id;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean getGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }
}
