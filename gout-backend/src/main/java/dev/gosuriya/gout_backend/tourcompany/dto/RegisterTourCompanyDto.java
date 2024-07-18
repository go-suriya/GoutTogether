package dev.gosuriya.gout_backend.tourcompany.dto;

import jakarta.validation.constraints.NotBlank;

public record RegisterTourCompanyDto(
                Integer id,
                @NotBlank String name,
                String username,
                String password,
                String status) {
}
