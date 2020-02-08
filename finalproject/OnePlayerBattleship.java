package finalproject;

import cse131.ArgsProcessor;
import cse131.NotYetImplementedException;

public class OnePlayerBattleship implements Battleship {

	private ArgsProcessor ap;
	private Player p1;
	private int numShips;
	private boolean randomShips;
	private int width;
	private int length;
	/**
	 * The main method that gets the starting parameters for a game,
	 * creates an instance of the OnePlayerBattleship class,
	 * and plays the game!
	 * @param args
	 */
	public static void main(String[] args) {
		ArgsProcessor ap = new ArgsProcessor(args);

		String name = ap.nextString("What is the player's name?");
		int length = ap.nextInt("What is the length of the board?");
		int width = ap.nextInt("What is the width of the board?");
		int numShips = ap.nextInt("How many ships should each player have?");
		boolean randomShips = ap.nextBoolean("Should the ships be placed randomly? Type true or false");
		Battleship bs = new OnePlayerBattleship(length, width, randomShips, numShips, name, ap);
		Player winner = bs.play();
		System.out.println(winner.getName() + " won!");
	}

	/**
	 * Create an instance of the OnePlayerBattleship class
	 * Create a player with the given name
	 * Place all ships, randomly or manually
	 * 
	 * NOTE: We've created the player for you, in order to deal with the ArgsProcessor that needs to be passed
	 * 
	 * @param width width of the board (# cols)
	 * @param height height of the board (# rows)
	 * @param randomShips whether or not the ships should be placed randomly
	 * @param playerName the name of the Player who will be playing the game
	 */
	public OnePlayerBattleship(int width, int height, boolean randomShips, int numShips, String playerName, ArgsProcessor ap) {
		p1 = new HumanPlayer(playerName, width, height, ap); // DON'T CHANGE THIS
		this.numShips=numShips;
		this.randomShips=randomShips;
		this.width=width;
		this.length=height;
	}
	/**
	 * Start/play the game!
	 * @return the player who won the game
	 */
	@Override
	public Player play() {
		boolean isHori = false;
		int nShips =0;
		if (this.randomShips==true) {	
			while (nShips<this.numShips) {
				if (Math.random()>=0.5) {
					p1.addRandomShip((int)(Math.random()*(this.width)));
				}else {
					p1.addRandomShip((int)(Math.random()*(this.length)));
				}
				nShips++;
			}
		}else {
			while (nShips<this.numShips) {
				isHori = ap.nextBoolean("Horizontal?");
				int x = ap.nextInt("X Coordinate of Top Left part of ship");
				int y = ap.nextInt("Y coordinate of Top Left part of ship");
				int length = ap.nextInt("What is the size of the ship?");
				Ship a = new Ship(x,y,length,isHori);
				if (p1.isValidShipToAdd(a)) {
					p1.addShip(a);
				}else {
					while (p1.isValidShipToAdd(a)==false) {
						x = ap.nextInt("X Coordinate of Top Left part of ship");
						y = ap.nextInt("Y coordinate of Top Left part of ship");
						length = ap.nextInt("What is the size of the ship?");
					}
				}
				nShips++;
			}
		}
		while(!this.turn(p1)) {}
		return p1;
	}
	/** 
	 * @param p the player whose turn it is
	 * @return true if the game is over, false otherwise
	 */
	@Override
	public boolean turn(Player p) {

		if(p1.numShipsStillAfloat()!=0) {
			p1.printRadar();
			int[] a = p1.getTargetLocation();
			p1.recordHitOrMiss(a[0], a[1],(p1.respondToFire(a[0], a[1])));
			p1.printRadar();
			return false;
		}
		return true;
	}

	/**
	 * We've implemented this for you since there's only one player, you can leave this alone!
	 */
	@Override
	public Player getPlayerOne() {
		return p1;
	}

	/**
	 * We've implemented this for you since there's only one player, you can leave this alone!
	 */
	@Override
	public Player getPlayerTwo() {
		return null;
	}

}
