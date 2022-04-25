package com.sany.alphatech;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.NoCache;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;

import javax.xml.transform.ErrorListener;

public class MainActivity extends AppCompatActivity {
    String key= "aio_ldRB11HiNXqHEb1sfvrEai41TPUJ";
    ArrayList<Integer> datalist;
    ArrayAdapter<Integer> adapter;
    Handler handler=new Handler();
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        datalist=new ArrayList<>();
        adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,datalist);
        ListView listView=findViewById(R.id.display);
        listView.setAdapter(adapter);
        Cache cache=new NoCache();
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();
        runnable.run();
    }
    Runnable runnable=new Runnable() {
        @Override
        public void run() {

            String url="https://io.adafruit.com/api/v2/sanskar1001/feeds/tempfeed/data?x-aio-key="+key;
            JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    datalist.clear();
                    for(int i=0;i<response.length();i++)
                    {
                        try {
                            JSONObject obj=response.getJSONObject(i);
                            int data=obj.getInt("value");
                            Log.d("data",data+"");
                            datalist.add(data);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println(error.getMessage());
                }
            });
            jsonArrayRequest.setShouldCache(false);
            requestQueue.add(jsonArrayRequest);

            handler.postDelayed(this,1500);
        }
    };
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}