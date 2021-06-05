package com.diksha.employeedata.RoomDB;



import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.diksha.employeedata.ModelClass.Maindatadb;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

/**
 * Created by AB on 7/12/2017.
 */

@Dao
public interface UserDAO {

    @Insert(onConflict = REPLACE)
    void insertUser(Maindatadb user);

    @Query("SELECT * FROM user")
    List<Maindatadb> getUsers();


}
