package br.com.smartstudent.api.repository;

import br.com.smartstudent.api.model.Aluno;
import br.com.smartstudent.api.model.ListaPresenca;
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
public class ListaPresencaRepository extends AbstractFirebaseRepository<ListaPresenca> {

    protected ListaPresencaRepository(Firestore firestore) {
        super(firestore, "lista-presenca");
    }

    public List<ListaPresenca> getByTurma(String turmaId) throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = collectionReference.get();
        Query query = querySnapshotApiFuture.get().getQuery();
        Query turma = query.whereEqualTo("turma", turmaId);
        List<QueryDocumentSnapshot> queryDocumentSnapshots = turma.get().get().getDocuments();

        List<ListaPresenca> tList = new ArrayList<>();

        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
            ListaPresenca obj = doc.toObject(ListaPresenca.class);
            obj.setDocumentId(doc.getId());
            tList.add(obj);
        }

        return tList;
    }
}
