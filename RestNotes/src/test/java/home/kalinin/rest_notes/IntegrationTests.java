package home.kalinin.rest_notes;
import home.kalinin.rest_notes.controller.RestNoteController;
import home.kalinin.rest_notes.model.Note;
import home.kalinin.rest_notes.repository.NoteRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Создает контейнер Spring и прочее. Работает значительно дольше Unit Tests
 * Это похоже на ПОЛУИНТЕГРАЦИОННЫЙ тест
 */
@SpringBootTest
public class IntegrationTests {
    /**
     * Создать объект контроллера REST API, для проверки внем методов и
     * Создать и добавить в контейнер Spring
     */
    @Autowired
    private RestNoteController restNoteController;
    /**
     * Пометить объект репозитория, который вызывается в проверяемых методах.
     * Создать объект и добавить объект в контейнер Spring
     * Запускается вся машина Spring, запускается h2 сервер.
     */
    //@Autowired
    @MockBean  // без этого не будет работать Mockito.verify
    private NoteRepository noteRepository;
    @Test
    @DisplayName("Integration проверка работы метода RestNoteController.addNote.")
    public void test1RestNoteController_AddNote() {
        // подготовка данных
        Note note = new Note();
        note.setId(1L);
        note.setName("Тест. Заметка 1");
        note.setNumber(11.22);

        // выполнение проверяемого метода
        ResponseEntity assResponseEntity = restNoteController.addNote(note);

        System.out.println("assResponseEntity="+assResponseEntity);
        System.out.println("assResponseEntity.getBody()="+assResponseEntity.getBody());
        System.out.println("assResponseEntity.getStatusCode()="+assResponseEntity.getStatusCode());
        System.out.println("assResponseEntity.getHeaders()="+assResponseEntity.getHeaders());

        // проверка работы метода RestNoteController.addNote
        Assertions.assertEquals(HttpStatus.CREATED, assResponseEntity.getStatusCode());
        Assertions.assertEquals(note, (Note)assResponseEntity.getBody());
        Assertions.assertEquals(0, assResponseEntity.getHeaders().size());

        // не понял что такое verify
        // :) оказалось, что verify проверяет вызывался ли метод и с теми ли параметрами и сколько раз
        // Вызывается ли ЗАГЛУШЕННЫЙ МЕТОД, который должен вызываться внутри проверямого метода addNote
        Mockito.verify(noteRepository, Mockito.times(1)).save(note);
    }
}

