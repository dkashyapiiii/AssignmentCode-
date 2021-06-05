package com.diksha.employeedata.RoomDB;



import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.diksha.employeedata.ModelClass.EmployeeModeldb;
import com.diksha.employeedata.ModelClass.Maindatadb;


@Database(entities = {EmployeeModeldb.class, Maindatadb.class},version = 2)
@TypeConverters({Converters.class})
public abstract class UserDB extends RoomDatabase {

    public abstract UserDAO userDAO();

}
