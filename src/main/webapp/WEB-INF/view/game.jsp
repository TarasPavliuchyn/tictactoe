<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
  <title>Tic Tac Toe</title>
  <meta name="viewport" content="width=600px" />
  <link href="<c:url value="/resources/css/main.css" />" rel="stylesheet">

  <script type="text/javascript"
          src="webjars/jquery/2.2.4/jquery.min.js"></script>
  <script src="<c:url value="/resources/js/main.js" />"></script>
</head>
<body onload="init();">
<div align="center">
        <a href="<c:url value='/'/>">"Game List"</a>
        <form action="/new" method="POST">
            New Game Name: <input type="text" name="gameName">
            <input type="submit" value="Play" />
        </form>
        <h4 id="game">"Game: ${game.gameName}|Status: ${game.status}"</h4>
        <c:if test="${not empty game}">
           <script>
              findGameById("${game.gameId}");
           </script>
        </c:if>
</div>
<div id="players">
  <div id="X" class="">Player X</div>
  <div id="O" class="">Player O</div>
</div>
<table>
  <tr>
    <td id="0"></td>
    <td id="1"></td>
    <td id="2"></td>
  </tr>
  <tr>
    <td id="3"></td>
    <td id="4"></td>
    <td id="5"></td>
  </tr>
  <tr>
    <td id="6"></td>
    <td id="7"></td>
    <td id="8"></td>
  </tr>
</table>
</body>
</html>
