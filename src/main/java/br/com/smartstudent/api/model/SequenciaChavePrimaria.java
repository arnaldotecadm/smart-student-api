package br.com.smartstudent.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SequenciaChavePrimaria extends AbstractModel{

    private String nomeTabela;
    private int ultimoValor;

}
