package com.project.courier_app.classes;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
public class SocketIO {
    private static Socket mSocket;
    public static Socket getSocket()
    {
        if(mSocket!= null && mSocket.isActive())
            return mSocket;
        try {
             mSocket = IO.socket(RetrofitBase.BASE_URL);
            mSocket.connect();
        } catch ( URISyntaxException e) {}
        return mSocket;
    }


}
