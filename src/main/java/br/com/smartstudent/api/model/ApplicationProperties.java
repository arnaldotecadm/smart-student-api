package br.com.smartstudent.api.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "firebase")
public class ApplicationProperties {

    private String credentialPath;
    private String databaseUrl;
    private String projectId;
    private String bucketName;
}
