package br.com.smartstudent.api.security.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties("security")
@Data
public class SecurityProperties {

    private CookieProperties cookieProps;

    @Autowired
    private FirebaseProperties firebaseProps;

    private boolean allowCredentials;
    private List<String> allowedOrigins = new ArrayList<>();
    private List<String> allowedHeaders = new ArrayList<>();
    private List<String> exposedHeaders = new ArrayList<>();
    private List<String> allowedMethods = new ArrayList<>();
    private List<String> allowedPublicApis = new ArrayList<>();
    List<String> superAdmins = new ArrayList<>();
    List<String> validApplicationRoles = new ArrayList<>();

}
