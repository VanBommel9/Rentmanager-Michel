package com.epf.rentmanager.model;

import com.epf.rentmanager.constraints.ClientConstraints;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.service.ClientService;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static org.junit.Assert.*;

@DisplayName("Test associé à la classe Client")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestModelClient {

    private ClientService clientService;

    @Test
    @Order(1)
    public void testGetClient() {

        LocalDate date = LocalDate.parse("1990-07-09");
        Client client = new Client(1,
                "destames",
                "charles",
                "charlesdest@test.com",
                date);
        assertEquals(1,client.getId());
        assertEquals("destames", client.getNom());
        assertEquals("charles",client.getPrenom());
        assertEquals("charlesdest@test.com",client.getEmail());
        assertEquals(date ,client.getNaissance());
    }

    @Test
    @Order(2)
    public void validEmail() throws ServiceException {
        LocalDate date = LocalDate.parse("1990-07-09");
        Client client = new Client(1,
                "destames",
                "charles",
                "charlesdest@test.com",
                date);

        assertEquals( clientService.create(client),0);
    }

}
