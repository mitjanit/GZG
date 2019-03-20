import controlP5.Range;
import geomerative.RG;
import geomerative.RPoint;
import geomerative.RShape;
import processing.core.PApplet;
import processing.core.PVector;
import java.util.ArrayList;

import static controlP5.ControlP5Constants.TWO_PI;
import static processing.core.PApplet.*;

public class Preview {

    public static void previewAll(PApplet pA, ControlWindow cw){
        pA.background(255, 255, 255, 255);
        switch(cw.activeTab) {
            case "line": previewLineOfPoints(pA, cw); break;
            case "random": previewAreaOfPoints(pA, cw); break;
            case "bezier": previewBezierOfPoints(pA, cw); break;
            case "polyline": previewPolyLineOfPoints(pA, cw); break;
            case "polygon": previewPolygonOfPoints(pA, cw); break;
            case "circle": previewRingOfPoints(pA, cw); break;
            case "spiral": previewSpiralOfPoints(pA, cw); break;
            case "grid": previewGridOfPoints(pA, cw); break;
            case "wave": previewWaveOfPoints(pA, cw); break;
            case "formula": previewFormulaOfPoints(pA, cw); break;
            case "whitney": previewWhitneyOfPoints(pA, cw); break;
            case "text": previewTextOfPoints(pA, cw); break;
            case "poisson": previewPoissonOfPoints(pA, cw); break;
            /*



            case "particles": //println("PREVIEW PARTICLES.");
                previewParticles();
                break;*/
        }
    }


    // Preview LINE ************************************************//

    public static void previewLineOfPoints(PApplet pA, ControlWindow cw) {
        //println("LINE OF POINTS.");
        int nt= cw.cRepeat.numTimes;
        int numPoints = cw.cCommons.numPoints;
        PVector corner1 = cw.cLine.corner1;
        PVector corner2 = cw.cLine.corner2;
        RangFloat dx = cw.cRepeat.displaceX;
        RangFloat dy = cw.cRepeat.displaceY;
        for (int t=0; t < nt; t++) {

            PVector p1 = new PVector(corner1.x + t*dx.getMaxValue(), corner1.y + t*dy.getMaxValue());
            PVector p2 = new PVector(corner2.x + t*dx.getMaxValue(), corner2.y + t*dy.getMaxValue());
            displayLine(pA, p1, p2, numPoints);

            if (cw.cRepeat.symmetryX && t>0) {
                PVector p1s = new PVector(corner1.x - t*dx.getMaxValue(), corner1.y + t*dy.getMaxValue());
                PVector p2s = new PVector(corner2.x - t*dx.getMaxValue(), corner2.y + t*dy.getMaxValue());
                displayLine(pA, p1s, p2s, numPoints);
            }
            if (cw.cRepeat.symmetryY && t>0) {
                PVector p1s = new PVector(corner1.x + t*dx.getMaxValue(), corner1.y - t*dy.getMaxValue());
                PVector p2s = new PVector(corner2.x + t*dx.getMaxValue(), corner2.y - t*dy.getMaxValue());
                displayLine(pA, p1s, p2s, numPoints);
            }
            if (cw.cRepeat.symmetryX && cw.cRepeat.symmetryY && t>0) {
                PVector p1s = new PVector(corner1.x - t*dx.getMaxValue(), corner1.y - t*dy.getMaxValue());
                PVector p2s = new PVector(corner2.x - t*dx.getMaxValue(), corner2.y - t*dy.getMaxValue());
                displayLine(pA, p1s, p2s, numPoints);
            }
        }
    }

    public static void displayLine(PApplet pA, PVector p1, PVector p2, int numP) {
        for (int i = 0; i < numP; i++) {
            float x = map(i, 0, numP-1, p1.x, p2.x);
            float y = map(i, 0, numP-1, p1.y, p2.y);
            pA.fill(0);
            pA.noStroke();
            pA.pushMatrix();
                //pA.translate(-screenWidth/2, -screenHeight/2, 0);
                pA.translate(x, y, 0);
                pA.ellipse(0, 0, 5, 5);
            pA.popMatrix();
        }
    }

    // Preview AREA ****************************************************//

    public static void previewAreaOfPoints(PApplet pA, ControlWindow cw) {
        //println("AREA OF POINTS.");
        int nt= cw.cRepeat.numTimes;
        RangFloat dx = cw.cRepeat.displaceX;
        RangFloat dy = cw.cRepeat.displaceY;
        Range rX = cw.cRandom.rX;
        Range rY = cw.cRandom.rY;
        PVector p1s, p2s;
        //println("PREVIEW AREA OF POINTS.");
        for (int t=0; t < nt; t++) {
            PVector p1 = new PVector(rX.getLowValue() + t*dx.getMaxValue(), rY.getLowValue() + t*dy.getMaxValue());
            PVector p2 = new PVector(rX.getHighValue() + t*dx.getMaxValue(), rY.getHighValue() + t*dy.getMaxValue());
            displayArea(pA, p1, p2);
            if (cw.cRepeat.symmetryX && t>0) {
                p1s = new PVector(rX.getLowValue() - t*dx.getMaxValue(), rY.getLowValue() + t*dy.getMaxValue());
                p2s = new PVector(rX.getHighValue() - t*dx.getMaxValue(), rY.getHighValue() + t*dy.getMaxValue());
                displayArea(pA, p1s, p2s, pA.color(255, 0, 0));
            }
            if (cw.cRepeat.symmetryY && t>0) {
                p1s = new PVector(rX.getLowValue() + t*dx.getMaxValue(), rY.getLowValue() - t*dy.getMaxValue());
                p2s = new PVector(rX.getHighValue() + t*dx.getMaxValue(), rY.getHighValue() - t*dy.getMaxValue());
                displayArea(pA, p1s, p2s, pA.color(0, 255, 0));
            }
            if (cw.cRepeat.symmetryX && cw.cRepeat.symmetryY && t>0) {
                p1s = new PVector(rX.getLowValue() - t*dx.getMaxValue(), rY.getLowValue() - t*dy.getMaxValue());
                p2s = new PVector(rX.getHighValue() - t*dx.getMaxValue(), rY.getHighValue() - t*dy.getMaxValue());
                displayArea(pA, p1s, p2s, pA.color(0, 0, 255));
            }
        }
    }

