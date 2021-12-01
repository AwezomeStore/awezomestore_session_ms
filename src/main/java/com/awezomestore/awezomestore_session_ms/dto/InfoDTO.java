package com.awezomestore.awezomestore_session_ms.dto;

import lombok.Data;

@Data
public class InfoDTO{
    
    private String id;
    private String userId;
    private String countryId;
    private String currencyId;
    private String languageId;
    private String email;
    private String phone;
}