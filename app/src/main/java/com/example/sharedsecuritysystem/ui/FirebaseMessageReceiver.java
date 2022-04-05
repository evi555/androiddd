package com.example.sharedsecuritysystem.ui;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import com.example.sharedsecuritysystem.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

    public class FirebaseMessageReceiver extends FirebaseMessagingService {

        @Override
        public void onMessageReceived(@NonNull RemoteMessage remoteMessage)
        {
        super.onMessageReceived(remoteMessage);
        getFirebaseMessage(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());
        }

        public void getFirebaseMessage(String title, String msg){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "myFirebaseChannel")
                .setSmallIcon(R.drawable.user1)
                .setContentTitle(title)
                .setContentText(msg)
                .setAutoCancel(true);

            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
            managerCompat.notify(101, builder.build());
        }
    }
