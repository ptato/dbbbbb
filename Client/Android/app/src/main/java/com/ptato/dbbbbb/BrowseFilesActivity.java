package com.ptato.dbbbbb;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

public class BrowseFilesActivity extends AppCompatActivity
{
    public static final String FILE_NAMES_EXTRA = "filenames";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState)
    {
        super.onCreate(savedInstanceState, persistentState);
        Intent intent = getIntent();
        List<String> fileNames = intent.getStringArrayListExtra(FILE_NAMES_EXTRA);
    }
}
