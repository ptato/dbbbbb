package com.ptato.dbbbbb.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ptato.dbbbbb.data.ConnectionParameters;

public class BrowseFilesActivity extends AppCompatActivity
{
    public static final String CONNECTION_PARAMETERS_EXTRA = "ConnectionParameters";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState)
    {
        super.onCreate(savedInstanceState, persistentState);
        Intent intent = getIntent();
        ConnectionParameters cp = (ConnectionParameters)intent.getSerializableExtra(CONNECTION_PARAMETERS_EXTRA);
    }
}
