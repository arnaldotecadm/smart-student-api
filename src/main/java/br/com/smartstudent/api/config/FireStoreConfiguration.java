package br.com.smartstudent.api.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.storage.StorageOptions;
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

    @Value("${firebase.project.id}")
    String projectId;

    private StorageOptions storageOptions;

    @Bean
    public Firestore getFirestore() {
        return FirestoreClient.getFirestore();
    }

    @Bean
    public StorageOptions getStorageOptions(){
        return this.storageOptions;
    }

    @PostConstruct
    public void initializeFrebase() throws IOException {
        InputStream serviceAccount = getClass().getClassLoader().getResourceAsStream(credentialPath);
        GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(credentials)
                .setDatabaseUrl(databaseUrl)
                .build();

        this.storageOptions = StorageOptions.newBuilder()
                .setProjectId(projectId)
                .setCredentials(GoogleCredentials.fromStream(getClass().getClassLoader().getResourceAsStream(credentialPath))).build();

        FirebaseApp.initializeApp(options);
    }
}
