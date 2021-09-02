package br.com.smartstudent.api.repository;

import br.com.smartstudent.api.model.Professor;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Repository
public class ProfessorRepository extends AbstractFirebaseRepository<Professor> {

    protected ProfessorRepository(Firestore firestore) {
        super(firestore, "professor");
    }

    public Optional<Professor> getByUsuario(String usuario) throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = collectionReference.get();
        Query query = querySnapshotApiFuture.get().getQuery();
        Query query1 = query.whereEqualTo("usuario", usuario);
        List<QueryDocumentSnapshot> queryDocumentSnapshots = query1.get().get().getDocuments();

        List<Professor> tList = new ArrayList<>();

        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
            Professor obj = doc.toObject(Professor.class);
            obj.setDocumentId(doc.getId());
            tList.add(obj);
        }

        return tList.isEmpty() ? Optional.empty() : Optional.of(tList.get(0));
    }
}
