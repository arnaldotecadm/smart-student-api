package br.com.smartstudent.api.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Nota {
    private String usuario;
    private String atividade;
    private double nota;
}
