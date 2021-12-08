package com.awezomestore.awezomestore_session_ms.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.awezomestore.awezomestore_session_ms.dto.UserDTO;
import com.awezomestore.awezomestore_session_ms.service.UserService;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private CollectionReference getCollection() {
        return FirestoreClient.getFirestore().collection("users");
    }

    private Map<String, Object> getDocData(UserDTO user) {
        Map<String, Object> docData = new HashMap<>();
        docData.put("firstName", user.getFirstName());
        docData.put("lastName", user.getLastName());
        return docData;
    }

    @Override
    public List<UserDTO> getAll() {
        List<UserDTO> userList = new ArrayList<>();
        UserDTO user;

        try {
            for (DocumentSnapshot doc : getCollection().get().get().getDocuments()) {
                user = doc.toObject(UserDTO.class);
                user.setId(doc.getId());
                userList.add(user);
            }
            return userList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    };

    @Override
    public String create(UserDTO user) {
        CollectionReference users = getCollection();
        ApiFuture<DocumentReference> addedDocRef = users.add(user);
        try {
            return addedDocRef.get().getId();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    };

    @Override
    public Boolean update(String id, UserDTO user) {
        Map<String, Object> docData = getDocData(user);
        ApiFuture<WriteResult> writeResultApiFuture = getCollection().document(id).set(docData);
        try {
            if (null != writeResultApiFuture.get()) {
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }
    };

    @Override
    public Boolean delete(String id) {
        ApiFuture<WriteResult> writeResultApiFuture = getCollection().document(id).delete();
        try {
            if (null != writeResultApiFuture.get()) {
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        } catch (Exception e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }

    @Override
    public UserDTO getById(String id) {
        try {
            DocumentSnapshot doc = getCollection().document(id).get().get();
            if (null != doc.getData()) {
                UserDTO user = new UserDTO();
                user.setId(id);
                user.setFirstName((String) doc.getData().get("firstName"));
                user.setLastName((String) doc.getData().get("lastName"));
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    };
}
