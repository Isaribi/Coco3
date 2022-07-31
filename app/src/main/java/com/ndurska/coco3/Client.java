package com.ndurska.coco3;

import java.io.Serializable;
import java.util.Arrays;

public class Client implements Serializable {
    private int clientId;
    private String name;
    private String adjective;
    private String breed;
    private String phoneNumber1;
    private String phoneNumber2;
    private String phoneNumberLabel1;
    private String phoneNumberLabel2;
    private int ownerId;
    private String[] notes;

    public Client(int clientId, String name, String adjective, String breed, int ownerId, String phoneNumber1,String phoneNumber2,String phoneNumberLabel1,String phoneNumberLabel2) {
        this.clientId = clientId;
        this.name = name;
        this.adjective = adjective;
        this.breed = breed;
        this.ownerId = ownerId;
        this.phoneNumber1 = phoneNumber1;
        this.phoneNumber2 = phoneNumber2;
        this.phoneNumberLabel1 = phoneNumberLabel1;
        this.phoneNumberLabel2 = phoneNumberLabel2;
    }

    public Client(String name) {
        this.name = name;
    }

    public Client(int clientId,String name) {
        this.clientId=clientId;
        this.name = name;

    }
    public Client() {
    }

    public int getClientId() {
        return clientId;
    }

    public String getName() {
        return name;
    }

    public String getAdjective() {
        return adjective;
    }

    public String getBreed() {
        return breed;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public String[] getNotes() {
        return notes;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAdjective(String adjective) {
        this.adjective = adjective;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public void setNotes(String[] notes) {
        this.notes = notes;
    }

    public String getPhoneNumber1() { return phoneNumber1; }

    public void setPhoneNumber1(String phoneNumber1) { this.phoneNumber1 = phoneNumber1; }

    public String getPhoneNumber2() { return phoneNumber2; }

    public void setPhoneNumber2(String phoneNumber2) { this.phoneNumber2 = phoneNumber2; }

    public String getPhoneNumberLabel1() { return phoneNumberLabel1;}

    public void setPhoneNumberLabel1(String phoneNumberLabel1) { this.phoneNumberLabel1 = phoneNumberLabel1;}

    public String getPhoneNumberLabel2() { return phoneNumberLabel2;}

    public void setPhoneNumberLabel2(String phoneNumberLabel2) { this.phoneNumberLabel2 = phoneNumberLabel2;}

    @Override
    public String toString() {
        return "Client{" +
                "clientId=" + clientId +
                ", name='" + name + '\'' +
                ", adjective='" + adjective + '\'' +
                ", breed='" + breed + '\'' +
                '}';
    }
    public String toStringShort() {
        return name + adjective + breed + phoneNumber1 +" "+ phoneNumber2;
    }
}
