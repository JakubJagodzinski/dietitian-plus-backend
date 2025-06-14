package com.example.dietitian_plus.auth.access.manager;

import com.example.dietitian_plus.auth.access.SecurityUtils;
import com.example.dietitian_plus.common.constants.messages.NoteMessages;
import com.example.dietitian_plus.common.constants.messages.PatientMessages;
import com.example.dietitian_plus.domain.dietitian.Dietitian;
import com.example.dietitian_plus.domain.note.Note;
import com.example.dietitian_plus.domain.patient.Patient;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class NoteAccessManager {

    private final SecurityUtils authenticationService;

    public void checkCanCreateNote(Patient patient, Dietitian dietitian) throws AccessDeniedException {
        UUID currentUserId = authenticationService.getCurrentUserId();
        boolean isAdmin = authenticationService.isAdmin();
        boolean isDietitianOwner = patient.getDietitian() != null && patient.getDietitian().getUserId().equals(dietitian.getUserId());
        boolean isDietitianOwnerLogged = dietitian.getUserId().equals(currentUserId);

        if (!isAdmin && !(isDietitianOwner && isDietitianOwnerLogged)) {
            throw new AccessDeniedException(PatientMessages.YOU_HAVE_NO_ACCESS_TO_THIS_PATIENT);
        }
    }

    public void checkCanReadPatientNotes(Patient patient) throws AccessDeniedException {
        UUID currentUserId = authenticationService.getCurrentUserId();
        boolean isAdmin = authenticationService.isAdmin();
        boolean isDietitianOwner = patient.getDietitian() != null && patient.getDietitian().getUserId().equals(currentUserId);

        if (!isAdmin && !isDietitianOwner) {
            throw new AccessDeniedException(NoteMessages.YOU_HAVE_NO_ACCESS_TO_THIS_NOTE);
        }
    }

    public void checkCanReadDietitianNotes(Dietitian dietitian) throws AccessDeniedException {
        UUID currentUserId = authenticationService.getCurrentUserId();
        boolean isAdmin = authenticationService.isAdmin();
        boolean isDietitianOwner = dietitian.getUserId().equals(currentUserId);

        if (!isAdmin && !isDietitianOwner) {
            throw new AccessDeniedException(NoteMessages.YOU_HAVE_NO_ACCESS_TO_THIS_NOTE);
        }
    }

    public void checkCanReadNote(Note note) throws AccessDeniedException {
        UUID currentUserId = authenticationService.getCurrentUserId();
        boolean isAdmin = authenticationService.isAdmin();
        boolean isDietitianOwner = note.getDietitian() != null && note.getDietitian().getUserId().equals(currentUserId);

        if (!isAdmin && !isDietitianOwner) {
            throw new AccessDeniedException(NoteMessages.YOU_HAVE_NO_ACCESS_TO_THIS_NOTE);
        }
    }

    public void checkCanUpdateNote(Note note) throws AccessDeniedException {
        UUID currentUserId = authenticationService.getCurrentUserId();
        boolean isAdmin = authenticationService.isAdmin();
        boolean isDietitianOwner = note.getDietitian() != null && note.getDietitian().getUserId().equals(currentUserId);

        if (!isAdmin && !isDietitianOwner) {
            throw new AccessDeniedException(NoteMessages.YOU_HAVE_NO_ACCESS_TO_THIS_NOTE);
        }
    }

    public void checkCanDeleteNote(Note note) throws AccessDeniedException {
        UUID currentUserId = authenticationService.getCurrentUserId();
        boolean isAdmin = authenticationService.isAdmin();
        boolean isDietitianOwner = note.getDietitian() != null && note.getDietitian().getUserId().equals(currentUserId);

        if (!isAdmin && !isDietitianOwner) {
            throw new AccessDeniedException(NoteMessages.YOU_HAVE_NO_ACCESS_TO_THIS_NOTE);
        }
    }

}
