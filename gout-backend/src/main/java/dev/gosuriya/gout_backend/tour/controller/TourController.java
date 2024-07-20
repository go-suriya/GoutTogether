package dev.gosuriya.gout_backend.tour.controller;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.gosuriya.gout_backend.tour.dto.TourDto;
import dev.gosuriya.gout_backend.tour.model.Tour;
import dev.gosuriya.gout_backend.tour.service.TourService;

@RestController
@RequestMapping("/api/v1/tours")
public class TourController {

  private final Logger logger = LoggerFactory.getLogger(TourController.class);

  private final TourService tourService;

  public TourController(TourService tourService) {
    this.tourService = tourService;
  }

  @GetMapping
  // Pagination in Spring Boot (Spring Data JDBC)
  public Page<Tour> getTours(
      @RequestParam(name = "page", required = true) int page,
      @RequestParam(name = "size", required = true) int size,
      @RequestParam(name = "sortField", required = true) String sortField,
      @RequestParam(name = "sortDirection", required = true) String sortDirection) {
    // 1-100 tours
    // Size - 20 [1-20][21-40][41-60][61-80][81-100] <- ASC
    // Page 0 | 1 | 2 | 3 | 4
    // Page - 2
    // Sort - ASC, DESC
    logger.info("Get tours: {}", page);
    Sort sort = Sort.by(Sort.Direction.valueOf(sortDirection.toUpperCase()), sortField);
    Pageable pageable = PageRequest.of(page, size, sort);
    return tourService.getPageTour(pageable);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Tour> getTourById(@PathVariable(name = "id", required = true) int id) {
    logger.info("Get tourId: {}", id);
    return ResponseEntity.ok(tourService.getTourById(id));
  }

  @PostMapping
  public ResponseEntity<Tour> createTour(@RequestBody @Validated TourDto body) {
    var newTour = tourService.createTour(body);
    var location = String.format("http://localhost/api/v1/tours/%d", newTour.id());
    return ResponseEntity.created(URI.create(location)).body(newTour);
  }
}