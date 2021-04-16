public class UFO extends Traveller{
    public UFO(Asteroid a) {
        super(a);
    }

    /**
     * Az UFO-t radioakt�v robban�s �ri, amit�l meghal
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
     * itt vagy mozog, vagypedig b�ny�szik az UFO
     * ha a gener�lt random igaz, akkor b�ny�szik, egy�bk�nt pedig morog az aszteroid�j�nak egy random szomsz�dj�ra
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
     * Az UFO ki�r�ti az aszteroid�t, amin tart�zkodik
     */
    public void mine(){
        asteroid.onMine();
    }
}
