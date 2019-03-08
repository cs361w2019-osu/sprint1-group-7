package controllers;

import com.google.inject.Singleton;
import cs361.battleships.models.Game;
import cs361.battleships.models.Ship;
import cs361.battleships.models.ShipFactory;
import cs361.battleships.models.Submarine;
import ninja.Context;
import ninja.Result;
import ninja.Results;

@Singleton
public class ApplicationController {

    public Result index() {
        return Results.html();
    }

    public Result newGame() {
        Game g = new Game();
        return Results.json().render(g);
    }

    public Result placeShip(Context context, PlacementGameAction g) {
        Game game = g.getGame();
        Ship ship = ShipFactory.createShip(g.getShipType());
        boolean result = game.placeShip(ship, g.getActionRow(), g.getActionColumn(), g.isVertical());
        if (result) {
            return Results.json().render(game);
        } else {
            return Results.badRequest();
        }
    }

    public Result placeSubmarine(Context context, SubmarinePlacementGameAction g) {
        Game game = g.getGame();
        Submarine ship = new Submarine(g.getDepth());
        ship.setDepth(g.getDepth());
        boolean result = game.placeShip(ship, g.getActionRow(), g.getActionColumn(), g.isVertical());
        if (result) {
            return Results.json().render(game);
        } else {
            return Results.badRequest();
        }
    }

    public Result attack(Context context, AttackGameAction g) {
        Game game = g.getGame();
        boolean result = game.attack(g.getActionRow(), g.getActionColumn());
        if (result) {
            return Results.json().render(game);
        } else {
            return Results.badRequest();
        }
    }

    public Result sonar(Context context, SonarAction g){
        Game game = g.getGame();
        boolean result = game.sonar(g.getActionRow(), g.getActionColumn());
        if (result) {
            return Results.json().render(game);
        } else {
            return Results.badRequest();
        }
    }

    public Result move(Context context, MoveAction g){
        Game game = g.getGame();
        boolean result = game.move(g.getDirection());
        if (result) {
            return Results.json().render(game);
        } else {
            return Results.badRequest();
        }
    }
}
