package com.example.dietitian_plus.domain.note.dto;

import com.example.dietitian_plus.domain.note.Note;
import org.springframework.stereotype.Component;

@Component
public class NoteDtoMapper {

    public NoteResponseDto toDto(Note note) {
        NoteResponseDto dto = new NoteResponseDto();

        dto.setNoteId(note.getNoteId());
        dto.setText(note.getText());
        dto.setDatetime(note.getDatetime());
        dto.setDietitianId(note.getDietitian().getDietitianId());
        dto.setPatientId(note.getPatient().getPatientId());

        return dto;
    }

}
