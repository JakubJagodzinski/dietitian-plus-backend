package com.example.dietitian_plus.domain.note;

import com.example.dietitian_plus.auth.access.CheckPermission;
import com.example.dietitian_plus.domain.note.dto.request.CreateNoteRequestDto;
import com.example.dietitian_plus.domain.note.dto.request.UpdateNoteRequestDto;
import com.example.dietitian_plus.domain.note.dto.response.NoteResponseDto;
import com.example.dietitian_plus.exception.ApiError;
import com.example.dietitian_plus.user.Permission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(
            summary = "Get all notes"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of all notes",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = NoteResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content()
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access Denied",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.NOTE_READ_ALL)
    @GetMapping("/notes")
    public ResponseEntity<List<NoteResponseDto>> getAllNotes() {
        List<NoteResponseDto> noteResponseDtoList = noteService.getAllNotes();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(noteResponseDtoList);
    }

    @Operation(
            summary = "Get note by id"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Note found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = NoteResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content()
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access Denied",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Note not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.NOTE_READ)
    @GetMapping("/notes/{noteId}")
    public ResponseEntity<NoteResponseDto> getNoteById(@PathVariable Long noteId) {
        NoteResponseDto noteResponseDto = noteService.getNoteById(noteId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(noteResponseDto);
    }

    @Operation(
            summary = "Get patient all notes"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of patient all notes",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = NoteResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content()
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access Denied",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Patient not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.PATIENT_NOTE_READ_ALL)
    @GetMapping("/patients/{patientId}/notes")
    public ResponseEntity<List<NoteResponseDto>> getPatientAllNotes(@PathVariable UUID patientId) {
        List<NoteResponseDto> noteResponseDtoList = noteService.getPatientAllNotes(patientId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(noteResponseDtoList);
    }

    @Operation(
            summary = "Get dietitian all notes"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of dietitian all notes",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = NoteResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content()
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access Denied",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Dietitian not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.DIETITIAN_NOTE_READ_ALL)
    @GetMapping("/dietitians/{dietitianId}/notes")
    public ResponseEntity<List<NoteResponseDto>> getDietitianAllNotes(@PathVariable UUID dietitianId) {
        List<NoteResponseDto> noteResponseDtoList = noteService.getDietitianAllNotes(dietitianId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(noteResponseDtoList);
    }

    @Operation(
            summary = "Create new note"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Note created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = NoteResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content()
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access Denied",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Dietitian / patient not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.NOTE_CREATE)
    @PostMapping("/notes")
    public ResponseEntity<NoteResponseDto> createNote(@Valid @RequestBody CreateNoteRequestDto createNoteRequestDto) {
        NoteResponseDto createdNoteResponseDto = noteService.createNote(createNoteRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/notes/" + createdNoteResponseDto.getNoteId()))
                .body(createdNoteResponseDto);
    }

    @Operation(
            summary = "Update note by id"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Note updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = NoteResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content()
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access Denied",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Note not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.NOTE_UPDATE)
    @PatchMapping("/notes/{noteId}")
    public ResponseEntity<NoteResponseDto> updateNoteById(@PathVariable Long noteId, @Valid @RequestBody UpdateNoteRequestDto updateNoteRequestDto) {
        NoteResponseDto updatedNoteResponseDto = noteService.updateNoteById(noteId, updateNoteRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedNoteResponseDto);
    }

    @Operation(
            summary = "Delete note by id"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Note deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content()
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access Denied",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Note not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.NOTE_DELETE)
    @DeleteMapping("/notes/{noteId}")
    public ResponseEntity<Void> deleteNoteById(@PathVariable Long noteId) {
        noteService.deleteNoteById(noteId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
