package com.diksha.employeedata.RoomDB;//package com.diksha.employeedata;
//
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.content.SharedPreferences.Editor;
//import android.util.Log;
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//
//public class MyForumstorage {
//    public static final String PREFS_NAME = "MY_APP_DATA";
//    public static final String FAVORITES = "Employeee_data";
//
//
//
//    public void storeFavorites(Context context, List favorites) {
//        SharedPreferences settings;
//        Editor editor;
//        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
//        editor = settings.edit();
//        GsonBuilder builder = new GsonBuilder();
//        Gson sExposeGson = builder.create();
//        String jsonFavorites = sExposeGson.toJson(favorites);
//        editor.putString(FAVORITES, jsonFavorites);
//        editor.commit();
//    }
//
//    public ArrayList loadFavorites(Context context) {
//        SharedPreferences settings;
//        List favorites;
//        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
//        if (settings.contains(FAVORITES)) {
//            String jsonFavorites = settings.getString(FAVORITES, null);
//            GsonBuilder builder = new GsonBuilder();
//            Gson sExposeGson = builder.create();
//            Maindata[] favoriteItems = sExposeGson.fromJson(jsonFavorites, Maindata[].class);
//            favorites = Arrays.asList(favoriteItems);
//            favorites = new ArrayList(favorites);
//        } else
//            return null;
//        return (ArrayList) favorites;
//    }
//
//    public void addFavorite(Context context, List<Maindata> myModel) {
//        List favorites = loadFavorites(context);
//        if (favorites == null)
//            favorites = new ArrayList();
//        favorites.add(myModel);
//        storeFavorites(context, favorites);
//    }
//
//    public void removeall(Context context) {
//        ArrayList favorites = loadFavorites(context);
//        if (favorites != null) {
//            favorites.clear();
//            Log.d("mylog", "reached above savepref");
//            storeFavorites(context, favorites);
//        }
//    }
//}
