package br.com.smartstudent.api.repository;

import br.com.smartstudent.api.model.AbstractModel;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public class AbstractFirebaseRepository<T extends AbstractModel> {

    private final CollectionReference collectionReference;

    private final Class<T> parameterizedType;

    protected AbstractFirebaseRepository(Firestore firestore, String collection) {
        this.collectionReference = firestore.collection(collection);
        this.parameterizedType = getParameterizedType();
    }

    private Class<T> getParameterizedType() {
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        return (Class<T>) type.getActualTypeArguments()[0];
    }

    public T save(T model) {
        String documentId = model.getDocumentId();
        ApiFuture<WriteResult> resultApiFuture = collectionReference.document(documentId).set(model);
        return model;
    }

    public void delete(T model) {
        String documentId = model.getDocumentId();
        ApiFuture<WriteResult> resultApiFuture = collectionReference.document(documentId).delete();
    }

    public void deleteById(String documentId) {
        ApiFuture<WriteResult> resultApiFuture = collectionReference.document(documentId).delete();
    }

    public List<T> getAll() throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = collectionReference.get();
        List<QueryDocumentSnapshot> queryDocumentSnapshots = querySnapshotApiFuture.get().getDocuments();

        List<T> tList = new ArrayList<>();

        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
            T obj = doc.toObject(parameterizedType);
            obj.setDocumentId(doc.getId());
            tList.add(obj);
        }

        return tList;
    }

    public Optional<T> getById(String documentId) throws ExecutionException, InterruptedException {
        DocumentReference documentReference = collectionReference.document(documentId);
        ApiFuture<DocumentSnapshot> documentSnapshotApiFuture = documentReference.get();

        DocumentSnapshot documentSnapshot = documentSnapshotApiFuture.get();

        if (documentSnapshot.exists()) {
            return Optional.ofNullable(documentSnapshot.toObject(parameterizedType));
        }

        return Optional.empty();
    }

}
