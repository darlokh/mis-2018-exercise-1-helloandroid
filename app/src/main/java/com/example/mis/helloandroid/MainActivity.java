package com.example.mis.helloandroid;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.webkit.URLUtil;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.net.HttpURLConnection;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button myTextViewButton = findViewById(R.id.button);
        final Button myWebViewButton = findViewById(R.id.button2);

        myTextViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTextView(v);
            }
        });

        myWebViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWebView(v);
            }
        });
    }

    public void showWebView(View view) {
        final Activity activity = this;
        final WebView myWebView = findViewById(R.id.aWebView);
        final EditText myEditView = findViewById(R.id.editText);

        Toast toast = Toast.makeText(MainActivity.this, "This is not a valid URL.", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0 ,0);

        String inputUrl = myEditView.getText().toString();

        if(URLUtil.isValidUrl(inputUrl) && !inputUrl.isEmpty() ) {
            // followed this guide to show HTML-content
            // https://developer.android.com/guide/webapps/webview.html
            myWebView.setWebViewClient(new WebViewClient() {
                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                    Toast.makeText(activity, "Oh no! " + description, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            toast.show();
        }
        myWebView.setVisibility(view.VISIBLE);
        myWebView.loadUrl(inputUrl);
    }

    public void showTextView(View view) {
        final TextView myTextView = findViewById(R.id.textView);
        final EditText myEditView = findViewById(R.id.editText);
        final WebView myWebView = findViewById(R.id.aWebView);

        Toast toast = Toast.makeText(MainActivity.this, "This is not a valid URL.", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0 ,0);

        // if webView is overlapping textView make it 'gone'
        myWebView.setVisibility(view.GONE);

        // followed this guide to get the communication running
        // https://developer.android.com/training/volley/simple.html
        myTextView.setMovementMethod(new ScrollingMovementMethod());
        RequestQueue queue = Volley.newRequestQueue(this);
        String inputUrl = myEditView.getText().toString();

        if(URLUtil.isValidUrl(inputUrl) && !inputUrl.isEmpty() ) {
            //fill myTextView accordingly to the response
            StringRequest inputUrlRequest = new StringRequest(Request.Method.GET, inputUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            myTextView.setText(response.substring(0));
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            NetworkResponse networkResponse = error.networkResponse;
                            if(networkResponse != null && networkResponse.statusCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                                myTextView.setText(error.networkResponse.statusCode);
                            } else {
                                Toast toast = Toast.makeText(MainActivity.this, "No response from this URL.", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0 ,0);
                                toast.show();
                            }
                        }
                    }
            );
            queue.add(inputUrlRequest);
        } else {
            toast.show();
        }
    }
}
