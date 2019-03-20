import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

import static processing.core.PApplet.lerp;

public

class LineOfParticles extends SourceOfParticles {

    // Line of Particles
    PVector c1, c2;
    float numParticles;
    float lineWidth;

    public LineOfParticles(PVector c1, PVector c2, int n, float lw){
        super();
        this.c1 = c1.copy(); this.c2 = c2.copy();
        this.numParticles = n; this.lineWidth = lw;
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

    public void setNumParticles(int np){
        this.numParticles = np;
    }

    public void setLineWidth(float lw){
        this.lineWidth = lw;
    }


    public ArrayList<AttractedParticle> createParticles(PApplet pA){

        ArrayList<AttractedParticle> particles= new ArrayList<AttractedParticle>();

        float amtStep = 1/numParticles;
        float wStep = 5.0f;

        for(float w=0.0f; w<lineWidth; w+=wStep){
            for(float amt = 0.0f; amt<=1.0f; amt+=amtStep){
                float xpos = lerp(c1.x, c2.x, amt) + xVar.getRandomValue();
                float ypos = lerp(c1.y + w, c2.y + w, amt) + yVar.getRandomValue();
                PVector position = new PVector(xpos, ypos);
                AttractedParticle p = new AttractedParticle(position, pA.frameCount);
                p.setParamsFromSource(this);
                particles.add(p);
            }
        }
        return particles;
    }


}
