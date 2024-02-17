package home.kalinin.rest_notes;

import home.kalinin.rest_notes.model.Note;
import home.kalinin.rest_notes.repository.NoteRepository;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@Aspect
@Slf4j
@SpringBootApplication
public class MainRestNotes {
    public static void main(String[] args) {
        SpringApplication.run(MainRestNotes.class, args);
    }
    @Bean
    public CommandLineRunner dataLoader(NoteRepository noteRepository) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                noteRepository.save(new Note("Заметка №1", 1));
                noteRepository.save(new Note("Заметка №2", 22));
                noteRepository.save(new Note("Заметка №3", 33.33));
                noteRepository.save(new Note("Заметка №4", 4.4));
            }
        };
    }
}

