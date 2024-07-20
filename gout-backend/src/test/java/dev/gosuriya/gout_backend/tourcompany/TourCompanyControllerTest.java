package dev.gosuriya.gout_backend.tourcompany;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import dev.gosuriya.gout_backend.common.enumeration.TourCompanyStatus;
import dev.gosuriya.gout_backend.common.exception.EntityNotFound;
import dev.gosuriya.gout_backend.tourcompany.controller.TourCompanyController;
import dev.gosuriya.gout_backend.tourcompany.dto.RegisterTourCompanyDto;
import dev.gosuriya.gout_backend.tourcompany.model.TourCompany;
import dev.gosuriya.gout_backend.tourcompany.service.TourCompanyService;

@WebMvcTest(TourCompanyController.class)
class TourCompanyControllerTest {

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private TourCompanyService tourCompanyService;

  private MockMvc mockMvc;

  @BeforeEach
  public void setup() {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

  @Test
  void whenCreateTourCompanyThenSuccessful() throws Exception {
    var mockTourCompany = new TourCompany(
        1,
        "Mart Tour",
        TourCompanyStatus.WAITING.name());
    when(tourCompanyService.registerTourCompany(any(RegisterTourCompanyDto.class)))
        .thenReturn(mockTourCompany);
    var payload = new RegisterTourCompanyDto(null, "Mart Tour", "mart", "123456789", null);

    mockMvc.perform(
        post("/api/v1/tour-companies")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(payload)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1));
  }

  @Test
  void whenApproveTourCompanyThenSuccessful() throws Exception {
    var mockTourCompany = new TourCompany(
        1,
        "Mart Tour",
        TourCompanyStatus.APPROVED.name());
    when(tourCompanyService.approvedTourCompany(anyInt()))
        .thenReturn(mockTourCompany);
    mockMvc.perform(
        patch(String.format("/api/v1/tour-companies/%d/approve", 1))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.status").value(TourCompanyStatus.APPROVED.name()));
  }

  @Test
  void whenApproveTourButCompanyNotFoundThenReturn404() throws Exception {
    when(tourCompanyService.approvedTourCompany(anyInt()))
        .thenThrow(new EntityNotFound());
    mockMvc.perform(patch(String.format("/api/v1/tour-companies/%d/approve", 1)))
        .andExpect(status().isNotFound());
  }
}
