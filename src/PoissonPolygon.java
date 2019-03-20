import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

import static processing.core.PApplet.*;

public
class PoissonPolygon {

    int r = 14;  // Distancia mínima entre mostres
    int k = 30; // Limit d'intents abans de rebutjar

    PVector[] grid;  // Graella per accelerar cerques
    float w;  // dimensions de casella
    int cols, rows;
    ArrayList<PVector> corners;
    PVector topLeft, bottomRight;
    float rectWidth, rectHeight;

    ArrayList<PVector> active;
    ArrayList<PVector> ordered;

    boolean enabled = false;

    PoissonPolygon(int k, int r, ArrayList<PVector> cs){

        this.k = k; this.r = r;
        this.corners = new ArrayList<PVector>();
        for(PVector p : cs){
            this.corners.add(new PVector(p.x, p.y));
        }
        float xMin = 10000, xMax = -10000, yMin = 1000, yMax = -10000;
        for(PVector c : corners){
            if(c.x < xMin){ xMin = c.x; }
            if(c.x > xMax){ xMax = c.x; }
            if(c.y < yMin){ yMin = c.y; }
            if(c.y > yMax){ yMax = c.y; }
        }
        this.topLeft = new PVector(xMin, yMin); this.bottomRight = new PVector(xMax, yMax);
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

        grid[i + j * cols] = pos;
        active.add(pos);
    }

    void startPoisson(PApplet pA){

        // STEP 1

        PVector pos;

        int n=0, numIntents = 1000;

        do {
            pos = new PVector(Defaults.getRandom(topLeft.x,bottomRight.x),
                              Defaults.getRandom(topLeft.y,bottomRight.y));
            pA.fill(255,0,0);
            pA.ellipse(pos.x, pos.y, 10,10);
            n++;
            println(n);
        } while(!pointInPolygon(pos) && n<numIntents);

        if(n==numIntents){
            int nc = 0; //(int) random(corners.size()-1);
            pos = new PVector(corners.get(nc).x, corners.get(nc).y);
        }

        int i = floor((pos.x - topLeft.x) / w);  // columna
        int j = floor((pos.y - topLeft.y) / w);  // fila

        grid[constrain(i + j * cols, 0, cols*rows-1)] = pos;
        active.add(pos);
    }


    boolean pointInPolygon(PVector p){

        int nvert = corners.size();
        //int i = 0; int j = nvert -1;
        boolean c = false;
        for(int i = 0, j = nvert - 1; i < nvert; j= i++){
            PVector cI = corners.get(i);
            PVector cJ = corners.get(j);
            if (((cI.y > p.y) != (cJ.y > p.y)) && (p.x < (cJ.x - cI.x) * (p.y - cI.y) / (cJ.y - cI.y) + cI.x))
                c = !c;
        }
        return c;
    }



    void animatePoisson(int num){
        PVector sample;

        for (int total = 0; total < num; total++) {

            if(active.size()>0){

                int randIndex = floor(Defaults.getRandom(active.size()));
                PVector pos = active.get(randIndex);
                boolean found = false;

                for(int n=0; n<k; n++){

                    int ni=0, numIntents=10;

                    do {
                        sample = PVector.random2D();
                        float m = Defaults.getRandom(r, 2 * r);
                        sample.setMag(m);
                        sample.add(pos);

                    } while (!pointInPolygon(sample) && ni<numIntents);

                    if(ni==numIntents) break;

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

    void drawPoisson(PApplet  pA){
        for(int i=0; i<grid.length; i++){
            if((grid[i]!=null)){
                pA.fill(0); pA.noStroke();
                pA.pushMatrix();
                pA.translate(-Defaults.screenWidth/2, -Defaults.screenHeight/2,0);
                pA.translate(grid[i].x, grid[i].y,0);
                pA.ellipse(0,0, 5, 5);
                pA.popMatrix();
            }
        }
    }

    void drawPolygonBorders(PApplet pA){
        PVector c0, c1;
        pA.noFill();
        pA.stroke(255, 50); pA.strokeWeight(1);
        for(int i=0; i<corners.size(); i++){
            c0 = corners.get(i);
            if(i<corners.size()-1){
                c1 = corners.get(i+1);
            }
            else {
                c1 = corners.get(0);
            }
            pA.line(c0.x, c0.y, c1.x, c1.y);
        }
    }

    void drawPolygonCorners(PApplet pA){
        for(PVector p : corners){
            pA.fill(255);
            pA.ellipseMode(CENTER);
            pA.ellipse(p.x, p.y, 25, 25);
        }
    }

}
