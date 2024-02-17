package home.kalinin.rest_notes.controller;

import home.kalinin.rest_notes.aspect.IActionMethod;
import home.kalinin.rest_notes.integration.FileGateway;
import home.kalinin.rest_notes.model.Note;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import home.kalinin.rest_notes.repository.NoteRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/RestNotes/api", produces = "application/json")
@AllArgsConstructor
@Slf4j
public class RestNoteController {
    private final FileGateway fileGateway;
    private final MeterRegistry meterRegistry;
    /**
     * Репозиторий заметок
     */
    private final NoteRepository noteRepository;

    /**
     * Получение полного перечная заметок.
     * @return Список заметок, упакованный в полноценный http формат.
     */
    @IActionMethod
    @GetMapping()
    public ResponseEntity<List<Note>> getAllNotes() {
        meterRegistry.counter("requests_all_notes").increment();
        fileGateway.writeToFile("http_requests.txt", "GET /RestNotes/api/");
        return new ResponseEntity<>(noteRepository.findAll(), HttpStatus.OK);
    }

    /**
     * Получение данных по указанной заметке.
     * @param id - код заметки
     * @return Искомая заметока, упакованная в полноценный http формат.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Note> getNote(@PathVariable Long id) {
        fileGateway.writeToFile("http_requests.txt", "GET /RestNotes/api/{id} "+id);
        if(id == null || id <= 0)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        Optional<Note> oNote = noteRepository.findById(id);
        if (oNote.isEmpty())
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(oNote.get(), HttpStatus.OK);
    }

    /**
     * Удаление указанной заметки.
     * @param id - код заметки
     * @return код удаленной заметки, упакованный в полноценный http формат.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteNote(@PathVariable Long id) {
        fileGateway.writeToFile("http_requests.txt", "DELETE /RestNotes/api/{id} "+id);
        if(id == null || id <= 0)
            return new ResponseEntity<>(id, HttpStatus.BAD_REQUEST);
        try {
            noteRepository.deleteById(id);
        } catch (DataAccessException ex) {
            log.error("DataAccessException ");
            log.error(ex.getLocalizedMessage());
            return new ResponseEntity<>(id, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @IActionMethod
    @DeleteMapping("/deleteAllNotes")
    public ResponseEntity<?> deleteAllNotes() {
        fileGateway.writeToFile("http_requests.txt", "DELETE /RestNotes/api/deleteAllNotes");
        try {
            noteRepository.deleteAll();
        } catch (DataAccessException ex) {
            log.error("DataAccessException ");
            log.error(ex.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Добавление новой заметки
     * @param note - добавляемая заметка
     * @return Добавленная заметка, упакованная в полноценный http формат.
     */
    @IActionMethod
    @PostMapping
    public ResponseEntity<Note> addNote(@RequestBody Note note) {
        fileGateway.writeToFile("http_requests.txt", "POST /RestNotes/api/ "+note);
        if (note == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        try {
            noteRepository.save(note);
        } catch (DataAccessException ex) {
            log.error("DataAccessException ");
            log.error(ex.getLocalizedMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(note, HttpStatus.CREATED);
    }

    /**
     * Заменить заметку с указанным кодом на новую.
     * @param id - код ЗАМЕНЯЕМОЙ заметки.
     * @param note - заметка, все поля которой, заменят существующую заметку с кодом id
     * @return замененная заметка, упакованная в полноценный http формат.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Note> updateNote(
            @PathVariable Long id, @RequestBody Note note) {
        fileGateway.writeToFile("http_requests.txt", "PUT /RestNotes/api/{id} "+id + " "+note);
        if(id == null || id <= 0 || note == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        note.setId(id);
        try {
            noteRepository.save(note);
        } catch (DataAccessException ex) {
            log.error("DataAccessException ");
            log.error(ex.getLocalizedMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(note, HttpStatus.CREATED);
    }

    /**
     * Обновление (замена) данных полей указанной заметки.
     * @param id - код заметки, в которая будет частично обновлена
     * @param note - заметка содержащая обновленные поля для замены
     * @return обновленная заметка с кодом id, упакованная в полноценный http формат.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Note> updateSelectiveFieldsNote(
            @PathVariable Long id, @RequestBody Note note) {
        fileGateway.writeToFile("http_requests.txt", "PATCH /RestNotes/api/{id} "+id + " "+note);
        if(id == null || id <= 0 || note == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        Optional<Note> oNote = noteRepository.findById(id);
        if (oNote.isEmpty())
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        Note existNote = oNote.get();
        if (note.getName() != null && !note.getName().isBlank())
            existNote.setName(note.getName());
        existNote.setNumber(note.getNumber());
        try {
            noteRepository.save(existNote);
        } catch (DataAccessException ex) {
            log.error("DataAccessException ");
            log.error(ex.getLocalizedMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(existNote, HttpStatus.OK);
    }
}