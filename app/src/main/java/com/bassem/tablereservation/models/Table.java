package com.bassem.tablereservation.models;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Bassem Samy on 4/22/2017.
 */

public class Table implements RealmModel{
    @PrimaryKey
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private boolean available;

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

}
