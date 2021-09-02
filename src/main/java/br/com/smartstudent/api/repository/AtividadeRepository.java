package br.com.smartstudent.api.repository;

import br.com.smartstudent.api.model.Atividade;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Repository
public class AtividadeRepository extends AbstractFirebaseRepository<Atividade> {

    protected AtividadeRepository(Firestore firestore) {
        super(firestore, "atividade");
    }

    public List<Atividade> getAtividadesByTurma(String turmaId) throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = collectionReference.get();
        Query query = querySnapshotApiFuture.get().getQuery();
        Query turma = query.whereEqualTo("turma", turmaId);
        List<QueryDocumentSnapshot> queryDocumentSnapshots = turma.get().get().getDocuments();

        List<Atividade> tList = new ArrayList<>();

        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
            Atividade obj = doc.toObject(Atividade.class);
            obj.setDocumentId(doc.getId());
            tList.add(obj);
        }

        return tList;
    }

}