    public static void displayArea(PApplet pA, PVector p1, PVector p2) {

        pA.pushMatrix();
            //pA.translate(-Defaults.screenWidth/2, -Defaults.screenHeight/2, 0);
            pA.stroke(0);
            pA.noFill();
            pA.line(p1.x, p1.y, p2.x, p1.y);
            pA.line(p2.x, p1.y, p2.x, p2.y);
            pA.line(p1.x, p2.y, p2.x, p2.y);
            pA.line(p1.x, p1.y, p1.x, p2.y);
        pA.popMatrix();
    }

    public static void displayArea(PApplet pA, PVector p1, PVector p2, int c) {
        pA.stroke(c);
        pA.pushMatrix();
            //pA.translate(-Defaults.screenWidth/2, -Defaults.screenHeight/2, 0);
            pA.line(p1.x, p1.y, p2.x, p1.y);
            pA.line(p2.x, p1.y, p2.x, p2.y);
            pA.line(p1.x, p2.y, p2.x, p2.y);
            pA.line(p1.x, p1.y, p1.x, p2.y);
        pA.popMatrix();
    }

    // Preview POLYLINE ************************************************//

    public static void displayPolyLine(PApplet pA, PVector p1, PVector p2, int numP) {
        for (int i = 0; i < numP; i++) {
            float x = map(i, 0, numP-1, p1.x, p2.x);
            float y = map(i, 0, numP-1, p1.y, p2.y);
            pA.fill(0); //noStroke();
            pA.pushMatrix();
                pA.translate(x, y, 0);
                pA.ellipse(0, 0, 10, 10);
            pA.popMatrix();
        }
    }

    public static void previewPolyLineOfPoints(PApplet pA, ControlWindow cw) {
        //println("POLYLINE OF POINTS.");
        int nt= cw.cRepeat.numTimes;
        RangFloat dx = cw.cRepeat.displaceX;
        RangFloat dy = cw.cRepeat.displaceY;

        pA.strokeWeight(1);
        pA.noFill();

        for (int t=0; t<nt; t++) {
            float ddx = t*dx.getMaxValue(), ddy = t*dy.getMaxValue();
            pA.pushMatrix();
                //pA.translate(-Defaults.screenWidth/2, -Defaults.screenHeight/2, 0);
                //TEXT
                if (cw.cPolyline.addPolyPointOnClick) {
                    pA.text(cw.cBezier.createText, Defaults.screenWidth/2, Defaults.screenHeight/5);
                } else if (cw.cPolyline.editPolyPointOnDrag) {
                    pA.text(cw.cBezier.editText, Defaults.screenWidth/2, Defaults.screenHeight/5);
                }
                // Display Polyline points
                ArrayList<PVector> plPoints = cw.cPolyline.polylinePoints;
                for (int i=1; i<plPoints.size(); i++) {
                    PVector p1 = new PVector(plPoints.get(i).x + t*dx.getMaxValue(), plPoints.get(i).y + t*dy.getMaxValue());
                    PVector p2 = new PVector(plPoints.get(i-1).x + t*dx.getMaxValue(), plPoints.get(i-1).y + t*dy.getMaxValue());
                    pA.line(p1.x, p1.y, p2.x, p2.y);
                    displayPolyLine(pA, p1, p2, cw.cCommons.numPoints);
                    if (cw.cRepeat.symmetryX && t>0) {
                        PVector p1s = new PVector(plPoints.get(i).x - t*dx.getMaxValue(), plPoints.get(i).y + t*dy.getMaxValue());
                        PVector p2s = new PVector(plPoints.get(i-1).x - t*dx.getMaxValue(), plPoints.get(i-1).y + t*dy.getMaxValue());
                        pA.line(p1s.x, p1s.y, p2s.x, p2s.y);
                        displayPolyLine(pA, p1s, p2s, cw.cCommons.numPoints);
                    }
                    if (cw.cRepeat.symmetryY && t>0) {
                        PVector p1s = new PVector(plPoints.get(i).x + t*dx.getMaxValue(), plPoints.get(i).y - t*dy.getMaxValue());
                        PVector p2s = new PVector(plPoints.get(i-1).x + t*dx.getMaxValue(), plPoints.get(i-1).y - t*dy.getMaxValue());
                        pA.line(p1s.x, p1s.y, p2s.x, p2s.y);
                        displayPolyLine(pA, p1s, p2s, cw.cCommons.numPoints);
                    }
                    if (cw.cRepeat.symmetryX && cw.cRepeat.symmetryY && t>0) {
                        PVector p1s = new PVector(plPoints.get(i).x - t*dx.getMaxValue(), plPoints.get(i).y - t*dy.getMaxValue());
                        PVector p2s = new PVector(plPoints.get(i-1).x - t*dx.getMaxValue(), plPoints.get(i-1).y - t*dy.getMaxValue());
                        pA.line(p1s.x, p1s.y, p2s.x, p2s.y);
                        displayPolyLine(pA, p1s, p2s, cw.cCommons.numPoints);
                    }
                }
            pA.popMatrix();
        }
    }

    // Preview BEZIER **************************************************//

