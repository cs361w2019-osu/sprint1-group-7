var isSetup = true;
var placedShips = 0;
var game;
var shipType = "BATTLESHIP";
var vertical;
var sonarRemaining = 2;

function makeGrid(table, isPlayer) {
    for (i=0; i<10; i++) {
        let row = document.createElement('tr');
        for (j=0; j<10; j++) {
            let column = document.createElement('td');
            column.addEventListener("click", cellClick);
            row.appendChild(column);
        }
        table.appendChild(row);
    }
}

function markHits(board, elementId, surrenderText) {
    board.attacks.forEach((attack) => {
        let className;
        if (attack.result === "MISS"){
            className = "miss";
        }else if (attack.result === "HIT"){
            className = "hit";
        }else if (attack.result === "SUNK"){
            className = "sink"
            if (document.getElementById("sonarContainer").style.display != "block"){
                document.getElementById("sonarContainer").style.display = "block";
            }
        }else if (attack.result === "FOUND"){
            className = "occupied"
        }else if (attack.result === "EMPTY"){
            className = "empty"
        }else if(attack.result === "OUCH"){
            className = "ouch"
        }else if (attack.result === "SURRENDER"){
            className = "sink"
            alert(surrenderText);
        }
        document.getElementById(elementId).rows[attack.location.row-1].cells[attack.location.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.remove("miss");
        document.getElementById(elementId).rows[attack.location.row-1].cells[attack.location.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.remove("hit");
        document.getElementById(elementId).rows[attack.location.row-1].cells[attack.location.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.remove("sink");
        document.getElementById(elementId).rows[attack.location.row-1].cells[attack.location.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.remove("occupied");
        document.getElementById(elementId).rows[attack.location.row-1].cells[attack.location.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.remove("empty");
        document.getElementById(elementId).rows[attack.location.row-1].cells[attack.location.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.remove("ouch");
        document.getElementById(elementId).rows[attack.location.row-1].cells[attack.location.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.add(className);
    });
}

function redrawGrid() {
    Array.from(document.getElementById("opponent").childNodes).forEach((row) => row.remove());
    Array.from(document.getElementById("player").childNodes).forEach((row) => row.remove());
    makeGrid(document.getElementById("opponent"), false);
    makeGrid(document.getElementById("player"), true);
    if (game === undefined) {
        return;
    }

    game.playersBoard.ships.forEach((ship) => {
        console.log("count");
        for(var i=0; i < ship.occupiedSquares.length; i++){classAssigner(ship.occupiedSquares, i, ship.shipType);}
    });
    markHits(game.opponentsBoard, "opponent", "You won the game");
    markHits(game.playersBoard, "player", "You lost the game");
}

function classAssigner(square, i, shipType){
    var vert = 0;
    var sq = document.getElementById("player").rows[square[i].location.row-1].cells[square[i].location.column.charCodeAt(0) - 'A'.charCodeAt(0)]
    if((i == (square.length -1) && square[i].location.column != square[i-1].location.column) || (i != (square.length-1)) && square[i].location.column != square[i+1].location.column){vert = 1;}
    if(vert){
        if(shipType == "BATTLESHIP"){sq.classList.add("batt" + i);}
        else if(shipType == "DESTROYER"){sq.classList.add("dest" + i);}
        else if(shipType == "SUBMARINE"){sq.classList.add("sub" + i);}
        else{sq.classList.add("mine" + i);}
    }
    else{
        if(shipType == "BATTLESHIP"){sq.classList.add("battv" + i);}
        else if(shipType == "DESTROYER"){sq.classList.add("destv" + i);}
        else if(shipType == "SUBMARINE"){sq.classList.add("subv" + i);}
        else{sq.classList.add("minev" + i);}
    }
    sq.classList.add("occupied");
}

var oldListener;
function registerCellListener(f) {
    let el = document.getElementById("player");
    for (i=0; i<10; i++) {
        for (j=0; j<10; j++) {
            let cell = el.rows[i].cells[j];
            cell.removeEventListener("mouseover", oldListener);
            cell.removeEventListener("mouseout", oldListener);
            cell.addEventListener("mouseover", f);
            cell.addEventListener("mouseout", f);
        }
    }
    oldListener = f;
}

function toggleShipType() {
    if(shipType == "BATTLESHIP"){
        shipType = "DESTROYER";
        registerCellListener(place(3));
    }else if(shipType == "DESTROYER"){
        shipType = "MINESWEEPER";
        registerCellListener(place(2));
    }
    else if(shipType == "MINESWEEPER"){
        shipType = "SUBMARINE";
        registerCellListener(place(5));
    }
}

function cellClick() {
    let row = this.parentNode.rowIndex + 1;
    let col = String.fromCharCode(this.cellIndex + 65);
    console.log("clicked")
    if (isSetup) {

        sendXhr("POST", "/place", {game: game, shipType: shipType, x: row, y: col, isVertical: vertical}, function(data) {
            game = data;
            redrawGrid();
            placedShips++;
            toggleShipType();
            if (placedShips == 4) {
                isSetup = false;
                registerCellListener((e) => {});
                document.getElementById("verticalContainer").style.display = "none";
            }
        });
    } else if(!document.getElementById("is_sonar").checked) {
        console.log("atrack");
        sendXhr("POST", "/attack", {game: game, x: row, y: col}, function(data) {
            game = data;
            redrawGrid();
            console.log(game);
        })
    } else {
        sendXhr("POST", "/sonar", {game: game, x: row, y: col}, function(data) {
            game = data;
            redrawGrid();
            sonarRemaining--;
            if(sonarRemaining == 0){
                document.getElementById("is_sonar").checked = false;
                document.getElementById("sonarContainer").style.display = "none";
            }
        });
    }
}

function sendXhr(method, url, data, handler) {
    var req = new XMLHttpRequest();
    req.addEventListener("load", function(event) {
        console.log(game);
        if (req.status != 200) {
            alert("Cannot complete the action");
            return;
        }
        handler(JSON.parse(req.responseText));
    });
    req.open(method, url);
    req.setRequestHeader("Content-Type", "application/json");
    req.send(JSON.stringify(data));
}

function place(size) {
    return function() {
        let tempsize = size;
        if(size == 5){tempsize -= 1;}
        let cell;
        let row = this.parentNode.rowIndex;
        let col = this.cellIndex;
        vertical = document.getElementById("is_vertical").checked;
        let table = document.getElementById("player");
        if(size == 5){
            if(vertical){
                let tableRow = table.rows[row + 2];
                let cell;
                cell = tableRow.cells[col + 1];
                if(!(cell === undefined)){
                    cell.classList.toggle("placed");
                }
            }
            else{
                let tableRow = table.rows[row - 1];
                let cell;
                cell = tableRow.cells[col + 2];
                if(!(cell === undefined)){
                    cell.classList.toggle("placed");
                }
                
            }
        }
        for (let i=0; i<tempsize; i++) {
            let cell;
            if(vertical) {
                let tableRow = table.rows[row+i];
                if (tableRow === undefined) {
                    // ship is over the edge; let the back end deal with it
                    break;
                }
                cell = tableRow.cells[col];
            } else {
                cell = table.rows[row].cells[col+i];
            }
            if (cell === undefined) {
                // ship is over the edge; let the back end deal with it
                break;
            }
            cell.classList.toggle("placed");
        }
    }
}

function initGame() {
    makeGrid(document.getElementById("opponent"), false);
    makeGrid(document.getElementById("player"), true);
    registerCellListener(place(4));
    sendXhr("GET", "/game", {}, function(data) {
        game = data;
    });
};
