var isSetup = true;
var placedShips = 0;
var game;
var shipType = "BATTLESHIP";
var vertical;
var sonarRemaining = 2;
var direction;
var timesDirectionClicked = 0;
var sinkCount = 0;

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
            if (sonarRemaining > 0 && document.getElementById("sonarContainer").style.display != "block"){
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

    sinkCount = game.sunkShips;
    if (sinkCount >= 2 && timesDirectionClicked == 0 && document.getElementById("moveContainer").style.display != "block" && document.getElementById("northContainer").style.display != "block"){
        document.getElementById("moveContainer").style.display = "block";
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
    var sq = document.getElementById("player").rows[square[i].location.row-1].cells[square[i].location.column.charCodeAt(0) - 'A'.charCodeAt(0)];
    if(shipType == "SUBMARINE" && ((i == (square.length - 1) && square[i].location.column != square[i - 2].location.column) || (i < (square.length - 1) && i > 0 && square[i].location.row != square[i-1].location.row) || (i == 0 && square[i].location.row != square[i + 1].location.row))){
        vert = 1;
    } else if (shipType != "SUBMARINE" && ((i < (square.length - 1) && square[i].location.row != square[i + 1].location.row) || (i == (square.length - 1) && square[i].location.row != square[i - 1].location.row))){
        vert = 1;
    } else {
        vert = 0;
    }
    //if((i == (square.length -1) && square[i].location.column != square[i-1].location.column) || (i != (square.length-1) && square[i].location.column != square[i+1].location.column)){vert = 1;}
    if(vert){
        if(shipType == "BATTLESHIP"){sq.classList.add("battv" + i);}
        else if(shipType == "DESTROYER"){sq.classList.add("destv" + i);}
        else if(shipType == "SUBMARINE"){
            sq.classList.add("subv" + i);
            console.log("subv" + i);
        }
        else{sq.classList.add("minev" + i);}
    }
    else{
        if(shipType == "BATTLESHIP"){sq.classList.add("batt" + i);}
        else if(shipType == "DESTROYER"){sq.classList.add("dest" + i);}
        else if(shipType == "SUBMARINE"){
            sq.classList.add("sub" + i);
            console.log("sub" + i);
        }
        else{sq.classList.add("mine" + i);}
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
    }else if(shipType == "MINESWEEPER"){
        shipType = "SUBMARINE";
        registerCellListener(placeSubmarine);
        document.getElementById("submergedContainer").style.display = "block";
    }
}

function cellClick() {
    let row = this.parentNode.rowIndex + 1;
    let col = String.fromCharCode(this.cellIndex + 65);
    if (isSetup) {
        let endpoint = placedShips == 3 ? "/placeSubmarine" : "/place";
        let context = placedShips == 3 ? {game: game, shipType: shipType, x: row, y: col, isVertical: vertical, depth: document.getElementById("is_submerged").checked ? -1 : 0}
                : {game: game, shipType: shipType, x: row, y: col, isVertical: vertical};
        sendXhr("POST", endpoint, context, function(data) {
            game = data;
            redrawGrid();
            placedShips++;
            toggleShipType();
            if (placedShips == 4) {
                isSetup = false;
                registerCellListener((e) => {});
                document.getElementById("submergedContainer").style.display = "none";
                document.getElementById("verticalContainer").style.display = "none";
            }
        });
    } else if(!document.getElementById("is_sonar").checked) {
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
            if(sonarRemaining <= 0){
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
        let row = this.parentNode.rowIndex;
        let col = this.cellIndex;
        vertical = document.getElementById("is_vertical").checked;
        let table = document.getElementById("player");
        for (let i=0; i<size; i++) {
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

function placeSubmarine() {
    let row = this.parentNode.rowIndex;
    let col = this.cellIndex;
    vertical = document.getElementById("is_vertical").checked;
    let table = document.getElementById("player");
    for (let i=0; i<4; i++) {
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
    if(vertical) {
        let tableRow = table.rows[row + 2];
        if (tableRow === undefined) {
            // ship is over the edge; let the back end deal with it
            return;
        }
        cell = tableRow.cells[col + 1];
        if (cell === undefined) {
            // ship is over the edge; let the back end deal with it
            return;
        }
        cell.classList.toggle("placed");
    } else {
        let tableRow = table.rows[row - 1];
        if (tableRow === undefined) {
            // ship is over the edge; let the back end deal with it
            return;
        }
        cell = tableRow.cells[col + 2];
        if (cell === undefined) {
            // ship is over the edge; let the back end deal with it
            return;
        }
        cell.classList.toggle("placed");
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

function revealDirections(){
    console.log("doing the function");
    document.getElementById("moveContainer").style.display = "none";
    document.getElementById("eastContainer").style.display = "block";
    document.getElementById("westContainer").style.display = "block";
    document.getElementById("northContainer").style.display = "block";
    document.getElementById("southContainer").style.display = "block";
};

function directionClick(elem) {
     if(timesDirectionClicked < 2){
        timesDirectionClicked += 1;
        direction = parseInt(elem.target.getAttribute("num"));
        console.log("Direction: ", direction)
        sendXhr("POST", "/move", {game: game, direction: direction}, function(data) {
            game = data;
            redrawGrid();
            console.log(game);
        });
        if(timesDirectionClicked == 2){
            document.getElementById("eastContainer").style.display = "none";
            document.getElementById("westContainer").style.display = "none";
            document.getElementById("northContainer").style.display = "none";
            document.getElementById("southContainer").style.display = "none";
        }
    }
}

var moveWake = document.getElementById("moveContainer");
moveWake.addEventListener("click", revealDirections);
var eastTrigger = document.getElementById("eastContainer");
eastTrigger.addEventListener("click", directionClick);
var westTrigger = document.getElementById("westContainer");
westTrigger.addEventListener("click", directionClick);
var northTrigger = document.getElementById("northContainer");
northTrigger.addEventListener("click", directionClick);
var southTrigger = document.getElementById("southContainer");
southTrigger.addEventListener("click", directionClick);
