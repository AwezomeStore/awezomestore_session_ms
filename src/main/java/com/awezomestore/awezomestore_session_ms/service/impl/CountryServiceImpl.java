package com.awezomestore.awezomestore_session_ms.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.awezomestore.awezomestore_session_ms.dto.CountryDTO;
import com.awezomestore.awezomestore_session_ms.service.CountryService;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;

import org.springframework.stereotype.Service;

@Service
public class CountryServiceImpl implements CountryService {

    private CollectionReference getCollection(){
        return FirestoreClient.getFirestore().collection("countries");
    }

    private Map<String, Object> getDocData(CountryDTO country){
        Map<String, Object> docData = new HashMap<>();
        docData.put("countryName", country.getCountryName());
        docData.put("countryCode", country.getCountryCode());
        return docData;
    }

    @Override
    public List<CountryDTO> getAll(){
        List<CountryDTO> countryList = new ArrayList<>();
        CountryDTO country;

        try {
            for (DocumentSnapshot doc : getCollection().get().get().getDocuments()) {
                country = doc.toObject(CountryDTO.class);
                country.setId(doc.getId());
                countryList.add(country);
            }
            return countryList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    };

    @Override
    public Boolean create(CountryDTO country){
        Map<String, Object> docData = getDocData(country);
        CollectionReference countries = getCollection();
        ApiFuture<WriteResult> writeResultApiFurute = countries.document().create(docData);

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
    public Boolean update(String id, CountryDTO country){
        Map<String, Object> docData = getDocData(country);
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
