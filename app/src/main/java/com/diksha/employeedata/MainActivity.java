package com.diksha.employeedata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.diksha.employeedata.ModelClass.EmployeeModel;
import com.diksha.employeedata.ModelClass.Maindata;
import com.diksha.employeedata.RoomDB.UserDB;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity {
    private static final int FILE_SELECT_CODE = 0;
    private RecyclerView recyclerView;
    LinearLayout linearLayout;
    SwipeRefreshLayout mSwipeRefreshLayout;
    String url="https://bbf2a516-7989-4779-a5bf-ecb2777960c4.mock.pstmn.io/v1/dev/t1/employee/";
    private List<Object> recyclerViewItems2 = new ArrayList<>();
    Button jsonb;
    UserDB userDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSwipeRefreshLayout = findViewById(R.id.pullToRefresh);
        linearLayout = findViewById(R.id.nointernet);
        jsonb = findViewById(R.id.jsonfile);
        recyclerView=findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//         userDB = Room.databaseBuilder(this
//                , UserDB.class
//                , "MyDB").build();

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(mSwipeRefreshLayout.isRefreshing()) {
                            if(isNetworkConnected()){
                                getrespondse();
                                mSwipeRefreshLayout.setRefreshing(true);
                                interntlayout();

                            }else{

                                nointerntlayout();
                                Toast.makeText(getApplicationContext(), "Please check your connection", LENGTH_SHORT).show();
                                mSwipeRefreshLayout.setRefreshing(false);

                            }
                        }
                    }
                }, 1000);
            }
        });

        jsonb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();
            }
        });

        if(isNetworkConnected()){
            interntlayout();
            getrespondse();

        }else{
             nointerntlayout();
//            Executors.newSingleThreadExecutor().execute(new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                final List<Maindata> petsSoFar = userDB.userDAO().getUsers().get(0).getBanner1();
//                                                                EmployeeAdapter employeeAdapter = new EmployeeAdapter(MainActivity.this, petsSoFar,"live");
//                                                                recyclerView.setAdapter(employeeAdapter);
//                                                            }
//                                                        });
            Toast.makeText(this, "Please check your connection", LENGTH_SHORT).show();
        }

    }

    private void interntlayout() {
        linearLayout.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }
    private void nointerntlayout() {
        linearLayout.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    private void getrespondse() {
        mSwipeRefreshLayout.setRefreshing(true);

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JSONHolder jsonHolder=retrofit.create(JSONHolder.class);
        Call<EmployeeModel> call= jsonHolder.getEmployeeModel();
        call.enqueue(new Callback<EmployeeModel>() {
            @Override
            public void onResponse(Call<EmployeeModel> call, Response<EmployeeModel> response) {
                if(response.isSuccessful()) {

                    List<Maindata> employeeModels = response.body().getBanner1();
                 //   List<Maindatadb> employeeModelsdb = response.body().getBanner2();
                    EmployeeAdapter employeeAdapter = new EmployeeAdapter(MainActivity.this, employeeModels,"live");
                    recyclerView.setAdapter(employeeAdapter);
//                    Executors.newSingleThreadExecutor().execute(new Runnable() {
//                        @Override
//                        public void run() {
//                            userDB.userDAO().insertUser(new EmployeeModel(employeeModeldb));
//                        }
//                    });
                }
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<EmployeeModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), LENGTH_SHORT).show();
            }


        });

    }


    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }


    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }
    }
    public String getRealPathFromURI(MainActivity context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                     String path=getRealPathFromURI(this,uri);
                     String filename=path.substring(path.lastIndexOf("/")+1);
                    ReadFile(filename);
                    Toast.makeText(this, filename, LENGTH_SHORT).show();

                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    public void ReadFile(String path) {
        Gson gson = new Gson();
        String text = "";
        try {
            String yourFilePath = getApplicationContext().getFilesDir() + "/" + path;
            File yourFile = new File(yourFilePath);
            InputStream inputStream = new FileInputStream(yourFile);
            StringBuilder stringBuilder = new StringBuilder();

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                while ((receiveString = bufferedReader.readLine()) != null){
                    stringBuilder.append(receiveString);
                }
                inputStream.close();
                text = stringBuilder.toString();
                Parsetest(text);
                Toast.makeText(this, "READ"+text, LENGTH_SHORT).show();
                Log.d("TAG",text);
            }
        } catch (FileNotFoundException e) {
            //Log your error with Log.e
        } catch (IOException e) {
            //Log your error with Log.e
        }
    }

    private void Parsetest(String text) {

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(text);

            Gson gson = new Gson();
            Type listType = new TypeToken<List<Maindata>>() {
            }.getType();
            List<Maindata> popModel = gson.fromJson(jsonObject.getString("data"), listType);

            EmployeeAdapter employeeAdapter = new EmployeeAdapter(MainActivity.this, popModel,"live");
            recyclerView.setAdapter(employeeAdapter);
            employeeAdapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}