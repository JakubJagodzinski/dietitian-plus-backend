package com.example.dietitian_plus.note;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    private final NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/")
    public ResponseEntity<List<NoteDto>> getNotes() {
        return ResponseEntity.ok(noteService.getNotes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteDto> getNoteById(@PathVariable Long id) {
        return ResponseEntity.ok(noteService.getNoteById(id));
    }

    @PostMapping("/")

    public ResponseEntity<NoteDto> createNote(@RequestBody CreateNoteDto createNoteDto) {
        NoteDto createdNoteDto = noteService.createNote(createNoteDto);
        return ResponseEntity.created(URI.create("api/notes/" + createdNoteDto.getNoteId())).body(createdNoteDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoteDto> updateNoteById(@PathVariable Long id, @RequestBody UpdateNoteDto updateNoteDto) {
        return ResponseEntity.ok(noteService.updateNoteById(id, updateNoteDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNoteById(@PathVariable Long id) {
        noteService.deleteNoteById(id);
        return ResponseEntity.ok("Note deleted successfully");
    }

}
