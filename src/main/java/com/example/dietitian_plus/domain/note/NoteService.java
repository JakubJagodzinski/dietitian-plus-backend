package com.example.dietitian_plus.domain.note;


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
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;
    private final PatientRepository patientRepository;
    private final DietitianRepository dietitianRepository;

    private final NoteDtoMapper noteDtoMapper;

    private static final String NOTE_NOT_FOUND_MESSAGE = "Note not found";
    private static final String PATIENT_NOT_FOUND_MESSAGE = "Patient not found";
    private static final String DIETITIAN_NOT_FOUND_MESSAGE = "Dietitian not found";

    public List<NoteResponseDto> getNotes() {
        List<Note> notes = noteRepository.findAll();
        List<NoteResponseDto> notesDto = new ArrayList<>();

        for (Note note : notes) {
            notesDto.add(noteDtoMapper.toDto(note));
        }

        return notesDto;
    }

    public NoteResponseDto getNoteById(Long id) throws EntityNotFoundException {
        if (!noteRepository.existsById(id)) {
            throw new EntityNotFoundException(NOTE_NOT_FOUND_MESSAGE);
        }

        return noteDtoMapper.toDto(noteRepository.getReferenceById(id));
    }

    @Transactional
    public NoteResponseDto createNote(CreateNoteRequestDto createNoteRequestDto) throws EntityNotFoundException {
        Note note = new Note();

        if (!patientRepository.existsById(createNoteRequestDto.getPatientId())) {
            throw new EntityNotFoundException(PATIENT_NOT_FOUND_MESSAGE);
        }

        if (!dietitianRepository.existsById(createNoteRequestDto.getDietitianId())) {
            throw new EntityNotFoundException(DIETITIAN_NOT_FOUND_MESSAGE);
        }

        Patient patient = patientRepository.getReferenceById(createNoteRequestDto.getPatientId());
        Dietitian dietitian = dietitianRepository.getReferenceById(createNoteRequestDto.getDietitianId());

        note.setDatetime(LocalDateTime.now());
        note.setText(createNoteRequestDto.getText());
        note.setPatient(patient);
        note.setDietitian(dietitian);

        return noteDtoMapper.toDto(noteRepository.save(note));
    }

    @Transactional
    public NoteResponseDto updateNoteById(Long id, UpdateNoteRequestDto updateNoteRequestDto) throws EntityNotFoundException {
        if (!noteRepository.existsById(id)) {
            throw new EntityNotFoundException(NOTE_NOT_FOUND_MESSAGE);
        }

        Note note = noteRepository.getReferenceById(id);

        if (updateNoteRequestDto.getText() != null) {
            note.setText(updateNoteRequestDto.getText());
            note.setDatetime(LocalDateTime.now());
        }

        return noteDtoMapper.toDto(noteRepository.save(note));
    }

    @Transactional
    public void deleteNoteById(Long id) throws EntityNotFoundException {
        if (!noteRepository.existsById(id)) {
            throw new EntityNotFoundException(NOTE_NOT_FOUND_MESSAGE);
        }

        noteRepository.deleteById(id);
    }

}
