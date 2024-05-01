package com.gestion.coloc.crud.controllers;


import com.gestion.coloc.crud.models.FlatShare;
import com.gestion.coloc.crud.models.FlatShareApplication;
import com.gestion.coloc.crud.services.FlatShareAppService;
import com.gestion.coloc.crud.services.FlatShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flatshare-applications")
public class FlatShareApplicationController {

    private final FlatShareAppService flatShareAppService;
    private final FlatShareService flatShareService;
    @Autowired
    public FlatShareApplicationController(FlatShareAppService flatShareAppService, FlatShareService flatShareService) {
        this.flatShareAppService = flatShareAppService;
        this.flatShareService = flatShareService;
    }

    @GetMapping
    public ResponseEntity<List<FlatShareApplication>> getApplicationsByFlatShareId(@RequestParam(name = "flatShareId") Long flatShareId) {
        return ResponseEntity.ok(flatShareAppService.getApplicationsByFlatShareId(flatShareId));
    }

    @PostMapping
    public ResponseEntity<FlatShareApplication> sendFlatShareApplication(@RequestParam(name = "username") String username, @RequestParam(name ="flatShareId") Long flatShareId) {
        return ResponseEntity.ok(flatShareAppService.sendFlatShareApplication(username, flatShareId));
    }

    @PostMapping("/approve")
    public ResponseEntity<Void> approveFlatShareApplication(@RequestParam(name = "flatShareId") Long flatShareId, @RequestParam(name = "applicationId") Long applicationId) {
        flatShareService.approveFlatShareApplication(flatShareId, applicationId);
        return ResponseEntity.noContent().build();
    }
}
