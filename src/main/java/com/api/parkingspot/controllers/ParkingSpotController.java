package com.api.parkingspot.controllers;

import com.api.parkingspot.dtos.ParkingSpotDto;
import com.api.parkingspot.entities.ParkingSpot;
import com.api.parkingspot.services.ParkingSpotService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    @GetMapping
    public ResponseEntity<Page<ParkingSpot>> findAll(@PageableDefault(
                page = 0, size = 10,
                sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable(value = "id") UUID id) {
        Optional<ParkingSpot> entity = service.findById(id);
        if(!entity.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(entity.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") UUID id) {
        Optional<ParkingSpot> entity = service.findById(id);
        if(!entity.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found");
        }
        service.delete(entity.get());
        return ResponseEntity.status(HttpStatus.OK).body("Parking Sport deleted successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable(value = "id") UUID id, @RequestBody @Valid ParkingSpotDto dto) {
        Optional<ParkingSpot> entity = service.findById(id);
        if(!entity.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found");
        }
        var parking = new ParkingSpot();
        BeanUtils.copyProperties(dto, parking);
        parking.setId(entity.get().getId());
        parking.setRegistrationDate(entity.get().getRegistrationDate());
        return ResponseEntity.status(HttpStatus.OK).body(service.save(parking));
    }
}
