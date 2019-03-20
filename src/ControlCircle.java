import controlP5.*;
import processing.core.PVector;

import static processing.core.PApplet.println;
import static processing.core.PConstants.PI;
import static processing.core.PConstants.TWO_PI;

public class ControlCircle {

    Slider2D s2dCentre;
    PVector centre;
    Range rMinRadius, rMaxRadius, rRadiusStep, rAngle, rAngleStep, rRadiusVariability, rRandomAngle;
    RangFloat minRadius, maxRadius, radiusStep, angle, angleStep, radiusVariability, randomAngle;
    Button bFull, bHalfB, bHalfT, bHalfL, bHalfR, bOrigin, bSingle, bFibonacci;
    Slider sMinDist;
    float minDistance=0;
    boolean single, fibonacci;

    boolean lockX, lockY; //VIUS REPETITS

    public ControlCircle(ControlWindow cw){
        setupCircleControls(cw);
    }

    public void setupCircleControls(ControlWindow cw){

        // CENTRE
        s2dCentre = cw.cp5.addSlider2D("CENTRE")
                .setPosition(cw.marginLeft,cw.marginTop + cw.rowStep*10)
                .setSize((int)(cw.rangeWidth),(int)(3*cw.rangeWidth/4))
                .setArrayValue(new float[] {Defaults.sceneWidth/2, Defaults.sceneHeight/2})
                .setCursorX(50).setCursorY(50).setMinX(0).setMaxX(Defaults.sceneWidth).setMinY(0).setMaxY(Defaults.sceneHeight)
                .moveTo("circle")
        ;

        centre = new PVector(Defaults.sceneWidth/2,Defaults.sceneHeight/2);

        // ORIGIN CHECKBOX
        bOrigin = cw.cp5.addButton("ORIGIN").setValue(0)
                .setPosition(cw.marginLeft+ 2*cw.rangeWidth/4, cw.marginTop+ cw.rowStep*9)
                .setSize(cw.rangeWidth/4 -5,cw.controlHeight)
                .moveTo("circle")
        ;

        // SINGLE BUTTON
        bSingle = cw.cp5.addButton("SINGLE").setValue(0)
                .setPosition(cw.marginLeft+ 3*cw.rangeWidth/4, cw.marginTop+ cw.rowStep*9)
                .setSize(cw.rangeWidth/4 -5,cw.controlHeight)
                .setSwitch(true).setOff().setLabel("NO SINGLE")
                .moveTo("circle")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        single = bSingle.isOn();
                    }
                })
        ;
        single = false;

        // MIN RADIUS RANGE
        rMinRadius= cw.cp5.addRange("MIN RADIUS").setBroadcast(false)
                .setPosition(cw.marginLeft,cw.marginTop + cw.rowStep*18)
                .setSize(cw.rangeWidth,cw.controlHeight).setHandleSize(20)
                .setRange(Defaults.MIN_R,Defaults.MAX_R)
                .setRangeValues(Defaults.MIN_R,Defaults.MAX_R).setBroadcast(true)
                .moveTo("circle")
        ;
        minRadius = new RangFloat(Defaults.MIN_R, Defaults.MAX_R);

        // MAX RADIUS RANGE
        rMaxRadius= cw.cp5.addRange("MAX RADIUS").setBroadcast(false)
                .setPosition(cw.marginLeft,cw.marginTop + cw.rowStep*19)
                .setSize(cw.rangeWidth,cw.controlHeight).setHandleSize(20)
                .setRange(Defaults.MIN_R,Defaults.MAX_R)
                .setRangeValues(Defaults.MIN_R,Defaults.MAX_R).setBroadcast(true)
                .moveTo("circle")
        ;
        maxRadius = new RangFloat(Defaults.MIN_R, Defaults.MAX_R);

        // VAR RADIUS RANGE
        rRadiusVariability= cw.cp5.addRange("VAR RADIUS").setBroadcast(false)
                .setPosition(cw.marginLeft,cw.marginTop + cw.rowStep*20)
                .setSize(cw.rangeWidth,cw.controlHeight)
                .setHandleSize(20)
                .setRange(-Defaults.VAR_R,Defaults.VAR_R)
                .setRangeValues(0,0).setBroadcast(true)
                .moveTo("circle")
        ;
        radiusVariability = Defaults.rZERO.copy();

        // RADIUS STEP RANGE
        rRadiusStep= cw.cp5.addRange("RADIUS STEP").setBroadcast(false)
                .setPosition(cw.marginLeft,cw.marginTop + cw.rowStep*21)
                .setSize(cw.rangeWidth,cw.controlHeight).setHandleSize(20)
                .setRange(Defaults.MIN_RSTEP,Defaults.MAX_RSTEP)
                .setRangeValues(Defaults.MIN_RSTEP,Defaults.MAX_RSTEP).setBroadcast(true)
                .moveTo("circle")
        ;
        radiusStep= new RangFloat(Defaults.MIN_RSTEP, Defaults.MAX_RSTEP);

        // FULL ANGLE BUTTON
        bFull = cw.cp5.addButton("FULL").setValue(0)
                .setPosition(cw.marginLeft, cw.marginTop+ cw.rowStep*22)
                .setSize(cw.rangeWidth/5 - 4,cw.controlHeight)
                .moveTo("circle")
        ;

        // HALF BOTTOM ANGLE BUTTON
        bHalfB = cw.cp5.addButton("HALF B").setValue(0)
                .setPosition(cw.marginLeft + cw.rangeWidth/5, cw.marginTop+ cw.rowStep*22)
                .setSize(cw.rangeWidth/5 -4,cw.controlHeight)
                .moveTo("circle")
        ;

        // HALF TOP ANGLE BUTTON
        bHalfT = cw.cp5.addButton("HALF T").setValue(0)
                .setPosition(cw.marginLeft + 2*cw.rangeWidth/5, cw.marginTop+ cw.rowStep*22)
                .setSize(cw.rangeWidth/5 -4,cw.controlHeight)
                .moveTo("circle")
        ;

        // HALF LEFT ANGLE BUTTON
        bHalfB = cw.cp5.addButton("HALF L").setValue(0)
                .setPosition(cw.marginLeft + 3*cw.rangeWidth/5, cw.marginTop+ cw.rowStep*22)
                .setSize(cw.rangeWidth/5 -4,cw.controlHeight)
                .moveTo("circle")
        ;

        // HALF RIGHT ANGLE BUTTON
        bHalfT = cw.cp5.addButton("HALF R").setValue(0)
                .setPosition(cw.marginLeft + 4*cw.rangeWidth/5, cw.marginTop+ cw.rowStep*22)
                .setSize(cw.rangeWidth/5 -4,cw.controlHeight)
                .moveTo("circle")
        ;

        // VAR ANGLE RANGE
        rAngle= cw.cp5.addRange("ANGLE").setBroadcast(false)
                .setPosition(cw.marginLeft,cw.marginTop + cw.rowStep*23)
                .setSize(cw.rangeWidth,cw.controlHeight).setHandleSize(20)
                .setRange(Defaults.MIN_A,Defaults.MAX_A)
                .setRangeValues(Defaults.MIN_A,Defaults.MAX_A).setBroadcast(true)
                .moveTo("circle")
        ;
        angle = new RangFloat(Defaults.MIN_A, Defaults.MAX_A);

        // RADIUS STEP RANGE
        rAngleStep= cw.cp5.addRange("ANGLE STEP").setBroadcast(false)
                .setPosition(cw.marginLeft,cw.marginTop + cw.rowStep*24)
                .setSize(cw.rangeWidth,cw.controlHeight).setHandleSize(20)
                .setRange(Defaults.MIN_ASTEP,Defaults.MAX_ASTEP)
                .setRangeValues(Defaults.MIN_ASTEP,Defaults.MAX_ASTEP).setBroadcast(true)
                .moveTo("circle")
        ;
        angleStep = new RangFloat(Defaults.MIN_ASTEP, Defaults.MAX_ASTEP);

        // RANDOM ANGLE RANGE
        rRandomAngle= cw.cp5.addRange("RANDOM ANGLE").setBroadcast(false)
                .setPosition(cw.marginLeft,cw.marginTop + cw.rowStep*25)
                .setSize(cw.rangeWidth,cw.controlHeight).setHandleSize(20)
                .setRange(Defaults.MIN_A_RAND,Defaults.MAX_A_RAND).setRangeValues(0,0).setBroadcast(true)
                .moveTo("circle")
        ;
        randomAngle = Defaults.rZERO.copy();

        // MIN DISTANCE SLIDER
        sMinDist = cw.cp5.addSlider("MIN DIST").setPosition(cw.marginLeft,cw.marginTop + cw.rowStep*26).setSize(cw.rangeWidth,cw.controlHeight)
                .setRange(Defaults.MIN_DIST,Defaults.MAX_DIST).setValue(Defaults.MIN_DIST).setValueLabel(""+minDistance)
                .moveTo("circle")
        ;

        minDistance = Defaults.MIN_DIST;

        // FIBONNACI BUTTON
        bFibonacci = cw.cp5.addButton("FIBONACCI").setValue(0).setPosition(cw.marginLeft + cw.rangeWidth/2, cw.marginTop+ cw.rowStep*26).setSize(cw.rangeWidth/2,cw.controlHeight)
                .setSwitch(true).setOff().setLabel("FIBONACCI OFF")
                .moveTo("circle")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        fibonacci = bFibonacci.isOn();
                    }
                })
        ;

        fibonacci = false;
    }

    public void resetControls(){
        bSingle.setOff(); single = false;
        s2dCentre.setArrayValue(new float[] {Defaults.sceneWidth/2, Defaults.sceneHeight/2});
        centre = new PVector(Defaults.sceneWidth/2,Defaults.sceneHeight/2);
        rMinRadius.setRangeValues(Defaults.MIN_R,Defaults.MAX_R);
        minRadius = new RangFloat(Defaults.MIN_R, Defaults.MAX_R);
        rMaxRadius.setRangeValues(Defaults.MIN_R,Defaults.MAX_R);
        maxRadius = new RangFloat(Defaults.MIN_R, Defaults.MAX_R);
        rRadiusVariability.setRangeValues(0,0); radiusVariability = Defaults.rZERO.copy();
        rRadiusStep.setRangeValues(Defaults.MIN_RSTEP,Defaults.MAX_RSTEP);
        radiusStep= new RangFloat(Defaults.MIN_RSTEP, Defaults.MAX_RSTEP);
        rAngle.setRangeValues(Defaults.MIN_A,Defaults.MAX_A);
        angle = new RangFloat(Defaults.MIN_A, Defaults.MAX_A);
        rAngleStep.setRangeValues(Defaults.MIN_ASTEP,Defaults.MAX_ASTEP);
        angleStep = new RangFloat(Defaults.MIN_ASTEP, Defaults.MAX_ASTEP);
        rRandomAngle.setRangeValues(0,0); randomAngle = Defaults.rZERO.copy();
        sMinDist.setValue(Defaults.MIN_DIST); minDistance = Defaults.MIN_DIST;
        bFibonacci.setOff(); fibonacci = false;
    }

    public void checkControlEvents(ControlEvent theControlEvent){
        //********************************  CIRCLE DIST PARAMS *******************//
        // CENTRE POSITION
        if(theControlEvent.isFrom("CENTRE")) {
            float ox = s2dCentre.getArrayValue()[0];
            float oy = s2dCentre.getArrayValue()[1];
            if(lockX){
                ox = centre.x;
            }
            if(lockY){
                oy = centre.y;
            }
            centre = new PVector(ox, oy);
            float[] o = {ox,oy};
            s2dCentre.setBroadcast(false); s2dCentre.setArrayValue(o); s2dCentre.setBroadcast(true);
            println("CENTRE POSITION // x: "+centre.x+", y: "+centre.y);
        }
        //ORIGIN BUTTON
        else if(theControlEvent.isFrom("ORIGIN")) {
            float[] o = {Defaults.sceneWidth/2,Defaults.sceneHeight/2};
            s2dCentre.setBroadcast(false); s2dCentre.setArrayValue(o); s2dCentre.setBroadcast(true);
            centre = new PVector(o[0], o[1]);
            println("ORIGIN");
        }
        // SINGLE BUTTON
        else if(theControlEvent.isFrom("SINGLE")) {
            if(single){
                bSingle.setLabel("SINGLE");
                maxRadius.setValues(rMinRadius.getHighValue()+1, rMinRadius.getHighValue()+1);
            }
            else bSingle.setLabel("NO SINGLE");
            println("Single: "+single);
        }
        //MIN RADIUS RANGE
        else if(theControlEvent.isFrom("MIN RADIUS")) {
            minRadius.setValues(rMinRadius.getLowValue(), rMinRadius.getHighValue());
            println("MIN RADIUS RANGE: "+minRadius);
            if(single){
                maxRadius.setValues(rMinRadius.getHighValue()+1, rMinRadius.getHighValue()+1);
            }
        }
        //MAX RADIUS RANGE
        else if(theControlEvent.isFrom("MAX RADIUS")) {
            maxRadius.setValues(rMaxRadius.getLowValue(), rMaxRadius.getHighValue());
            println("MAX RADIUS RANGE: "+maxRadius);
        }
        //VARIABILITY RADIUS RANGE
        else if(theControlEvent.isFrom("VAR RADIUS")) {
            radiusVariability.setValues(rRadiusVariability.getLowValue(), rRadiusVariability.getHighValue());
            println("RADIUS VARIABILITY RANGE: "+radiusVariability);
        }
        //RADIUS STEP RANGE
        else if(theControlEvent.isFrom("RADIUS STEP")) {
            radiusStep.setValues(rRadiusStep.getLowValue(), rRadiusStep.getHighValue());
            println("RADIUS STEP RANGE: "+radiusStep);
        }
        //ANGLE RANGE
        else if(theControlEvent.isFrom("ANGLE")) {
            angle.setValues(rAngle.getLowValue(), rAngle.getHighValue());
            println("ANGLE RANGE: "+angle);
        }
        //FULL ANGLE BUTTON
        else if(theControlEvent.isFrom("FULL")) {
            rAngle.setBroadcast(false).setLowValue(0.0f).setHighValue(TWO_PI).setBroadcast(true);
            angle.setValues(rAngle.getLowValue(), rAngle.getHighValue());
            println("ANGLE RANGE: "+angle);
        }
        //HALF BOTTOM ANGLE BUTTON
        else if(theControlEvent.isFrom("HALF B")) {
            rAngle.setBroadcast(false).setLowValue(0.0f).setHighValue(PI).setBroadcast(true);
            angle.setValues(rAngle.getLowValue(), rAngle.getHighValue());
            println("ANGLE RANGE: "+angle);
        }
        //HALF TOP ANGLE BUTTON
        else if(theControlEvent.isFrom("HALF T")) {
            rAngle.setBroadcast(false).setLowValue(PI).setHighValue(TWO_PI).setBroadcast(true);
            angle.setValues(rAngle.getLowValue(), rAngle.getHighValue());
            println("ANGLE RANGE: "+angle);
        }
        //HALF LEFT ANGLE BUTTON
        else if(theControlEvent.isFrom("HALF L")) {
            rAngle.setBroadcast(false).setLowValue(PI/2).setHighValue(3*PI/2).setBroadcast(true);
            angle.setValues(rAngle.getLowValue(), rAngle.getHighValue());
            println("ANGLE RANGE: "+angle);
        }
        //HALF RIGHT ANGLE BUTTON
        else if(theControlEvent.isFrom("HALF R")) {
            rAngle.setBroadcast(false).setLowValue(3*PI/2).setHighValue(5*PI/2).setBroadcast(true);
            angle.setValues(rAngle.getLowValue(), rAngle.getHighValue());
            println("ANGLE RANGE: "+angle);
        }
        //ANGLE STEP RANGE
        else if(theControlEvent.isFrom("ANGLE STEP")) {
            angleStep.setValues(rAngleStep.getLowValue(), rAngleStep.getHighValue());
            println("ANGLE STEP RANGE: "+angleStep);
        }
        //RANDOM ANGLE
        else if(theControlEvent.isFrom("RANDOM ANGLE")) {
            randomAngle.setValues(rRandomAngle.getLowValue(), rRandomAngle.getHighValue());
            println("RANDOM ANGLE RANGE: "+angleStep);
        }
        // MIN DISTANCE
        else if(theControlEvent.isFrom("MIN DIST")) {
            minDistance = (int)sMinDist.getValue();
            sMinDist.setBroadcast(false); sMinDist.setValueLabel(""+minDistance); sMinDist.setBroadcast(true);
            println("Min Distance : "+minDistance);
        }
        // FIBONACCI MODE
        else if(theControlEvent.isFrom("FIBONACCI")) {
            if(fibonacci) bFibonacci.setLabel("FIBONACCI ON");
            else bFibonacci.setLabel("FIBONACCI OFF");
            println("FIBONACCI: "+fibonacci);
        }
    }
}
