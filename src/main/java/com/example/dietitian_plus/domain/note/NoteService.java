package com.example.dietitian_plus.domain.note;

import com.example.dietitian_plus.common.Messages;
import com.example.dietitian_plus.domain.dietitian.Dietitian;
import com.example.dietitian_plus.domain.dietitian.DietitianRepository;
import com.example.dietitian_plus.domain.note.dto.CreateNoteRequestDto;
import com.example.dietitian_plus.domain.note.dto.NoteDtoMapper;
import com.example.dietitian_plus.domain.note.dto.NoteResponseDto;
import com.example.dietitian_plus.domain.note.dto.UpdateNoteRequestDto;
import com.example.dietitian_plus.domain.patient.Patient;
import com.example.dietitian_plus.domain.patient.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;
    private final PatientRepository patientRepository;
    private final DietitianRepository dietitianRepository;

    private final NoteDtoMapper noteDtoMapper;

    public List<NoteResponseDto> getAllNotes() {
        return noteDtoMapper.toDtoList(noteRepository.findAll());
    }

    @Transactional
    public NoteResponseDto getNoteById(Long noteId) throws EntityNotFoundException {
        Note note = noteRepository.findById(noteId).orElse(null);

        if (note == null) {
            throw new EntityNotFoundException(Messages.NOTE_NOT_FOUND);
        }

        return noteDtoMapper.toDto(note);
    }

    @Transactional
    public List<NoteResponseDto> getPatientAllNotes(Long patientId) throws EntityNotFoundException {
        if (!patientRepository.existsById(patientId)) {
            throw new EntityNotFoundException(Messages.PATIENT_NOT_FOUND);
        }

        return noteDtoMapper.toDtoList(noteRepository.findAllByPatient_Id(patientId));
    }

    @Transactional
    public List<NoteResponseDto> getDietitianAllNotes(Long dietitianId) throws EntityNotFoundException {
        if (!dietitianRepository.existsById(dietitianId)) {
            throw new EntityNotFoundException(Messages.DIETITIAN_NOT_FOUND);
        }

        return noteDtoMapper.toDtoList(noteRepository.findAllByDietitian_Id(dietitianId));
    }

    @Transactional
    public NoteResponseDto createNote(CreateNoteRequestDto createNoteRequestDto) throws EntityNotFoundException {
        Patient patient = patientRepository.findById(createNoteRequestDto.getPatientId()).orElse(null);

        if (patient == null) {
            throw new EntityNotFoundException(Messages.PATIENT_NOT_FOUND);
        }

        Dietitian dietitian = dietitianRepository.findById(createNoteRequestDto.getDietitianId()).orElse(null);

        if (dietitian == null) {
            throw new EntityNotFoundException(Messages.DIETITIAN_NOT_FOUND);
        }

        Note note = new Note();

        note.setTitle(createNoteRequestDto.getTitle());
        note.setText(createNoteRequestDto.getText());
        note.setPatient(patient);
        note.setDietitian(dietitian);

        return noteDtoMapper.toDto(noteRepository.save(note));
    }

    @Transactional
    public NoteResponseDto updateNoteById(Long noteId, UpdateNoteRequestDto updateNoteRequestDto) throws EntityNotFoundException {
        Note note = noteRepository.findById(noteId).orElse(null);

        if (note == null) {
            throw new EntityNotFoundException(Messages.NOTE_NOT_FOUND);
        }

        if (updateNoteRequestDto.getTitle() != null) {
            note.setTitle(updateNoteRequestDto.getTitle());
        }

        if (updateNoteRequestDto.getText() != null) {
            note.setText(updateNoteRequestDto.getText());
            note.setLastEditedAt(LocalDateTime.now());
        }

        if (updateNoteRequestDto.getPatientId() != null) {
            Patient patient = patientRepository.findById(updateNoteRequestDto.getPatientId()).orElse(null);

            if (patient == null) {
                throw new EntityNotFoundException(Messages.PATIENT_NOT_FOUND);
            }

            note.setPatient(patient);
        }

        return noteDtoMapper.toDto(noteRepository.save(note));
    }

    @Transactional
    public void deleteNoteById(Long noteId) throws EntityNotFoundException {
        if (!noteRepository.existsById(noteId)) {
            throw new EntityNotFoundException(Messages.NOTE_NOT_FOUND);
        }

        noteRepository.deleteById(noteId);
    }

}
