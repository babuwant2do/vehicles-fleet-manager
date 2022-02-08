package com.wordpress.babuwant2do.vehiclesfleetmanager.rest.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.wordpress.babuwant2do.vehiclesfleetmanager.domain.VehicleBase;
import lombok.Data;

/**
 * DTO class is introduced here to separate/loosely-couple Domain model from the user Input.
 * This facilitate to change Domain models without breaking the existing endpoints.
 */
@Data
@JacksonXmlRootElement(localName ="Vehicle")
public class VehicleDto extends VehicleBase {

}
