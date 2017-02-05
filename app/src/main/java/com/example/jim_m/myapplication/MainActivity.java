package com.example.jim_m.myapplication;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import cz.msebera.android.httpclient.Header;
import org.json.*;
import com.loopj.android.http.*;
import android.widget.Toast;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import java.io.UnsupportedEncodingException;


public class MainActivity extends AppCompatActivity {
    EditText First_Name;
    Button Post;
    EditText Last_Name;
    EditText Salary;
    EditText Address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        First_Name= (EditText)findViewById(R.id.edtTxtFirstName);
        Last_Name= (EditText)findViewById(R.id.edtLastName);
        Salary=(EditText)findViewById(R.id.editSalary);
        Address=(EditText)findViewById(R.id.editText3);
        Post= (Button)findViewById(R.id.btnPost);

    }
    public void Search(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);

    }

    public void Post(View view){
        String first_name = First_Name.getText().toString();
        String last_name = Last_Name.getText().toString();
        String salary = Salary.getText().toString();
        String address = Address.getText().toString();
        RequestParams params = new RequestParams();
        params.put("first_name", first_name);
        params.put("last_name", last_name);
        params.put("address", address);
        params.put("salary1", salary);

        invokeWS(params);

    }

    public void invokeWS(RequestParams params){

        AsyncHttpClient client = new AsyncHttpClient();
        client.post("http://employeesservice.azurewebsites.net/api/employees",params, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {


                    try {
                        String str = new String(response, "UTF-8");
                        // JSON Object
                        JSONObject obj = new JSONObject(str);
                        // When the JSON response has status boolean value assigned with true
                        if(obj.getBoolean("status")){
                            // Set Default Values for Edit View controls

                            // Display successfully registered message using Toast
                            Toast.makeText(getApplicationContext(), "You are successfully registered!", Toast.LENGTH_LONG).show();
                        }
                        // Else display error message
                        else{

                            Toast.makeText(getApplicationContext(), obj.getString("error_msg"), Toast.LENGTH_LONG).show();
                        }
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
