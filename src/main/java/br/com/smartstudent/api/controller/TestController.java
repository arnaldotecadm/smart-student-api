package br.com.smartstudent.api.controller;

import br.com.smartstudent.api.service.FirebaseInitializer;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@RestController
public class TestController {

    @Autowired
    private FirebaseInitializer firebase;

    @GetMapping
    public List<Student> getAll() throws ExecutionException, InterruptedException {
        CollectionReference testList = firebase.getFirebase().collection("test");
        ApiFuture<QuerySnapshot> query = testList.get();
        List<QueryDocumentSnapshot> documents = query.get().getDocuments();
        return documents.stream().map(item -> item.toObject(Student.class)).collect(Collectors.toList());
    }

    @PostMapping
    public void saveEntry(){
        CollectionReference testList = firebase.getFirebase().collection("test");
        testList.add(new Student("Arnold"));
    }
}

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
class Student{
    private String name;
}
