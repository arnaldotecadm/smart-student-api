package br.com.smartstudent.api.model;

import java.util.UUID;

public class AbstractModel {
    protected String documentId;
    protected Integer id;
    protected String usuarioCriacao;

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

    public void setUsuarioCriacao(String usuarioCriacao) {
        this.usuarioCriacao = usuarioCriacao;
    }

    public String getUsuarioCriacao() {
        return usuarioCriacao;
    }
}
