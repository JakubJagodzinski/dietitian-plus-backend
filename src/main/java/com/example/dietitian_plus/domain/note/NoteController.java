package com.example.dietitian_plus.domain.note;

import com.example.dietitian_plus.common.MessageResponseDto;
import com.example.dietitian_plus.domain.note.dto.CreateNoteRequestDto;
import com.example.dietitian_plus.domain.note.dto.NoteResponseDto;
import com.example.dietitian_plus.domain.note.dto.UpdateNoteRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @GetMapping
    public ResponseEntity<List<NoteResponseDto>> getNotes() {
        List<NoteResponseDto> noteResponseDtoList = noteService.getNotes();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(noteResponseDtoList);
    }

    @GetMapping("/{noteId}")
    public ResponseEntity<NoteResponseDto> getNoteById(@PathVariable Long noteId) {
        NoteResponseDto noteResponseDto = noteService.getNoteById(noteId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(noteResponseDto);
    }

    @GetMapping("/by-patient/{patientId}")
    public ResponseEntity<List<NoteResponseDto>> getNotesByPatientId(@PathVariable Long patientId) {
        List<NoteResponseDto> noteResponseDtoList = noteService.getNotesByPatientId(patientId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(noteResponseDtoList);
    }

    @GetMapping("/by-dietitian/{dietitianId}")
    public ResponseEntity<List<NoteResponseDto>> getNotesByDietitianId(@PathVariable Long dietitianId) {
        List<NoteResponseDto> noteResponseDtoList = noteService.getNotesByDietitianId(dietitianId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(noteResponseDtoList);
    }

    @PostMapping
    public ResponseEntity<NoteResponseDto> createNote(@RequestBody CreateNoteRequestDto createNoteRequestDto) {
        NoteResponseDto createdNoteResponseDto = noteService.createNote(createNoteRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/notes" + createdNoteResponseDto.getNoteId()))
                .body(createdNoteResponseDto);
    }

    @PutMapping("/{noteId}")
    public ResponseEntity<NoteResponseDto> updateNoteById(@PathVariable Long noteId, @RequestBody UpdateNoteRequestDto updateNoteRequestDto) {
        NoteResponseDto updatedNoteResponseDto = noteService.updateNoteById(noteId, updateNoteRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedNoteResponseDto);
    }

    @DeleteMapping("/{noteId}")
    public ResponseEntity<MessageResponseDto> deleteNoteById(@PathVariable Long noteId) {
        noteService.deleteNoteById(noteId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Note with id " + noteId + " deleted successfully"));
    }

}