    public static void previewBezierOfPoints(PApplet pA, ControlWindow cw){
        int nt= cw.cRepeat.numTimes;
        RangFloat dx = cw.cRepeat.displaceX;
        RangFloat dy = cw.cRepeat.displaceY;
        RangFloat dr = cw.cRepeat.displaceR;

        pA.strokeWeight(1);
        pA.noFill();

        PVector centroide = getCentroide(getPointsFromBeziers(cw.cBezier.beziers));
        for (int t=0; t<nt; t++) {
            float ddx = t*dx.getMaxValue(), ddy = t*dy.getMaxValue(), ddr =t*dr.getMaxValue();
            pA.pushMatrix();
                //pA.translate(-screenWidth/2, -screenHeight/2, 0);
                //TEXT
                if (cw.cBezier.addBezierOnClick) {
                    pA.text(cw.cBezier.createText, Defaults.screenWidth/2, Defaults.screenHeight/5);
                } else if (cw.cBezier.editBezierOnDrag) {
                    pA.text(cw.cBezier.editText, Defaults.screenWidth/2, Defaults.screenHeight/5);
                }

                // Display bezier points
                for (int i=0; i<cw.cBezier.beziers.size(); i++) {
                    BezierCurve bc = cw.cBezier.beziers.get(i).copy();
                    bc.dilate(centroide, ddr);
                    bc.display(pA,cw.cCommons.numPoints, ddx, ddy);
                    if (cw.cRepeat.symmetryX  && t>0) {
                        bc.display(pA,cw.cCommons.numPoints, -ddx, ddy);
                    } else if (cw.cRepeat.symmetryY && t>0) {
                        bc.display(pA,cw.cCommons.numPoints, ddx, -ddy);
                    }
                }
            pA.popMatrix();
        }
    }

    public  static ArrayList<PVector> getPointsFromBeziers(ArrayList<BezierCurve> bcs) {
        ArrayList<PVector> pts = new ArrayList<PVector>();
        for (BezierCurve bc : bcs) {
            for (PVector p : bc.points) {
                if (bc.isAnchorPoint(p) && !pts.contains(p))
                    pts.add(p);
            }
        }
        return pts;
    }

    public static PVector getCentroide(ArrayList<PVector> ps) {
        float x=0, y=0;
        for (PVector p : ps) {
            x += p.x;
            y += p.y;
        }
        return new PVector(x/ps.size(), y/ps.size());
    }

    // Preview POLYGON ******************************************************//

    public static  void previewPolygonOfPoints(PApplet pA, ControlWindow cw){
        // println("POLYGON OF POINTS.");
        int nt= cw.cRepeat.numTimes;
        int numVertexos = cw.cParticles.numVertexos;
        int numPoints = cw.cCommons.numPoints;
        PVector centre = cw.cCircle.centre;
        RangFloat dx = cw.cRepeat.displaceX;
        RangFloat dy = cw.cRepeat.displaceY;
        RangFloat dr = cw.cRepeat.displaceR;
        RangFloat da = cw.cRepeat.displaceA;
        float radius = cw.cCircle.rMaxRadius.getHighValue();
        float angle0 = cw.cCircle.rRandomAngle.getHighValue();

        for (int t=0; t<nt; t++) {
            PVector c = new PVector(centre.x + t*dx.getMaxValue(), centre.y + t*dy.getMaxValue());
            float r = radius + t*dr.getMaxValue();
            float a = angle0 + t*da.getMaxValue();
            displayPolygon(pA,c, r, numVertexos, a, numPoints);

            if (cw.cRepeat.symmetryX && t>0) {
                PVector cs = new PVector(centre.x - t*dx.getMaxValue(), centre.y + t*dy.getMaxValue());
                displayPolygon(pA, cs, r, numVertexos, a, numPoints);
            }
            if (cw.cRepeat.symmetryY && t>0) {
                PVector cs = new PVector(centre.x + t*dx.getMaxValue(), centre.y - t*dy.getMaxValue());
                displayPolygon(pA, cs, r, numVertexos, a, numPoints);
            }
            if (cw.cRepeat.symmetryX && cw.cRepeat.symmetryY && t>0) {
                PVector cs = new PVector(centre.x - t*dx.getMaxValue(), centre.y - t*dy.getMaxValue());
                displayPolygon(pA,cs, r, numVertexos, a, numPoints);
            }
        }
    }

    public static void displayPolygon(PApplet pA, PVector c, float r, int nv, float a, int np) {
        float astep = TWO_PI/nv;
        for (int i=0; i<nv; i++) {
            float x0 = c.x + r*cos(a + i*astep);
            float y0 = c.y + r*sin(a + i*astep);
            float x1 = c.x + r*cos(a + ((i+1)%nv)*astep);
            float y1 = c.y + r*sin(a + ((i+1)%nv)*astep);
            pA.pushMatrix();
                //pA.translate(-Defaults.screenWidth/2, -Defaults.screenHeight/2, 0);
                pA.stroke(0);
                pA.strokeWeight(1);
                pA.noFill();
                pA.line(x0, y0, x1, y1);
                pA.fill(0);
                pA.ellipse(x0, y0, 10, 10);
            pA.popMatrix();
            displayLine(pA, new PVector(x0, y0), new PVector(x1, y1), np);
        }
    }

    // Preview RING ************************************************************//

