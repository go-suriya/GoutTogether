package dev.gosuriya.gout_backend.tour.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.gosuriya.gout_backend.common.enumeration.TourStatus;
import dev.gosuriya.gout_backend.common.exception.EntityNotFound;
import dev.gosuriya.gout_backend.tour.dto.TourDto;
import dev.gosuriya.gout_backend.tour.model.Tour;
import dev.gosuriya.gout_backend.tour.model.TourCount;
import dev.gosuriya.gout_backend.tour.repositories.TourCountRepository;
import dev.gosuriya.gout_backend.tour.repositories.TourRepository;
import dev.gosuriya.gout_backend.tourcompany.model.TourCompany;
import dev.gosuriya.gout_backend.tourcompany.repositories.TourCompanyRepository;

@Service
public class TourServiceImpl implements TourService {

  private final Logger logger = LoggerFactory.getLogger(TourServiceImpl.class);

  private final TourRepository tourRepository;
  private final TourCompanyRepository tourCompanyRepository;
  private final TourCountRepository tourCountRepository;

  public TourServiceImpl(
      TourRepository tourRepository,
      TourCompanyRepository tourCompanyRepository,
      TourCountRepository tourCountRepository) {
    this.tourRepository = tourRepository;
    this.tourCompanyRepository = tourCompanyRepository;
    this.tourCountRepository = tourCountRepository;
  }

  @Override
  @Transactional
  public Tour createTour(TourDto body) {
    var tourCompanyId = body.tourCompanyId();
    var tourCompany = tourCompanyRepository.findById(tourCompanyId)
        .orElseThrow(() -> new EntityNotFound(String.format("Tour Company Id: %s not found", tourCompanyId)));

    AggregateReference<TourCompany, Integer> tourCompanyReference = AggregateReference.to(tourCompany.id());

    var tour = new Tour(
        null,
        tourCompanyReference,
        body.title(),
        body.description(),
        body.location(),
        body.numberOfPeople(),
        body.activityDate(),
        TourStatus.PENDING.name());
    var newTour = tourRepository.save(tour);

    logger.debug("Tour has been created: {}", tour);

    tourCountRepository.save(new TourCount(null, AggregateReference.to(newTour.id()), 0));

    return newTour;
  }

  @Override
  public Tour getTourById(int id) {
    return tourRepository.findById(id)
        .orElseThrow(() -> new EntityNotFound(String.format("Tour Id: %d not found", id)));
  }

  @Override
  public Page<Tour> getPageTour(Pageable pageable) {
    return tourRepository.findAll(pageable);
  }

}