package br.com.smartstudent.api.repository;

import br.com.smartstudent.api.model.CalendarioProfessor;
import com.google.cloud.firestore.Firestore;
import org.springframework.stereotype.Repository;

@Repository
public class CalendarioProfessorRepository extends AbstractFirebaseRepository<CalendarioProfessor> {

    protected CalendarioProfessorRepository(Firestore firestore) {
        super(firestore, "calendario_professor");
    }

}
