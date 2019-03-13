package com.ptato.dbbbbb.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ptato.dbbbbb.R;
import com.ptato.dbbbbb.data.ConnectionParameters;
import com.ptato.dbbbbb.data.Repository;

import java.util.ArrayList;
import java.util.List;

public class BrowseFilesActivity extends AppCompatActivity
{
    public static final String CONNECTION_PARAMETERS_EXTRA = "ConnectionParameters";

    private RecyclerView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState)
    {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_browse_files);
        recyclerView = findViewById(R.id.recycler_list_widget);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new BrowseFilesAdapter());

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        Repository repository = Repository.getInstance(this);

        Intent intent = getIntent();
        ConnectionParameters cp = (ConnectionParameters)intent.getSerializableExtra(CONNECTION_PARAMETERS_EXTRA);

        AppViewModelFactory factory = new AppViewModelFactory(repository, cp);
        BrowseFilesViewModel viewModel = ViewModelProviders.of(this, factory)
                .get(BrowseFilesViewModel.class);
        viewModel.getFileNames().observe(this, new Observer<List<String>>()
        {
            @Override
            public void onChanged(@Nullable List<String> strings)
            {
                updateUi(strings);
            }
        });
    }

    private void updateUi(List<String> fileNames)
    {
        BrowseFilesAdapter bfa = (BrowseFilesAdapter)recyclerView.getAdapter();
        if (bfa != null && fileNames != null)
        {
            bfa.fileNames = fileNames;
            bfa.notifyDataSetChanged();
        }
    }

    private class BrowseFilesAdapter extends RecyclerView.Adapter<BrowseFilesViewHolder>
    {
        public List<String> fileNames = new ArrayList<>();

        @NonNull @Override
        public BrowseFilesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
        {
            View fileLayoutView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.entry_file, viewGroup, false);
            return new BrowseFilesViewHolder(fileLayoutView);
        }
        @Override
        public void onBindViewHolder(@NonNull BrowseFilesViewHolder browseFilesViewHolder, int i)
        {
            browseFilesViewHolder.name.setText(fileNames.get(i));
        }
        @Override
        public int getItemCount()
        {
            return fileNames.size();
        }
    }

    private class BrowseFilesViewHolder extends RecyclerView.ViewHolder
    {
        public TextView name;
        public BrowseFilesViewHolder(View itemView)
        {
            super(itemView);
            name = itemView.findViewById(R.id.label_file_name);
        }
    }
}
