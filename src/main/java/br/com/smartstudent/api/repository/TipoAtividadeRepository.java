package br.com.smartstudent.api.repository;

import br.com.smartstudent.api.model.TipoAtividade;
import com.google.cloud.firestore.Firestore;
import org.springframework.stereotype.Repository;

@Repository
public class TipoAtividadeRepository extends AbstractFirebaseRepository<TipoAtividade> {

    protected TipoAtividadeRepository(Firestore firestore) {
        super(firestore, "tipo-atividade");
    }

}
