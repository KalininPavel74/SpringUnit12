package home.kalinin.web_client1.service;

import home.kalinin.web_client1.model.Note;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

//@Data
//@RequiredArgsConstructor // не сработало, только @Autowired на конструктор
//@AllArgsConstructor // не сработало, только @Autowired на конструктор

    /**
     * Обертка для клиента REST API
     */
    @Slf4j
    @Service
    public class NoteRestService {
        private RestTemplate restTemplate;
        //private static final String API_URL = "http://localhost:8080/api";
        private static final String API_URL = "http://localhost:8081/RestNotes/api";

        @Autowired
        public NoteRestService(RestTemplate restTemplate) {
            this.restTemplate = restTemplate;
        }

        public Note getNoteById(Long id) {
            return restTemplate.getForObject(API_URL +"/{id}", Note.class, id);
        }

        public void updateNote(Note note) {
            restTemplate.put(API_URL +"/{id}", note, note.getId());
        }

        public Note createNote(Note note) {
            return restTemplate.postForObject(API_URL, note, Note.class);
        }

        public void deleteNote(Note note) {
            restTemplate.delete(API_URL +"/{id}", note.getId());
        }

        public void deleteAllNotes() { restTemplate.delete(API_URL +"/deleteAllNotes"); }

        public List<Note> getAllNotes() {
            return restTemplate.exchange(API_URL,
                            HttpMethod.GET
                            , null
                            , new ParameterizedTypeReference<List<Note>>() {
                            })
                    .getBody();
        }

    }
