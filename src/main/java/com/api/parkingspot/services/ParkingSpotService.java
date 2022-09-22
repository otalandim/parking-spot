package com.api.parkingspot.services;

import com.api.parkingspot.entities.ParkingSpot;
import com.api.parkingspot.repositories.ParkingSpotRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ParkingSpotService {

    @Autowired
    private ParkingSpotRepository repository;

    @Transactional
    public ParkingSpot save(ParkingSpot entity) {
        return repository.save(entity);
    }
}
