package com.example.dietitian_plus.note;


import com.example.dietitian_plus.dietitian.Dietitian;
import com.example.dietitian_plus.dietitian.DietitianRepository;
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

    private final NoteMapper noteMapper;

    private final String NOTE_NOT_FOUND_MESSAGE = "Note not found";
    private final String PATIENT_NOT_FOUND_MESSAGE = "Patient not found";
    private final String DIETITIAN_NOT_FOUND_MESSAGE = "Dietitian not found";

    @Autowired
    public NoteService(NoteRepository noteRepository, PatientRepository patientRepository, DietitianRepository dietitianRepository, NoteMapper noteMapper) {
        this.noteRepository = noteRepository;
        this.patientRepository = patientRepository;
        this.dietitianRepository = dietitianRepository;
        this.noteMapper = noteMapper;
    }

    public List<NoteDto> getNotes() {
        List<Note> notes = noteRepository.findAll();
        List<NoteDto> notesDto = new ArrayList<>();

        for (Note note : notes) {
            notesDto.add(noteMapper.toDto(note));
        }

        return notesDto;
    }

    public NoteDto getNoteById(Long id) throws EntityNotFoundException {
        if (!noteRepository.existsById(id)) {
            throw new EntityNotFoundException(NOTE_NOT_FOUND_MESSAGE);
        }

        return noteMapper.toDto(noteRepository.getReferenceById(id));
    }

    @Transactional
    public NoteDto createNote(CreateNoteDto createNoteDto) throws EntityNotFoundException {
        Note note = new Note();

        if (!patientRepository.existsById(createNoteDto.getPatientId())) {
            throw new EntityNotFoundException(PATIENT_NOT_FOUND_MESSAGE);
        }

        if (!dietitianRepository.existsById(createNoteDto.getDietitianId())) {
            throw new EntityNotFoundException(DIETITIAN_NOT_FOUND_MESSAGE);
        }

        Patient patient = patientRepository.getReferenceById(createNoteDto.getPatientId());
        Dietitian dietitian = dietitianRepository.getReferenceById(createNoteDto.getDietitianId());

        note.setDatetime(LocalDateTime.now());
        note.setText(createNoteDto.getText());
        note.setPatient(patient);
        note.setDietitian(dietitian);

        return noteMapper.toDto(noteRepository.save(note));
    }

    @Transactional
    public NoteDto updateNoteById(Long id, UpdateNoteDto updateNoteDto) throws EntityNotFoundException {
        if (!noteRepository.existsById(id)) {
            throw new EntityNotFoundException(NOTE_NOT_FOUND_MESSAGE);
        }

        Note note = noteRepository.getReferenceById(id);

        if (updateNoteDto.getText() != null) {
            note.setText(updateNoteDto.getText());
            note.setDatetime(LocalDateTime.now());
        }

        return noteMapper.toDto(noteRepository.save(note));
    }

    @Transactional
    public void deleteNoteById(Long id) throws EntityNotFoundException {
        if (!noteRepository.existsById(id)) {
            throw new EntityNotFoundException(NOTE_NOT_FOUND_MESSAGE);
        }

        noteRepository.deleteById(id);
    }

}
