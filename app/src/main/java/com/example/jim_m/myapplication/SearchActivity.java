package com.example.jim_m.myapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;

public class SearchActivity extends AppCompatActivity {
    EditText Last_Name;
    Button Search;
    String last_name="";
    TextView res;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Search= (Button)findViewById(R.id.btnSearch);
        Last_Name= (EditText)findViewById(R.id.editLastName);
        res= (TextView)findViewById(R.id.textResult);
    }
    public void Search(View view){

        last_name = Last_Name.getText().toString();
        RequestParams params = new RequestParams();
        params.put("last_name", last_name);

        invokeWS(params);
    }
    public void invokeWS(RequestParams params) {

        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://employeesservice.azurewebsites.net/api/employees/GetemployeeByName/"+last_name, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
                String test="a";
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {


                try {
                    String str = new String(response, "UTF-8");
                    // JSON Object
                    JSONObject obj = new JSONObject(str);
                    // When the JSON response has status boolean value assigned with true


                        res.setText("Result Found : "+System.getProperty("line.separator")+
                                    "First Name: "+obj.get("First_Name")+ System.getProperty("line.separator")+
                                    "Last Name: "+obj.get("Last_Name")+ System.getProperty("line.separator")+
                                    "Address: "+ ( !obj.isNull("Address") ? obj.get("Address") : "")+ System.getProperty("line.separator")+
                                    "BirthDate: "+obj.get("birthdate"));

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
               res.setText("No results");
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
                String test="a";
            }
        });
    }
}
