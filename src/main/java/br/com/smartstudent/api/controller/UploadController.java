package br.com.smartstudent.api.controller;

import br.com.smartstudent.api.model.*;
import br.com.smartstudent.api.service.AlunoService;
import com.google.api.gax.paging.Page;
import com.google.cloud.ReadChannel;
import com.google.cloud.storage.*;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@RestController
@CrossOrigin()
@RequestMapping(path = "upload")
public class UploadController {

    private StorageOptions storageOptions;
    private ApplicationProperties properties;
    private AlunoService alunoService;

    @Autowired
    public UploadController(StorageOptions storageOptions, ApplicationProperties properties, AlunoService alunoService) {
        this.storageOptions = storageOptions;
        this.properties = properties;
        this.alunoService = alunoService;
    }

    @GetMapping("/all")
    public List getAll() {
        return getFiles(null, null);
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

        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int indiceNome = 3;
        String folder;
        if ("respostas-dos-alunos".equals(tipoMaterial)) {
            folder = String.format("atividades/%s/%s/%s", atividadeId, tipoMaterial, usuario.getUid());
            indiceNome = 4;
        } else {
            folder = String.format("atividades/%s/%s", atividadeId, tipoMaterial);
        }

        if (null != atividadeId) {
            upload = Storage.BlobListOption.prefix(folder);
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
            v2.setNomeArquivo(split[indiceNome]);
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

        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String folder;
        if ("respostas-dos-alunos".equals(tipoMaterial)) {
            folder = String.format("atividades/%s/%s/%s/%s", atividadeUUID, tipoMaterial, usuario.getUid(), file.getOriginalFilename());
        } else {
            folder = String.format("atividades/%s/%s/%s", atividadeUUID, tipoMaterial, file.getOriginalFilename());
        }

        BlobId blobId = BlobId.of(properties.getBucketName(), folder);

        Storage storage = storageOptions.getService();

        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();

        Blob blob = storage.create(blobInfo, arquivo);

        return ResponseEntity.ok(new StringResponse("Registro salvo com sucesso!"));

    }

    @GetMapping(path = "/atividades-submetidas-alunos/{atividadeId}")
    public ResponseEntity<Object> getAlunosJaSubmeteramMaterial(@PathVariable("atividadeId") String atividadeId) throws ExecutionException, InterruptedException {

        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Storage storage = storageOptions.getService();

        Bucket bucket = storage.get(properties.getBucketName());

        Storage.BlobListOption upload = null;

        Page<Blob> list = null;

        String folder = String.format("atividades/%s/respostas-dos-alunos", atividadeId);

        upload = Storage.BlobListOption.prefix(folder);

        list = bucket.list();

        List<FileUploadResponseDTO> v2List = new ArrayList<>();
        List<String> alunoUidList = new ArrayList<>();
        for (Blob blob : list.iterateAll()) {
            String[] split = blob.getName().split("/");
            alunoUidList.add(split[3]);
        }

        List<Aluno> alunoList = new ArrayList<>();
        for(String uid : alunoUidList){
            Optional<Aluno> byId = this.alunoService.getById(uid);
            if(byId.isPresent()){
                alunoList.add(byId.get());
            }
        }

        return ResponseEntity.ok(alunoList);

    }

    @GetMapping("download/{tipoMaterial}/{atividadeId}/{nomeDocumento}")
    public ResponseEntity<Object> downloadFile(
            @PathVariable("tipoMaterial") String tipoMaterial,
            @PathVariable("atividadeId") String atividadeId,
            @PathVariable("nomeDocumento") String nomeDocumento) throws Exception {
        Storage storage = storageOptions.getService();

        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String folder;
        if ("respostas-dos-alunos".equals(tipoMaterial)) {
            folder = String.format("atividades/%s/%s/%s/%s", atividadeId, tipoMaterial, usuario.getUid(), nomeDocumento);
        } else {
            folder = String.format("atividades/%s/%s/%s", atividadeId, tipoMaterial, nomeDocumento);
        }

        Blob blob = storage.get(BlobId.of(properties.getBucketName(), folder));
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