    public static void previewRingOfPoints(PApplet pA, ControlWindow cw){
        //println("PREVIEW CIRCLE OF POINTS.");
        int nt= cw.cRepeat.numTimes;
        PVector centre = cw.cCircle.centre;
        RangFloat dx = cw.cRepeat.displaceX;
        RangFloat dy = cw.cRepeat.displaceY;
        RangFloat dr = cw.cRepeat.displaceR;
        RangFloat da = cw.cRepeat.displaceA;
        for (int t=0; t<nt; t++) {
            PVector c = new PVector(centre.x + t*dx.getMaxValue(), centre.y + t*dy.getMaxValue());
            float r0 = cw.cCircle.rMinRadius.getHighValue() + t*dr.getMaxValue();
            float r1 = cw.cCircle.rMaxRadius.getHighValue() + t*dr.getMaxValue();
            float aStep = cw.cCircle.rAngleStep.getHighValue()/100 + t*da.getMaxValue();
            float rStep = cw.cCircle.rRadiusStep.getHighValue()/100 + t*dr.getMaxValue();
            if (cw.cCircle.fibonacci) {
                displayFibo(pA,c, r0, r1, aStep);
                if (cw.cRepeat.symmetryX && t>0) {
                    PVector cs = new PVector(centre.x - t*dx.getMaxValue(), centre.y + t*dy.getMaxValue());
                    displayFibo(pA,cs, r0, r1, aStep);
                } else if (cw.cRepeat.symmetryY && t>0) {
                    PVector cs = new PVector(centre.x + t*dx.getMaxValue(), centre.y - t*dy.getMaxValue());
                    displayFibo(pA, cs, r0, r1, aStep);
                }
            } else {
                displayRing(pA, cw, centre, r0, r1, rStep);
                if (cw.cRepeat.symmetryX && t>0) {
                    PVector cs = new PVector(centre.x - t*dx.getMaxValue(), centre.y + t*dy.getMaxValue());
                    displayRing(pA, cw, cs, r0, r1, rStep);
                } else if (cw.cRepeat.symmetryY && t>0) {
                    PVector cs = new PVector(centre.x + t*dx.getMaxValue(), centre.y - t*dy.getMaxValue());
                    displayRing(pA, cw, cs, r0, r1, rStep);
                }
            }
        }
    }

    public static void displayFibo(PApplet pA, PVector c, float r0, float r1, float aStep) {

        float a = 0.0f;
        for (float v=0.0f; v<1.0f; v+= aStep) {
            float r = map(sqrt(v), 0, 1, r0, r1);
            a += TWO_PI*((sqrt(5)-1)/2);
            float x = c.x + r*cos(a);
            float y = c.y + r*sin(a);
            pA.fill(0);
            pA.noStroke();
            pA.pushMatrix();
                //pA.translate(-Defaults.screenWidth/2, -Defaults.screenHeight/2, 0);
                pA.translate(x, y, 0);
                pA.ellipse(0, 0, 5, 5);
            pA.popMatrix();
        }
    }

    public static void displayRing(PApplet pA, ControlWindow cw, PVector c, float r0, float r1, float rStep) {

        for (float r = r0; r<r1; r+=rStep) {

            float a0 = cw.cCircle.rAngle.getLowValue();
            float a1 = cw.cCircle.rAngle.getHighValue();
            float aStep = map(r, r0, r1, cw.cCircle.rAngleStep.getHighValue(), cw.cCircle.rAngleStep.getLowValue());
            rStep = map(r, r0, r1, cw.cCircle.rRadiusStep.getHighValue(), cw.cCircle.rRadiusStep.getLowValue());

            for (float a = a0; a < a1; a += aStep) {
                float rf = r + rStep;
                float x = c.x + rf*cos(a);
                float y = c.y + rf*sin(a);
                pA.fill(0);
                pA.noStroke();
                pA.pushMatrix();
                    //pA.translate(-screenWidth/2, -screenHeight/2, 0);
                    pA.translate(x, y, 0);
                    pA.ellipse(0, 0, 5, 5);
                pA.popMatrix();
            }
        }
    }

    // Preview SPIRAL ***********************************************************//

    public static  void previewSpiralOfPoints(PApplet pA, ControlWindow cw){
        //println("PREVIEW SPIRAL OF POINTS.");
        int nt= cw.cRepeat.numTimes;
        PVector centre = cw.cCircle.centre;
        RangFloat dx = cw.cRepeat.displaceX;
        RangFloat dy = cw.cRepeat.displaceY;
        RangFloat dr = cw.cRepeat.displaceR;
        RangFloat da = cw.cRepeat.displaceA;

        float rStep = cw.cCircle.rRadiusStep.getHighValue();
        float aStep = cw.cCircle.rAngleStep.getLowValue();

        for (int t=0; t<nt; t++) {
            PVector c = new PVector(centre.x + t*dx.getMaxValue(), centre.y + t*dy.getMaxValue());
            float r0 = cw.cCircle.rMinRadius.getHighValue() + t*dr.getMaxValue();
            float r1 = cw.cCircle.rMaxRadius.getHighValue() + t*dr.getMaxValue();
            float a0 = cw.cCircle.rAngle.getLowValue() + t*da.getMaxValue();
            float a1 = cw.cCircle.rAngle.getHighValue() + t*da.getMaxValue();
            displaySpiral(pA, cw, c, a0, a1, aStep, r0, r1, rStep);

            if (cw.cRepeat.symmetryX && t>0) {
                PVector cs = new PVector(cw.cLine.corner1.x - t*dx.getMaxValue(), cw.cLine.corner1.y + t*dy.getMaxValue());
                displaySpiral(pA, cw, cs, a0, a1, aStep, r0, r1, rStep);
            } else if (cw.cRepeat.symmetryY && t>0) {
                PVector cs = new PVector(cw.cLine.corner1.x + t*dx.getMaxValue(), cw.cLine.corner1.y - t*dy.getMaxValue());
                displaySpiral(pA, cw, cs, a0, a1, aStep, r0, r1, rStep);
            }
        }

    }

    public static void displaySpiral(PApplet pA, ControlWindow cw, PVector c, float a0, float a1, float as, float r0, float r1, float rs) {

        float rStep=rs;
        float aStep=as;

        for (float r = r0, a=a0; r<r1 && a<a1; r+=rStep, a+=aStep) {
            if (cw.cSpiral.invertRadius) {
                rStep = map(r, r0, r1, cw.cCircle.rRadiusStep.getHighValue(), cw.cCircle.rRadiusStep.getLowValue());
            } else {
                rStep = map(r, r0, r1, cw.cCircle.rRadiusStep.getLowValue(), cw.cCircle.rRadiusStep.getHighValue());
            }
            if (cw.cSpiral.invertAngle) {
                aStep = map(a, a0, a1, cw.cCircle.rAngleStep.getHighValue(), cw.cCircle.rAngleStep.getLowValue());
            } else {
                aStep = map(a, a0, a1, cw.cCircle.rAngleStep.getLowValue(), cw.cCircle.rAngleStep.getHighValue());
            }
            float x = c.x + r*cos(a);
            float y = c.y + r*sin(a);
            pA.pushMatrix();
                //pA.translate(-Defaults.screenWidth/2, -Defaults.screenHeight/2, 0);
                pA.translate(x, y, 0);
                pA.ellipse(0, 0, 5, 5);
            pA.popMatrix();
        }
    }

