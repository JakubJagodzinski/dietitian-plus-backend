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
        dto.setTitle(note.getTitle());
        dto.setText(note.getText());
        dto.setCreatedAt(note.getCreatedAt());
        dto.setLastEditedAt(note.getLastEditedAt());
        dto.setDietitianId(note.getDietitian().getUserId());
        dto.setPatientId(note.getPatient().getUserId());

        return dto;
    }

    public List<NoteResponseDto> toDtoList(List<Note> notes) {
        return notes.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

}
