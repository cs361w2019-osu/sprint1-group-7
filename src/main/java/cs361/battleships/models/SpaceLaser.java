package cs361.battleships.models;

public class SpaceLaser implements Weapon {
	@Override
	public boolean filterShip(Ship s){
		return false;//filter nothing
	}
}
