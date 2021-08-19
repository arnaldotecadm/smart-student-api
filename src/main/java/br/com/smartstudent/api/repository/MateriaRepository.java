package br.com.smartstudent.api.repository;

import br.com.smartstudent.api.model.Materia;
import br.com.smartstudent.api.model.Turma;
import com.google.cloud.firestore.Firestore;
import org.springframework.stereotype.Repository;

@Repository
public class MateriaRepository extends AbstractFirebaseRepository<Materia> {

    protected MateriaRepository(Firestore firestore) {
        super(firestore, "materia");
    }

}
