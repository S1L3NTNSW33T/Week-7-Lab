package dataaccess;

import domainmodel.Note;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NoteDB {

    public int insert(Note note) throws NotesDBException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        try {

            String preparedQuery = "INSERT INTO Notes (dateCreated, contents) VALUES (?, ?)";
            PreparedStatement ps = connection.prepareStatement(preparedQuery);
            ps.setDate(1, javaDateToSql(note.getDateCreated()));
            ps.setString(2, note.getContents());

            int rows = ps.executeUpdate();
            return rows;

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new NotesDBException("Error inserting note");
        } finally {
            pool.freeConnection(connection);
        }
    }

    public java.sql.Date javaDateToSql(java.util.Date date) {

        long javaDateMilisec = date.getTime();
        java.sql.Date sqlDate = new java.sql.Date(javaDateMilisec);
        return sqlDate;
    }

    public java.util.Date sqlDateToJava(java.sql.Date date) {

        long sqlDateMilisec = date.getTime();
        java.util.Date javaDate = new java.util.Date(sqlDateMilisec);
        return javaDate;
    }

    public int update(Note note) throws NotesDBException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        try {
            
            String preparedSQL = "UPDATE Notes SET " + "contents = ? " + "WHERE noteId = ?";
            PreparedStatement ps = connection.prepareStatement(preparedSQL);
            ps.setString(1, note.getContents());
            ps.setInt(2, note.getNoteId());
            
            int rows = ps.executeUpdate();
            return rows;
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new NotesDBException("Error updating note");
        } finally {
            pool.freeConnection(connection);
        }
    }

    public List<Note> getAll() throws NotesDBException {
        
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            
            ps = connection.prepareStatement("SELECT * FROM notes;");
            rs = ps.executeQuery();
            List<Note> notes = new ArrayList<>();
            
            while (rs.next()) {
                notes.add(new Note(rs.getInt("noteId"), sqlDateToJava(rs.getDate("dateCreated")), rs.getString("contents")));
            }
            
            pool.freeConnection(connection);
            return notes;
            
        } catch (SQLException ex) {
            Logger.getLogger(NoteDB.class.getName()).log(Level.SEVERE, "Cannot read notes", ex);
            throw new NotesDBException("Error getting Note");
        } finally {
            
            try {
                rs.close();
                ps.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            pool.freeConnection(connection);
        }
    }

   
    public Note getNote(int noteId) throws NotesDBException {
        
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        String selectSQL = "SELECT * FROM Notes WHERE noteId = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = connection.prepareStatement(selectSQL);
            ps.setInt(1, noteId);
            rs = ps.executeQuery();

            Note note = null;
            
            while (rs.next()) {
                note = new Note(rs.getInt("noteId"), sqlDateToJava(rs.getDate("dateCreated")), rs.getString("contents"));
            }
            
            pool.freeConnection(connection);
            return note;
            
        } catch (SQLException ex) {
            
            ex.printStackTrace();
            throw new NotesDBException("Error getting Notes");
        } finally {
            
            try {
                rs.close();
                ps.close();
            } catch (SQLException ex) { 
                ex.printStackTrace();
            }
            pool.freeConnection(connection);
        }
    }

    public int delete(Note note) throws NotesDBException {
        
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        String preparedQuery = "DELETE FROM Notes WHERE noteId = ?";
        PreparedStatement ps;

        try {
            
            ps = connection.prepareStatement(preparedQuery);
            ps.setInt(1, note.getNoteId());
            
            int rows = ps.executeUpdate();
            return rows;
            
        } catch (SQLException ex) {
            Logger.getLogger(NoteDB.class.getName()).log(Level.SEVERE, "Cannot delete " + note.toString(), ex);
            throw new NotesDBException("Error deleting Note");
            
        } finally {
            pool.freeConnection(connection);
        }
    }
}
