package br.com.smartstudent.api.repository;

import br.com.smartstudent.api.model.Boletim;
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
public class BoletimRepository extends AbstractFirebaseRepository<Boletim> {

    protected BoletimRepository(Firestore firestore) {
        super(firestore, "boletim");
    }

    public List<Boletim> getBoletimByTurma(String turmaId) throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = collectionReference.get();
        Query query = querySnapshotApiFuture.get().getQuery();
        Query turma = query.whereEqualTo("turma", turmaId);
        List<QueryDocumentSnapshot> queryDocumentSnapshots = turma.get().get().getDocuments();

        List<Boletim> tList = new ArrayList<>();

        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
            Boletim obj = doc.toObject(Boletim.class);
            obj.setDocumentId(doc.getId());
            tList.add(obj);
        }

        return tList;
    }
}
