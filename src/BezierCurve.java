import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

import static processing.core.PApplet.*;
import static processing.core.PConstants.PI;

public class BezierCurve {

    PVector c1, c2, a1, a2;
    ArrayList<PVector> points;

    public BezierCurve(PVector c1, PVector c2, PVector a1, PVector a2){
        this.c1 = c1; this.c2 = c2;  //control points
        this.a1 = a1; this.a2 = a2;  // anchor points
        points = new ArrayList<PVector>();
        points.add(c1); points.add(c2); points.add(a1); points.add(a2);
        println("BEZIER CURVE: "+c1+", "+c2+", "+a1+", "+a2);
    }

    BezierCurve copy(){
        PVector cc1 = c1.copy(), cc2 = c2.copy();
        PVector ca1 = a1.copy(), ca2 = a2.copy();
        BezierCurve bc = new BezierCurve(cc1, cc2, ca1, ca2);
        return bc;
    }

    void updatePoints(){
        points = new ArrayList<PVector>();
        points.add(c1); points.add(c2); points.add(a1); points.add(a2);
    }

    void setRect(){
        this.c1 = a1.copy(); this.c2 = a2.copy();
        points.clear();
        points.add(c1); points.add(c2); points.add(a1); points.add(a2);
    }

    void setCurve(){
        float dc = 50;
        this.c1 = new PVector(a1.x - dc, a1.y - dc);
        this.c2 = new PVector(a2.x - dc, a2.y - dc);
        points.clear();
        points.add(c1); points.add(c2); points.add(a1); points.add(a2);
    }

    void setQuadratic(){
        float dc = 50;
        this.c1 = new PVector((a1.x + a2.x)/2 - dc, (a1.y + a2.y)/2 - dc);
        this.c2 = c1.copy();
        points.clear();
        points.add(c1); points.add(c2); points.add(a1); points.add(a2);
    }


    boolean isControlPoint(PVector p){
        return (p.equals(c1) || p.equals(c2));
    }

    boolean isAnchorPoint(PVector p){
        return (p.equals(a1) || p.equals(a2));
    }

    void displayPoints(PApplet pA, int np, float ddx, float ddy){
        if(np>1){
            float step = 1.0f/(np-1);
            for(float t=0.0f; t<=1.0f; t+=step){
                float t2=1.0f-t;
                float xcalc = t2*t2*t2*a1.x  + 3*t2*t2*t*c1.x + 3*t2*t*t*c2.x  + t*t*t*a2.x;
                float ycalc = t2*t2*t2*a1.y  + 3*t2*t2*t*c1.y + 3*t2*t*t*c2.y  + t*t*t*a2.y;
                pA.ellipse(xcalc + ddx,ycalc+ddy,10,10);
            }
        }
    }

    ArrayList<PVector> getPoints(int np){
        ArrayList<PVector> ps = new ArrayList<PVector>();
        ps.add(a1.copy()); ps.add(a2.copy());
        float step = 1.0f/(np-1);
        for(float t=0.0f; t<=1.0; t+=step){
            float t2=1.0f-t;
            float xcalc = t2*t2*t2*a1.x  + 3*t2*t2*t*c1.x + 3*t2*t*t*c2.x  + t*t*t*a2.x;
            float ycalc = t2*t2*t2*a1.y  + 3*t2*t2*t*c1.y + 3*t2*t*t*c2.y  + t*t*t*a2.y;
            ps.add(new PVector(xcalc,ycalc));
        }
        return ps;
    }


    public float getAngle(PVector p, PVector c){
        float dx = (c.x - p.x); float dy = (c.y - p.y);
        float a = atan(dy/dx) + 0;
        if(dx<=0 && dy<=0 ){ a = map(a, 0, PI/2, 0, PI/2);}
        else if(dx<=0 && dy>=0 ){ a = map(a, -PI/2, 0, 3*PI/2, TWO_PI);}
        else if(dx>=0 && dy>=0 ){ a = map(a, -PI/2, 0, PI/2, PI);}
        else if(dx>=0 && dy<=0 ){ a = map(a, 0, PI/2, PI, 3*PI/2);}
        return a;
    }

    PVector dilatePoint(PVector p, PVector c, float d){
        float a = getAngle(p, c);
        float dd = d + c.dist(p);
        return new PVector(c.x + dd*cos(a), c.y + dd*sin(a));
    }

    void dilate(PVector c, float d){
        a1 = dilatePoint(a1, c, d); a2 = dilatePoint(a2, c, d);
        c1 = dilatePoint(c1, c, d); c2 = dilatePoint(c2, c, d);
        updatePoints();
    }

    BezierCurve getDilatedBezierCurve(PVector c, float d){
        PVector da1 = dilatePoint(a1, c, d),  da2 = dilatePoint(a2, c, d);
        PVector dc1 = dilatePoint(c1, c, d),  dc2 = dilatePoint(c2, c, d);
        return new BezierCurve(dc1, dc2, da1, da2);
    }

    void display(PApplet pA, int n, float ddx, float ddy) {
        pA.noFill(); pA.stroke(0,100);
        pA.ellipse(c1.x,c1.y,10,10);
        pA.ellipse(c2.x,c2.y,10,10);
        pA.stroke(255,0,0);
        pA.beginShape();
            pA.vertex(a1.x, a1.y);
            pA.bezierVertex(c1.x, c1.y, c2.x, c2.y, a2.x, a2.y);
        pA.endShape();
        pA.stroke(0,100);
        pA.line(a1.x, a1.y, c1.x, c1.y);
        pA.line(a2.x, a2.y, c2.x, c2.y);

        displayPoints(pA, n, ddx, ddy);
    }
}
