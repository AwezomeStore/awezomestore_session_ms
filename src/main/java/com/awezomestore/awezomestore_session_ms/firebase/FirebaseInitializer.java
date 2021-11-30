package com.awezomestore.awezomestore_session_ms.firebase;

import java.io.IOException;
import java.io.InputStream;

import javax.annotation.PostConstruct;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.v1.FirestoreClient;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import org.springframework.stereotype.Service;

@Service
public class FirebaseInitializer {

    @PostConstruct
    private void initFirestore() throws IOException {
        InputStream serviceAccount = getClass().getClassLoader().getResourceAsStream("awezomestore-firebase-adminsdk-t4lrv-c7426e6145.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("http://awezomestore.firebaseio.com/")
                .build();

        if(FirebaseApp.getApps().isEmpty()){
            FirebaseApp.initializeApp(options);
        }
    }

    public static FirestoreClient getFirestore() throws IOException{
        return FirestoreClient.create();
    }

}
