package com.api.parkingspot.services;

import com.api.parkingspot.entities.ParkingSpot;
import com.api.parkingspot.repositories.ParkingSpotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ParkingSpotService {

    @Autowired
    private ParkingSpotRepository repository;

    @Transactional
    public ParkingSpot save(ParkingSpot entity) {
        return repository.save(entity);
    }

    public boolean existsByLicensePlateCar(String licensePlateCar) {
        return repository.existsByLicensePlateCar(licensePlateCar);
    }

    public boolean existsByParkingSpotNumber(String parkingSpotNumber) {
        return repository.existsByParkingSpotNumber(parkingSpotNumber);
    }


    public boolean existsByApartamentAndBlock(String apartament, String block) {
        return repository.existsByApartamentAndBlock(apartament, block);
    }

    public List<ParkingSpot> findAll() {
        return repository.findAll();
    }

    public Optional<ParkingSpot> findById(UUID id) {
        return repository.findById(id);
    }

}
