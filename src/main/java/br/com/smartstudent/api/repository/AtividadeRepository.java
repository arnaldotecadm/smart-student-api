package br.com.smartstudent.api.repository;

import br.com.smartstudent.api.model.Atividade;
import br.com.smartstudent.api.model.Turma;
import com.google.cloud.firestore.Firestore;
import org.springframework.stereotype.Repository;

@Repository
public class AtividadeRepository extends AbstractFirebaseRepository<Atividade> {

    protected AtividadeRepository(Firestore firestore) {
        super(firestore, "atividade");
    }

}
