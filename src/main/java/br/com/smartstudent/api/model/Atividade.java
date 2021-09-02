package br.com.smartstudent.api.model;

import br.com.smartstudent.api.enums.StatusAtividadeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Atividade extends AbstractModel{

    private String nome;
    private String descricao;
    private String tipoAtividade;
    private String materia;
    private String professor;
    private String turma;
    private Double notaMaxima;
    private StatusAtividadeEnum statusAtividadeEnum;
}
