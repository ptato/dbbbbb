package com.ptato.dbbbbb;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
    private ConnectViewModel viewModel;

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

        viewModel = ViewModelProviders.of(this).get(ConnectViewModel.class);
        viewModel.getHost().observe(this, new Observer<String>()
        {
            @Override
            public void onChanged(@Nullable String s)
            {
                hostInput.setText(s);
            }
        });
        viewModel.getPort().observe(this, new Observer<String>()
        {
            @Override
            public void onChanged(@Nullable String s)
            {
                portInput.setText(s);
            }
        });
        viewModel.getUser().observe(this, new Observer<String>()
        {
            @Override
            public void onChanged(@Nullable String s)
            {
                userInput.setText(s);
            }
        });
        viewModel.getPass().observe(this, new Observer<String>()
        {
            @Override
            public void onChanged(@Nullable String s)
            {
                passInput.setText(s);
            }
        });

        connectButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                CharSequence host = hostInput.getText();
                viewModel.setHost(host.toString());
                CharSequence port = portInput.getText();
                viewModel.setPort(port.toString());
                CharSequence user = userInput.getText();
                viewModel.setUser(user.toString());
                CharSequence pass = passInput.getText();
                viewModel.setPass(pass.toString());

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
                startBrowse.putStringArrayListExtra(BrowseFilesActivity.FILE_NAMES_EXTRA, fileNames);
                ConnectActivity.this.startActivity(startBrowse);
            }
        });
    }
}
