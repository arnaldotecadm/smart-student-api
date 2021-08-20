package br.com.smartstudent.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Atividade extends AbstractModel{

    private String nome;
    private String descricao;
    private String atividadeId;
    private String materiaId;
    private String professorId;
    private String turmaId;
    private BigDecimal nataMaxima;
    private byte[] arquivos;
}
