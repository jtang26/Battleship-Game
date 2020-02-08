package finalproject;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import cse131.ArgsProcessor;
import cse131.NotYetImplementedException;

public class HumanPlayer implements Player {

	private final ArgsProcessor ap; // Don't change this!
	private String name;
	private String[][] board;
	private boolean[][] x;
	private int numShips;
	private boolean isHit[][];
	private Set<Ship> numberShips;
	/**
	 * Creates an instance of the HumanPlayer class
	 * Note that we already dealt with taking in an ArgsProcessor object
	 * 		We know you've never seen this before, which is why it's given to you
	 * 		You can treat this ArgsProcessor (ap) throughout the HumanPlayer class
	 * 			like any other ArgsProcessor you've used in 131
	 * We leave the rest of the constructor to you
	 * 
	 * @param name the name of the player
	 * @param height the height of the board
	 * @param width the width of the board
	 * @param ap the ArgsProcessor object
	 */
	public HumanPlayer(String name, int height, int width, ArgsProcessor ap) {
		this.ap = ap;
		this.name=name;
		this.board=new String[width][height];
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				board[i][j] = "*";
			}
		}
		this.isHit=new boolean[width][height];
		this.numberShips=new HashSet<Ship>();
	}

	@Override
	public boolean addShip(Ship s) {

		if (isValidShipToAdd(s)==true) {
			this.numberShips.add(s);
			if(s.getHorizontal()==false) {
				for (int i=s.getTopLeftY();i<(s.getTopLeftY()+s.getLength());i++) {
					this.board[s.getTopLeftX()][i]=".";				
				}
			}else{
				for (int j=s.getTopLeftX();j<(s.getTopLeftX()+s.getLength());j++) {
					this.board[j][s.getTopLeftY()]=".";
				}
			}
			return true;
		}else {
			return false;
		}

	}

	@Override
	public int[] getTargetLocation() {
		int[] coor = new int [2];
		int xCo = ap.nextInt("Enter a X coordinate to fire at");
		int yCo = ap.nextInt("Enter a Y coordinate to fire at");
		while (xCo<0 || xCo>=this.board.length || yCo<0 || yCo>=this.board[0].length) {
			xCo = ap.nextInt("Enter a X coordinate to fire at");
			yCo = ap.nextInt("Enter a Y coordinate to fire at");
		}
		coor[0]=xCo;
		coor[1]=yCo;
		return coor;
	}

	@Override
	public void recordHitOrMiss(int x, int y, boolean isHit) {
		this.isHit[x][y]=isHit;
		if(isHit==true) {
			this.board[x][y]="x";
		}else {
			this.board[x][y]="o";
		}
	}
	/**
	 * Given the length of a ship, decide where on the board to place that ship
	 * Note: The decision must be valid, i.e. the ship does not overlap any other ships or go off the board, etc
	 * 
	 * @param l the length of the ship to place
	 * @return a Ship instance with the desired (x, y) location, length, and orientation
	 */
	@Override
	public Ship decideShipPlacement(int length) {
		int topLeftX = ap.nextInt("Enter a x coordinate for topLeftX position of ship");
		int topLeftY = ap.nextInt("Enter a Y coordinate for topLeftY position of ship");
		boolean hori = ap.nextBoolean("Is the ship horizontal?");
		Ship x = new Ship(topLeftX, topLeftY, length, hori);
		while(topLeftX<0 || topLeftX>=this.board.length || topLeftY<0 || topLeftY>=this.board[0].length || isValidShipToAdd(x)==false) {
			topLeftX = ap.nextInt("Enter a x coordinate for topLeftX position of ship");
			topLeftY = ap.nextInt("Enter a Y coordinate for topLeftY position of ship");
			hori = ap.nextBoolean("Is the ship horizontal?");
			x = new Ship(topLeftX, topLeftY, length, hori);
		}
		return x;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public boolean respondToFire(int x, int y) {
		for(Ship a : this.numberShips) {
			if(a.isHit(x, y)) {
				return true;

			}
		}

		return false;

	}

	@Override
	public int numShipsStillAfloat() {
		int afloat = 0;
		for(Ship x : numberShips) {
			if (x.isSunk()==false) {
				afloat+=1;
			}
		}
		return afloat;
	}

	@Override
	public boolean addRandomShip(int length) {
		boolean x = false;
		int xCo = (int)(Math.random()*this.board.length);
		int yCo = (int)(Math.random()*this.board[0].length);
		if (Math.random()>0.5) {
			x=true;
		}else {
			x=false;
		}
		Ship s = new Ship(xCo, yCo, length, x);
		if (isValidShipToAdd(s)==true) {
			addShip(s);
			return true;
		}else {
			return addRandomShip(length);
//			return false;
		}
		
	}

	@Override
	public boolean isValidShipToAdd(Ship s) {
		if (s.getTopLeftX()<0 || s.getTopLeftY()<0) {
			return false;
		}else if ((s.getTopLeftX()+s.getLength())>this.board.length && s.getHorizontal()==true) {
			return false;
		}else if ((s.getTopLeftY()+s.getLength())>this.board[0].length && s.getHorizontal()==false) {
			return false;
		}

		else if (s.getHorizontal()==false) {
			for (int i=s.getTopLeftY();i<(s.getTopLeftY()+s.getLength());i++) {
				if(this.board[s.getTopLeftX()][i]==".") {
					return false;
				}
			}
		}else if(s.getHorizontal()==true) {
			for (int j=s.getTopLeftX();j<(s.getTopLeftX()+s.getLength());j++) {
				if (this.board[j][s.getTopLeftY()]==".") {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public void printRadar() {

		for ( int i=0;i<this.board.length;i++) {
			System.out.println();
			for (int j=0;j<this.board[0].length;j++) {
				System.out.print(this.board[i][j]);
			}
		}
		System.out.println();
		
	}


}
