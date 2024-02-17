package home.kalinin.rest_notes.repository;

import home.kalinin.rest_notes.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> { }
