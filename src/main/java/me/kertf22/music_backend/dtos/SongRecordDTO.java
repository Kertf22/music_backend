package me.kertf22.music_backend.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record SongRecordDTO(
                            @NotBlank String title
                          ) {
}
