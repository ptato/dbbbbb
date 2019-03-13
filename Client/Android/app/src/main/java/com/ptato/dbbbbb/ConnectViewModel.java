package com.ptato.dbbbbb;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class ConnectViewModel extends ViewModel
{
    private MutableLiveData<String> hostData;
    private MutableLiveData<String> portData;
    private MutableLiveData<String> userData;
    private MutableLiveData<String> passData;

    public ConnectViewModel(String host, String port, String user, String pass)
    {
        hostData = new MutableLiveData<>();
        hostData.setValue(host);
        portData = new MutableLiveData<>();
        portData.setValue(port);
        userData = new MutableLiveData<>();
        userData.setValue(user);
        passData = new MutableLiveData<>();
        passData.setValue(pass);
    }

    public LiveData<String> getHost()
    {
        return hostData;
    }
    public LiveData<String> getPort()
    {
        return portData;
    }
    public LiveData<String> getUser()
    {
        return userData;
    }
    public LiveData<String> getPass()
    {
        return passData;
    }

    public void setHost(String s)
    {
        hostData.postValue(s);
    }
    public void setPort(String s)
    {
        portData.postValue(s);
    }
    public void setUser(String s)
    {
        userData.postValue(s);
    }
    public void setPass(String s)
    {
        passData.postValue(s);
    }
}