    // Preview GRID *********************************************************//

    public  static  void previewGridOfPoints(PApplet pA, ControlWindow cw){
        //println("PREVIEW GRID OF POINTS.");
        int nt= cw.cRepeat.numTimes;
        RangFloat dx = cw.cRepeat.displaceX;
        RangFloat dy = cw.cRepeat.displaceY;
        for (int t=0; t<nt; t++) {

            PVector p1 = new PVector(cw.cLine.corner1.x + t*dx.getMaxValue(), cw.cLine.corner1.y + t*dy.getMaxValue());
            PVector p2 = new PVector(cw.cLine.corner2.x + t*dx.getMaxValue(), cw.cLine.corner2.y + t*dy.getMaxValue());
            displayGrid(pA, p1, p2, (int)cw.cGrid.sNumCols.getValue(), (int)cw.cGrid.sNumRows.getValue());

            if (cw.cRepeat.symmetryX && t>0) {
                PVector p1s = new PVector(cw.cLine.corner1.x - t*dx.getMaxValue(), cw.cLine.corner1.y + t*dy.getMaxValue());
                PVector p2s = new PVector(cw.cLine.corner2.x - t*dx.getMaxValue(), cw.cLine.corner2.y + t*dy.getMaxValue());
                displayGrid(pA, p1s, p2s, (int)cw.cGrid.sNumCols.getValue(), (int)cw.cGrid.sNumRows.getValue());
            }
            if (cw.cRepeat.symmetryY && t>0) {
                PVector p1s = new PVector(cw.cLine.corner1.x + t*dx.getMaxValue(), cw.cLine.corner1.y - t*dy.getMaxValue());
                PVector p2s = new PVector(cw.cLine.corner2.x + t*dx.getMaxValue(), cw.cLine.corner2.y - t*dy.getMaxValue());
                displayGrid(pA, p1s, p2s, (int)cw.cGrid.sNumCols.getValue(), (int)cw.cGrid.sNumRows.getValue());
            }
            if (cw.cRepeat.symmetryX && cw.cRepeat.symmetryY && t>0) {
                PVector p1s = new PVector(cw.cLine.corner1.x - t*dx.getMaxValue(), cw.cLine.corner1.y - t*dy.getMaxValue());
                PVector p2s = new PVector(cw.cLine.corner2.x - t*dx.getMaxValue(), cw.cLine.corner2.y - t*dy.getMaxValue());
                displayGrid(pA, p1s, p2s, (int)cw.cGrid.sNumCols.getValue(), (int)cw.cGrid.sNumRows.getValue());
            }
        }
    }

    public static void displayGrid(PApplet pA, PVector p1, PVector p2, int nc, int nr) {

        float wCol = (p2.x - p1.x)/(nc-1);
        float hRow = (p2.y - p1.y)/(nr-1);

        for (int r=0; r<nr; r++) {
            for (int c=0; c < nc; c++) {
                float x = p1.x + wCol*c;
                float y = p1.y + hRow*r;
                pA.fill(0);
                pA.noStroke();
                pA.pushMatrix();
                    //pA.translate(-Defaults.screenWidth/2, -Defaults.screenHeight/2, 0);
                    pA.translate(x, y, 0);
                    pA.ellipse(0, 0, 5, 5);
                pA.popMatrix();
            }
        }
    }

    // Preview WAVE ********************************************************//

    public static void previewWaveOfPoints(PApplet pA, ControlWindow cw){
        //println("PREVIEW WAVE OF POINTS.");
        int nt= cw.cRepeat.numTimes;
        RangFloat dx = cw.cRepeat.displaceX;
        RangFloat dy = cw.cRepeat.displaceY;
        RangFloat da = cw.cRepeat.displaceA;
        float xs = cw.cWave.rXStep.getHighValue();
        float as = cw.cCircle.rAngleStep.getHighValue();

        for (int t=0; t<nt; t++) {

            PVector p1 = new PVector(cw.cLine.corner1.x + t*dx.getMaxValue(), cw.cLine.corner1.y + t*dy.getMaxValue());
            PVector p2 = new PVector(cw.cLine.corner2.x + t*dx.getMaxValue(), cw.cLine.corner2.y + t*dy.getMaxValue());
            float a0 = cw.cCircle.rAngle.getHighValue() + t*da.getMaxValue();
            displayWave(pA, cw, p1, p2, xs, a0, as);

            if (cw.cRepeat.symmetryX && t>0) {
                PVector p1s = new PVector(cw.cLine.corner1.x - t*dx.getMaxValue(), cw.cLine.corner1.y + t*dy.getMaxValue());
                PVector p2s = new PVector(cw.cLine.corner2.x - t*dx.getMaxValue(), cw.cLine.corner2.y + t*dy.getMaxValue());
                displayWave(pA, cw, p1s, p2s, xs, a0, as);
            } else if (cw.cRepeat.symmetryY && t>0) {
                PVector p1s = new PVector(cw.cLine.corner1.x + t*dx.getMaxValue(), cw.cLine.corner1.y - t*dy.getMaxValue());
                PVector p2s = new PVector(cw.cLine.corner2.x + t*dx.getMaxValue(), cw.cLine.corner2.y - t*dy.getMaxValue());
                displayWave(pA, cw, p1s, p2s, xs, a0, as);
            }
        }
    }

