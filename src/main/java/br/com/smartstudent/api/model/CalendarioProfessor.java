package br.com.smartstudent.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CalendarioProfessor extends AbstractModel{

    private String evento;
    private String descricao;
    private Date data;
    private String professor;
}
