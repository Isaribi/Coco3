package com.ndurska.coco3;

public class Owner {
    int ownerId;
    int phoneNumber;

    public Owner(int ownerId, int phoneNumber) {
        this.ownerId = ownerId;
        this.phoneNumber = phoneNumber;
    }

    public Owner() {
    }

    public int getOwnerId() {
        return ownerId;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }
}
