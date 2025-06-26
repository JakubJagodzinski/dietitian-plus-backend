package com.example.dietitian_plus.domain.note;

import com.example.dietitian_plus.auth.access.CheckPermission;
import com.example.dietitian_plus.domain.note.dto.request.CreateNoteRequestDto;
import com.example.dietitian_plus.domain.note.dto.request.UpdateNoteRequestDto;
import com.example.dietitian_plus.domain.note.dto.response.NoteResponseDto;
import com.example.dietitian_plus.user.Permission;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Validated
public class NoteController {

    private final NoteService noteService;

    @CheckPermission(Permission.NOTE_READ_ALL)
    @GetMapping("/notes")
    public ResponseEntity<List<NoteResponseDto>> getAllNotes() {
        List<NoteResponseDto> noteResponseDtoList = noteService.getAllNotes();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(noteResponseDtoList);
    }

    @CheckPermission(Permission.NOTE_READ)
    @GetMapping("/notes/{noteId}")
    public ResponseEntity<NoteResponseDto> getNoteById(@PathVariable Long noteId) {
        NoteResponseDto noteResponseDto = noteService.getNoteById(noteId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(noteResponseDto);
    }

    @CheckPermission(Permission.PATIENT_NOTE_READ_ALL)
    @GetMapping("/patients/{patientId}/notes")
    public ResponseEntity<List<NoteResponseDto>> getPatientAllNotes(@PathVariable UUID patientId) {
        List<NoteResponseDto> noteResponseDtoList = noteService.getPatientAllNotes(patientId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(noteResponseDtoList);
    }

    @CheckPermission(Permission.DIETITIAN_NOTE_READ_ALL)
    @GetMapping("/dietitians/{dietitianId}/notes")
    public ResponseEntity<List<NoteResponseDto>> getDietitianAllNotes(@PathVariable UUID dietitianId) {
        List<NoteResponseDto> noteResponseDtoList = noteService.getDietitianAllNotes(dietitianId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(noteResponseDtoList);
    }

    @CheckPermission(Permission.NOTE_CREATE)
    @PostMapping("/notes")
    public ResponseEntity<NoteResponseDto> createNote(@Valid @RequestBody CreateNoteRequestDto createNoteRequestDto) {
        NoteResponseDto createdNoteResponseDto = noteService.createNote(createNoteRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/notes/" + createdNoteResponseDto.getNoteId()))
                .body(createdNoteResponseDto);
    }

    @CheckPermission(Permission.NOTE_UPDATE)
    @PatchMapping("/notes/{noteId}")
    public ResponseEntity<NoteResponseDto> updateNoteById(@PathVariable Long noteId, @Valid @RequestBody UpdateNoteRequestDto updateNoteRequestDto) {
        NoteResponseDto updatedNoteResponseDto = noteService.updateNoteById(noteId, updateNoteRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedNoteResponseDto);
    }

    @CheckPermission(Permission.NOTE_DELETE)
    @DeleteMapping("/notes/{noteId}")
    public ResponseEntity<Void> deleteNoteById(@PathVariable Long noteId) {
        noteService.deleteNoteById(noteId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
