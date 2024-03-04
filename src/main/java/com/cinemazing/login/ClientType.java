package com.cinemazing.login;

public enum ClientType {


    ADMINISTRATOR(1),
    COMPANY(2),
    CUSTOMER(3);

    private int id;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    ClientType(int id) {
        this.id = id;
    }
}
