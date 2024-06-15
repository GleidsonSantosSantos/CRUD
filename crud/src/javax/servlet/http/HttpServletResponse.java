package javax.servlet.http;

package tela;

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Cadastro de Aluno</title>
</head>
<body>
<h2>Cadastro de Aluno</h2>
<form action="AlunoController" method="post">
    <label for="RA">RA:</label>
    <input type="text" id="RA" name="RA"><br><br>
    <label for="nome">Nome:</label>
    <input type="text" id="nome" name="nome"><br><br>
    <label for="email">Email:</label>
    <input type="text" id="email" name="email"><br><br>
    <input type="submit" value="Cadastrar">
</form>

<h2>Lista de Alunos</h2>
<table border="1">
    <tr>
        <th>RA</th>
        <th>Nome</th>
        <th>Email</th>
    </tr>
    <c:forEach var="aluno" items="${alunos}">
        <tr>
            <td>${aluno.RA}</td>
            <td>${aluno.nome}</td>
            <td>${aluno.email}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
