package cs361.battleships.models;

public class ShipFactory{
    public static Ship createShip(String shipType) {
        if (shipType.equals("MINESWEEPER")) {
            return new Minesweeper();
        } else if (shipType.equals("DESTROYER")) {
            return new Destroyer();
        } else{
            return new Battleship();
        }
    }
}
