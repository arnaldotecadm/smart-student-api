package br.com.smartstudent.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter()
@Setter
public class Aluno extends Pessoa {

    private String matricula;
    private String cpf;
    private String turma;
    private String contato;

}