    public  static void displayWave(PApplet pA, ControlWindow cw, PVector p1, PVector p2, float xs, float a0, float as) {

        float h;
        float a = a0;
        float distX = abs(p1.x - p2.x);
        float distY = abs(p1.y - p2.y);

        if (distX >= distY) {
            float minX = min(p1.x, p2.x);
            float maxX = max(p1.x, p2.x);
            for (float x = minX; x < maxX; x+=xs) {
                if (cw.cWave.ampAsc) h = map(x, minX, maxX, cw.cWave.rAmplitud.getLowValue(), cw.cWave.rAmplitud.getHighValue());
                else h = map(x, minX, maxX, cw.cWave.rAmplitud.getHighValue(), cw.cWave.rAmplitud.getLowValue());
                float y = map(x, p1.x, p2.x, p1.y, p2.y) + h*sin(a);
                pA.pushMatrix();
                    //pA.translate(-Defaults.screenWidth/2, -Defaults.screenHeight/2, 0);
                    pA.translate(x, y, 0);
                    pA.ellipse(0, 0, 5, 5);
                pA.popMatrix();
                a+= as;
            }
        } else {
            float minY = min(p1.y, p2.y);
            float maxY = max(p1.y, p2.y);
            for (float y = minY; y < maxY; y+=xs) {
                if (cw.cWave.ampAsc) h = map(y, minY, maxY, cw.cWave.rAmplitud.getLowValue(), cw.cWave.rAmplitud.getHighValue());
                else h = map(y, minY, maxY, cw.cWave.rAmplitud.getHighValue(), cw.cWave.rAmplitud.getLowValue());
                float x = map(y, p1.y, p2.y, p1.x, p2.x) + h*sin(a);
                pA.pushMatrix();
                    //pA.translate(-Defaults.screenWidth/2, -Defaults.screenHeight/2, 0);
                    pA.translate(x, y, 0);
                    pA.ellipse(0, 0, 5, 5);
                pA.popMatrix();
                a+= as;
            }
        }
    }

    // Preview FORMULA ***********************************************//

    public static void previewFormulaOfPoints(PApplet pA, ControlWindow cw){
        //println("PREVIEW FORMULA OF POINTS.");
        int nt= cw.cRepeat.numTimes;
        RangFloat dx = cw.cRepeat.displaceX;
        RangFloat dy = cw.cRepeat.displaceY;
        RangFloat dr = cw.cRepeat.displaceR;
        float newscaler = cw.cFormula.sSizeF.getValue();

        for (int s0 = (int)cw.cFormula.sLapsF.getValue(); s0 > 0; s0--) {

            float mm = (int)cw.cFormula.sM.getValue() + s0;
            float nn1 = cw.cFormula.sN1.getValue() + s0;
            float nn2 = cw.cFormula.sN2.getValue() + s0;
            float nn3 = cw.cFormula.sN3.getValue() + s0;
            newscaler = newscaler * cw.cFormula.sStepF.getValue();
            float sscaler = newscaler;

            PVector[] points = superformula(mm, nn1, nn2, nn3, cw.cCommons.numPoints);

            for (int t=0; t<nt; t++) {
                PVector c = new PVector(cw.cCircle.centre.x + t*dx.getMaxValue(), cw.cCircle.centre.y + t*dy.getMaxValue());
                float sscaler0 = sscaler + t*dr.getMaxValue();
                diplayFormula(pA, c, points, sscaler0);

                if (cw.cRepeat.symmetryX && t>0) {
                    PVector cs = new PVector(cw.cCircle.centre.x - t*dx.getMaxValue(), cw.cCircle.centre.y + t*dy.getMaxValue());
                    diplayFormula(pA, cs, points, sscaler0);
                }
                if (cw.cRepeat.symmetryY && t>0) {
                    PVector cs = new PVector(cw.cCircle.centre.x + t*dx.getMaxValue(), cw.cCircle.centre.y - t*dy.getMaxValue());
                    diplayFormula(pA, cs, points, sscaler0);
                }
                if (cw.cRepeat.symmetryX && cw.cRepeat.symmetryY && t>0) {
                    PVector cs = new PVector(cw.cCircle.centre.x - t*dx.getMaxValue(), cw.cCircle.centre.y - t*dy.getMaxValue());
                    diplayFormula(pA, cs, points, sscaler0);
                }
            }
        }
    }

    public static void diplayFormula(PApplet pA, PVector c, PVector[] points, float sscaler) {
        for (int i = 0; i < points.length; i++) {
            float x = c.x + points[i].x*sscaler;
            float y = c.y + points[i].y*sscaler;
            pA.fill(0);
            pA.noStroke();
            pA.pushMatrix();
                //pA.translate(-Defaults.screenWidth/2, -Defaults.screenHeight/2, 0);
                pA.translate(x, y, 0);
                pA.ellipse(0, 0, 5, 5);
            pA.popMatrix();
        }
    }

    public static PVector[] superformula(float m, float n1, float n2, float n3, int numPoints) {
        float phi = TWO_PI / numPoints;
        PVector[] points = new PVector[numPoints+1];
        for (int i = 0; i <= numPoints; i++) {
            points[i] = superformulaPoint(m, n1, n2, n3, phi * i);
        }
        return points;
    }

    public static PVector superformulaPoint(float m, float n1, float n2, float n3, float phi) {
        float r, t1, t2;
        float a=1, b=1;
        float x = 0, y = 0;

        t1 = cos(m * phi / 4) / a;
        t1 = abs(t1);
        t1 = pow(t1, n2);

        t2 = sin(m * phi / 4) / b;
        t2 = abs(t2);
        t2 = pow(t2, n3);

        r = pow(t1+t2, 1/n1);
        if (abs(r) == 0) {
            x = 0;
            y = 0;
        } else {
            r = 1 / r;
            x = r * cos(phi);
            y = r * sin(phi);
        }

        return new PVector(x, y);
    }

    // Preview WHITNEY *******************************************************//

