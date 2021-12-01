package com.awezomestore.awezomestore_session_ms.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.awezomestore.awezomestore_session_ms.dto.InfoDTO;
import com.awezomestore.awezomestore_session_ms.service.InfoService;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;

import org.springframework.stereotype.Service;

@Service
public class InfoServiceImpl implements InfoService {

    private CollectionReference getCollection(){
        return FirestoreClient.getFirestore().collection("informations");
    }

    private Map<String, Object> getDocData(InfoDTO info){
        Map<String, Object> docData = new HashMap<>();
        docData.put("userId", info.getUserId());
        docData.put("countryId", info.getCountryId());
        docData.put("currencyId", info.getCurrencyId());
        docData.put("languageId", info.getLanguageId());
        docData.put("email", info.getEmail());
        docData.put("phone", info.getPhone());
        return docData;
    }

    @Override
    public List<InfoDTO> getAll(){
        List<InfoDTO> infoList = new ArrayList<>();
        InfoDTO info;

        try {
            for (DocumentSnapshot doc : getCollection().get().get().getDocuments()) {
                info = doc.toObject(InfoDTO.class);
                info.setId(doc.getId());
                infoList.add(info);
            }
            return infoList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    };

    @Override
    public Boolean create(InfoDTO info){
        Map<String, Object> docData = getDocData(info);
        CollectionReference infos = getCollection();
        ApiFuture<WriteResult> writeResultApiFurute = infos.document().create(docData);

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
    public Boolean update(String id, InfoDTO info){
        Map<String, Object> docData = getDocData(info);
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
