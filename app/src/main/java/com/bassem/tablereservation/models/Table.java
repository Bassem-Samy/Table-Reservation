package com.bassem.tablereservation.models;

/**
 * Created by Mina Samy on 4/22/2017.
 */

public class Table {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;

    public boolean isReserved() {
        return isReserved;
    }

    public void setReserved(boolean reserved) {
        isReserved = reserved;
    }

    private boolean isReserved;
}
