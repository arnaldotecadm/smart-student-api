package br.com.smartstudent.api.controller;

import br.com.smartstudent.api.controller.generics.RestBasicController;
import br.com.smartstudent.api.enums.StatusAprovacaoEnum;
import br.com.smartstudent.api.model.Usuario;
import br.com.smartstudent.api.service.UsuarioService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("usuario")
@CrossOrigin("*")
public class UsuarioController extends RestBasicController<Usuario> {

    @Autowired
    public UsuarioController(UsuarioService basicService) {
        super(basicService);
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
}
