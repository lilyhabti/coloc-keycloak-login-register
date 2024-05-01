package com.gestion.coloc.crud.services;



import com.gestion.coloc.crud.models.FlatShareApplication;

import java.util.List;

public interface FlatShareAppService {

    List<FlatShareApplication> getApplicationsByFlatShareId(Long flatShareId);
    FlatShareApplication sendFlatShareApplication(String username, Long flatShareId);
}
