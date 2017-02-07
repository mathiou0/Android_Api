package com.example.jim_m.myapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.os.AsyncTask;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

public class UpdateActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();

    private ListView lv;
    ArrayList<HashMap<String, String>> employeeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        employeeList = new ArrayList<>();

        lv = (ListView)findViewById(R.id.list);

        GetEmployees();
    }
    public void GetEmployees() {

        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://employeesservice.azurewebsites.net/api/employees", new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {


                try {

                    String str = new String(response, "UTF-8");
                    JSONArray emmployees= new JSONArray(str);


                    for (int i = 0; i < emmployees.length(); i++) {
                        JSONObject c = emmployees.getJSONObject(i);

                        String f_n = c.getString("First_Name");
                        String l_n = c.getString("Last_Name");
                        String birthdate = c.getString("birthdate");
                        String address = c.getString("Address");
                        if(address=="null")
                            address="";

                        HashMap<String, String> employee = new HashMap<>();


                        employee.put("first_name", "First Name : " +f_n);
                        employee.put("last_name","Last Name : " + l_n);
                        employee.put("birthdate", "Birthdate : " + birthdate);
                        employee.put("address", "Address : " + address);


                        employeeList.add(employee);
                    }
                    ListAdapter adapter = new SimpleAdapter(
                            UpdateActivity.this, employeeList,
                            R.layout.content_update, new String[]{"first_name", "last_name",
                            "address","birthdate"}, new int[]{R.id.first_name,
                            R.id.last_name,R.id.address, R.id.birthdate});

                    lv.setAdapter(adapter);


                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
            }
        });
    }

}
