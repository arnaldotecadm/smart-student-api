package br.com.smartstudent.api.repository;

import br.com.smartstudent.api.model.AbstractModel;
import br.com.smartstudent.api.model.Usuario;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.springframework.security.core.context.SecurityContextHolder;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public class AbstractFirebaseRepository<T extends AbstractModel> {

    protected final CollectionReference collectionReference;
    private final Class<T> parameterizedType;
    private String collection;
    public String getCollection() {
        return collection;
    }

    protected AbstractFirebaseRepository(Firestore firestore, String collection) {
        this.collection = collection;
        this.collectionReference = firestore.collection(collection);
        this.parameterizedType = getParameterizedType();
    }

    private Class<T> getParameterizedType() {
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        return (Class<T>) type.getActualTypeArguments()[0];
    }

    public T save(T model) {
        String documentId = model.getDocumentId();
        Usuario usuario = ((Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        model.setUsuarioCriacao(usuario.getUid());

        ApiFuture<WriteResult> resultApiFuture = collectionReference.document(documentId).set(model);
        waitProcessToBeFinished(resultApiFuture);
        return model;
    }

    public void deleteById(String documentId) {
        ApiFuture<WriteResult> resultApiFuture = collectionReference.document(documentId).delete();
        waitProcessToBeFinished(resultApiFuture);
    }

    public List<T> getAll() throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = collectionReference.get();
        Query orderBy = querySnapshotApiFuture.get().getQuery().orderBy("id");
        List<QueryDocumentSnapshot> queryDocumentSnapshots = orderBy.get().get().getDocuments();
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

    private void waitProcessToBeFinished(ApiFuture<WriteResult> resultApiFuture) {
        while(!resultApiFuture.isDone()){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }
}
