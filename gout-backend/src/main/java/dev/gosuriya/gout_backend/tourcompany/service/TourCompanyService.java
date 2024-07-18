package dev.gosuriya.gout_backend.tourcompany.service;

import dev.gosuriya.gout_backend.tourcompany.dto.RegisterTourCompanyDto;
import dev.gosuriya.gout_backend.tourcompany.model.TourCompany;

public interface TourCompanyService {
  TourCompany registerTourCompany(RegisterTourCompanyDto payload);

  TourCompany approvedTourCompany(Integer id);
}
