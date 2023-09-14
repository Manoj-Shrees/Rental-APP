package com.trendsetter.deck_out.Extra;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import androidx.core.app.NotificationCompat;
import android.util.Log;

import com.onesignal.NotificationExtenderService;
import com.onesignal.OSNotificationDisplayedResult;
import com.onesignal.OSNotificationReceivedResult;
import com.trendsetter.deck_out.R;

/**
 * Created by Dreamer on 26-07-2017.
 */

public class onesignalnotification extends NotificationExtenderService {
    @Override
    public void onCreate() {
        super.onCreate();

    }


    @Override
    protected boolean onNotificationProcessing(OSNotificationReceivedResult notification) {
        OverrideSettings overrideSettings = new OverrideSettings();
        overrideSettings.extender = new NotificationCompat.Extender()
        {
            @Override
            public NotificationCompat.Builder extend(NotificationCompat.Builder builder) {

                NotificationCompat.BigTextStyle notiStyle = new NotificationCompat.BigTextStyle();

                /*if(sp.getString("sounduri","")!=null && sp.getString("lightcolor","")!=null)
                {
                    Uri sounduri = Uri.parse(sp.getString("sounduri",""));
                    builder.setSound(sounduri);
                    builder.setLights(Integer.parseInt(sp.getString("lightcolor","")),500, 1000);
                    Log.e(">>",sounduri.toString());
                }*/

                Bitmap notificicon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_badge);
                builder.setLargeIcon(notificicon);
                builder.setColor(Color.BLACK);
                builder.setLights(Color.RED,500,1000);
                builder.setStyle(notiStyle);
                builder.setVibrate(new long[]{0,100,1000,0,0,1000});

                return builder;
            }
        };


        OSNotificationDisplayedResult displayedResult = displayNotification(overrideSettings);
        Log.d("OneSignalExample", "Notification displayed with id: " + displayedResult.androidNotificationId);

        return true;
    }
}
