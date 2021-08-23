package br.com.smartstudent.api.controller;

import com.google.api.gax.paging.Page;
import com.google.cloud.ReadChannel;
import com.google.cloud.storage.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@CrossOrigin()
@RequestMapping(path = "upload")
public class UploadController {

    @Autowired
    private StorageOptions storageOptions;

    @Value("smart-student-d3812.appspot.com")
    private String bucketName;

    @GetMapping("/all")
    public List getAll() {
        return getFiles(null);
    }

    @GetMapping("/all/{atividadeId}")
    public List getAllByAtividade(@PathVariable("atividadeId") String atividadeId) {
       return getFiles(atividadeId);
    }

    private List getFiles(String atividadeId){
        Storage storage = storageOptions.getService();
        Bucket bucket = storage.get(bucketName);

        Storage.BlobListOption upload = null;
        Page<Blob> list = null;

        if(null != atividadeId){
            upload = Storage.BlobListOption.prefix(atividadeId);
            list = bucket.list(upload);
        } else {
            list = bucket.list();
        }

        List<FileUploadResponseDTO_V2> v2List = new ArrayList<>();
        for (Blob blob : list.iterateAll()) {
            String[] split = blob.getName().split("/");
            FileUploadResponseDTO_V2 v2 = new FileUploadResponseDTO_V2();
            v2.setId(split[0]);
            v2.setGravadoNaBase(true);
            v2.setNomeArquivo(split[1]);
            v2.setTamanhoArquivo(blob.getSize());

            v2List.add(v2);
        }

        return v2List;
    }

    @PostMapping(path = "/add")
    public ResponseEntity<FileUploadResponseDTO> save(@RequestParam("file") MultipartFile file,
                                                      @RequestParam("atividadeUUID") String atividadeUUID) throws ParseException, IOException {

        byte[] arquivo = file.getBytes();

        BlobId blobId = BlobId.of(bucketName, String.format("%s/%s", atividadeUUID, file.getOriginalFilename()));

        Storage storage = storageOptions.getService();

        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();

        Blob blob = storage.create(blobInfo, arquivo);

        return new ResponseEntity<FileUploadResponseDTO>(
                new FileUploadResponseDTO("Registro salvo com Sucesso!", 1, file.getOriginalFilename()),
                HttpStatus.OK);

    }

    @GetMapping("download/{atividadeId}/{nomeDocumento}")
    public ResponseEntity<Object> downloadFile(@PathVariable("atividadeId") String atividadeId, @PathVariable("nomeDocumento") String nomeDocumento) throws Exception {
        Storage storage = storageOptions.getService();

        Blob blob = storage.get(BlobId.of(bucketName, String.format("%s/%s", atividadeId, nomeDocumento)));
        ReadChannel reader = blob.reader();
        InputStream inputStream = Channels.newInputStream(reader);

        byte[] content = null;

        content = IOUtils.toByteArray(inputStream);

        final ByteArrayResource byteArrayResource = new ByteArrayResource(content);

        return ResponseEntity
                .ok()
                .contentLength(content.length)
                .header("Content-type", "application/octet-stream")
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + nomeDocumento + "\"")
                .body(byteArrayResource);

    }

    @GetMapping(path = "/por-processo/{processoId}")
    public List getByProcesso(@PathVariable("processoId") Integer processoId) throws ParseException {
        return Collections.emptyList();
    }

    @GetMapping(path = "/por-processo/{processoId}/{tipoRelacionamento}/{idRelacionamento}")
    public List getByProcessoTipoAndRelacionamento(@PathVariable("processoId") Integer processoId,
                                                   @PathVariable("tipoRelacionamento") String tipoRelacionamento,
                                                   @PathVariable("idRelacionamento") Integer idRelacionamento) throws ParseException {
        return Collections.emptyList();
    }
}

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class FileUploadResponseDTO {
    private String message;
    private Integer id;
    private String nomeOriginal;
}

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class FileUploadResponseDTO_V2 {
    private String id;
    private boolean gravadoNaBase;
    private int ordem;
    private String nomeArquivo;
    private Long tamanhoArquivo;
}