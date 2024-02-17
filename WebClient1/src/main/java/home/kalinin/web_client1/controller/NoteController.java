package home.kalinin.web_client1.controller;

import home.kalinin.web_client1.aspect.TrackUserAction;
import home.kalinin.web_client1.model.Note;
import home.kalinin.web_client1.service.NoteRestService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/notes")
@AllArgsConstructor
@Slf4j
public class NoteController {
    private final NoteRestService noteRestService;
    @ModelAttribute(name = "note")
    public Note createDict() {
        return new Note();
    }
    @TrackUserAction
    @GetMapping
    public String getNotes(Model model){
        //log.info("GET notes");
        model.addAttribute("notes", noteRestService.getAllNotes());
        return "notes";
    }
    @TrackUserAction
    @PostMapping
    public String addNote(@Valid Note note, Errors errors, Model model){
        //log.info("POST "+note);
        if (errors.hasErrors()) {
            log.error("errors.hasErrors() "+errors);
            model.addAttribute("db_save_error", errors);
        } else {
            try {
                noteRestService.createNote(note);
            } catch (DataAccessException ex) {
                log.error("DataAccessException ");
                log.error(ex.getLocalizedMessage());
                model.addAttribute("db_save_error", ex.getMessage());
            }
        }
        model.addAttribute("notes", noteRestService.getAllNotes());
        return "notes";
    }
}