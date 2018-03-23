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

        try {
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
        } catch (Exception ex) {
            if(intent.getAction()!=null) {
                if (intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {
                    Toast.makeText(context, R.string.broadcast_HEADSET_ON, Toast.LENGTH_LONG).show();
                    sonido.putExtra("ID", R.raw.bob_marley_cybl);
                    sonido.putExtra("SOUND", "Servicio Cancion Iniciado");
                    context.startService(sonido);
                } else {
                    Toast.makeText(context, R.string.broadcast_HEADSET_OFF, Toast.LENGTH_LONG).show();
                    context.stopService(sonido);
                }
            }
            /*int state = intent.getIntExtra("state", 0);
                    switch (state) {
                        case 0:
                            Toast.makeText(context, R.string.broadcast_HEADSET_ON, Toast.LENGTH_LONG).show();
                            sonido.putExtra("ID", R.raw.bob_marley_cybl);
                            sonido.putExtra("SOUND", "Servicio Cancion Iniciado");
                            context.startService(sonido);
                            break;
                        case 1:
                            Toast.makeText(context, R.string.broadcast_HEADSET_OFF, Toast.LENGTH_LONG).show();
                            context.stopService(sonido);
                            break;
                        default:
                            //No va a llegar nunca
                            break;
                    }
              */
        }
    }
}
