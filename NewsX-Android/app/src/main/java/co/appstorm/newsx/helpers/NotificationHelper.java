package co.appstorm.newsx.helpers;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.bumptech.glide.Glide;

import java.util.Random;
import java.util.concurrent.ExecutionException;

import co.appstorm.newsx.R;
import co.appstorm.newsx.activities.MainActivity;

/**
 * Created by ozzmhmt on 4/2/2018.
 */

public class NotificationHelper {
    private static NotificationHelper instance;
    private Context context;

    private NotificationHelper(Context context) {
        this.context = context;
    }

    public static NotificationHelper getInstance(Context context) {
        if (instance == null)
            instance = new NotificationHelper(context);
        return instance;
    }

    public void sendNotification(String title, String body) {
        int id = generateRandom();

        Intent resultIntent = new Intent(context, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, id, resultIntent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.img_oto)
                .setContentTitle(title)
                .setContentText(body)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(id, notificationBuilder.build());
    }

    private int generateRandom() {
        Random random = new Random();
        return random.nextInt(9999 - 1000) + 1000;
    }

    //If you want load a large image. You can add a param with a image url from Firebase. Then you can download it here and load it by '.setLargeIcon(getBitmapFromURL(getApplicationContext(), url))'
    private static Bitmap getBitmapFromURL(Context context, String url) {
        Bitmap bitmap = null;
        if (!url.equals("")) {
            try {
                bitmap = Glide.
                        with(context).
                        load(url).
                        asBitmap().
                        into(-1, -1).
                        get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }
}
