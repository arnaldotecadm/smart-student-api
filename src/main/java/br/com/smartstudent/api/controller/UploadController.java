package br.com.smartstudent.api.controller;

import br.com.smartstudent.api.model.ApplicationProperties;
import br.com.smartstudent.api.model.FileUploadResponseDTO;
import br.com.smartstudent.api.model.StringResponse;
import com.google.api.gax.paging.Page;
import com.google.cloud.ReadChannel;
import com.google.cloud.storage.*;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin()
@RequestMapping(path = "upload")
public class UploadController {

    private StorageOptions storageOptions;
    private ApplicationProperties properties;

    @Autowired
    public UploadController(StorageOptions storageOptions, ApplicationProperties properties) {
        this.storageOptions = storageOptions;
        this.properties = properties;
    }

    @GetMapping("/all")
    public List getAll() {
        return getFiles(null,null);
    }

    @GetMapping("/all/{tipoMaterial}/{atividadeId}")
    public List getAllByAtividade(
            @PathVariable("tipoMaterial") String tipoMaterial,
            @PathVariable("atividadeId") String atividadeId
    ) {
        return getFiles(tipoMaterial, atividadeId);
    }

    private List getFiles(String tipoMaterial, String atividadeId) {
        Storage storage = storageOptions.getService();
        Bucket bucket = storage.get(properties.getBucketName());

        Storage.BlobListOption upload = null;
        Page<Blob> list = null;

        if (null != atividadeId) {
            upload = Storage.BlobListOption.prefix(String.format("atividades/%s/%s", atividadeId, tipoMaterial));
            list = bucket.list(upload);
        } else {
            list = bucket.list();
        }

        List<FileUploadResponseDTO> v2List = new ArrayList<>();
        for (Blob blob : list.iterateAll()) {
            String[] split = blob.getName().split("/");
            FileUploadResponseDTO v2 = new FileUploadResponseDTO();
            v2.setId(split[1]);
            v2.setGravadoNaBase(true);
            v2.setNomeArquivo(split[3]);
            v2.setTamanhoArquivo(blob.getSize());

            v2List.add(v2);
        }

        return v2List;
    }

    @PostMapping(path = "/add")
    public ResponseEntity<StringResponse> save(@RequestParam("file") MultipartFile file,
                                               @RequestParam("tipoMaterial") String tipoMaterial,
                                               @RequestParam("atividadeUUID") String atividadeUUID) throws ParseException, IOException {

        byte[] arquivo = file.getBytes();

        BlobId blobId = BlobId.of(properties.getBucketName(), String.format("atividades/%s/%s/%s", atividadeUUID, tipoMaterial, file.getOriginalFilename()));

        Storage storage = storageOptions.getService();

        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();

        Blob blob = storage.create(blobInfo, arquivo);

        return ResponseEntity.ok(new StringResponse("Registro salvo com sucesso!"));

    }

    @GetMapping("download/{tipoMaterial}/{atividadeId}/{nomeDocumento}")
    public ResponseEntity<Object> downloadFile(
            @PathVariable("tipoMaterial") String tipoMaterial,
            @PathVariable("atividadeId") String atividadeId,
            @PathVariable("nomeDocumento") String nomeDocumento) throws Exception {
        Storage storage = storageOptions.getService();

        Blob blob = storage.get(BlobId.of(properties.getBucketName(), String.format("atividades/%s/%s/%s", atividadeId, tipoMaterial, nomeDocumento)));
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
}