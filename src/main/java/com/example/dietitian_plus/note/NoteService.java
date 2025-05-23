package com.example.dietitian_plus.note;


import com.example.dietitian_plus.dietitian.Dietitian;
import com.example.dietitian_plus.dietitian.DietitianRepository;
import com.example.dietitian_plus.note.dto.CreateNoteRequestDto;
import com.example.dietitian_plus.note.dto.NoteResponseDto;
import com.example.dietitian_plus.note.dto.NoteDtoMapper;
import com.example.dietitian_plus.note.dto.UpdateNoteRequestDto;
import com.example.dietitian_plus.patient.Patient;
import com.example.dietitian_plus.patient.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class NoteService {

    private final NoteRepository noteRepository;
    private final PatientRepository patientRepository;
    private final DietitianRepository dietitianRepository;

    private final NoteDtoMapper noteDtoMapper;

    private final String NOTE_NOT_FOUND_MESSAGE = "Note not found";
    private final String PATIENT_NOT_FOUND_MESSAGE = "Patient not found";
    private final String DIETITIAN_NOT_FOUND_MESSAGE = "Dietitian not found";

    @Autowired
    public NoteService(NoteRepository noteRepository, PatientRepository patientRepository, DietitianRepository dietitianRepository, NoteDtoMapper noteDtoMapper) {
        this.noteRepository = noteRepository;
        this.patientRepository = patientRepository;
        this.dietitianRepository = dietitianRepository;
        this.noteDtoMapper = noteDtoMapper;
    }

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
