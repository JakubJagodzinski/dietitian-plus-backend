package com.example.dietitian_plus.domain.note;

import com.example.dietitian_plus.auth.access.annotation.AdminOnly;
import com.example.dietitian_plus.auth.access.annotation.DietitianAccess;
import com.example.dietitian_plus.auth.access.annotation.OwnerDietitianAccess;
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
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @AdminOnly
    @GetMapping("/notes")
    public ResponseEntity<List<NoteResponseDto>> getAllNotes() {
        List<NoteResponseDto> noteResponseDtoList = noteService.getAllNotes();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(noteResponseDtoList);
    }

    // TODO check ownership in access manager
    @DietitianAccess
    @GetMapping("/notes/{noteId}")
    public ResponseEntity<NoteResponseDto> getNoteById(@PathVariable Long noteId) {
        NoteResponseDto noteResponseDto = noteService.getNoteById(noteId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(noteResponseDto);
    }

    // TODO check ownership in access manager
    @DietitianAccess
    @GetMapping("/patients/{patientId}/notes")
    public ResponseEntity<List<NoteResponseDto>> getPatientAllNotes(@PathVariable Long patientId) {
        List<NoteResponseDto> noteResponseDtoList = noteService.getPatientAllNotes(patientId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(noteResponseDtoList);
    }

    @OwnerDietitianAccess
    @GetMapping("/dietitians/{dietitianId}/notes")
    public ResponseEntity<List<NoteResponseDto>> getDietitianAllNotes(@PathVariable Long dietitianId) {
        List<NoteResponseDto> noteResponseDtoList = noteService.getDietitianAllNotes(dietitianId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(noteResponseDtoList);
    }

    @DietitianAccess
    @PostMapping("/notes")
    public ResponseEntity<NoteResponseDto> createNote(@RequestBody CreateNoteRequestDto createNoteRequestDto) {
        NoteResponseDto createdNoteResponseDto = noteService.createNote(createNoteRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/notes/" + createdNoteResponseDto.getNoteId()))
                .body(createdNoteResponseDto);
    }

    // TODO check ownership in access manager
    @DietitianAccess
    @PutMapping("/notes/{noteId}")
    public ResponseEntity<NoteResponseDto> updateNoteById(@PathVariable Long noteId, @RequestBody UpdateNoteRequestDto updateNoteRequestDto) {
        NoteResponseDto updatedNoteResponseDto = noteService.updateNoteById(noteId, updateNoteRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedNoteResponseDto);
    }

    // TODO check ownership in access manager
    @DietitianAccess
    @DeleteMapping("/notes/{noteId}")
    public ResponseEntity<MessageResponseDto> deleteNoteById(@PathVariable Long noteId) {
        noteService.deleteNoteById(noteId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Note with id " + noteId + " deleted successfully"));
    }

}
