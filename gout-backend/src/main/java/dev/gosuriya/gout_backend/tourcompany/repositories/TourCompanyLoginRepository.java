package dev.gosuriya.gout_backend.tourcompany.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import dev.gosuriya.gout_backend.tourcompany.model.TourCompanyLogin;

@Repository
public interface TourCompanyLoginRepository extends CrudRepository<TourCompanyLogin, Integer> {

}
