package com.example.dietitian_plus.domain.note;

import com.example.dietitian_plus.auth.access.manager.NoteAccessManager;
import com.example.dietitian_plus.common.constants.messages.DietitianMessages;
import com.example.dietitian_plus.common.constants.messages.NoteMessages;
import com.example.dietitian_plus.common.constants.messages.PatientMessages;
import com.example.dietitian_plus.domain.dietitian.Dietitian;
import com.example.dietitian_plus.domain.dietitian.DietitianRepository;
import com.example.dietitian_plus.domain.note.dto.NoteDtoMapper;
import com.example.dietitian_plus.domain.note.dto.request.CreateNoteRequestDto;
import com.example.dietitian_plus.domain.note.dto.request.UpdateNoteRequestDto;
import com.example.dietitian_plus.domain.note.dto.response.NoteResponseDto;
import com.example.dietitian_plus.domain.patient.Patient;
import com.example.dietitian_plus.domain.patient.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;
    private final PatientRepository patientRepository;
    private final DietitianRepository dietitianRepository;

    private final NoteDtoMapper noteDtoMapper;

    private final NoteAccessManager noteAccessManager;

    public List<NoteResponseDto> getAllNotes() {
        return noteDtoMapper.toDtoList(noteRepository.findAll());
    }

    @Transactional
    public NoteResponseDto getNoteById(Long noteId) throws EntityNotFoundException {
        Note note = noteRepository.findById(noteId).orElse(null);

        if (note == null) {
            throw new EntityNotFoundException(NoteMessages.NOTE_NOT_FOUND);
        }

        noteAccessManager.checkCanReadNote(note);

        return noteDtoMapper.toDto(note);
    }

    @Transactional
    public List<NoteResponseDto> getPatientAllNotes(UUID patientId) throws EntityNotFoundException {
        Patient patient = patientRepository.findById(patientId).orElse(null);

        if (patient == null) {
            throw new EntityNotFoundException(PatientMessages.PATIENT_NOT_FOUND);
        }

        noteAccessManager.checkCanReadPatientNotes(patient);

        return noteDtoMapper.toDtoList(noteRepository.findAllByPatient_UserId(patientId));
    }

    @Transactional
    public List<NoteResponseDto> getDietitianAllNotes(UUID dietitianId) throws EntityNotFoundException {
        Dietitian dietitian = dietitianRepository.findById(dietitianId).orElse(null);

        if (dietitian == null) {
            throw new EntityNotFoundException(DietitianMessages.DIETITIAN_NOT_FOUND);
        }

        noteAccessManager.checkCanReadDietitianNotes(dietitian);

        return noteDtoMapper.toDtoList(noteRepository.findAllByDietitian_UserId(dietitianId));
    }

    @Transactional
    public NoteResponseDto createNote(CreateNoteRequestDto createNoteRequestDto) throws EntityNotFoundException {
        Patient patient = patientRepository.findById(createNoteRequestDto.getPatientId()).orElse(null);

        if (patient == null) {
            throw new EntityNotFoundException(PatientMessages.PATIENT_NOT_FOUND);
        }

        Dietitian dietitian = dietitianRepository.findById(createNoteRequestDto.getDietitianId()).orElse(null);

        if (dietitian == null) {
            throw new EntityNotFoundException(DietitianMessages.DIETITIAN_NOT_FOUND);
        }

        noteAccessManager.checkCanCreateNote(patient, dietitian);

        Note note = new Note();

        note.setTitle(createNoteRequestDto.getTitle().trim());
        note.setText(createNoteRequestDto.getText().trim());
        note.setPatient(patient);
        note.setDietitian(dietitian);

        return noteDtoMapper.toDto(noteRepository.save(note));
    }

    @Transactional
    public NoteResponseDto updateNoteById(Long noteId, UpdateNoteRequestDto updateNoteRequestDto) throws EntityNotFoundException {
        Note note = noteRepository.findById(noteId).orElse(null);

        if (note == null) {
            throw new EntityNotFoundException(NoteMessages.NOTE_NOT_FOUND);
        }

        noteAccessManager.checkCanUpdateNote(note);

        if (updateNoteRequestDto.getTitle() != null) {
            note.setTitle(updateNoteRequestDto.getTitle().trim());
        }

        if (updateNoteRequestDto.getText() != null) {
            note.setText(updateNoteRequestDto.getText().trim());
            note.setLastEditedAt(LocalDateTime.now());
        }

        return noteDtoMapper.toDto(noteRepository.save(note));
    }

    @Transactional
    public void deleteNoteById(Long noteId) throws EntityNotFoundException {
        Note note = noteRepository.findById(noteId).orElse(null);

        if (note == null) {
            throw new EntityNotFoundException(NoteMessages.NOTE_NOT_FOUND);
        }

        noteAccessManager.checkCanDeleteNote(note);

        noteRepository.deleteById(noteId);
    }

}
