import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

import static processing.core.PApplet.*;

public
class PoissonCircle {

    int r = 14;  // Distancia mínima entre mostres
    int k = 30; // Limit d'intents abans de rebutjar

    PVector[] grid;  // Graella per accelerar cerques
    float w;  // dimensions de casella
    int cols, rows;
    PVector centre, topLeft, bottomRight;
    float radiusMax, radiusMin, rectWidth, rectHeight;

    PVector centerGradient;
    float redBottom, redTop, greenBottom, greenTop, blueBottom, blueTop;

    ArrayList<PVector> active;
    ArrayList<PVector> ordered;

    boolean enabled = true;

    PoissonCircle(int k, int r, PVector centre, float radiusMax, float radiusMin){

        this.k = k; this.r = r; this.radiusMax = radiusMax; this.radiusMin = radiusMin;
        PVector topLeft = new PVector(centre.x - radiusMax, centre.y - radiusMax);
        PVector bottomRight = new PVector(centre.x + radiusMax, centre.y + radiusMax);
        this.centre = centre; this.topLeft = topLeft; this.bottomRight = bottomRight;
        this.rectWidth = abs(topLeft.x - bottomRight.x);
        this.rectHeight = abs(topLeft.y - bottomRight.y);

        //STEP 0
        w = r / sqrt(2);

        cols = floor(rectWidth / w);
        rows = floor(rectHeight / w);

        grid = new PVector[cols*rows];
        for (int i = 0; i < cols * rows; i++) {
            grid[i] = null;
        }
        active = new ArrayList<PVector>();
        ordered = new ArrayList<PVector>();

        centerGradient = new PVector(Defaults.getRandom(topLeft.x, bottomRight.x),
                Defaults.getRandom(topLeft.y, bottomRight.y));

        redBottom = (int)Defaults.getRandom(0,100); redTop = (int)Defaults.getRandom(150, 255);
        greenBottom = (int)Defaults.getRandom(0,100); greenTop = (int)Defaults.getRandom(150, 255);
        blueBottom = (int)Defaults.getRandom(0,100); blueTop = (int)Defaults.getRandom(150, 255);
    }

    public ArrayList<PVector> getPoints(){
        println("POINTS: "+grid.length);
        ArrayList<PVector> ps = new ArrayList<PVector>();
        for(int i=0; i<grid.length; i++){
            if(grid[i]!=null){
                PVector point = new PVector(grid[i].x, grid[i].y, 0);
                ps.add(point);
            }
        }
        return ps;
    }

    void startPoisson(PVector p){

        // STEP 1
        PVector pos = new PVector(p.x, p.y);

        int i = floor((p.x - topLeft.x) / w);  // columna
        int j = floor((p.y - topLeft.y) / w);  // fila

        grid[constrain(i + j * cols, 0, cols*rows -1)] = pos;
        active.add(pos);
    }

    void startPoisson(){

        // STEP 1
        PVector pos;
        do {

            pos = new PVector(Defaults.getRandom(topLeft.x,bottomRight.x),
                              Defaults.getRandom(topLeft.y,bottomRight.y));

        } while (!pointInCircle(pos));

        //println("START at: "+pos.x+", "+pos.y);

        int i = floor((pos.x - topLeft.x) / w);  // columna
        int j = floor((pos.y - topLeft.y) / w);  // fila

        grid[constrain(i + j * cols, 0, cols*rows -1)] = pos;
        active.add(pos);
    }

    boolean pointInCircle(PVector p){
        return (dist(centre.x, centre.y, p.x, p.y)<radiusMax)&&(dist(centre.x, centre.y, p.x, p.y)>radiusMin);
    }

    void animatePoisson(int num){

        PVector sample;

        for (int total = 0; total < num; total++) {

            if(active.size()>0){

                int randIndex = floor(Defaults.getRandom(active.size()));
                PVector pos = active.get(randIndex);
                boolean found = false;

                for(int n=0; n<k; n++){
                    do {
                        sample = PVector.random2D();
                        float m = Defaults.getRandom(r, 2 * r);
                        sample.setMag(m);
                        sample.add(pos);
                    } while (!pointInCircle(sample));

                    int col = floor((sample.x - topLeft.x) / w);
                    int row = floor((sample.y - topLeft.y) / w);

                    if (col > -1 && row > -1 && col < cols && row < rows && grid[col + row * cols]==null) {
                        boolean ok = true;
                        for (int i = -1; i <= 1; i++) {
                            for (int j = -1; j <= 1; j++) {
                                int index = (col + i) + (row + j) * cols;
                                PVector neighbor = grid[constrain(index, 0, cols*rows -1)];
                                if(neighbor!=null){
                                    float d = dist(sample.x, sample.y, neighbor.x, neighbor.y);
                                    if (d < r) {
                                        ok = false;
                                    }
                                }
                            }
                        }

                        if(ok){
                            found = true;
                            grid[col + row * cols] = sample;
                            active.add(sample);
                            ordered.add(sample);
                            break;
                        }
                    }

                    if(!found){
                        if(randIndex<active.size()){
                            //active.remove(randIndex);
                        }
                    }

                }
            }
        }
    }

    void drawPoisson(PApplet pA){
        for(int i=0; i<grid.length; i++){

            if((grid[i]!=null)){

                pA.fill(0); pA.noStroke();
                pA.pushMatrix();
                //pA.translate(-Defaults.screenWidth/2, -Defaults.screenHeight/2,0);
                pA.translate(grid[i].x, grid[i].y);
                pA.ellipse(0,0, 5, 5);
                pA.popMatrix();
            }
        }
    }

    void drawPoissonCircle(PApplet pA){
        pA.noFill();
        pA.stroke(255, 50); pA.strokeWeight(1);
        pA.ellipseMode(pA.CENTER);
        pA.ellipse(centre.x, centre.y, 2*radiusMax, 2*radiusMax);
        //ellipse(centre.x, centre.y, radiusMin, radiusMin);

    }

}
