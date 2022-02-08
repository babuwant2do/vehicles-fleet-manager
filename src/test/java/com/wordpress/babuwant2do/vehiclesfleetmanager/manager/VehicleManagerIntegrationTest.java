package com.wordpress.babuwant2do.vehiclesfleetmanager.manager;

import com.wordpress.babuwant2do.vehiclesfleetmanager.domain.Vehicle;
import com.wordpress.babuwant2do.vehiclesfleetmanager.exception.ApplicationCustomException;
import com.wordpress.babuwant2do.vehiclesfleetmanager.repository.VehicleRepository;
import com.wordpress.babuwant2do.vehiclesfleetmanager.rest.errors.ErrorConstants;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class VehicleManagerIntegrationTest {
    @Autowired
    private VehicleManager vehicleManager;

    @Autowired
    private VehicleRepository vehicleRepository;

    @BeforeEach
    @AfterEach
    public void cleanupDatabase() {
        this.vehicleRepository.deleteAll();
    }

    @Test
    public void given_validNewVehicle_when_createVehicle_then_saveAllInputValuesCorrectly() {
        // GIVEN
        Vehicle vehicle = new Vehicle();
        vehicle.setName("name 1");
        vehicle.setVin("vin 1");
        vehicle.setLicensePlateNumber("Lic 1");
        HashMap<String, String> properties = new HashMap<>();
        properties.put("key1", "val1");
        properties.put("key2", "val2");
        vehicle.setProperties(properties);

        // WHEN
        Vehicle createdVehicle = this.vehicleManager.createVehicle(vehicle);

        //THEN
        assertTrue(!createdVehicle.getId().isBlank());
        assertEquals("name 1", createdVehicle.getName());
        assertEquals("vin 1", createdVehicle.getVin());
        assertEquals("Lic 1", createdVehicle.getLicensePlateNumber());

        assertTrue(createdVehicle.getProperties().containsKey("key1"), "property name 'val1' is present");
        assertTrue(createdVehicle.getProperties().containsKey("key2"));
        assertEquals("val1", createdVehicle.getProperties().get("key1"));
        assertEquals("val2", createdVehicle.getProperties().get("key2"));
    }

    /**
     * Same test can be written for only duplicate 'Vin' or 'LicensePlateNumber'
     */
    @Test
    public void given_VehicleWithSameVinAndLicensePlateNumber_when_createVehicle_then_throwsApplicationCustomExceptionWithHttpStatusConflict() {
        // GIVEN
        Vehicle vehicle = new Vehicle();
        vehicle.setName("name 1");
        vehicle.setVin("vin 1");
        vehicle.setLicensePlateNumber("Lic 1");
        HashMap<String, String> properties = new HashMap<>();
        properties.put("key1", "val1");
        properties.put("key2", "val2");
        vehicle.setProperties(properties);

        Vehicle vehicleDuplicate = new Vehicle();
        vehicleDuplicate.setVin("vin 1");

        //WHEN AND THEN: create Vehicle with same vin.
        this.vehicleManager.createVehicle(vehicle);
        ApplicationCustomException thrown = assertThrows(
                ApplicationCustomException.class,
                () -> this.vehicleManager.createVehicle(vehicleDuplicate), "Expected createVehicle(vehicle) to throw, but it didn't"
        );

        assertEquals(HttpStatus.CONFLICT, thrown.getStatus());
        assertEquals(ErrorConstants.ERR_DUPLICATE_KEY_FAILURE, thrown.getDescription());
    }

    @Test
    public void given_VehicleWithValidVin_when_updateVehicle_then_updateAllInputValuesExceptVin() {
        // GIVEN: a new Vehicle
        Vehicle vehicle = new Vehicle();
        vehicle.setName("name 1");
        vehicle.setVin("vin 1");
        vehicle.setLicensePlateNumber("Lic 1");
        HashMap<String, String> properties = new HashMap<>();
        properties.put("key1", "val1");
        properties.put("key2", "val2");
        vehicle.setProperties(properties);
        this.vehicleManager.createVehicle(vehicle);

        //WHEN: updated Vehicle
        Vehicle updatedVehicle = new Vehicle();
        updatedVehicle.setVin("vin X");
        updatedVehicle.setName("name X");
        updatedVehicle.setVin("vin X");
        updatedVehicle.setLicensePlateNumber("Lic X");
        HashMap<String, String> updatedProperties = new HashMap<>();
        updatedProperties.put("key1", "valX1");
        updatedProperties.put("key2", "valX2");
        updatedProperties.put("key3", "valX3");
        updatedVehicle.setProperties(updatedProperties);

        updatedVehicle = this.vehicleManager.updateVehicle("vin 1", updatedVehicle);

        // THEN
        assertEquals("vin 1", updatedVehicle.getVin());

        assertEquals(updatedVehicle.getId(), updatedVehicle.getId());
        assertEquals("name X", updatedVehicle.getName());
        assertEquals("Lic X", updatedVehicle.getLicensePlateNumber());

        assertEquals(3, updatedVehicle.getProperties().size());
        assertTrue(updatedVehicle.getProperties().containsKey("key1"), "Property 'key1' expected to exist but not.");
        assertTrue(updatedVehicle.getProperties().containsKey("key2"), "Property 'key2' expected to exist but not.");
        assertTrue(updatedVehicle.getProperties().containsKey("key3"), "Property 'key3' expected to exist but not.");
        assertEquals("valX1", updatedVehicle.getProperties().get("key1"));
        assertEquals("valX2", updatedVehicle.getProperties().get("key2"));
        assertEquals("valX3", updatedVehicle.getProperties().get("key3"));
    }

    /**
     * TODO: following test case can be written, here skipped as it is just a demo
     */
    @Test
    public void given_validVin_when_deleteVehicle_then_VehicleNotExistInDatabase(){
        assertTrue(true);
    }

    @Test
    public void given_invalidVin_when_deleteVehicle_then_throwsDataNotFoundException() {
        assertTrue(true);
    }

    @Test
    public void given_PageableWithPageSize2_when_getAllVehicles_then_receivesListOfVehicleWithSize2() {
        assertTrue(true);
        // insert 5 Vehicle
        // call getAllVehicles()
        // check size of Vehicles
    }
}