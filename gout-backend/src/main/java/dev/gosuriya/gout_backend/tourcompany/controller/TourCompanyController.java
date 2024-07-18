package dev.gosuriya.gout_backend.tourcompany.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.gosuriya.gout_backend.tourcompany.dto.RegisterTourCompanyDto;
import dev.gosuriya.gout_backend.tourcompany.model.TourCompany;
import dev.gosuriya.gout_backend.tourcompany.service.TourCompanyService;

@RestController
@RequestMapping("/api/v1/tour-companies")
public class TourCompanyController {
  private final Logger logger = LoggerFactory.getLogger(TourCompanyController.class);

  private final TourCompanyService tourCompanyService;

  public TourCompanyController(TourCompanyService tourCompanyService) {
    this.tourCompanyService = tourCompanyService;
  }

  @PostMapping
  public ResponseEntity<TourCompany> registerNewTourCompany(
      @RequestBody @Validated RegisterTourCompanyDto body) {
    var tourCompany = tourCompanyService.registerTourCompany(body);
    return ResponseEntity.ok(tourCompany);
  }

  @PatchMapping("/{id}/approve")
  public ResponseEntity<TourCompany> approvedCompany(@PathVariable("id") Integer id) {
    var approvedTourCompany = tourCompanyService.approvedTourCompany(id);
    logger.info("[approvedCompany] company id: {} is approved", id);
    return ResponseEntity.ok(approvedTourCompany);
  }

}
