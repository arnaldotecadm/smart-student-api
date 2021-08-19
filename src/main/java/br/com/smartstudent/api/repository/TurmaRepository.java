package br.com.smartstudent.api.repository;

import br.com.smartstudent.api.model.Turma;
import com.google.cloud.firestore.Firestore;
import org.springframework.stereotype.Repository;

@Repository
public class TurmaRepository extends AbstractFirebaseRepository<Turma> {

    protected TurmaRepository(Firestore firestore) {
        super(firestore, "turma");
    }

}
