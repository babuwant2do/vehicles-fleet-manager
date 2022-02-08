package com.wordpress.babuwant2do.vehiclesfleetmanager.exception;

import org.springframework.dao.DataAccessException;

public class DataNotFoundException  extends DataAccessException {
    public DataNotFoundException(String msg) {
        super(msg);
    }
}
