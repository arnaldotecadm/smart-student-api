package br.com.smartstudent.api.repository;

import br.com.smartstudent.api.model.Usuario;
import com.google.cloud.firestore.Firestore;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioRepository extends AbstractFirebaseRepository<Usuario> {

    protected UsuarioRepository(Firestore firestore) {
        super(firestore, "usuario");
    }

}
