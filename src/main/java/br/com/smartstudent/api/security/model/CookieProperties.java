package br.com.smartstudent.api.security.model;

import lombok.Data;

@Data
public class CookieProperties {
    private String domain;
    private String path;
    private boolean httpOnly;
    private boolean secure;
    private int maxAgeInMinutes;
}
