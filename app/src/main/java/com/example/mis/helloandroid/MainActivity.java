package com.example.mis.helloandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button myButton = findViewById(R.id.button);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foobar(v);
            }
        });
    }

    public void foobar(View view) {
        final TextView myView = (EditText) findViewById(R.id.editText);
        // followed this tutorial to get the communication running
        // https://developer.android.com/training/volley/simple.html
        RequestQueue queue = Volley.newRequestQueue(this);
        String inputUrl = (String) myView.getText();
        //String inputUrl = "http://www.google.de";
        StringRequest inputUrlRequest = new StringRequest(Request.Method.GET, inputUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        myView.setText(response.substring(0, 500));
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        myView.setText(error.getMessage());
                    }
                }
        );
        queue.add(inputUrlRequest);
    }

    public void helloWorld(View view) {
        Toast toast = Toast.makeText(MainActivity.this, "hey there", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0 ,0);
        toast.show();
        final TextView myView = (TextView) findViewById(R.id.textView);
        myView.setText(R.string.foobarText);
    }

}
