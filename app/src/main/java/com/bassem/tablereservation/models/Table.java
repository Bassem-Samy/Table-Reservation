package com.bassem.tablereservation.models;

import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Bassem Samy on 4/22/2017.
 */

public class Table extends RealmObject {
    @PrimaryKey
    private int id;

    public Table() {
    }

    public Table(int id, boolean val) {
        this.id = id;
        setAvailable(val);
        setOriginallyAvailable(val);
    }

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

    private boolean isOriginallyAvailable;


    public boolean isOriginallyAvailable() {
        return isOriginallyAvailable;
    }

    public void setOriginallyAvailable(boolean originallyAvailable) {
        isOriginallyAvailable = originallyAvailable;
    }
}
