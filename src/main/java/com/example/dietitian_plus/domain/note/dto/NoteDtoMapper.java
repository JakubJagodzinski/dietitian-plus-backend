package com.example.dietitian_plus.domain.note.dto;

import com.example.dietitian_plus.domain.note.Note;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class NoteDtoMapper {

    public NoteResponseDto toDto(Note note) {
        NoteResponseDto dto = new NoteResponseDto();

        dto.setNoteId(note.getNoteId());
        dto.setText(note.getText());
        dto.setDatetime(note.getDatetime());
        dto.setDietitianId(note.getDietitian().getId());
        dto.setPatientId(note.getPatient().getId());

        return dto;
    }

    public List<NoteResponseDto> toDtoList(List<Note> notes) {
        return notes.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

}
