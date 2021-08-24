package br.com.smartstudent.api.config;

import br.com.smartstudent.api.model.ApplicationProperties;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.storage.StorageOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FireStoreConfiguration {

    private ApplicationProperties properties;

    public FireStoreConfiguration(ApplicationProperties properties) {
        this.properties = properties;
    }

    private StorageOptions storageOptions;

    @Bean
    public Firestore getFirestore() {
        return FirestoreClient.getFirestore();
    }

    @Bean
    public StorageOptions getStorageOptions() {
        return this.storageOptions;
    }

    @PostConstruct
    public void initializeFrebase() throws IOException {
        InputStream serviceAccount = getClass().getClassLoader().getResourceAsStream(properties.getCredentialPath());
        GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(credentials)
                .setDatabaseUrl(properties.getDatabaseUrl())
                .build();

        this.storageOptions = StorageOptions.newBuilder()
                .setProjectId(properties.getProjectId())
                .setCredentials(GoogleCredentials.fromStream(getClass().getClassLoader().getResourceAsStream(properties.getCredentialPath()))).build();

        FirebaseApp.initializeApp(options);
    }
}
