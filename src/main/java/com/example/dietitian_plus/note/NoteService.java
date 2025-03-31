package com.example.dietitian_plus.note;


import com.example.dietitian_plus.dietitian.Dietitian;
import com.example.dietitian_plus.dietitian.DietitianRepository;
import com.example.dietitian_plus.user.User;
import com.example.dietitian_plus.user.UserRepository;
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
    private final UserRepository userRepository;
    private final DietitianRepository dietitianRepository;

    private final String NOTE_NOT_FOUND_MESSAGE = "Note not found";
    private final String USER_NOT_FOUND_MESSAGE = "User not found";
    private final String DIETITIAN_NOT_FOUND_MESSAGE = "Dietitian not found";

    @Autowired
    public NoteService(NoteRepository noteRepository, UserRepository userRepository, DietitianRepository dietitianRepository) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
        this.dietitianRepository = dietitianRepository;
    }

    public List<NoteDto> getNotes() {
        List<Note> notes = noteRepository.findAll();
        List<NoteDto> notesDto = new ArrayList<>();

        for (Note note : notes) {
            notesDto.add(mapToDto(note));
        }

        return notesDto;
    }

    public NoteDto getNoteById(Long id) throws EntityNotFoundException {
        if (!noteRepository.existsById(id)) {
            throw new EntityNotFoundException(NOTE_NOT_FOUND_MESSAGE);
        }

        return mapToDto(noteRepository.getReferenceById(id));
    }

    @Transactional
    public NoteDto createNote(CreateNoteDto createNoteDto) throws EntityNotFoundException {
        Note note = new Note();

        if (!userRepository.existsById(createNoteDto.getUserId())) {
            throw new EntityNotFoundException(USER_NOT_FOUND_MESSAGE);
        }

        if (!dietitianRepository.existsById(createNoteDto.getDietitianId())) {
            throw new EntityNotFoundException(DIETITIAN_NOT_FOUND_MESSAGE);
        }

        User user = userRepository.getReferenceById(createNoteDto.getUserId());
        Dietitian dietitian = dietitianRepository.getReferenceById(createNoteDto.getDietitianId());

        note.setDatetime(LocalDateTime.now());
        note.setText(createNoteDto.getText());
        note.setUser(user);
        note.setDietitian(dietitian);

        return mapToDto(noteRepository.save(note));
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

        return mapToDto(noteRepository.save(note));
    }

    @Transactional
    public void deleteNoteById(Long id) throws EntityNotFoundException {
        if (!noteRepository.existsById(id)) {
            throw new EntityNotFoundException(NOTE_NOT_FOUND_MESSAGE);
        }

        noteRepository.deleteById(id);
    }

    private NoteDto mapToDto(Note note) {
        NoteDto noteDto = new NoteDto();

        noteDto.setNoteId(note.getNoteId());
        noteDto.setText(note.getText());
        noteDto.setDatetime(note.getDatetime());
        noteDto.setDietitianId(note.getDietitian().getDietitianId());
        noteDto.setUserId(note.getUser().getUserId());

        return noteDto;
    }

}
