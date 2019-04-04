import java.util.ArrayList;

import processing.core.*;
import static processing.core.PApplet.*;
import static processing.core.PApplet.dist;
import static processing.core.PApplet.map;
import static processing.core.PConstants.PI;
import static processing.core.PConstants.ROUND;
import static processing.core.PConstants.TWO_PI;

class AttractorPoint {

    PVector position;
    float gravity;
    float spinAngle;

    boolean enabled;
    boolean collapsable;
    boolean removeable;
    float numParticles2Collapse;
    float numParticles;

    PVector c;
    float radius;
    float angle;

    //Oscillator oscPosX, oscPosY, oscGravity, oscSpinAngle, oscRadius, oscAngle;

    // Constructors

    public AttractorPoint(PVector pos){
        this.position = new PVector(pos.x, pos.y);
    }

    public AttractorPoint(PVector pos, float g, float spin, boolean on){

        this.position = new PVector(pos.x, pos.y);
        this.gravity = g;
        this.spinAngle = spin;
        this.enabled = on;

        this.collapsable = false;
        this.numParticles2Collapse=0;
        this.numParticles=0;

        this.removeable = false;

        this.c = new PVector(Defaults.sceneWidth/2, Defaults.sceneHeight/2);
        this.radius = dist(pos.x, pos.y, c.x, c.y);

        float dx = (Defaults.sceneWidth/2 - pos.x); float dy = (Defaults.sceneHeight/2 - pos.y);
        float atemp = atan(dy/dx) + PI; if(dx<0 ){ atemp+=PI;}
        this.angle = atemp;

        //oscPosX = new Oscillator(); oscPosY = new Oscillator();
        //oscGravity = new Oscillator(); oscSpinAngle = new Oscillator();
        //oscRadius = new Oscillator(); oscAngle = new Oscillator();

    }


    public AttractorPoint(PVector pos, float g, float spin, boolean on, boolean co, float np2c){

        this.position = new PVector(pos.x, pos.y);
        this.gravity = g;
        this.spinAngle = spin;
        this.enabled = on;

        this.collapsable = co;
        this.numParticles2Collapse=np2c;
        this.numParticles=0;

        this.removeable = false;

        this.c = new PVector(Defaults.sceneWidth/2, Defaults.sceneHeight/2);
        this.radius = dist(pos.x, pos.y, c.x, c.y);

        float dx = (Defaults.sceneWidth/2 - pos.x); float dy = (Defaults.sceneHeight/2 - pos.y);
        float atemp = atan(dy/dx) + PI; if(dx<0 ){ atemp+=PI;}
        this.angle = atemp;

        //oscPosX = new Oscillator(); oscPosY = new Oscillator();
        //oscGravity = new Oscillator(); oscSpinAngle = new Oscillator();
        //oscRadius = new Oscillator(); oscAngle = new Oscillator();

    }

    public AttractorPoint(PVector pos, float g, float spin, boolean on, boolean co, float np2c, PVector c, float radius, float angle){

        this.position = new PVector(pos.x, pos.y);
        this.gravity = g;
        this.spinAngle = spin;
        this.enabled = on;

        this.collapsable = co;
        this.numParticles2Collapse=np2c;
        this.numParticles=0;

        this.removeable = false;

        this.c = new PVector(c.x, c.y); this.radius = radius; this.angle = angle;

        //oscPosX = new Oscillator(); oscPosY = new Oscillator();
        //oscGravity = new Oscillator(); oscSpinAngle = new Oscillator();
        //oscRadius = new Oscillator(); oscAngle = new Oscillator();

    }

    AttractorPoint copy(){
        AttractorPoint nap = new AttractorPoint(position, gravity, spinAngle, enabled, collapsable, numParticles2Collapse);
        return nap;
    }


    //Setters

    public void setPosition(PVector pos){
        this.position = new PVector(pos.x, pos.y);
    }

    public void displacePosition(float dx, float dy){
        this.position = new PVector(position.x + dx, position.y + dy);
    }

    public void setGravity(float g){
        this.gravity = g;
    }

