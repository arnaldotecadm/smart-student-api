package br.com.smartstudent.api.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FireStoreConfiguration {

    @Value("${firebase.credential.path}")
    String credentialPath;

    @Value("${firebase.database.url}")
    String databaseUrl;

    @Bean
    public Firestore getFirestore() {
        return FirestoreClient.getFirestore();
    }

    @PostConstruct
    public void initializeFrebase() throws IOException {
        InputStream serviceAccount = getClass().getClassLoader().getResourceAsStream(credentialPath);
        GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(credentials)
                .setDatabaseUrl(databaseUrl)
                .build();
        FirebaseApp.initializeApp(options);
    }
}
