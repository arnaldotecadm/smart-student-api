package br.com.smartstudent.api.security.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class FirebaseProperties {

    private int sessionExpiryInDays;
    private String databaseUrl;
    private boolean enableStrictServerSession = false;
    private boolean enableCheckSessionRevoked = true;
    private boolean enableLogoutEverywhere;

}
