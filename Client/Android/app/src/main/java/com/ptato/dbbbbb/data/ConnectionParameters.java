package com.ptato.dbbbbb.data;

import java.io.Serializable;

public class ConnectionParameters implements Serializable
{
    public String host, port, user, pass;
    public ConnectionParameters(String host, String port, String user, String pass)
    {
        this.host = host;
        this.port = port;
        this.user = user;
        this.pass = pass;
    }
}
