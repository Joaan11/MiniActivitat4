package com.android.mdw.demo;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Main extends Activity implements OnClickListener {
    private Intent cancion;
    private Intent sonido;
    private Intent URI;
    private Intent Select;
    private final String SOUND = "SOUND";
    private final String STOP = "STOP";
    private final String PLAY= "PLAY";
    private static final Uri ALBUMART_URI = Uri.parse("content://media/external/audio/media/57");
    private int PICK_SONG = 1;
    private static final int MYPERMISSIONS_EX_STORAGE = 0 ;
    private static final int MYPERMISSIONS_EX_MEDIA = 1;
    private static final int RESULT_LOAD_SONG = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button btnCancion = (Button) findViewById(R.id.ReprCancion);
        Button btnSonido = (Button) findViewById(R.id.ReprSonido);
        Button btnDetener = (Button) findViewById(R.id.STOP);
        Button btnUri = (Button) findViewById(R.id.ReprURI);
        Button btnSELECT = (Button) findViewById(R.id.SELECTAudio);

        btnCancion.setOnClickListener(this);
        btnSonido.setOnClickListener(this);
        btnDetener.setOnClickListener(this);
        btnUri.setOnClickListener(this);
        btnSELECT.setOnClickListener(this);
    }

    public void onClick(View src) {
        switch (src.getId()) {
            case R.id.ReprCancion:
                cancion = new Intent(this, MyBroadcastReceiver.class);
                cancion.putExtra(PLAY, R.raw.bob_marley_cybl);
                cancion.putExtra(SOUND, "Servicio Cancion Iniciado");
                cancion.putExtra(STOP, "false");
                sendBroadcast(cancion);
                break;
            case R.id.ReprSonido:
                sonido = new Intent(this, MyBroadcastReceiver.class);
                sonido.putExtra(PLAY, R.raw.train);
                sonido.putExtra(SOUND, "Servicio Sonido Iniciado");
                sonido.putExtra(STOP, "false");
                sendBroadcast(sonido);
                break;
            case R.id.STOP:
                sendBroadcast(new Intent(this, MyBroadcastReceiver.class).putExtra(STOP, "true"));
                break;
            case R.id.ReprURI:
                if (ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    accessResources(Manifest.permission.READ_EXTERNAL_STORAGE, MYPERMISSIONS_EX_STORAGE);
                }else {
                    URI = new Intent(this, ElServicio.class);
                    //URL.putExtra("URI", ALBUMART_URI);
                    URI.setData(ALBUMART_URI);
                    URI.setType("audio/*");
                    startService(URI);
                }
                break;
            case R.id.SELECTAudio:

                if (ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    accessResources(Manifest.permission.READ_EXTERNAL_STORAGE, MYPERMISSIONS_EX_MEDIA);
                }else {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, RESULT_LOAD_SONG);
                }
                break;
        }
    }

    private void accessResources(String readExternalStorage, int mypermissionsExMedia) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                readExternalStorage)) {
        } else {
            // No explanation needed, we can request the permission.
            ActivityCompat.requestPermissions(this,
                    new String[]{readExternalStorage},
                    mypermissionsExMedia);
        }

    }


    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
           case MYPERMISSIONS_EX_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted
                    URI = new Intent(this, ElServicio.class);
                    URI.setData(ALBUMART_URI);
                    startService(URI);

                } else {
                    // permission denied
                }
                return;
            }

            case MYPERMISSIONS_EX_MEDIA: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, RESULT_LOAD_SONG);

                } else {
                    // permission denied
                }
                return;

            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_SONG && resultCode == RESULT_OK && null != data){
            Uri uri = data.getData();
            Log.i("CANCION", uri.getPath());
            Intent intent = new Intent(this, ElServicio.class);
            intent.setData(uri);
            startService(intent);
        }
    }
}
