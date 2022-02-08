package com.wordpress.babuwant2do.vehiclesfleetmanager.converter;

import com.wordpress.babuwant2do.vehiclesfleetmanager.domain.Vehicle;
import com.wordpress.babuwant2do.vehiclesfleetmanager.rest.dto.VehicleDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class VehicleDtoToVehicleConverterTest {

    private static VehicleDtoToVehicleConverter vehicleDtoToVehicleConverter;

    @BeforeAll
    public static void init() {
        vehicleDtoToVehicleConverter = new VehicleDtoToVehicleConverter();
    }

    @Test
    public void givenVehicleDto_whenConvertToVehicle_thenGetVehicleWithAllValues() {
        // GIVEN
        VehicleDto vehicleDto = new VehicleDto();
        vehicleDto.setVin("vin 1");
        vehicleDto.setName("name 1");
        vehicleDto.setLicensePlateNumber("lic 1");
        HashMap<String, String> properties = new HashMap<>();
        properties.put("key1", "val1");
        properties.put("key2", "val2");
        vehicleDto.setProperties(properties);

        // WHEN
        Optional<Vehicle> vehicleOptional =  vehicleDtoToVehicleConverter.convert(vehicleDto);

        // THEN
        assertEquals(true, vehicleOptional.isPresent(), "Vehicle expected not be empty but it is not.");
        Vehicle vehicle = vehicleOptional.get();
        assertEquals(null, vehicle.getId());
        assertEquals("name 1", vehicle.getName());
        assertEquals("vin 1", vehicle.getVin());
        assertEquals("lic 1", vehicle.getLicensePlateNumber());

        assertTrue(vehicle.getProperties().containsKey("key1"), "property name 'val1' is present");
        assertTrue(vehicle.getProperties().containsKey("key2"));
        assertEquals("val1", vehicle.getProperties().get("key1"));
        assertEquals("val2", vehicle.getProperties().get("key2"));

    }

    @Test
    public void givenNull_whenConvertToVehicle_thenGetEmptyVehicle() {
        // GIVEN
        VehicleDto vehicleDto = null;

        // WHEN
        Optional<Vehicle> vehicleOptional =  vehicleDtoToVehicleConverter.convert(vehicleDto);

        // THEN
        assertEquals(true, vehicleOptional.isEmpty(), "Vehicle expected be empty but it is not.");
    }
}