    public static void previewWhitneyOfPoints(PApplet pA, ControlWindow cw){
        int nt= cw.cRepeat.numTimes;
        RangFloat dx = cw.cRepeat.displaceX;
        RangFloat dy = cw.cRepeat.displaceY;
        RangFloat dr = cw.cRepeat.displaceR;
        float amp = (int)cw.cWhitney.rWAmplitude.getValue();
        float pha = (int)cw.cWhitney.rWPhase.getValue();

        for (int t=0; t<nt; t++) {

            PVector c = new PVector(cw.cCircle.centre.x + t*dx.getMaxValue(), cw.cCircle.centre.y + t*dy.getMaxValue());
            float mag = cw.cWhitney.rWMagnify.getValue() + t*dr.getMaxValue();

            displayWhitney(pA, c, cw.cCommons.numPoints, amp, pha, mag);

            if (cw.cRepeat.symmetryX && t>0) {
                PVector cs = new PVector(cw.cCircle.centre.x - t*dx.getMaxValue(), cw.cCircle.centre.y + t*dy.getMaxValue());
                displayWhitney(pA, cs, cw.cCommons.numPoints, amp, pha, mag);
            } else if (cw.cRepeat.symmetryY && t>0) {
                PVector cs = new PVector(cw.cCircle.centre.x + t*dx.getMaxValue(), cw.cCircle.centre.y - t*dy.getMaxValue());
                displayWhitney(pA, cs, cw.cCommons.numPoints, amp, pha, mag);
            }
        }
    }

    public static void displayWhitney(PApplet pA, PVector c, int numP, float amp, float pha, float mag) {

        float spa = TWO_PI / numP;

        for (int i=0; i<numP; i++) {
            float x = c.x + sin(spa*amp*i)*(cos(spa*pha*i)*mag);
            float x2 = c.x + sin(spa*amp*(i+1))*(cos(spa*pha*(i+1))*mag);
            float y = c.y + sin(spa*pha*i)*(sin(spa*amp*i)*mag);
            float y2 = c.y + sin(spa*pha*(i+1))*(sin(spa*amp*(i+1))*mag);
            pA.fill(0);
            pA.noStroke();
            pA.pushMatrix();
                //pA.translate(-Defaults.screenWidth/2, -Defaults.screenHeight/2, 0);
                //translate(x, y, 0);
                pA.fill(0, 255, 0);
                pA.ellipse(x, y, 5, 5);
                pA.stroke(0);
                pA.strokeWeight(1);
                pA.line(x, y, x2, y2);
            pA.popMatrix();
        }
    }


    // Preview TEXT *****************************************************//

    public static void previewTextOfPoints(PApplet pA, ControlWindow cw){
        //println("PREVIEW TEXT OF POINTS.");
        int nt= cw.cRepeat.numTimes;
        RangFloat dx = cw.cRepeat.displaceX;
        RangFloat dy = cw.cRepeat.displaceY;
        RangFloat da = cw.cRepeat.displaceA;
        RangFloat dr = cw.cRepeat.displaceR;

        for (int t=0; t<nt; t++) {

            PVector c = new PVector(cw.cCircle.centre.x + t*dx.getMaxValue(), cw.cCircle.centre.y + t*dy.getMaxValue());
            float a = cw.cText.rRotAngle.getLowValue() + t*da.getMaxValue();
            float r = cw.cText.fontSize + t*dr.getMaxValue();

            RShape grp= RG.getText(cw.cText.word, cw.cText.fontFamily, (int)r, cw.cText.fontAlignX);

            if (cw.cText.letterCenter) {
                for (int i=0; i<grp.children.length; i++) {
                    grp.children[i].rotate(a, grp.children[i].getCenter());
                }
            } else {
                grp.rotate(a, grp.getCenter());
            }

            RG.setPolygonizer(cw.cText.polyMode);
            RG.setPolygonizerLength(cw.cText.polyLength); //UNIFORMLENGTH - [0, Length]
            RG.setPolygonizerAngle(cw.cText.polyAngle);  //ADAPTATIVE - [0 - PI/2]
            RG.setPolygonizerStep(cw.cText.polyStep);

            displayText(pA, c, grp, r);

            if (cw.cRepeat.symmetryX && t>0) {
                PVector cs = new PVector(cw.cCircle.centre.x - t*dx.getMaxValue(), cw.cCircle.centre.y + t*dy.getMaxValue());
                displayText(pA, cs, grp, r);
            } else if (cw.cRepeat.symmetryY && t>0) {
                PVector cs = new PVector(cw.cCircle.centre.x + t*dx.getMaxValue(), cw.cCircle.centre.y - t*dy.getMaxValue());
                displayText(pA, cs, grp, r);
            }
        }

    }

    public static void displayText(PApplet pA, PVector c, RShape grp, float fontSize) {
        RPoint[] pointsRG = grp.getPoints();
        int nPoints = pointsRG.length;

        for (int i = 0; i < nPoints; i++) {
            float x = pointsRG[i].x + c.x;
            float y = pointsRG[i].y + c.y + fontSize/3;
            pA.pushMatrix();
                //pA.translate(-Defaults.screenWidth/2, -Defaults.screenHeight/2, 0);
                pA.translate(x, y, 0);
                pA.ellipse(0, 0, 5, 5);
            pA.popMatrix();
        }
    }

    // Preview POISSON *************************************************//

