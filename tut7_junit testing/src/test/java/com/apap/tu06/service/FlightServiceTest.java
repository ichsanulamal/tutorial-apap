package com.apap.tu06.service;

import com.apap.tu06.model.FlightModel;
import com.apap.tu06.repository.FlightDb;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.util.Optional;

import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
public class FlightServiceTest {
    @Autowired
    private FlightService flightService;

    @MockBean
    private FlightDb flightDb;

    @TestConfiguration // membatasi scope bean menjadi local
    static class FLightServiceTestContextConfiguration {
        @Bean //
        public FlightService flightService() {
            return new FlightServiceImpl();
        }
    }

    @Test
    public void whenValidFlightNumber_thenFlightShouldBeFound() {
        // Given
        FlightModel flightModel = new FlightModel();
        flightModel.setFlightNumber("I765");
        flightModel.setOrigin("Jakarta");
        flightModel.setDestination("Bali");
        flightModel.setTime((new Date(new java.util.Date().getTime())));
        Optional<FlightModel> flight = Optional.of(flightModel);
        Mockito.when(flightDb.findByFlightNumber(flight.get().getFlightNumber())).thenReturn(flight);

        // When
        Optional<FlightModel> found = flightService.getFlightDetailByFlightNumber(flight.get().getFlightNumber());

        // Then
        assertThat(found, Matchers.notNullValue());
        assertThat(found.get().getFlightNumber(), Matchers.equalTo(flightModel.getFlightNumber()));
    }
}
