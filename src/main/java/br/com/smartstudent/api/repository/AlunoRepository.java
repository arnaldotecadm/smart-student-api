package br.com.smartstudent.api.repository;

import br.com.smartstudent.api.model.Aluno;
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
public class AlunoRepository extends AbstractFirebaseRepository<Aluno> {

    protected AlunoRepository(Firestore firestore) {
        super(firestore, "aluno");
    }

    public List<Aluno> getAlunosByTurma(String turmaId) throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = collectionReference.get();
        Query query = querySnapshotApiFuture.get().getQuery();
        Query turma = query.whereEqualTo("turma", turmaId);
        List<QueryDocumentSnapshot> queryDocumentSnapshots = turma.get().get().getDocuments();

        List<Aluno> tList = new ArrayList<>();

        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
            Aluno obj = doc.toObject(Aluno.class);
            obj.setDocumentId(doc.getId());
            tList.add(obj);
        }

        return tList;
    }
}
