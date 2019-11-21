package com.example.houseclean;

import android.os.Parcel;
import android.os.Parcelable;

public class Cliente implements Parcelable {

    public String ID;
    public String nome;
    public String cpf;

    public Cliente() {

    }

    protected Cliente(Parcel in) {
        ID = in.readString();
        nome = in.readString();
        cpf = in.readString();
    }

    public static final Parcelable.Creator<Cliente> CREATOR = new Parcelable.Creator<Cliente>() {
        @Override
        public Cliente createFromParcel(Parcel in) { return new Cliente(in);
        }

        @Override
        public Cliente[] newArray(int size) {return new Cliente[size];

    }

};

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
