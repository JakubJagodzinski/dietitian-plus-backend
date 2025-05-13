package com.example.dietitian_plus.note.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateNoteRequestDto {

    private Long noteId;

    private String text;

}
