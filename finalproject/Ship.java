package finalproject;

import java.util.Arrays;

import cse131.NotYetImplementedException;

/**
 * @author Mariah Yelenick and Adam Kern
 * @version 11/18/18
 */
public class Ship {
	private int topLeftX;
	private int topLeftY;
	private int length;
	private boolean isHorizontal;
	private boolean[] hits;
	/**
	 * Create an instance of the ship class, recording all necessary information into
	 * any instance variables you choose to create
	 * 
	 * @param topLeftX the x coordinate of the ship's uppermost, leftmost space
	 * @param topLeftY the y coordinate of the ship's uppermost, leftmost space
	 * @param length the number of spaces the ship occupies
	 * @param isHorizontal if true, the ship is placed horizontally, else the ship is placed vertically
	 */
	public Ship(int topLeftX, int topLeftY, int length, boolean isHorizontal) {
		this.topLeftX=topLeftX;
		hits = new boolean[length];
		this.topLeftY=topLeftY;
		this.length=length;
		this.isHorizontal=isHorizontal;
	}
	
	/**
	 * Check if the ship has been sunk
	 * This should only be true if the ship was hit in all the spaces it occupies
	 * 
	 * @return true if the ship has been sunk
	 */
	public int getTopLeftX() {
		return this.topLeftX;
	}
	public int getTopLeftY(){
		return this.topLeftY;
	}
	public int getLength() {
		return this.length;
	}
	public boolean getHorizontal() {
		return this.isHorizontal;
	}
	public boolean isSunk() {
		for(int i = 0; i < length; i++) {
			if(!hits[i]) {
				return false;
			}
		}
		return true;

	}
	
	/**
	 * Check if the ship occupies a space at (x, y)
	 * Note: Be sure to update the hits array so that you can check if the ship is sunk!
	 * 
	 * @param x the x coordinate to shoot at
	 * @param y the y coordinate to shoot at
	 * @return true if this ship occupies that spot (hit), false otherwise (miss)
	 */
	public boolean isHit(int x, int y) {
		
		if (isHorizontal==true && y==this.topLeftY) {
			
			for (int i=0;i<length;i++) {
				//update hits
				if (this.topLeftX+i==x) {
					this.hits[i]=true;
					return true;
				}
			}
		
		}if (isHorizontal==false && x==this.topLeftX) {
			
			for (int i=0;i<length;i++) {
				//update hits
				if (this.topLeftY+i==y) {
					this.hits[i]=true;
					return true;
				}
			}
		}
		return false;
		
	}

	@Override
	public String toString() {
		return "Ship [topLeftX=" + topLeftX + ", topLeftY=" + topLeftY + ", length=" + length + ", isHorizontal="
				+ isHorizontal + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(hits);
		result = prime * result + (isHorizontal ? 1231 : 1237);
		result = prime * result + length;
		result = prime * result + topLeftX;
		result = prime * result + topLeftY;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ship other = (Ship) obj;
		if (!Arrays.equals(hits, other.hits))
			return false;
		if (isHorizontal != other.isHorizontal)
			return false;
		if (length != other.length)
			return false;
		if (topLeftX != other.topLeftX)
			return false;
		if (topLeftY != other.topLeftY)
			return false;
		return true;
	}


	
}