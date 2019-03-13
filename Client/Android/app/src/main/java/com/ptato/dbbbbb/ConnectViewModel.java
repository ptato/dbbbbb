package com.ptato.dbbbbb;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.ptato.dbbbbb.data.ConnectionParameters;

public class ConnectViewModel extends ViewModel
{
    private MutableLiveData<ConnectionParameters> connectionData;

    public ConnectViewModel(String host, String port, String user, String pass)
    {
        /*
        hostData = new MutableLiveData<>();
        hostData.setValue(host);
        portData = new MutableLiveData<>();
        portData.setValue(port);
        userData = new MutableLiveData<>();
        userData.setValue(user);
        passData = new MutableLiveData<>();
        passData.setValue(pass);
        */
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
