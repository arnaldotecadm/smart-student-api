package br.com.smartstudent.api.model;

import java.util.UUID;

public class AbstractModel {
    protected String documentId;

    public String getDocumentId() {
        this.setDocumentId(null != documentId ? documentId : UUID.randomUUID().toString());

        return this.documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }
}
