package businesslogic;

import dataaccess.NoteDB;
import domainmodel.Note;
import java.util.List;

public class NoteService {

    private NoteDB noteDB;

    public NoteService() {
        noteDB = new NoteDB();
    }

    public Note get(int noteId) throws Exception {
        return noteDB.getNote(noteId);
    }

    public List<Note> getAll() throws Exception {
        return noteDB.getAll();
    }

    public int update(int noteId, String contents) throws Exception {  
        
        Note note = new Note(noteId, contents);
        return noteDB.update(note);
    }

    public int delete(int noteId) throws Exception {
        
        Note deletedNote = noteDB.getNote(noteId);
        return noteDB.delete(deletedNote);
    }

    public int insert(String contents) throws Exception {
        
        java.util.Date date = new java.util.Date();
        Note note = new Note(date, contents);
        return noteDB.insert(note);
    }
}