    public void setSpinAngle(float spin){
        this.spinAngle = spin;
    }

    public void setEnabled(boolean b){
        this.enabled = b;
    }

    public void setCollapsable(boolean b){
        this.collapsable = b;
    }

    public void setRemoveable(boolean b){
        this.removeable = b;
    }

    public void setNumParticles2Collapse(int n){
        this.numParticles2Collapse = n;
    }

    public void addParticle(){
        if(isCollapsable()){
            this.numParticles++;
            this.gravity -= 1;
        }
    }

    public void setOscillate(boolean[] b){
       /* oscPosX.setOscEnabled(b[0]);
        oscPosY.setOscEnabled(b[1]);
        oscGravity.setOscEnabled(b[2]);
        oscSpinAngle.setOscEnabled(b[3]);
        oscRadius.setOscEnabled(b[4]);
        oscAngle.setOscEnabled(b[5]); */
    }

    // Getters

    public PVector getPosition(){
        return this.position;
    }

    public float getX(){
        return this.position.x;
    }

    public float getY(){
        return this.position.y;
    }

    public boolean isCollapsed(){
        return this.numParticles>=this.numParticles2Collapse;
    }

    public float getGravity(){
        return this.gravity;
    }

    public float getRadius(){
        return this.radius;
    }

    public float getAngle(){
        return this.radius;
    }

    public float getSpinAngle(){
        return this.spinAngle;
    }

    public boolean isEnabled(){
        return this.enabled;
    }

    public boolean isCollapsable(){
        return this.collapsable;
    }

    public boolean isRemoveable(){
        return this.removeable;
    }

    public boolean isAttractor(){
        return this.gravity>=0;
    }

    public boolean isRepeler(){
        return this.gravity<0;
    }


    public void drawAttractorPoint(PApplet pA){

        float radi = PApplet.abs(this.gravity/2);
        float minR = 0.5f, maxR = 5;

        pA.strokeCap(ROUND);
        if(isCollapsed()) {
            pA.stroke(255, 0, 0, 50);
        }
        else {
            pA.stroke(50, 50);
        }
        pA.strokeWeight(map(radi, 0, 200, minR, maxR));
        pA.noFill();

        pA.pushMatrix();
            //pA.translate(-Defaults.screenWidth/2, -Defaults.screenHeight/2);
            pA.translate(this.position.x, this.position.y);

            if(this.isAttractor()){
                pA.line(0-radi/4,0,0+radi/4,0);
                pA.line(0,0-radi/4,0,0+radi/4);
            }
            else {
                pA.line(0-radi/4,0,0+radi/4,0);
            }

            pA.ellipse(0,0, radi, radi);

            if(spinAngle>=0){
                pA.arc(0, 0, radi + map(radi, 0, 500, 10, 25), radi +  map(radi, 0, 500, 10, 25), 0, spinAngle % TWO_PI);
            }
            else {
                pA.arc(0, 0, radi +  map(radi, 0, 500, 10, 25), radi +  map(radi, 0, 500, 10, 25), abs(spinAngle) % TWO_PI, TWO_PI);
            }

        pA.popMatrix();

        //BIG

        float radiBIG = abs(gravity/2)*Defaults.BIG_SCALE;

        Defaults.big.strokeCap(ROUND);
        Defaults.big.stroke(50,50);
        Defaults.big.noFill(); //noStroke();
        Defaults.big.strokeWeight(map(radi, 0, 200, minR, maxR)*Defaults.BIG_SCALE);

        Defaults.big.pushMatrix();

        float bigX = map(this.position.x, 0, Defaults.screenWidth, 0, Defaults.screenWidth*Defaults.BIG_SCALE);
        float bigY = map(this.position.y, 0, Defaults.screenHeight, 0, Defaults.screenHeight*Defaults.BIG_SCALE);
        Defaults.big.translate(bigX, bigY);

        if(this.isAttractor()){
            Defaults.big.line(0-radiBIG/4,0,0+radiBIG/4,0);
            Defaults.big.line(0,0-radiBIG/4,0,0+radiBIG/4);
        }
        else {
            Defaults.big.line(0-radiBIG/4,0,0+radiBIG/4,0);
        }


        Defaults.big.ellipse(0,0, radiBIG, radiBIG);

        if(spinAngle>=0){
            Defaults.big.arc(0, 0, radiBIG + map(radi, 0, 500, 10, 25)*Defaults.BIG_SCALE, radiBIG + map(radi, 0, 500, 10, 25)*Defaults.BIG_SCALE, 0, spinAngle % TWO_PI);
        }
        else {
            Defaults.big.arc(0, 0, radiBIG + map(radi, 0, 500, 10, 25)*Defaults.BIG_SCALE, radiBIG + map(radi, 0, 500, 10, 25)*Defaults.BIG_SCALE, abs(spinAngle) % TWO_PI, TWO_PI);
        }

        Defaults.big.popMatrix();

        //END BIG
    }

