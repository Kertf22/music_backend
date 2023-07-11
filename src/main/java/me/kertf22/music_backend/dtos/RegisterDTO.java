package me.kertf22.music_backend.dtos;

import jakarta.validation.constraints.NotBlank;

public record RegisterDTO(@NotBlank String email, @NotBlank  String password, @NotBlank String username) {
}
