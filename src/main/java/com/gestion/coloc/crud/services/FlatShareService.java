package com.gestion.coloc.crud.services;



import com.gestion.coloc.crud.models.FlatShare;

import java.util.List;
import java.util.Optional;

public interface FlatShareService {

    FlatShare createFlatShare(FlatShare flatShare, String ownerUsername);
    FlatShare updateFlatShare(Long flatShareId, FlatShare flatShare);
    void deleteFlatShare(Long flatShareId);
    Optional<FlatShare> getFlatShareByOwnerUsername(String username);
    List<FlatShare> getAllAvailableFlatShares();
    void approveFlatShareApplication(Long flatShareId, Long applicationId);


}
