package br.com.smartstudent.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Pessoa extends AbstractModel {
    private String nome;
    private String cep;
    private String tipoCep;
    private String endereco;
    private String numero;
    private String uf;
    private String bairro;
    private String cidade;
    private String complemento;
    private String telefone;
    private String email;
    private boolean ativo;
    private String usuario;
}
