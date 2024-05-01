package com.gestion.coloc.crud.services.imp;

import com.gestion.coloc.crud.models.FlatShare;
import com.gestion.coloc.crud.models.User;
import com.gestion.coloc.crud.models.FlatShareApplication;
import com.gestion.coloc.crud.repositories.FlatShareAppRepository;
import com.gestion.coloc.crud.repositories.FlatShareRepository;
import com.gestion.coloc.crud.repositories.UserRepository;
import com.gestion.coloc.crud.services.FlatShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FlatShareServiceImp implements FlatShareService {

    private final FlatShareRepository flatShareRepository;
    private final FlatShareAppRepository flatShareApplicationRepository;
    private final UserRepository userRepository;

    @Autowired
    public FlatShareServiceImp(FlatShareRepository flatShareRepository, FlatShareAppRepository flatShareApplicationRepository, UserRepository userRepository) {
        this.flatShareRepository = flatShareRepository;
        this.flatShareApplicationRepository = flatShareApplicationRepository;
        this.userRepository = userRepository;
    }


    public FlatShare createFlatShare(FlatShare flatShare, String ownerUsername) {
        // Fetch the owner user from the database based on the provided username
        Optional<User> ownerOptional = Optional.ofNullable(userRepository.findByUsername(ownerUsername));

        if (ownerOptional.isPresent()) {
            User owner = ownerOptional.get();

            // Set the owner of the flatshare
            flatShare.setOwner(owner);

            // Add the owner to the list of roommates of the flatshare
            flatShare.getRoomates().add(owner);

            // Set the flatshare as the colocation for the owner
            owner.setFlatShareColocs(flatShare);

            // Save the flatshare and owner
            flatShareRepository.save(flatShare);
            userRepository.save(owner);

            return flatShare;
        } else {
            throw new IllegalArgumentException("Owner with username " + ownerUsername + " not found.");
        }
    }

    // Modifier une colocation
    public FlatShare updateFlatShare(Long flatShareId, FlatShare flatShare) {
        FlatShare existingFlatShare = flatShareRepository.findById(flatShareId)
                .orElseThrow(() -> new RuntimeException("FlatShare not found with id: " + flatShareId));

        if (flatShare.getDescriptionF() != null) {
            existingFlatShare.setDescriptionF(flatShare.getDescriptionF());
        }
        if (flatShare.getAddress() != null) {
            existingFlatShare.setAddress(flatShare.getAddress());
        }
        if (flatShare.getNumberOfRooms() > 0) {
            existingFlatShare.setNumberOfRooms(flatShare.getNumberOfRooms());
        }
        if (flatShare.getNumberOfRoomsOccupied() > 0) {
            existingFlatShare.setNumberOfRoomsOccupied(flatShare.getNumberOfRoomsOccupied());
        }
        if (flatShare.getFlatPic() != null) {
            existingFlatShare.setFlatPic(flatShare.getFlatPic());
        }

        return flatShareRepository.save(existingFlatShare);
    }

    // Supprimer une colocation
    public void deleteFlatShare(Long flatShareId) {
        flatShareRepository.deleteById(flatShareId);
    }

    // Obtenir une colocation par ID
    public Optional<FlatShare> getFlatShareByOwnerUsername(String username) {
        // Rechercher l'utilisateur par son nom d'utilisateur
        User user = userRepository.findByUsername(username);

        // Vérifier si l'utilisateur existe
        if (user == null) {
            return Optional.empty(); // Renvoyer un Optional vide si l'utilisateur n'est pas trouvé
        }

        // Récupérer l'appartement associé à l'utilisateur
        FlatShare flatShare = user.getFlatShareColocs();

        // Retourner l'appartement (peut être null si l'utilisateur n'a pas d'appartement associé)
        return Optional.ofNullable(flatShare);
    }

    public List<FlatShare> getAllAvailableFlatShares() {
        return flatShareRepository.findAllAvailableFlatShares();
    }
    @Transactional
    public void approveFlatShareApplication(Long flatShareId, Long applicationId) {
        // Récupérer la colocation correspondant à l'ID
        FlatShare flatShare = flatShareRepository.findById(flatShareId)
                .orElseThrow(() -> new RuntimeException("Colocation introuvable"));

        // Récupérer la demande de colocation correspondant à l'ID
        FlatShareApplication application = flatShareApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Demande de colocation introuvable"));

        // Vérifier si l'utilisateur connecté est le propriétaire de la colocation
        if (!flatShare.getOwner().getIdUser().equals(application.getFlatShare().getOwner().getIdUser())) {
            throw new RuntimeException("Vous n'êtes pas autorisé à approuver cette demande.");
        }

        // Approuver la demande en ajoutant le demandeur à la liste des colocataires
        List<User> rooms = new ArrayList<>();
        rooms = flatShare.getRoomates();
        rooms.add(application.getApplicant());
        flatShare.setRoomates(rooms);

        // Enregistrer les modifications dans la base de données
        flatShareRepository.save(flatShare); // Save the updated flat share
        userRepository.save(application.getApplicant());

        // Supprimer la demande de la liste des demandes de colocation
        flatShareApplicationRepository.delete(application);
    }
}
