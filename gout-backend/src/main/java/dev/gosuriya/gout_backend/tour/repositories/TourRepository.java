package dev.gosuriya.gout_backend.tour.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListCrudRepository;

import dev.gosuriya.gout_backend.tour.model.Tour;

public interface TourRepository extends ListCrudRepository<Tour, Integer> {

  Page<Tour> findAll(Pageable pageable);

}
