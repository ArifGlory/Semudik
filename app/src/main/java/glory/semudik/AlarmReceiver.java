package glory.semudik;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

/**
 * Created by Glory on 05/10/2016.
 */
public class AlarmReceiver extends BroadcastReceiver {

    MediaPlayer mp;
    private NotificationManager mManager;
    Intent i;
    String jarak;

    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "Motor Anda dalam bahaya !", Toast.LENGTH_LONG).show();
        mp = MediaPlayer.create(context, R.raw.alarm);
        mp.start();

        jarak =  intent.getStringExtra("jarak");

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.drawable.semudik_logo);
        builder.setContentTitle("Motor dalam bahaya.");//"Notifications Example")
        builder.setContentText("Jarak Anda Ke Motor  =  "+ jarak);//"This is a test notification");
        //Log.d("SV.jarak AR = ",SharedVariable.jarakKeMotor);
        builder.setTicker("Motor dalam bahaya !");
        builder.setSmallIcon(R.drawable.semudik_logo);

        Intent notificationIntent = new Intent(context, SplashActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);
        // Add as notification
        NotificationManager manager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        manager.notify(1, builder.build());
    }
}
