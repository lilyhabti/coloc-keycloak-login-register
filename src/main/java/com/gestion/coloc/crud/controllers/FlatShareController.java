package com.gestion.coloc.crud.controllers;


import com.gestion.coloc.crud.models.FlatShare;
import com.gestion.coloc.crud.services.FlatShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/flatshares")
public class FlatShareController {


    private final FlatShareService flatShareService;
    @Autowired
    public FlatShareController(FlatShareService flatShareService) {
        this.flatShareService = flatShareService;
    }

    @GetMapping
    public ResponseEntity<List<FlatShare>> getAllFlatShares() {
        return ResponseEntity.ok(flatShareService.getAllAvailableFlatShares());
    }

    @PostMapping
    public ResponseEntity<FlatShare> createFlatShare(@RequestBody FlatShare flatShare, @RequestParam("ownerUsername") String ownerUsername) {
        return ResponseEntity.ok(flatShareService.createFlatShare(flatShare,ownerUsername));
    }

    @GetMapping("/{username}")
    public ResponseEntity<FlatShare> getFlatShareByOwnerUsername(@PathVariable String username) {
        Optional<FlatShare> flatShareOptional = flatShareService.getFlatShareByOwnerUsername(username);

        return flatShareOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlatShare> updateFlatShare(@PathVariable Long id, @RequestBody FlatShare flatShare) {
        return ResponseEntity.ok(flatShareService.updateFlatShare(id,flatShare));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlatShare(@PathVariable Long id) {
        flatShareService.deleteFlatShare(id);
        return ResponseEntity.noContent().build();
    }

    /*@GetMapping("/available")
    public ResponseEntity<List<FlatShare>> getAllAvailableFlatShares() {
        return ResponseEntity.ok(flatShareService.getAllAvailableFlatShares());
    }*/
}
