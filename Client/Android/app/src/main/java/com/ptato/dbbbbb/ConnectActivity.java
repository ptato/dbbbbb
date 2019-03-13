package com.ptato.dbbbbb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ConnectActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
        final TextView hostInput = findViewById(R.id.hostInput);
        final TextView portInput = findViewById(R.id.portInput);
        final TextView userInput = findViewById(R.id.userInput);
        final TextView passInput = findViewById(R.id.passInput);
        final Button connectButton = findViewById(R.id.connectButton);
        connectButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                CharSequence host = hostInput.getText();
                CharSequence port = portInput.getText();
                CharSequence user = userInput.getText();
                CharSequence pass = passInput.getText();

                ArrayList<String> fileNames = new ArrayList<>();
                try
                {
                    URL url = new URL(host + ":" + port + "/static/md.json");
                    HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                    urlConnection.setRequestProperty("Authorization", "Basic " + user + ":" + pass);

                    BufferedReader bufferedReader =
                            new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    while (true)
                    {
                        String line = bufferedReader.readLine();
                        if (line == null) break;
                        stringBuilder.append(line);
                    }

                    if (urlConnection.getResponseCode() != 200)
                    {
                        Log.e(this.getClass().getSimpleName(),
                                "HTTP Error " + urlConnection.getResponseCode());
                    }

                    String stringResponse = stringBuilder.toString();
                    JSONObject jsonResult = new JSONObject(stringResponse);
                    JSONArray root = jsonResult.optJSONArray("root");
                    if (root != null)
                    {
                        for (int i = 0; i < root.length(); i++)
                        {
                            fileNames.add(root.getString(i));
                        }
                    }
                } catch (IOException|JSONException e)
                {
                    Log.w(this.getClass().getSimpleName(), e);
                }

                Intent startBrowse = new Intent(ConnectActivity.this, BrowseFilesActivity.class);
                startBrowse.putStringArrayListExtra("filenames", fileNames);
                ConnectActivity.this.startActivity(startBrowse);
            }
        });
    }
}
