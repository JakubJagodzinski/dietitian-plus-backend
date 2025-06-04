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

    @GetMapping("/")
    public ResponseEntity<List<NoteResponseDto>> getNotes() {
        List<NoteResponseDto> noteResponseDtoList = noteService.getNotes();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(noteResponseDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteResponseDto> getNoteById(@PathVariable Long id) {
        NoteResponseDto noteResponseDto = noteService.getNoteById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(noteResponseDto);
    }

    @PostMapping("/")
    public ResponseEntity<NoteResponseDto> createNote(@RequestBody CreateNoteRequestDto createNoteRequestDto) {
        NoteResponseDto createdNoteResponseDto = noteService.createNote(createNoteRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/notes" + createdNoteResponseDto.getNoteId()))
                .body(createdNoteResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoteResponseDto> updateNoteById(@PathVariable Long id, @RequestBody UpdateNoteRequestDto updateNoteRequestDto) {
        NoteResponseDto updatedNoteResponseDto = noteService.updateNoteById(id, updateNoteRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedNoteResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponseDto> deleteNoteById(@PathVariable Long id) {
        noteService.deleteNoteById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Note with id " + id + " deleted successfully"));
    }

}
