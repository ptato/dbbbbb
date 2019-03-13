package com.ptato.dbbbbb.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ptato.dbbbbb.R;
import com.ptato.dbbbbb.data.ConnectionParameters;
import com.ptato.dbbbbb.data.Repository;

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

        Repository repository = Repository.getInstance(this);
        AppViewModelFactory factory = new AppViewModelFactory(repository, new ConnectionParameters("","","",""));
        viewModel = ViewModelProviders.of(this, factory).get(ConnectViewModel.class);
        viewModel.getConnectionParameters().observe(this, new Observer<ConnectionParameters>()
        {
            @Override
            public void onChanged(@Nullable ConnectionParameters connectionParameters)
            {
                if (connectionParameters != null)
                {
                    hostInput.setText(connectionParameters.host);
                    portInput.setText(connectionParameters.port);
                    userInput.setText(connectionParameters.user);
                    passInput.setText(connectionParameters.pass);
                }
            }
        });

        connectButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                CharSequence host = hostInput.getText();
                CharSequence port = portInput.getText();
                CharSequence user = userInput.getText();
                CharSequence pass = passInput.getText();
                viewModel.setConnectionParameters(new ConnectionParameters(
                        host.toString(), port.toString(), user.toString(), pass.toString()));

                Intent startBrowse = new Intent(ConnectActivity.this, BrowseFilesActivity.class);
                startBrowse.putExtra(
                        BrowseFilesActivity.CONNECTION_PARAMETERS_EXTRA,
                        viewModel.getConnectionParameters().getValue());
                ConnectActivity.this.startActivity(startBrowse);
            }
        });
    }
}
