package dev.gosuriya.gout_backend.tour.repositories;

import org.springframework.data.repository.CrudRepository;

import dev.gosuriya.gout_backend.tour.model.TourCount;

public interface TourCountRepository extends CrudRepository<TourCount, Integer> {

}
