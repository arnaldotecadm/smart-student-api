package br.com.smartstudent.api.repository;

import br.com.smartstudent.api.model.Aluno;
import com.google.cloud.firestore.Firestore;
import org.springframework.stereotype.Repository;

@Repository
public class AlunoRepository extends AbstractFirebaseRepository<Aluno> {

    protected AlunoRepository(Firestore firestore) {
        super(firestore, "aluno");
    }

}
