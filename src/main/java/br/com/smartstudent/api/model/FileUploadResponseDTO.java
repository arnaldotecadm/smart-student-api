package br.com.smartstudent.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileUploadResponseDTO {
    private String id;
    private boolean gravadoNaBase;
    private int ordem;
    private String nomeArquivo;
    private Long tamanhoArquivo;
}
