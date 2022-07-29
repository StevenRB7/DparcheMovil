package com.kamilo.deparche;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

public class Fcm extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);

        Log.e("token","mi token es:" +s);
        guardartoken(s);
    }
    private void guardartoken(String s) {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("token");
        ref.child("steven").setValue(s);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String from = remoteMessage.getFrom();
        Log.e("TAG","mensaje recibido de "+from);

        if(remoteMessage.getData().size() >0) {
            String titulo = remoteMessage.getData().get("titulo");
            String detalle = remoteMessage.getData().get("detalle");

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                mayorqueoreo(titulo,detalle);
            }
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O){
            }


            mayorqueoreo(titulo,detalle);
            // mayorqueoreo(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());

        }
    }
    private void mayorqueoreo(String titulo,String  detalle) {

        String id = "mensaje";
        NotificationManager nm =(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,id);



        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel nc = new NotificationChannel(id, "nuevo",NotificationManager.IMPORTANCE_HIGH);
            nc.setShowBadge(true);
            nm.createNotificationChannel(nc);
        }

/*
        try{
*/
        builder.setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .setContentTitle(titulo)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText(detalle)
                .setStyle(new NotificationCompat.BigPictureStyle())
                .setContentIntent(clicknoti())
                .setContentInfo("nuevo");
        Random random = new Random();
        int idNotify = random.nextInt(8000);

        nm.notify(idNotify,
                builder.build());
   /* } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
    //dar click a la notificacion ´para que nos mande algun lugar
    public PendingIntent clicknoti(){
        Intent nf = new Intent( getApplicationContext(),Publicar.class);
        nf.putExtra("color","rojo");
        nf.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP  | Intent.FLAG_ACTIVITY_SINGLE_TOP );
        return PendingIntent.getActivity(this, 0,nf, 0);
    }
}
