package com.awezomestore.awezomestore_session_ms.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.awezomestore.awezomestore_session_ms.dto.LanguageDTO;
import com.awezomestore.awezomestore_session_ms.service.LanguageService;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;

import org.springframework.stereotype.Service;

@Service
public class LanguageServiceImpl implements LanguageService {

    private CollectionReference getCollection(){
        return FirestoreClient.getFirestore().collection("languages");
    }

    private Map<String, Object> getDocData(LanguageDTO language){
        Map<String, Object> docData = new HashMap<>();
        docData.put("languageName", language.getLanguageName());
        docData.put("languageAbbreviation", language.getLanguageAbbreviation());
        return docData;
    }

    @Override
    public List<LanguageDTO> getAll(){
        List<LanguageDTO> languageList = new ArrayList<>();
        LanguageDTO language;

        try {
            for (DocumentSnapshot doc : getCollection().get().get().getDocuments()) {
                language = doc.toObject(LanguageDTO.class);
                language.setId(doc.getId());
                languageList.add(language);
            }
            return languageList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    };

    @Override
    public Boolean create(LanguageDTO language){
        Map<String, Object> docData = getDocData(language);
        CollectionReference languages = getCollection();
        ApiFuture<WriteResult> writeResultApiFurute = languages.document().create(docData);

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
    public Boolean update(String id, LanguageDTO language){
        Map<String, Object> docData = getDocData(language);
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
