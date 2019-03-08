package cs361.battleships.models;

public class Bomb implements Weapon {
	@Override
	public boolean filterShip(Ship s){
		return s.getDepth() < 0;
	}
}
