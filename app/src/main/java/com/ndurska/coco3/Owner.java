package com.ndurska.coco3;

public class Owner {
    private int ownerId;
    private int phoneNumber;
    private int surname;

    public Owner(int ownerId, int phoneNumber) {
        this.ownerId = ownerId;
        this.phoneNumber = phoneNumber;

    }

    public Owner() {
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setSurnameNumber(int surname) {
        this.surname = surname;
    }

    public int getSurnameNumber() {
        return phoneNumber;
    }
}
