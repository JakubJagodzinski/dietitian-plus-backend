package com.example.dietitian_plus.note;

import org.springframework.stereotype.Component;

@Component
public class NoteMapper {

    public NoteDto toDto(Note note) {
        NoteDto dto = new NoteDto();

        dto.setNoteId(note.getNoteId());
        dto.setText(note.getText());
        dto.setDatetime(note.getDatetime());
        dto.setDietitianId(note.getDietitian().getDietitianId());
        dto.setUserId(note.getUser().getUserId());

        return dto;
    }

}
