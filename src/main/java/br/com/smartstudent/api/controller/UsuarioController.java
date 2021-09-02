package br.com.smartstudent.api.controller;

import br.com.smartstudent.api.controller.generics.RestBasicController;
import br.com.smartstudent.api.enums.StatusAprovacaoEnum;
import br.com.smartstudent.api.model.*;
import br.com.smartstudent.api.service.AlunoService;
import br.com.smartstudent.api.service.ProfessorService;
import br.com.smartstudent.api.service.UsuarioService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("usuario")
@CrossOrigin("*")
public class UsuarioController extends RestBasicController<Usuario> {

    private UsuarioService basicService;
    private AlunoService alunoService;
    private ProfessorService professorService;

    @Autowired
    public UsuarioController(UsuarioService basicService, AlunoService alunoService, ProfessorService professorService) {
        super(basicService);
        this.basicService = basicService;
        this.alunoService = alunoService;
        this.professorService = professorService;
    }

    @PostMapping("create-user-from-token")
    public ResponseEntity<Usuario> save(@RequestBody String token) throws ExecutionException, InterruptedException, FirebaseAuthException {
        FirebaseToken firebaseToken = FirebaseAuth.getInstance().verifyIdToken(token);
        Usuario usuario = new Usuario(firebaseToken);
        return super.save(usuario);
    }

    @GetMapping(value = {"aprovados"})
    public List<Usuario> getAllAprovado() throws ExecutionException, InterruptedException {
        return super.getAll().stream().filter(item -> item.getStatusAprovacaoEnum() == StatusAprovacaoEnum.APROVADO).collect(Collectors.toList());
    }

    @GetMapping(value = {"reprovados"})
    public List<Usuario> getAllReprovado() throws ExecutionException, InterruptedException {
        return super.getAll().stream().filter(item -> item.getStatusAprovacaoEnum() == StatusAprovacaoEnum.REPROVADO).collect(Collectors.toList());
    }

    @GetMapping(value = {"pendentes"})
    public List<Usuario> getAllPendente() throws ExecutionException, InterruptedException {
        return super.getAll().stream().filter(item -> item.getStatusAprovacaoEnum() == StatusAprovacaoEnum.PENDENTE).collect(Collectors.toList());
    }

    @PostMapping(value = {"aprovar"})
    public ResponseEntity<StringResponse> aprovarUsuario(@RequestBody String documentId) throws ExecutionException, InterruptedException {
        this.basicService.aprovarUsuario(documentId);
        return ResponseEntity.ok(new StringResponse("Registro alterado com Sucesso"));
    }

    @PostMapping(value = {"reprovar"})
    public ResponseEntity<StringResponse> reprovarUsuario(@RequestBody String documentId) throws ExecutionException, InterruptedException {
        this.basicService.reprovarUsuario(documentId);
        return ResponseEntity.ok(new StringResponse("Registro alterado com Sucesso"));
    }

    @PostMapping(value = {"ativar"})
    public ResponseEntity<Usuario> ativarUsuario(@RequestBody String documentId) throws ExecutionException, InterruptedException {
        Usuario usuario = this.basicService.ativarUsuario(documentId);
        return ResponseEntity.ok(usuario);
    }

    @PostMapping(value = {"desativar"})
    public ResponseEntity<Usuario> desativarUsuario(@RequestBody String documentId) throws ExecutionException, InterruptedException {
        Usuario usuario = this.basicService.desativarUsuario(documentId);
        return ResponseEntity.ok(usuario);
    }

    @PostMapping(value = {"definir-como-aluno"})
    public ResponseEntity<Usuario> definirComoAluno(@RequestBody Usuario usuario) throws ExecutionException, InterruptedException {
        Usuario user = this.basicService.definirComoAluno(usuario);
        Optional<Professor> byUsuario = this.professorService.getByUsuario(usuario.getUid());
        if (byUsuario.isPresent()) {
            this.professorService.deleteById(byUsuario.get().getDocumentId());
        }
        Aluno p = getPessoa(user, new Aluno(), "aluno");
        this.alunoService.save(p);
        return ResponseEntity.ok(user);
    }

    @PostMapping(value = {"definir-como-professor"})
    public ResponseEntity<Usuario> definirComoProfessor(@RequestBody Usuario usuario) throws ExecutionException, InterruptedException {
        Usuario user = this.basicService.definirComoProfessor(usuario);
        Optional<Aluno> byUsuario = this.alunoService.getByUsuario(usuario.getUid());
        if (byUsuario.isPresent()) {
            this.alunoService.deleteById(byUsuario.get().getDocumentId());
        }
        Professor p = getPessoa(user, new Professor(), "professor");
        this.professorService.save(p);
        return ResponseEntity.ok(user);
    }

    public <T extends Pessoa> T getPessoa(Usuario usuario, Pessoa pessoa, String collectionName) throws ExecutionException, InterruptedException {
        super.setIdentifier(pessoa, collectionName);
        pessoa.setNome(usuario.getNome());
        pessoa.setUsuario(usuario.getUid());
        pessoa.setEmail(usuario.getEmail());
        pessoa.setDocumentId(usuario.getDocumentId());
        pessoa.setUsuarioCriacao(usuario.getUsuarioCriacao());
        return (T) pessoa;
    }
}
