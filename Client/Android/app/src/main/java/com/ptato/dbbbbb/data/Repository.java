package com.ptato.dbbbbb.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Repository
{
    // private AppDatabase database;
    private Repository(Context context)
    {
        // database = AppDatabase.getInstance(context);
    }

    private static final Object SINGLETON_LOCK = new Object();
    private static Repository instance;
    public static Repository getInstance(Context context)
    {
        if (instance == null)
        {
            synchronized (SINGLETON_LOCK)
            {
                instance = new Repository(context);
            }
        }
        return instance;
    }

    public LiveData<List<String>> getAvailableFileNames()
    {
        MutableLiveData<List<String>> result = new MutableLiveData<>();
        result.setValue(new ArrayList<String>());
        new GetAvailableFileNamesTask(result).execute();
        return result;
    }

    private static class GetAvailableFileNamesTask extends AsyncTask<Void, Void, List<String>>
    {
        private MutableLiveData<List<String>> liveData;
        public GetAvailableFileNamesTask(MutableLiveData<List<String>> ld)
        {
            liveData = ld;
        }

        @Override
        protected List<String> doInBackground(Void... voids)
        {
            ArrayList<String> fileNames = new ArrayList<>();
            String host = "";
            String port = "";
            String user = "";
            String pass = "";
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
            } catch (IOException | JSONException e)
            {
                Log.w(this.getClass().getSimpleName(), e);
            }

            return fileNames;
        }

        @Override
        protected void onPostExecute(List<String> strings)
        {
            super.onPostExecute(strings);
            liveData.setValue(strings);
        }
    }
}
