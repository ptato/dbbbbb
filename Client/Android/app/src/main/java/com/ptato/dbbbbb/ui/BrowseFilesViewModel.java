package com.ptato.dbbbbb.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.ptato.dbbbbb.data.ConnectionParameters;
import com.ptato.dbbbbb.data.Repository;

import java.util.List;

public class BrowseFilesViewModel extends ViewModel
{
    private LiveData<List<String>> fileNamesData;

    public BrowseFilesViewModel(Repository repository, ConnectionParameters cp)
    {
        fileNamesData = repository.getAvailableFileNames(cp);
    }

    public LiveData<List<String>> getFileNames()
    {
        return fileNamesData;
    }
}
