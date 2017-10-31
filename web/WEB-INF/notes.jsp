<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>

<html>
    <head>
        <link rel="stylesheet" href="<c:url value='styles/notes.css' />" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Manage Notes</title>
    </head>

    <body>

        <p>${errorMessage}</p>
        <table border="1">
            <tr>
                <th>Note ID</th>
                <th>Date Created</th>
                <th>contents</th>
            </tr>

            <c:forEach var="note" items="${notes}">
                <tr>
                    <td>${note.noteId}</td>
                    <td>${note.dateCreated}</td>
                    <td>${note.contents}</td>
                    <td>

                        <form action="notes" method="post" >

                            <input type="submit" value="Delete">
                            <input type="hidden" name="action" value="delete">
                            <input type="hidden" name="selectedNoteId" value="${note.noteId}">
                        </form>
                    </td>

                    <td>
                        <form action="notes" method="get">

                            <input type="submit" value="Edit">
                            <input type="hidden" name="action" value="view">
                            <input type="hidden" name="selectedNoteId" value="${note.noteId}">
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>

        <c:if test="${selectedNote == null}">

            <h3>Add Note</h3>
            <form action="notes" method="POST">

                Contents: <input type="text" name="contents"><br>
                <input type="hidden" name="action" value="add">
                <input type="submit" value="Save">
            </form>
        </c:if>

        <c:if test="${selectedNote != null}">
            <h3>Edit Note</h3>
            <form action="notes" method="POST">

                Note ID: <input type="text" name="noteId" value="${selectedNote.noteId}" readonly><br>
                Date Created: <input type="text" name="dateCreated" value="${selectedNote.dateCreated}" readonly><br>
                Contents: <input type="text" name="contents" value="${selectedNote.contents}"><br>
                <input type="hidden" name="action" value="edit">
                <input type="submit" value="Save">

            </form>
        </c:if>
    </body>
</html>
