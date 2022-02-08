package com.wordpress.babuwant2do.vehiclesfleetmanager.converter;

import com.wordpress.babuwant2do.vehiclesfleetmanager.domain.Vehicle;
import com.wordpress.babuwant2do.vehiclesfleetmanager.rest.dto.VehicleDto;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class VehicleToVehicleDtoConverter implements Converter<Vehicle, Optional<VehicleDto>> {
    @Override
    public Optional<VehicleDto> convert(Vehicle source) {
        if(source == null) return Optional.empty();

        VehicleDto vehicle = new VehicleDto();
        vehicle.setName(source.getName());
        vehicle.setVin(source.getVin());
        vehicle.setLicensePlateNumber(source.getLicensePlateNumber());
        vehicle.setProperties(SerializationUtils.clone(source.getProperties()));
        return Optional.of(vehicle);
    }
}
