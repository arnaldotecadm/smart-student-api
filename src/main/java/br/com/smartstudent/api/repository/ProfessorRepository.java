package br.com.smartstudent.api.repository;

import br.com.smartstudent.api.model.Professor;
import br.com.smartstudent.api.model.Turma;
import com.google.cloud.firestore.Firestore;
import org.springframework.stereotype.Repository;

@Repository
public class ProfessorRepository extends AbstractFirebaseRepository<Professor> {

    protected ProfessorRepository(Firestore firestore) {
        super(firestore, "professor");
    }

}
