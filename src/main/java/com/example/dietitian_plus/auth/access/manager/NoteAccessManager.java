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

    private final SecurityUtils securityUtils;

    public boolean isDietitianNoteOwner(Note note) {
        UUID currentUserId = securityUtils.getCurrentUserId();

        return note.getDietitian() != null && note.getDietitian().getUserId().equals(currentUserId);
    }

    public void checkCanCreateNote(Patient patient, Dietitian dietitian) throws AccessDeniedException {
        UUID currentUserId = securityUtils.getCurrentUserId();

        boolean isAdminRequest = securityUtils.isAdmin();
        boolean isDietitianAssignedToPatient = patient.getDietitian() != null && patient.getDietitian().getUserId().equals(dietitian.getUserId());
        boolean isDietitianSelfRequest = dietitian.getUserId().equals(currentUserId);
        boolean isDietitianPatientRequest = isDietitianAssignedToPatient && isDietitianSelfRequest;

        if (!isAdminRequest && !isDietitianPatientRequest) {
            throw new AccessDeniedException(PatientMessages.YOU_HAVE_NO_ACCESS_TO_THIS_PATIENT);
        }
    }

    public void checkCanReadPatientNotes(Patient patient) throws AccessDeniedException {
        UUID currentUserId = securityUtils.getCurrentUserId();

        boolean isAdminRequest = securityUtils.isAdmin();
        boolean isDietitianPatientRequest = patient.getDietitian() != null && patient.getDietitian().getUserId().equals(currentUserId);

        if (!isAdminRequest && !isDietitianPatientRequest) {
            throw new AccessDeniedException(NoteMessages.YOU_HAVE_NO_ACCESS_TO_THIS_NOTE);
        }
    }

    public void checkCanReadDietitianNotes(Dietitian dietitian) throws AccessDeniedException {
        UUID currentUserId = securityUtils.getCurrentUserId();

        boolean isAdminRequest = securityUtils.isAdmin();
        boolean isDietitianSelfRequest = dietitian.getUserId().equals(currentUserId);

        if (!isAdminRequest && !isDietitianSelfRequest) {
            throw new AccessDeniedException(NoteMessages.YOU_HAVE_NO_ACCESS_TO_THIS_NOTE);
        }
    }

    public void checkCanReadNote(Note note) throws AccessDeniedException {
        boolean isAdminRequest = securityUtils.isAdmin();
        boolean isDietitianNoteOwnerRequest = isDietitianNoteOwner(note);

        if (!isAdminRequest && !isDietitianNoteOwnerRequest) {
            throw new AccessDeniedException(NoteMessages.YOU_HAVE_NO_ACCESS_TO_THIS_NOTE);
        }
    }

    public void checkCanUpdateNote(Note note) throws AccessDeniedException {
        boolean isAdminRequest = securityUtils.isAdmin();
        boolean isDietitianNoteOwnerRequest = isDietitianNoteOwner(note);

        if (!isAdminRequest && !isDietitianNoteOwnerRequest) {
            throw new AccessDeniedException(NoteMessages.YOU_HAVE_NO_ACCESS_TO_THIS_NOTE);
        }
    }

    public void checkCanDeleteNote(Note note) throws AccessDeniedException {
        boolean isAdminRequest = securityUtils.isAdmin();
        boolean isDietitianNoteOwnerRequest = isDietitianNoteOwner(note);

        if (!isAdminRequest && !isDietitianNoteOwnerRequest) {
            throw new AccessDeniedException(NoteMessages.YOU_HAVE_NO_ACCESS_TO_THIS_NOTE);
        }
    }

}
