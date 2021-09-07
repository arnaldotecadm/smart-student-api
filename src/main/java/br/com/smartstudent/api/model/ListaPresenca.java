package br.com.smartstudent.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ListaPresenca extends AbstractModel{
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date data;
    private String turma;
    private List<AlunoPresenca> alunoList;
}
