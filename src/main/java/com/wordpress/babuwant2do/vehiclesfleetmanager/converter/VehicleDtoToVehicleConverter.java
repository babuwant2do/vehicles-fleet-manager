package com.wordpress.babuwant2do.vehiclesfleetmanager.converter;

import com.wordpress.babuwant2do.vehiclesfleetmanager.domain.Vehicle;
import com.wordpress.babuwant2do.vehiclesfleetmanager.rest.dto.VehicleDto;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class VehicleDtoToVehicleConverter implements Converter<VehicleDto, Optional<Vehicle>> {
    @Override
    public Optional<Vehicle> convert(VehicleDto source) {
        if(source == null) return Optional.empty();

        Vehicle vehicle = new Vehicle();
        vehicle.setName(source.getName());
        vehicle.setVin(source.getVin());
        vehicle.setLicensePlateNumber(source.getLicensePlateNumber());
        vehicle.setProperties(SerializationUtils.clone(source.getProperties()));
        return Optional.of(vehicle);
    }
}
