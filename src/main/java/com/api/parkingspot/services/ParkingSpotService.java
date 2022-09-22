package com.api.parkingspot.services;

import com.api.parkingspot.repositories.ParkingSpotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParkingSpotService {

    @Autowired
    private ParkingSpotRepository repository;
}
