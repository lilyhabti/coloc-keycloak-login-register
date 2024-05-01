package com.gestion.coloc.crud.services.imp;

import com.gestion.coloc.crud.models.*;
import com.gestion.coloc.crud.repositories.FlatShareAppRepository;
import com.gestion.coloc.crud.repositories.FlatShareRepository;
import com.gestion.coloc.crud.repositories.UserRepository;
import com.gestion.coloc.crud.services.FlatShareAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlatShareAppServiceImp implements FlatShareAppService {

    private final FlatShareAppRepository flatShareApplicationRepository;
    private final UserRepository userRepository;
    private final FlatShareRepository flatShareRepository;

    @Autowired
    public FlatShareAppServiceImp(UserRepository userRepository, FlatShareRepository flatShareRepository, FlatShareAppRepository flatShareApplicationRepository) {
        this.userRepository = userRepository;
        this.flatShareRepository = flatShareRepository;
        this.flatShareApplicationRepository = flatShareApplicationRepository;
    }

    public List<FlatShareApplication> getApplicationsByFlatShareId(Long flatShareId) {
        // Récupérer les demandes de colocation associées à la colocation spécifiée par son ID
        return flatShareApplicationRepository.findApplicationsByFlatShareId(flatShareId);
    }

    public FlatShareApplication sendFlatShareApplication(String username, Long flatShareId) {
        // Récupérer l'utilisateur par son nom d'utilisateur
        User applicant = userRepository.findByUsername(username);

        // Récupérer la colocation par son ID
        FlatShare flatShare = flatShareRepository.findById(flatShareId)
                .orElseThrow(() -> new RuntimeException("FlatShare not found"));

        // Vérifier si l'utilisateur est déjà membre de la colocation
        if (applicant.getFlatShareColocs() != null && applicant.getFlatShareColocs().getIdFlat().equals(flatShareId)) {
            throw new RuntimeException("User is already a member of this FlatShare");
        }

        // Créer une nouvelle demande d'adhésion
        FlatShareApplication application = new FlatShareApplication();
        application.setStatus(ApplicationStatus.EnAttente);
        application.setApplicant(applicant);
        application.setFlatShare(flatShare);

        // Enregistrer la demande d'adhésion dans la base de données
        flatShareApplicationRepository.save(application);
        return application;
    }
}
