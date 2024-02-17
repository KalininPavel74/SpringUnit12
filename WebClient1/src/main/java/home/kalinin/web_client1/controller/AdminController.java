package home.kalinin.web_client1.controller;

import home.kalinin.web_client1.service.NoteRestService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@EnableMethodSecurity  // для @PreAuthorize("hasRole('ADMIN')") подходят обе
//@EnableGlobalMethodSecurity(prePostEnabled = true)   // для @PreAuthorize("hasRole('ADMIN')") подходят обе  УСТАРЕЛА
@RequestMapping("/admin")
@AllArgsConstructor
@Slf4j
public class AdminController {
    private final NoteRestService noteRestService;
//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public String getAdmin(Model model) {
        model.addAttribute("notes", noteRestService.getAllNotes());
        return "admin";
    }

    @PreAuthorize("hasRole('ADMIN')")  // в месте с @EnableMethodSecurity на классе
    @PostMapping("/deleteAllNotes")
    public String deleteAllNotes(Model model) {
        log.info("POST AdminController deleteAllNotes");
        try {
            noteRestService.deleteAllNotes();
        } catch (DataAccessException ex) {
            log.error("DataAccessException ");
            log.error(ex.getLocalizedMessage());
            model.addAttribute("db_save_error", ex.getMessage());
        }
        model.addAttribute("notes", noteRestService.getAllNotes());
        return "redirect:/admin";
    }
}