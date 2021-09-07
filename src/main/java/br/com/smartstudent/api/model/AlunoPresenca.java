package br.com.smartstudent.api.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AlunoPresenca {
    private String aluno;
    private boolean presente;
}
