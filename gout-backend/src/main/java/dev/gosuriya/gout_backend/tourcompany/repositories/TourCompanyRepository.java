package dev.gosuriya.gout_backend.tourcompany.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import dev.gosuriya.gout_backend.tourcompany.model.TourCompany;

@Repository
public interface TourCompanyRepository extends CrudRepository<TourCompany, Integer> {

}
