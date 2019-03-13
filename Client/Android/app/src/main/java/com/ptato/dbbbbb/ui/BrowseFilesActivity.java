package com.ptato.dbbbbb.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ptato.dbbbbb.data.ConnectionParameters;
import com.ptato.dbbbbb.data.Repository;

public class BrowseFilesActivity extends AppCompatActivity
{
    public static final String CONNECTION_PARAMETERS_EXTRA = "ConnectionParameters";

    private BrowseFilesViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState)
    {
        super.onCreate(savedInstanceState, persistentState);

        Repository repository = Repository.getInstance(this);

        Intent intent = getIntent();
        ConnectionParameters cp = (ConnectionParameters)intent.getSerializableExtra(CONNECTION_PARAMETERS_EXTRA);

        AppViewModelFactory factory = new AppViewModelFactory(repository, cp);
        viewModel = ViewModelProviders.of(this, factory).get(BrowseFilesViewModel.class);
    }
}
