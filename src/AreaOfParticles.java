import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

import static processing.core.PApplet.println;

public class AreaOfParticles extends SourceOfParticles {

    // Area of Particles
    PVector c1, c2;
    int numParticles;
    float minDist;

    public AreaOfParticles(PVector c1, PVector c2, int np, float md){
        super();
        this.c1 = c1.copy(); this.c2 = c2.copy();
        this.numParticles = np;
        this.minDist = md;

        this.xVar = new RangFloat(0.0f);
        this.yVar = new RangFloat(0.0f);
    }

    public void setVertexs(PVector a, PVector b){
        this.c1 = new PVector(a.x, a.y);
        this.c2 = new PVector(b.x, b.y);
    }

    public void setVertex1(PVector a){
        this.c1 = new PVector(a.x, a.y);
    }

    public void setVertex2(PVector b){
        this.c2 = new PVector(b.x, b.y);
    }


    @Override public ArrayList<AttractedParticle> createParticles(PApplet pA){

        ArrayList<AttractedParticle> particles= new ArrayList<AttractedParticle>();


        for (int i = 0; i < this.numParticles; i++) {

            AttractedParticle p;
            PVector pos;
            do {
                float x = Defaults.getRandom(c1.x, c2.x) + xVar.getRandomValue();
                float y = Defaults.getRandom(c1.y, c2.y) + yVar.getRandomValue();
                pos = new PVector(x, y);
                p = new AttractedParticle(pos, pA.frameCount);

            } while(!p.awayFrom(minDist, particles));

            p.setParamsFromSource(this);
            particles.add(p);

            println(p);
        }
        return particles;
    }


}