    public static void previewPoissonOfPoints(PApplet pA, ControlWindow cw){
        // println("AREA OF POINTS.");
        int nt= cw.cRepeat.numTimes;
        RangFloat dx = cw.cRepeat.displaceX;
        RangFloat dy = cw.cRepeat.displaceY;
        PVector p1s, p2s;

        for (int t=0; t<nt; t++) {

            PVector c1 = new PVector(cw.cLine.corner1.x + t*dx.getMaxValue(), cw.cLine.corner1.y + t*dy.getMaxValue());
            PVector c2 = new PVector(cw.cLine.corner2.x + t*dx.getMaxValue(), cw.cLine.corner2.y + t*dy.getMaxValue());
            PVector c = new PVector(cw.cCircle.centre.x + t*dx.getMaxValue(), cw.cCircle.centre.y + t*dy.getMaxValue());
            /*
            switch(cw.cPoisson.poissonOption) {
                case 0: //println("PREVIEW POISSON RECT OF POINTS.");
                    if ((cw.cPoisson.createPoisson)&&(cw.cPoisson.pr!=null)) {
                        cw.cPoisson.pr.animatePoisson(25);
                        cw.cPoisson.pr.drawPoisson();
                    } else {
                        displayArea(pA,c1, c2);

                        if (cw.cRepeat.symmetryX  && t>0) {
                            p1s = new PVector(cw.cLine.corner1.x - t*dx.getMaxValue(), cw.cLine.corner1.y + t*dy.getMaxValue());
                            p2s = new PVector(cw.cLine.corner2.x - t*dx.getMaxValue(), cw.cLine.corner2.y + t*dy.getMaxValue());
                            displayArea(pA,p1s, p2s, pA.color(255, 0, 0));
                        }
                        if (cw.cRepeat.symmetryY && t>0) {
                            p1s = new PVector(cw.cLine.corner1.x + t*dx.getMaxValue(), cw.cLine.corner1.y - t*dy.getMaxValue());
                            p2s = new PVector(cw.cLine.corner2.x + t*dx.getMaxValue(), cw.cLine.corner2.y - t*dy.getMaxValue());
                            displayArea(pA, p1s, p2s, pA.color(0, 255, 0));
                        }
                        if (cw.cRepeat.symmetryX && cw.cRepeat.symmetryY && t>0) {
                            p1s = new PVector(cw.cLine.corner1.x - t*dx.getMaxValue(), cw.cLine.corner1.y - t*dy.getMaxValue());
                            p2s = new PVector(cw.cLine.corner2.x - t*dx.getMaxValue(), cw.cLine.corner2.y - t*dy.getMaxValue());
                            displayArea(pA, p1s, p2s, pA.color(0, 0, 255));
                        }
                    }
                    break;

                case 1: //println("PREVIEW POISSON CIRCLE OF POINTS.");
                    if ((cw.cPoisson.createPoisson)&&(cw.cPoisson.pc!=null)) {
                        cw.cPoisson.pc.animatePoisson(25);
                        cw.cPoisson.pc.drawPoisson();
                    } else {
                        displayCircle(c, cw.cCircle.rMinRadius.getHighValue(), cw.cCircle.rMaxRadius.getHighValue());
                        if (cw.cRepeat.symmetryX && t>0) {
                            PVector cs = new PVector(cw.cCircle.centre.x - t*dx.getMaxValue(), cw.cCircle.centre.y + t*dy.getMaxValue());
                            displayCircle(pA,cs, cw.cCircle.rMinRadius.getHighValue(), cw.cCircle.rMaxRadius.getHighValue(), pA.color(255, 0, 0));
                        }
                        if (cw.cRepeat.symmetryY && t>0) {
                            PVector cs = new PVector(cw.cCircle.centre.x + t*dx.getMaxValue(), cw.cCircle.centre.y - t*dy.getMaxValue());
                            displayCircle(pA,cs, cw.cCircle.rMinRadius.getHighValue(), cw.cCircle.rMaxRadius.getHighValue(), pA.color(0, 255, 0));
                        }
                        if (cw.cRepeat.symmetryX && cw.cRepeat.symmetryY && t>0) {
                            PVector cs = new PVector(cw.cCircle.centre.x - t*dx.getMaxValue(), cw.cCircle.centre.y - t*dy.getMaxValue());
                            displayCircle(pA,cs, cw.cCircle.rMinRadius.getHighValue(), cw.cCircle.rMaxRadius.getHighValue(), pA.color(0, 0, 255));
                        }
                    }
                    break;

                case 2: //println("PREVIEW POISSON POLYGON OF POINTS.");
                    if ((cw.cPoisson.createPoisson)&&(cw.cPoisson.closePolygon)&&(cw.cPoisson.pp!=null)) {
                        cw.cPoisson.pp.animatePoisson(25);
                        cw.cPoisson.pp.drawPoisson();
                    } else {
                        displayPolygon(pA, cw, t*dx.getMaxValue(), t*dy.getMaxValue());
                        if (cw.cRepeat.symmetryX && t>0) {
                            displayPolygon(pA, cw, -t*dx.getMaxValue(), t*dy.getMaxValue());
                        }
                        if (cw.cRepeat.symmetryY && t>0) {
                            displayPolygon(pA, cw, t*dx.getMaxValue(), -t*dy.getMaxValue());
                        }
                        if (cw.cRepeat.symmetryX && cw.cRepeat.symmetryY && t>0) {
                            displayPolygon(pA, cw, -t*dx.getMaxValue(), -t*dy.getMaxValue());
                        }
                    }
                    break;

            }*/
        }
    }

    public  static void displayPolygon(PApplet pA, ControlWindow cw, float ddx, float ddy) {
        /*
        for (int i=0; i<cw.XXXXXXXXXXXX.corners.size(); i++) {
            pA.fill(0);
            pA.noStroke();
            PVector v0 = corners.get(i);
            PVector v1 = (i>0)?corners.get(i-1):corners.get(i);
            pA.pushMatrix();
                //pA.translate(-screenWidth/2, -screenHeight/2, 0);
                pA.ellipse(v0.x + ddx, v0.y + ddy, 5, 5);
                pA.stroke(0);
                pA.strokeWeight(1);
                pA.line(v0.x + ddx, v0.y + ddy, v1.x + ddx, v1.y + ddy);
                if ((corners.size()>2)&&(i==corners.size()-1)) {
                    if (closePolygon) stroke(0);
                    else pA.stroke(255, 0, 0);
                    pA.line(v0.x + ddx, v0.y + ddy, corners.get(0).x + ddx, corners.get(0).y + ddy);
                }
            pA.popMatrix();
        }
        */
    }

    // Preview PARTICLES ********************************************//



}
