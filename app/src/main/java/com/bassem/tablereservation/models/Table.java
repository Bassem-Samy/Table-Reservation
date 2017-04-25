package com.bassem.tablereservation.models;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Bassem Samy on 4/22/2017.
 */

public class Table extends RealmObject implements Parcelable {
    @PrimaryKey
    private int id;

    // required for realm
    public Table() {
    }

    public Table(int id, boolean val) {
        this.id = id;
        setAvailable(val);
        setOriginallyAvailable(val);
    }

    public Table(Table t) {
        this.id = t.id;
        this.available = t.available;
        this.isOriginallyAvailable = t.isOriginallyAvailable;
    }

    protected Table(Parcel in) {
        id = in.readInt();
        available = in.readByte() != 0;
        isOriginallyAvailable = in.readByte() != 0;
    }

    public static final Creator<Table> CREATOR = new Creator<Table>() {
        @Override
        public Table createFromParcel(Parcel in) {
            return new Table(in);
        }

        @Override
        public Table[] newArray(int size) {
            return new Table[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeByte((byte) (available ? 1 : 0));
        parcel.writeByte((byte) (isOriginallyAvailable ? 1 : 0));
    }
}
