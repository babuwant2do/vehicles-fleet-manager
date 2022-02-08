package com.wordpress.babuwant2do.vehiclesfleetmanager.manager;

import com.wordpress.babuwant2do.vehiclesfleetmanager.domain.Vehicle;
import com.wordpress.babuwant2do.vehiclesfleetmanager.exception.ApplicationCustomException;
import com.wordpress.babuwant2do.vehiclesfleetmanager.exception.DataNotFoundException;
import com.wordpress.babuwant2do.vehiclesfleetmanager.repository.VehicleRepository;
import com.wordpress.babuwant2do.vehiclesfleetmanager.rest.errors.ErrorConstants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@Service
public class VehicleManager {
    private static final String NOT_FOUND_ERROR_MSG = "Vehicle with vin '%s' not found!";

    private final VehicleRepository vehicleRepository;

    public VehicleManager(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public Vehicle getVehicle(String vin) {
        return this.vehicleRepository.findByVin(vin)
                .orElseThrow(() -> new DataNotFoundException(String.format(NOT_FOUND_ERROR_MSG, vin)));
    }

    public Page<Vehicle> getAllVehicles(Pageable pageable) {
        return this.vehicleRepository.findAll(pageable);
    }

    public void deleteVehicle(String vin) {
        if(!this.vehicleRepository.existsByVin(vin)) {
            throw new DataNotFoundException(String.format(NOT_FOUND_ERROR_MSG, vin));
        }
        this.vehicleRepository.deleteByVin(vin);
    }

    public Vehicle createVehicle(Vehicle vehicle) {
        this.vehicleRepository.findByVinOrLicensePlateNumber(vehicle.getVin(), vehicle.getLicensePlateNumber())
                .stream().findFirst()
                .ifPresent(duplicateVehicle -> {
                    String errMessage;
                    if(duplicateVehicle.getVin().equalsIgnoreCase(vehicle.getVin())) {
                        errMessage = String.format("Can't Create Vehicle because Vin %s is already exist", vehicle.getVin());
                    } else {
                        errMessage = String.format("Can't Create Vehicle because LicensePlateNumber %s is already exist", vehicle.getLicensePlateNumber());
                    }

                    throw new ApplicationCustomException(errMessage, HttpStatus.CONFLICT, ErrorConstants.ERR_DUPLICATE_KEY_FAILURE);
                });

        return this.vehicleRepository.save(vehicle);
    }

    public Vehicle updateVehicle(String vin, Vehicle vehicle) {
        return this.vehicleRepository.findByVin(vin).map(vehicleToUpdate -> {
            // Just for demonstration purpose 'vin' not modified assuming that vin is not changeable.
            vehicleToUpdate.setName(vehicle.getName());
            vehicleToUpdate.setLicensePlateNumber(vehicle.getLicensePlateNumber());
            vehicleToUpdate.setProperties(vehicle.getProperties());

            return this.vehicleRepository.save(vehicleToUpdate);
        }).orElseThrow(() -> new DataNotFoundException(String.format(NOT_FOUND_ERROR_MSG, vin)));
    }

}
