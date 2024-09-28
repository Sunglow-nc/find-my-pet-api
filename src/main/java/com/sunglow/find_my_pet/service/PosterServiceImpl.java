package com.sunglow.find_my_pet.service;

import com.sunglow.find_my_pet.exception.ItemNotFoundException;
import com.sunglow.find_my_pet.model.Owner;
import com.sunglow.find_my_pet.model.Pet;
import com.sunglow.find_my_pet.model.Poster;
import com.sunglow.find_my_pet.repository.OwnerManagerRepository;
import com.sunglow.find_my_pet.repository.PetManagerRepository;
import com.sunglow.find_my_pet.repository.PosterManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PosterServiceImpl implements PosterService {

    @Autowired
    PosterManagerRepository posterManagerRepository;

    @Autowired
    PetManagerRepository petManagerRepository;

    @Autowired
    private OwnerManagerRepository ownerManagerRepository;

    @Override
    public List<Poster> getAllPosters() {
        ArrayList<Poster> posters = new ArrayList<>();
        posterManagerRepository.findAll().forEach(posters::add);
        return posters;
    }

    public Poster getPosterById(Long id) {
        return posterManagerRepository.findById(id)
            .orElseThrow(() -> new ItemNotFoundException("Poster not found for ID: " + id));
    }


    @Override
    @Transactional
    public Poster insertPoster(Poster poster) {
        if (poster.getPet() != null) {
            Pet pet = poster.getPet();
            if (pet.getOwner() != null) {
                Owner owner = pet.getOwner();
                if (owner.getId() == null) {
                    // The owner is new and needs to be saved first
                    owner = ownerManagerRepository.save(owner);
                } else {
                    Long ownerId = owner.getId();
                    owner = ownerManagerRepository.findById(ownerId)
                        .orElseThrow(
                            () -> new ItemNotFoundException("Owner not found for ID: " + ownerId));
                }
                pet.setOwner(owner);
            }

            if (pet.getId() == null) {
                // The pet is new and needs to be saved
                pet = petManagerRepository.save(pet);
            } else {
                Long petId = pet.getId();
                pet = petManagerRepository.findById(petId)
                    .orElseThrow(() -> new ItemNotFoundException("Pet not found for ID: " + petId));
            }
            poster.setPet(pet);
        }
        return posterManagerRepository.save(poster);
    }

    @Override
    @Transactional
    public Optional<Poster> updatePoster(Long id, Poster poster) {
        return posterManagerRepository.findById(id)
            .map(existingPoster -> {
                // Update poster properties
                existingPoster.setDatePosted(poster.getDatePosted());
                existingPoster.setDescription(poster.getDescription());
                existingPoster.setTitle(poster.getTitle());

                // Update pet properties
                if (poster.getPet() != null) {
                    Pet existingPet = existingPoster.getPet();
                    if (existingPet == null) {
                        existingPet = new Pet();
                        existingPoster.setPet(existingPet);
                    }

                    existingPet.setName(poster.getPet().getName());
                    existingPet.setColour(poster.getPet().getColour());
                    existingPet.setAge(poster.getPet().getAge());
                    existingPet.setIsFound(poster.getPet().getIsFound());
                    existingPet.setLongitude(poster.getPet().getLongitude());
                    existingPet.setLatitude(poster.getPet().getLatitude());
                    existingPet.setImageURL(poster.getPet().getImageURL());
                    existingPet.setLostDate(poster.getPet().getLostDate());

                    // Update owner properties
                    if (poster.getPet().getOwner() != null) {
                        Owner existingOwner = existingPet.getOwner();
                        if (existingOwner == null) {
                            existingOwner = new Owner();
                            existingPet.setOwner(existingOwner);
                        }

                        existingOwner.setName(poster.getPet().getOwner().getName());
                        existingOwner.setContactNumber(
                            poster.getPet().getOwner().getContactNumber());
                        existingOwner.setEmailAddress(poster.getPet().getOwner().getEmailAddress());

                        ownerManagerRepository.save(existingOwner);
                    }

                    petManagerRepository.save(existingPet);
                }

                return posterManagerRepository.save(existingPoster);
            });
    }
}
