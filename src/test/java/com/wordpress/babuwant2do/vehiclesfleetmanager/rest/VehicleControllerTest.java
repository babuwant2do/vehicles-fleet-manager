package com.wordpress.babuwant2do.vehiclesfleetmanager.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.wordpress.babuwant2do.vehiclesfleetmanager.domain.Vehicle;
import com.wordpress.babuwant2do.vehiclesfleetmanager.manager.VehicleManager;
import com.wordpress.babuwant2do.vehiclesfleetmanager.rest.dto.VehicleDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@WebMvcTest(VehicleController.class)
class VehicleControllerTest {

    @Autowired
    private MockMvc mvc;

    private static XmlMapper xmlMapper = new XmlMapper();;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private VehicleManager vehicleManager;

    @Test
    void given_validJsonInputData_when_createVehicle_then_responseWithCreated() throws Exception {
        Object vehicleToSave = this.getValidVehicleDto();

        Vehicle vehicleCreated = this.getMockedVehicleCreated();
        given(vehicleManager.createVehicle(any(Vehicle.class))).willReturn(vehicleCreated);

        mvc.perform(post("/vehicle").contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsBytes(vehicleToSave)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.vin").exists())
                .andExpect(jsonPath("$.licensePlateNumber").exists());
    }

    @Test
    void given_invalidJsonInputData_when_createVehicle_then_responseWithBadRequestAndInvalidFieldsOnly() throws Exception {
        Object vehicleToSave = this.getInvalidVehicleDto();

        Vehicle vehicleCreated = this.getMockedVehicleCreated();
        given(vehicleManager.createVehicle(any(Vehicle.class))).willReturn(vehicleCreated);

        mvc.perform(post("/vehicle").contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsBytes(vehicleToSave)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("error.validation"))
                .andExpect(jsonPath("$.fieldErrors").exists())
                .andExpect(jsonPath("$.fieldErrors").isNotEmpty())
                .andExpect(jsonPath("$.fieldErrors[*].field").value(not(hasItem("name"))))
                .andExpect(jsonPath("$.fieldErrors[*].field").value(hasItem("vin")))
                .andExpect(jsonPath("$.fieldErrors[*].field").value(hasItem("licensePlateNumber")))
        ;
    }


    @Test
    void given_validXMLInputData_when_createVehicle_then_responseWithCreated() throws Exception {
        Object vehicleToSave = this.getValidVehicleDto();

        Vehicle vehicleCreated = this.getMockedVehicleCreated();
        given(vehicleManager.createVehicle(any(Vehicle.class))).willReturn(vehicleCreated);

        mvc.perform(post("/vehicle").contentType(MediaType.APPLICATION_XML_VALUE)
                .accept(MediaType.APPLICATION_XML_VALUE)
                .content(xmlMapper.writeValueAsBytes(vehicleToSave)))
                .andExpect(status().isCreated())
                .andExpect(xpath("Vehicle").exists())
                .andExpect(xpath("Vehicle/name").exists())
                .andExpect(xpath("Vehicle/vin").exists())
                .andExpect(xpath("Vehicle/licensePlateNumber").exists());
    }

    /**
     * NOTE: same steps can be repeat for other methods
     */

    private VehicleDto getValidVehicleDto() {
        VehicleDto vehicleToSave = new VehicleDto();
        vehicleToSave.setName("name 1");
        vehicleToSave.setVin("vin 1");
        vehicleToSave.setLicensePlateNumber("Lic 1");
        return vehicleToSave;
    }

    private VehicleDto getInvalidVehicleDto() {
        VehicleDto vehicleToSave = new VehicleDto();
        vehicleToSave.setName("name 1");
        vehicleToSave.setLicensePlateNumber("Lic 1000000000000000");
        return vehicleToSave;
    }

    private Vehicle getMockedVehicleCreated() {
        Vehicle vehicleCreated = new Vehicle();
        vehicleCreated.setName("name 1");
        vehicleCreated.setVin("vin 1");
        vehicleCreated.setLicensePlateNumber("Lic 1");
        return vehicleCreated;
    }
}