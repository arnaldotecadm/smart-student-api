package br.com.smartstudent.api.repository;

import br.com.smartstudent.api.model.SequenciaChavePrimaria;
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
public class SequenciaChavePrimariaRepository extends AbstractFirebaseRepository<SequenciaChavePrimaria> {

    protected SequenciaChavePrimariaRepository(Firestore firestore) {
        super(firestore, "sequencia_chave_primaria");
    }

    public List<SequenciaChavePrimaria> getLastKey(String nomeTabela) throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> query = collectionReference.get();
        Query equalTo = query.get().getQuery().whereEqualTo("nomeTabela", nomeTabela);
        List<QueryDocumentSnapshot> queryDocumentSnapshots = equalTo.get().get().getDocuments();

        List<SequenciaChavePrimaria> tList = new ArrayList<>();

        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
            SequenciaChavePrimaria obj = doc.toObject(SequenciaChavePrimaria.class);
            obj.setDocumentId(doc.getId());
            tList.add(obj);
        }

        return tList;
    }
}