    public void update(){
        /*
        if(oscPosX.isEnabled()){
            position.x = oscPosX.oscillate();
        }
        if(oscPosY.isEnabled()){
            position.y = oscPosY.oscillate();
        }
        if(oscGravity.isEnabled()){
            gravity = oscGravity.oscillate();
        }
        if(oscSpinAngle.isEnabled()){
            spinAngle = oscSpinAngle.oscillate();
        }
        if(oscRadius.isEnabled()){
            radius = oscRadius.oscillate();
            position.x = c.x + radius*cos(angle);
            position.y = c.y + radius*sin(angle);
        }
        if(oscAngle.isEnabled()){
            angle = oscAngle.oscillate();
            position.x = c.x + radius*cos(angle);
            position.y = c.y + radius*sin(angle);
        }
        */
    }

    public float distance(AttractorPoint ap){
        return dist(this.position.x, this.position.y, ap.position.x, ap.position.y);
    }


    public boolean awayFrom(float minDist, ArrayList<AttractorPoint> ps){
        boolean b=true;
        for(AttractorPoint ap : ps){
            if(this.distance(ap)<minDist){
                b=false; break;
            }
        }
        return b;
    }

    public boolean samePosition(AttractorPoint ap){
        return (this.position.x==ap.position.x) &&(this.position.y==ap.position.y);
    }


    public String toString(){
        String t = isAttractor()?"ATTRACTOR POINT":"REPELER POINT";
        String p = " at ("+position.x+", "+position.y+")";
        String g = " with Mass: "+gravity;
        String a = ", Spin Angle: "+spinAngle;
        String e = (enabled)?", Enable:ON":", Enable:OFF";
        String c = (collapsable)?", Collapse:ON":", Collpase:OFF";
        String np = ", Num.Part2Collapse: "+numParticles2Collapse;
        return t+p+g+a+e+c+np;
    }

    // Oscillators:
    /*

    public void setOscPosX(float minV, float maxV, float v, float step, int d, boolean b, float f, float t, float dt){
        oscPosX = new Oscillator(minV, maxV, v, step, d, b, f, t, dt);
    }

    public void setOscPosY(float minV, float maxV, float v, float step, int d, boolean b, float f, float t, float dt){
        oscPosY = new Oscillator(minV, maxV, v, step, d, b, f, t, dt);
    }

    public void setOscGravity(float minV, float maxV, float v, float step, int d, boolean b, float f, float t, float dt){
        oscGravity = new Oscillator(minV, maxV, v, step, d, b, f, t, dt);
    }

    public void setOscSpinAngle(float minV, float maxV, float v, float step, int d, boolean b, float f, float t, float dt){
        oscSpinAngle = new Oscillator(minV, maxV, v, step, d, b, f, t, dt);
    }

    public void setOscRadius(float minV, float maxV, float v, float step, int d, boolean b, float f, float t, float dt){
        oscRadius = new Oscillator(minV, maxV, v, step, d, b, f, t, dt);
    }

    public void setOscAngle(float minV, float maxV, float v, float step, int d, boolean b, float f, float t, float dt){
        oscAngle = new Oscillator(minV, maxV, v, step, d, b, f, t, dt);
    }

    */

}
