package com.diksha.employeedata.RoomDB;


import androidx.room.TypeConverter;

import com.diksha.employeedata.ModelClass.Maindatadb;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;


public class Converters {
    @TypeConverter
    public static List<Maindatadb> fromTimestamp(String value) {
        Type listType = new TypeToken<List<Maindatadb>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String arraylistToString(List<Maindatadb> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}
