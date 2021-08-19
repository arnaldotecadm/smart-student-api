package br.com.smartstudent.api.model;

import java.util.UUID;

public class AbstractModel {
    protected String documentId;
    protected Integer id;

    public String getDocumentId() {
        this.setDocumentId(null != documentId ? documentId : UUID.randomUUID().toString());
        return this.documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
