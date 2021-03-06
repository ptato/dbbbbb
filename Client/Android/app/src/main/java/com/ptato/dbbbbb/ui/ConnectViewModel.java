package com.ptato.dbbbbb.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.ptato.dbbbbb.data.ConnectionParameters;

public class ConnectViewModel extends ViewModel
{
    private MutableLiveData<ConnectionParameters> connectionData;

    public ConnectViewModel(ConnectionParameters cp)
    {
        connectionData = new MutableLiveData<>();
        connectionData.setValue(cp);
    }

    public LiveData<ConnectionParameters> getConnectionParameters()
    {
        return connectionData;
    }
    public void setConnectionParameters(ConnectionParameters cp)
    {
        connectionData.setValue(cp);
    }
}
