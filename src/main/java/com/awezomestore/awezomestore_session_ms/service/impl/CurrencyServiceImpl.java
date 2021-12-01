package com.awezomestore.awezomestore_session_ms.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.awezomestore.awezomestore_session_ms.dto.CurrencyDTO;
import com.awezomestore.awezomestore_session_ms.service.CurrencyService;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;

import org.springframework.stereotype.Service;

@Service
public class CurrencyServiceImpl implements CurrencyService {

    private CollectionReference getCollection(){
        return FirestoreClient.getFirestore().collection("currencies");
    }

    private Map<String, Object> getDocData(CurrencyDTO currency){
        Map<String, Object> docData = new HashMap<>();
        docData.put("currencyName", currency.getCurrencyName());
        docData.put("currencyAbbreviation", currency.getCurrencyAbbreviation());
        docData.put("currencySymbol", currency.getCurrencySymbol());
        return docData;
    }

    @Override
    public List<CurrencyDTO> getAll(){
        List<CurrencyDTO> currencyList = new ArrayList<>();
        CurrencyDTO currency;

        try {
            for (DocumentSnapshot doc : getCollection().get().get().getDocuments()) {
                currency = doc.toObject(CurrencyDTO.class);
                currency.setId(doc.getId());
                currencyList.add(currency);
            }
            return currencyList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    };

    @Override
    public Boolean create(CurrencyDTO currency){
        Map<String, Object> docData = getDocData(currency);
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
    public Boolean update(String id, CurrencyDTO currency){
        Map<String, Object> docData = getDocData(currency);
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
