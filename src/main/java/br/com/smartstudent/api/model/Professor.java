package br.com.smartstudent.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Professor extends AbstractModel{

    private String nome;
    private String descricao;
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
}
