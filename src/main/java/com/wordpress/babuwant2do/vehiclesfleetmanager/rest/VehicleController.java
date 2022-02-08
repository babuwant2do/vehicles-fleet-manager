package com.wordpress.babuwant2do.vehiclesfleetmanager.rest;

import com.wordpress.babuwant2do.vehiclesfleetmanager.converter.VehicleDtoToVehicleConverter;
import com.wordpress.babuwant2do.vehiclesfleetmanager.converter.VehicleToVehicleDtoConverter;
import com.wordpress.babuwant2do.vehiclesfleetmanager.exception.ApplicationCustomException;
import com.wordpress.babuwant2do.vehiclesfleetmanager.manager.VehicleManager;
import com.wordpress.babuwant2do.vehiclesfleetmanager.rest.dto.VehicleDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller

@Slf4j
@RestController
@RequestMapping("vehicle")
public class VehicleController {
    private final VehicleManager vehicleManager;
    private final VehicleDtoToVehicleConverter vehicleDtoToVehicleConverter;
    private final VehicleToVehicleDtoConverter vehicleToVehicleDtoConverter;

    public VehicleController(VehicleManager vehicleManager, VehicleDtoToVehicleConverter vehicleDtoToVehicleConverter, VehicleToVehicleDtoConverter vehicleToVehicleDtoConverter) {
        this.vehicleManager = vehicleManager;
        this.vehicleDtoToVehicleConverter = vehicleDtoToVehicleConverter;
        this.vehicleToVehicleDtoConverter = vehicleToVehicleDtoConverter;
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<VehicleDto> createVehicle(@RequestBody @Valid VehicleDto vehicleDto) {
        return this.vehicleDtoToVehicleConverter.convert(vehicleDto)
                .map(vehicle -> {
                    VehicleDto vehicleDtoCreated = this.vehicleToVehicleDtoConverter.convert(this.vehicleManager.createVehicle(vehicle)).orElse(null);
                    return new ResponseEntity<>(vehicleDtoCreated, HttpStatus.CREATED);
                })
                .orElseThrow(() -> new ApplicationCustomException("Can't save null/invalid Vehicle"));
    }

    @PutMapping(value = "{vin}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<VehicleDto> updateVehicle(@PathVariable("vin") String vin, @RequestBody @Valid VehicleDto vehicleDto) {
        return this.vehicleDtoToVehicleConverter.convert(vehicleDto)
                .map(vehicle -> {
                    VehicleDto vehicleDtoUpdated = this.vehicleToVehicleDtoConverter.convert(this.vehicleManager.updateVehicle(vin, vehicle)).orElse(null);
                    return new ResponseEntity<>(vehicleDtoUpdated, HttpStatus.CREATED);
                }).orElseThrow(() -> new ApplicationCustomException("Can't update invalid Vehicle"));
    }

    @GetMapping(value = "{vin}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<VehicleDto> getVehicle(@PathVariable("vin") String vin) {
        VehicleDto vehicleDto = this.vehicleToVehicleDtoConverter.convert(this.vehicleManager.getVehicle(vin)).orElse(null);
        return new ResponseEntity<>(vehicleDto, HttpStatus.OK);
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity< List<VehicleDto>> getAllVehicles(Pageable pageable) {
        List<VehicleDto> vehicleDtoList =  this.vehicleManager.getAllVehicles(pageable).getContent().stream()
                .map(vehicle -> this.vehicleToVehicleDtoConverter.convert(vehicle).orElse(null))
                .filter(vehicleDto -> vehicleDto != null)
                .collect(Collectors.toList());

        return new ResponseEntity<>(vehicleDtoList, HttpStatus.OK);
    }

    @DeleteMapping(value = "{vin}")
    public ResponseEntity<Object> deleteVehicle(@PathVariable("vin") String vin) {
        this.vehicleManager.deleteVehicle(vin);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
