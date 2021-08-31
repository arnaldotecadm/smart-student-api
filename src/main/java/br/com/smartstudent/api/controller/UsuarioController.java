package br.com.smartstudent.api.controller;

import br.com.smartstudent.api.controller.generics.RestBasicController;
import br.com.smartstudent.api.model.Usuario;
import br.com.smartstudent.api.service.UsuarioService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.concurrent.ExecutionException;

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

}
