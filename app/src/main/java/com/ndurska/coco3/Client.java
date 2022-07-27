package com.ndurska.coco3;

import java.io.Serializable;
import java.util.Arrays;

public class Client implements Serializable {
    int clientId;
    String name;
    String adjective;
    String breed;
    int ownerId;
    String[] notes;

    public Client(int clientId, String name, String adjective, String breed, int ownerId) {
        this.clientId = clientId;
        this.name = name;
        this.adjective = adjective;
        this.breed = breed;
        this.ownerId = ownerId;
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
        return name + adjective + breed;
    }
}
