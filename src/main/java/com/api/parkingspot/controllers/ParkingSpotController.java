package com.api.parkingspot.controllers;

import com.api.parkingspot.dtos.ParkingSpotDto;
import com.api.parkingspot.entities.ParkingSpot;
import com.api.parkingspot.services.ParkingSpotService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/parking-spot")
public class ParkingSpotController {

    @Autowired
    private ParkingSpotService service;

    @PostMapping
    public ResponseEntity<Object> saveParkingSpot(@RequestBody @Valid ParkingSpotDto dto) {
        if(service.existsByLicensePlateCar(dto.getLicensePlateCar())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("License Plate Car is already in use");
        }
        if(service.existsByParkingSpotNumber(dto.getParkingSpotNumber())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Parking Spot is already in use");
        }
        if(service.existsByApartamentAndBlock(dto.getApartament(), dto.getBlock())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Parking Sport already registered for apartament/block");
        }

        var entity = new ParkingSpot();
        BeanUtils.copyProperties(dto, entity);
        entity.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(entity));
    }
}
