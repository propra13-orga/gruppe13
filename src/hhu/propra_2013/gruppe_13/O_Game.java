package hhu.propra_2013.gruppe_13;

class O_Game {
	
	static GameGraphics graphics;
	protected static Logic logic;
	
	// Initialize method for the actual game
	protected static void init() {
		graphics = new GameGraphics();
		logic = new Logic(graphics);
	}
}