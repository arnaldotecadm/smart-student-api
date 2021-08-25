package br.com.smartstudent.api.repository;

import br.com.smartstudent.api.model.Feature;
import com.google.cloud.firestore.Firestore;
import org.springframework.stereotype.Repository;

@Repository
public class FeatureRepository extends AbstractFirebaseRepository<Feature> {

    protected FeatureRepository(Firestore firestore) {
        super(firestore, "feature");
    }

}
