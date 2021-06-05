package com.diksha.employeedata.ModelClass;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

@Entity
public
class EmployeeModeldb implements Serializable {


//
//    public List<Maindata> getBanner1() {
//        return banner1;
//    }
//
//    public void setBanner1(List<Maindata> banner1) {
//        this.banner1 = banner1;
//    }
////
//    @SerializedName("data")
//    @Expose
//    private List<Maindata> banner1;

    public EmployeeModeldb(List<Maindatadb> banner2) {
        this.banner2 = banner2;
    }

    public List<Maindatadb> getBanner2() {
        return banner2;
    }

    public void setBanner2(List<Maindatadb> banner2) {
        this.banner2 = banner2;
    }

    @SerializedName("data")
    @Expose
    @PrimaryKey
    @NonNull
    private List<Maindatadb> banner2;

//
//    @Override
//    public String toString() {
//        return "EmployeeModel{" +
//
//                ", data=" + banner1 +
//
//                '}';
//    }
}
