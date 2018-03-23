package com.android.mdw.demo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by jonsa on 22/03/2018.
 */

public class MyBroadcastReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        Intent sonido = new Intent(context, ElServicio.class);
        String select = intent.getExtras().getString("STOP");
        if (select.equals("false")) {
            int id = intent.getExtras().getInt("PLAY");
            String msn = intent.getExtras().getString("SOUND");
            sonido.putExtra("ID", id);
            sonido.putExtra("SOUND", msn);
            if (msn.equals("Servicio Cancion Iniciado")) {
                Toast.makeText(context, R.string.broadcast_StartSong, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, R.string.broadcast_StartSound, Toast.LENGTH_LONG).show();
            }
            context.startService(sonido);
        } else {
            Toast.makeText(context, R.string.broadcast_Stop, Toast.LENGTH_LONG).show();
            context.stopService(sonido);
        }

    }
}

