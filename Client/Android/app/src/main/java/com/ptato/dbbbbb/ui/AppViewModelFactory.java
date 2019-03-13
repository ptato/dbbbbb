package com.ptato.dbbbbb.ui;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.ptato.dbbbbb.data.ConnectionParameters;
import com.ptato.dbbbbb.data.Repository;

public class AppViewModelFactory extends ViewModelProvider.NewInstanceFactory
{
    private Repository repository;
    private ConnectionParameters cp;

    public AppViewModelFactory(@NonNull Repository repository, @NonNull ConnectionParameters cp)
    {
        this.repository = repository;
        this.cp = cp;
    }

    @NonNull @Override @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass)
    {
        if (BrowseFilesViewModel.class.getName().equals(modelClass.getName()))
            return (T) new BrowseFilesViewModel(repository, cp);
        else
            return (T) new ConnectViewModel(null, null, null, null);
    }
}
