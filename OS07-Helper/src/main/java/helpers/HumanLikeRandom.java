package helpers;

import java.util.Random;
import org.apache.commons.math3.distribution.BetaDistribution;

public class HumanLikeRandom {
    private final Random random;
    private final BetaDistribution betaX;
    private final BetaDistribution betaY;

    public HumanLikeRandom() {
        this.random = new Random();
        this.betaX = new BetaDistribution(4.5, 5.4);
        this.betaY = new BetaDistribution(4.5, 7.1);
    }
    
    public int nextInt(int integer){
        return random.nextInt(integer);
    }
    
    public int nextBetaX(int integer){
        double beta = betaX.inverseCumulativeProbability(random.nextDouble());
        int rounded = (int) Math.floor(beta * integer);
        return rounded;
    }
    
    public int nextBetaY(int integer){
        double beta = betaY.inverseCumulativeProbability(random.nextDouble());
        int rounded = (int) Math.floor(beta * integer);
        return rounded;
    }
}
