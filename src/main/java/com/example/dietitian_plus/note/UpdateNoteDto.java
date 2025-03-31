package com.example.dietitian_plus.note;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateNoteDto {

    private Long noteId;

    private String text;

}
