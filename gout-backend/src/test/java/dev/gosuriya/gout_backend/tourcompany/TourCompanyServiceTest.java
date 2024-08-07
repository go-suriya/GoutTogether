package dev.gosuriya.gout_backend.tourcompany;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.security.crypto.password.PasswordEncoder;

import dev.gosuriya.gout_backend.common.enumeration.TourCompanyStatus;
import dev.gosuriya.gout_backend.common.exception.EntityNotFound;
import dev.gosuriya.gout_backend.tourcompany.dto.RegisterTourCompanyDto;
import dev.gosuriya.gout_backend.tourcompany.model.TourCompany;
import dev.gosuriya.gout_backend.tourcompany.model.TourCompanyLogin;
import dev.gosuriya.gout_backend.tourcompany.model.TourCompanyWallet;
import dev.gosuriya.gout_backend.tourcompany.repositories.TourCompanyLoginRepository;
import dev.gosuriya.gout_backend.tourcompany.repositories.TourCompanyRepository;
import dev.gosuriya.gout_backend.tourcompany.repositories.TourCompanyWalletRepository;
import dev.gosuriya.gout_backend.tourcompany.service.TourCompanyServiceImpl;

@ExtendWith(MockitoExtension.class)
class TourCompanyServiceTest {

    @InjectMocks
    private TourCompanyServiceImpl tourCompanyService;

    @Mock
    private TourCompanyRepository tourCompanyRepository;

    @Mock
    private TourCompanyLoginRepository tourCompanyLoginRepository;

    @Mock
    private TourCompanyWalletRepository tourCompanyWalletRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void whenRegisterTourThenSuccess() {
        var mockTourCompany = new TourCompany(
                1,
                "Mart Tour",
                TourCompanyStatus.WAITING.name());
        when(tourCompanyRepository.save(any(TourCompany.class)))
                .thenReturn(mockTourCompany);
        when(passwordEncoder.encode(anyString()))
                .thenReturn("encryptedValue");
        var companyCredential = new TourCompanyLogin(1, AggregateReference.to(1), "mart", "encryptedValue");
        when(tourCompanyLoginRepository.save(any(TourCompanyLogin.class)))
                .thenReturn(companyCredential);

        var payload = new RegisterTourCompanyDto(null, "Mart Tour", "mart", "123456789", null);
        var actual = tourCompanyService.registerTourCompany(payload);

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(1, actual.id().intValue());
        Assertions.assertEquals("Mart Tour", actual.name());
        Assertions.assertEquals(TourCompanyStatus.WAITING.name(), actual.status());
    }

    @Test
    void whenApproveTourThenSuccess() {
        var mockTourCompany = new TourCompany(
                1,
                "Mart Tour",
                TourCompanyStatus.WAITING.name());
        when(tourCompanyRepository.findById(anyInt()))
                .thenReturn(Optional.of(mockTourCompany));

        var updatedTourCompany = new TourCompany(mockTourCompany.id(), mockTourCompany.name(),
                TourCompanyStatus.APPROVED.name());
        when(tourCompanyRepository.save(any(TourCompany.class)))
                .thenReturn(updatedTourCompany);

        var wallet = new TourCompanyWallet(null, AggregateReference.to(1), Instant.now(), new BigDecimal("0.00"));
        when(tourCompanyWalletRepository.save(any(TourCompanyWallet.class)))
                .thenReturn(wallet);

        var actual = tourCompanyService.approvedTourCompany(1);

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(1, actual.id().intValue());
        Assertions.assertEquals("Mart Tour", actual.name());
        Assertions.assertEquals(TourCompanyStatus.APPROVED.name(), actual.status());
    }

    @Test
    void whenApproveTourButTourCompanyNotFoundThenError() {
        when(tourCompanyRepository.findById(anyInt()))
                .thenThrow(new EntityNotFound(String.format("Tour Company Id: %s not found", 1)));
        Assertions.assertThrows(EntityNotFound.class, () -> tourCompanyService.approvedTourCompany(1));
    }
}