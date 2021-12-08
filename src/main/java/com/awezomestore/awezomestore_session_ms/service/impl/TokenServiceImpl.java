package com.awezomestore.awezomestore_session_ms.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.awezomestore.awezomestore_session_ms.dto.TokenDTO;
import com.awezomestore.awezomestore_session_ms.service.TokenService;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;

import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {

    private CollectionReference getCollection(){
        return FirestoreClient.getFirestore().collection("tokens");
    }

    private Map<String, Object> getDocData(TokenDTO token){
        Map<String, Object> docData = new HashMap<>();
        docData.put("userId", token.getUserId());
        docData.put("token", token.getToken());
        docData.put("roles", token.getRoles());
        return docData;
    }

    @Override
    public List<TokenDTO> getAll(){
        List<TokenDTO> tokenList = new ArrayList<>();
        TokenDTO token;

        try {
            for (DocumentSnapshot doc : getCollection().get().get().getDocuments()) {
                token = doc.toObject(TokenDTO.class);
                token.setId(doc.getId());
                tokenList.add(token);
            }
            return tokenList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    };

    @Override
    public String create(TokenDTO token){
        CollectionReference tokens = getCollection();
        ApiFuture<DocumentReference> addedDocRef = tokens.add(token);
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
    public Boolean update(String id, TokenDTO token){
        Map<String, Object> docData = getDocData(token);
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
}
