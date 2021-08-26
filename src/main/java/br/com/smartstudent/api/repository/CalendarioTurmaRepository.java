package br.com.smartstudent.api.repository;

import br.com.smartstudent.api.model.CalendarioTurma;
import com.google.cloud.firestore.Firestore;
import org.springframework.stereotype.Repository;

@Repository
public class CalendarioTurmaRepository extends AbstractFirebaseRepository<CalendarioTurma> {

    protected CalendarioTurmaRepository(Firestore firestore) {
        super(firestore, "calendario_turma");
    }

}
