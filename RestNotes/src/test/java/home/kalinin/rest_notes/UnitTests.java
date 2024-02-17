package home.kalinin.rest_notes;

import home.kalinin.rest_notes.model.Note;
import home.kalinin.rest_notes.repository.NoteRepository;
import home.kalinin.rest_notes.controller.RestNoteController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@ExtendWith(MockitoExtension.class)  // Пока не знаю что это.
public class UnitTests {
  /**
   * Поле контроллера REST API, для проверки внем методов.
   * Создает объект. Не добавляет объект в контейнер Spring
   * Этот объект НЕ будет полноценным. Заглушка репозитория.
    */
  @InjectMocks
  private RestNoteController restNoteController;
  /**
   * Пометить объект репозитория, который вызывается в проверяемых методах.
   * Пометить для создания вместо него "примитивной" заглушки с одним состоянием.
   * Создает полноценный объект. Не добавляет объект в контейнер Spring
   */
  @Mock
  private NoteRepository noteRepository;
  @Test
  @DisplayName("1) Unit проверка работы метода RestNoteController.addNote.")
  public void test1RestNoteController_AddNote() {
    // что-то лишнее
    // создать заглушку для репозитория
    //NoteRepository noteRepository = Mockito.mock(NoteRepository.class);
    // создать проверяемый объект
    //RestNoteController restNoteController = new RestNoteController(noteRepository);

    // подготовка данных
    Note note = new Note();
    note.setId(1L);
    note.setName("Тест. Заметка 1");
    note.setNumber(11.22);
	ResponseEntity responseEntity = new ResponseEntity(note, HttpStatus.OK);
    // Оба варианта работают. В чем разница - не знаю.
    Mockito.when(noteRepository.save(note)).thenReturn(note);
    //BDDMockito.given(noteRepository.save(note)).willReturn(note);

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

/*
 // для проверки ошибочно введенных данных
 // в моем случае ошибки заменяются на 400 и 404
 assertThrows( AccountNotFoundException.class,
        () -> transferService.transferMoney(1, 2, new BigDecimal(100)) );
 // метод заглушенного объекта не должен выполнится
 verify(accountRepository, never()).changeAmount(anyLong(), any());
 */


}
