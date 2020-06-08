
<%@page import="com.emergentes.modelo.Libro"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Libro libro=(Libro)request.getAttribute("libro");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
      <h1>
            <c:if test="${libro.id ==0}">Nuevo</c:if>
             <c:if test="${libro.id !=0}">/Editar Entrada</c:if>
        </h1>
        <form action="MainController" method="post">
            <table border="1">
                <input type="hidden" name="id" value="${libro.id}">
                <tr>
                    <td>FECHA</td>
                    <td><input type="text" name="isbn" value="${libro.fecha}"></td>
                </tr>
                <tr>
                    <td>TITULO</td>
                    <td><input type="text" name="isbn" value="${libro.titulo}"></td>
                </tr>
                <tr>
                    <td>CONTENIDO</td>
                    <td><input type="text" name="isbn" value="${libro.contenido}"></td>
                </tr>
                <tr>
                    <td></td>
                    <td><input type="submit" value="enviar"></td>
                </tr>
            </table>
        </form>
    </body>
</html>
