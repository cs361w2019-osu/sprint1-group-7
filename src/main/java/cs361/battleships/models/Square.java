package cs361.battleships.models;

import java.util.Objects;

@SuppressWarnings("unused")
public class Square {

	private int row;
	private char column;

	public Square() {}
	
	//Copy constructor for deep copying ships during placement
	public Square(Square square) {
		row = square.row;
		column = square.column;
	}

	public Square(int row, char column) {
		this.row = row;
		this.column = column;
	}

	public char getColumn() {
		return column;
	}

	public void setColumn(char column) {
		this.column = column;
	}
	
	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	@Override
	public boolean equals(Object o){
		if (o == this)
			return true;
        if (!(o instanceof Square)){
            return false;
        }

		Square square = (Square) o;
		return (this.row == square.row && this.column == square.column);
	}

	@Override
	public int hashCode(){
		return Objects.hash(row, column);
	}
}
 