
var board = new Array(9);

function init() {
  var squares = document.getElementsByTagName("td");
  for (var s = 0; s < squares.length; s++) {
    squares[s].addEventListener('click', function(evt){squareSelected(evt, getCurrentPlayer());}, false);
  }

  clearGrid();

  var playerX = document.getElementById("X");
  var playerO = document.getElementById("O");
  playerX.className = "current-player";
  playerO.className = "";
}

function renderGrid(data) {
    var marks = data.marks;
    Object.keys(marks).forEach(key => {
        if (board[key] != "") {
            fillSquareWithMarker(document.getElementById(key), document.getElementById(marks[key].player));
        }
    });
    document.getElementById('game').innerHTML = 'Game: ' + data.gameName + '|Status: ' + data.status;
    localStorage.setItem('last-player', getCurrentPlayer());
}

function restoreGrid(data) {
    var playerX = document.getElementById("X");
    var playerO = document.getElementById("O");

    if(data.currentPlayer == 'X'){
         playerO.className = "current-player";
         playerX.className = "";
    }else{
         playerX.className = "current-player";
         playerO.className = "";
    }
    var marks = data.marks;
    Object.keys(marks).forEach(key => {
            fillSquareWithMarker(document.getElementById(key),marks[key].player);
    });
}

function clearGrid() {
 for (var i = 0; i < board.length; i++) {
   board[i] = "";
   document.getElementById(i).innerHTML = "";
 }
}

function squareSelected(evt, currentPlayer) {
  var square = evt.target;
  service(square, currentPlayer)
}

function fillSquareWithMarker(square, player) {
  var marker = document.createElement('div');
  marker.className = player + "-marker";
  square.appendChild(marker);
}

function checkForWinner(status) {
     if (status == 'X_WON') {
       alert("Player X won!");
     } else if (status == 'O_WON') {
       alert("Player O won!");
     }else if (status == 'DRAW') {
       alert("It's a draw!");
     }
}

function getCurrentPlayer() {
  return document.querySelector(".current-player").id;
}

function switchPlayers() {
  var playerX = document.getElementById("X");
  var playerO = document.getElementById("O");

  if (playerX.className.match(/current-player/)) {
    playerO.className = "current-player";
    playerX.className = "";
  }
  else {
    playerX.className = "current-player";
    playerO.className = "";
  }
}
function service(square, currentPlayer) {
    var request = {}
    request["cellId"] = square.id;
    request["player"] = currentPlayer;
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/mark",
        data: JSON.stringify(request),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success : function(data) {
        				console.log("SUCCESS: ", data);
        				fillSquareWithMarker(square, currentPlayer);
        				renderGrid(data);
                        checkForWinner(data.status);
                        switchPlayers(data);
        			},
        			error : function(e) {
        				console.log("ERROR: ", e);
        				alert("Sorry, that space is taken!");
        			}
    });

}

function findGameById(id) {
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/find",
        data: JSON.stringify(id),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success : function(data) {
        				console.log("SUCCESS: ", data);
        				restoreGrid(data);
        			},
        			error : function(e) {
        				console.log("ERROR: ", e);
        				alert("Game does not exist!");
        			}
    });

}