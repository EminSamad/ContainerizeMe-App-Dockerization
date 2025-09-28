package com.example.containerizeme.service;

import com.example.containerizeme.model.Note;
import com.example.containerizeme.repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    private final NoteRepository repo;

    public NoteService(NoteRepository repo) {
        this.repo = repo;
    }

    public Note create(Note note) {
        return repo.save(note);
    }

    public List<Note> listAll() {
        return repo.findAll();
    }

    public Optional<Note> getById(Long id) {
        return repo.findById(id);
    }

    public Note update(Long id, Note newData) {
        return repo.findById(id).map(existing -> {
            existing.setTitle(newData.getTitle());
            existing.setContent(newData.getContent());
            return repo.save(existing);
        }).orElseThrow(() -> new RuntimeException("Note not found with id: " + id));
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Note not found with id: " + id);
        }
        repo.deleteById(id);
    }
}
