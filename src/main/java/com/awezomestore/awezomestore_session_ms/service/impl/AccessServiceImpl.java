package com.awezomestore.awezomestore_session_ms.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.awezomestore.awezomestore_session_ms.dto.AccessDTO;
import com.awezomestore.awezomestore_session_ms.dto.RoleDTO;
import com.awezomestore.awezomestore_session_ms.service.AccessService;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccessServiceImpl implements AccessService {

    @Autowired
    PasswordEncoder passwordEncoder;

    private CollectionReference getCollection(){
        return FirestoreClient.getFirestore().collection("access");
    }

    private Map<String, Object> getDocData(AccessDTO access){
        Map<String, Object> docData = new HashMap<>();
        docData.put("userId", access.getUserId());
        docData.put("username", access.getUsername());
        docData.put("password", access.getPassword());
        docData.put("level", access.getLevel());
        docData.put("authorities", access.getRoles());
        return docData;
    }

    @Override
    public List<AccessDTO> getAll(){
        try {
            List<AccessDTO> accessList = new ArrayList<>();
            AccessDTO access;
            for (DocumentSnapshot doc : getCollection().get().get().getDocuments()) {
                access = doc.toObject(AccessDTO.class);
                access.setId(doc.getId());
                accessList.add(access);
            }
            return accessList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    };

    @Override
    public Boolean create(AccessDTO access){
        access.setPassword(passwordEncoder.encode(access.getPassword()));
        Map<String, Object> docData = getDocData(access);
        CollectionReference _access = getCollection();
        ApiFuture<WriteResult> writeResultApiFurute = _access.document().create(docData);
        try {
            if(null != writeResultApiFurute.get()){
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    };

    @Override
    public Boolean update(String id, AccessDTO access){
        Map<String, Object> docData = getDocData(access);
        ApiFuture<WriteResult> writeResultApiFuture = getCollection().document(id).set(docData);
        try {
            if(null != writeResultApiFuture.get()){
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }
    };

    @Override
    public Boolean delete(String id){
        ApiFuture<WriteResult> writeResultApiFuture = getCollection().document(id).delete();
        try {
            if(null != writeResultApiFuture.get()){
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        } catch (Exception e) {
            //TODO: handle exception
            e.printStackTrace();
            return Boolean.FALSE;
        }
    };

    @Override
    public AccessDTO getById(String id) {
        try {
            DocumentSnapshot doc = getCollection().document(id).get().get();
            if (null != doc.getData()) {
                AccessDTO access = new AccessDTO();
                access.setId(id);
                access.setUserId((String) doc.getData().get("userId"));
                access.setUsername((String) doc.getData().get("username"));
                access.setPassword((String) doc.getData().get("password"));
                access.setLevel((Long) doc.getData().get("level"));
                access.setAuthorities((ArrayList<RoleDTO>) doc.getData().get("authorities"));
                return access;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    };

    @Override
    public AccessDTO getByUsername(String username){
        AccessDTO access = new AccessDTO();
        try {
            for (DocumentSnapshot doc : getCollection().get().get().getDocuments()) {
                access = doc.toObject(AccessDTO.class);
                if (access.getUsername() != null) {
                    if (access.getUsername().equals(username)) {
                        access.setId(doc.getId());
                        return access;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    };
}
