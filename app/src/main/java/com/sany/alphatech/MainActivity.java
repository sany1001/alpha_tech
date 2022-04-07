package com.sany.alphatech;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

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

public class MainActivity extends AppCompatActivity {
    String data="";
    Handler handler=new Handler();
    Handler mhandler=new Handler();
    ProgressDialog progressDialog;
    TextView display;
    final String api_key="aio_vBGU54uGLUfVrDOX0TKUwpDEoH5i";
    int dt=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        display=findViewById(R.id.display);
        runnable.run();
        display.setText("Values : "+dt);
    }

    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            new fetch().start();
            mhandler.postDelayed(this,1000);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mhandler.removeCallbacks(runnable);
    }

    class fetch extends Thread{
        @Override
        public void run() {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    progressDialog=new ProgressDialog(MainActivity.this);
                    progressDialog.setMessage("Collecting data");
                    progressDialog.setCancelable(false);
                }
            });
            try {
                URL url=new URL("https://io.adafruit.com/api/v2/sanskar1001/feeds/tempfeed/data?x-aio-key="+api_key) ;
                HttpURLConnection cnnct= (HttpURLConnection) url.openConnection();
                InputStream input=cnnct.getInputStream();
                BufferedReader buffer=new BufferedReader(new InputStreamReader(input));
                String line="";
                while((line=buffer.readLine())!=null)
                {
                    data+=line;
                }
                if(data.length()!=0){
                    JSONArray arr=new JSONArray(data);
                    for(int i=0;i<arr.length();i++)
                    {
                        JSONObject obj=arr.getJSONObject(i);
                        dt=obj.getInt("value");
                        Log.d("getdata", "value: "+dt);
                    }
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if(progressDialog.isShowing())
                        progressDialog.dismiss();
                }
            });
        }
    }
}