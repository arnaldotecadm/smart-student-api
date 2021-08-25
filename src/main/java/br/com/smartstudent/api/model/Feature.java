package br.com.smartstudent.api.model;

import br.com.smartstudent.api.enums.FeatureStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Feature extends AbstractModel{

    private String nome;
    private String descricao;
    private FeatureStatusEnum featureStatusEnum;
}
