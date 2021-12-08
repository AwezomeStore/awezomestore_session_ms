package com.awezomestore.awezomestore_session_ms.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.awezomestore.awezomestore_session_ms.dto.RoleDTO;
import com.awezomestore.awezomestore_session_ms.enums.RoleName;
import com.awezomestore.awezomestore_session_ms.service.RoleService;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;

import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    private CollectionReference getCollection(){
        return FirestoreClient.getFirestore().collection("roles");
    }

    private Map<String, Object> getDocData(RoleDTO role){
        Map<String, Object> docData = new HashMap<>();
        docData.put("RoleName", role.getRoleName());
        return docData;
    }

    @Override
    public List<RoleDTO> getAll(){
        List<RoleDTO> roleList = new ArrayList<>();
        RoleDTO role;

        try {
            for (DocumentSnapshot doc : getCollection().get().get().getDocuments()) {
                role = doc.toObject(RoleDTO.class);
                role.setId(doc.getId());
                roleList.add(role);
            }
            return roleList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    };

    @Override
    public Boolean create(RoleDTO role){
        Map<String, Object> docData = getDocData(role);
        CollectionReference _role = getCollection();
        ApiFuture<WriteResult> writeResultApiFurute = _role.document().create(docData);
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
    public Boolean update(String id, RoleDTO role){
        Map<String, Object> docData = getDocData(role);
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
    public RoleDTO getById(String id) {
        try {
            DocumentSnapshot doc = getCollection().document(id).get().get();
            if (null != doc.getData()) {
                RoleDTO role = new RoleDTO();
                role.setId(id);
                role.setRoleName((RoleName) doc.getData().get("RoleName"));
                return role;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    };

    @Override
    public RoleDTO getByRoleName(RoleName RoleName){
        RoleDTO role = new RoleDTO();
        try {
            for (DocumentSnapshot doc : getCollection().get().get().getDocuments()) {
                role = doc.toObject(RoleDTO.class);
                if (role.getRoleName() != null) {
                    if (role.getRoleName() == RoleName) {
                        role.setId(doc.getId());
                        return role;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
