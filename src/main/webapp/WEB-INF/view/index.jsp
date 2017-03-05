<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<head>
<title>Tic-Tac-Toe</title>
    <style>
        table {
        border-collapse: collapse;
        width: 100%;
        }

        th, td {
        text-align: left;
        padding: 8px;
        }
        tr:nth-child(even){background-color: #f2f2f2}
    </style>
</head>
<body>
    <div align="center">
        <h2>List Of Games</h2>
        <form action="/new" method="POST">
            New Game Name: <input type="text" name="gameName">
            <input type="submit" value="Play" />
        </form>
    </div>
 <table align="center" style="width:50%">
     <tr>
       <th>Name</th>
       <th>Status</th>
       <th>Created date</th>
       <th>Action</th>
     </tr>
  <c:forEach items="${games}" var="game">
    <tr>
      <td><c:out value="${game.gameName}"/></td>
      <td><c:out value="${game.status}"/></td>
      <td><c:out value="${game.gameCreatedDate}"/></td>
      <td>
        <a href="<c:url value='/game?gameId=${game.gameId}'/>">"${game.status == 'IN_PROGRESS' ? 'Play' : 'Show'}"</a>
      </td>
    </tr>
  </c:forEach>
</table>
</body>
</html>
