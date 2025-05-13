package com.example.dietitian_plus.note;

import com.example.dietitian_plus.note.dto.CreateNoteRequestDto;
import com.example.dietitian_plus.note.dto.NoteResponseDto;
import com.example.dietitian_plus.note.dto.UpdateNoteRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/notes")
public class NoteController {

    private final NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/")
    public ResponseEntity<List<NoteResponseDto>> getNotes() {
        return ResponseEntity.ok(noteService.getNotes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteResponseDto> getNoteById(@PathVariable Long id) {
        return ResponseEntity.ok(noteService.getNoteById(id));
    }

    @PostMapping("/")

    public ResponseEntity<NoteResponseDto> createNote(@RequestBody CreateNoteRequestDto createNoteRequestDto) {
        NoteResponseDto createdNoteResponseDto = noteService.createNote(createNoteRequestDto);
        return ResponseEntity.created(URI.create("api/v1/notes/" + createdNoteResponseDto.getNoteId())).body(createdNoteResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoteResponseDto> updateNoteById(@PathVariable Long id, @RequestBody UpdateNoteRequestDto updateNoteRequestDto) {
        return ResponseEntity.ok(noteService.updateNoteById(id, updateNoteRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNoteById(@PathVariable Long id) {
        noteService.deleteNoteById(id);
        return ResponseEntity.ok("Note deleted successfully");
    }

}
