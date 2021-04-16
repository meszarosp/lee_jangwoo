public class UFO extends Traveller{
    public UFO(Asteroid a) {
        super(a);
    }

    /**
     * Az UFO-t radioaktív robbanás éri, amitõl meghal
     */
    public void hitByBlast() {
        die();
    }

    /**
     * Az UFO meghal
     */
    public void die() {
        asteroid.removeTraveller(this);
        game.removeUFO(this);
    }

    /**
     * itt vagy mozog, vagypedig bányászik az UFO
     * ha a generált random igaz, akkor bányászik, egyébként pedig morog az aszteroidájának egy random szomszédjára
     */
    public void makeAction(){
        Random rand = new Random();
        boolean randDecision = rand.nextBoolean();
    	if(randDecision)) {
    		mine();
    	} else {
            int randNeighbour = rand.nextInt(asteroid.getNeighbourCount());
    		move(randNeighbour);
    	}

    }

    /**
     * Az UFO kiüríti az aszteroidát, amin tartózkodik
     */
    public void mine(){
        asteroid.onMine();
    }
}
