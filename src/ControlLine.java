import controlP5.*;
import processing.core.PVector;

import static processing.core.PApplet.println;

public class ControlLine {

    Slider2D s2dLineCorner1, s2dLineCorner2;
    PVector corner1, corner2;
    Button bLockX, bLockY, bLockX2, bLockY2, bSameX, bSameY, bCentreTL, bCentreBR;
    boolean lockX = false, lockY = false, lockX2 = false, lockY2 = false, sameX=false, sameY=false;
    Range rXVariability, rYVariability;
    RangFloat xVar, yVar;

    public ControlLine(ControlWindow cw){
        setupLineControls(cw);
    }

    public void setupLineControls(ControlWindow cw){
        // LOCK X CHECKBOX
        bLockX = cw.cp5.addButton("LOCK X").setValue(0)
                .setPosition(cw.marginLeft, cw.marginTop+ cw.rowStep*9)
                .setSize(cw.rangeWidth/4 -5,cw.controlHeight)
                .setSwitch(true).setOff().setLabel("UNLOCKED X")
                .moveTo("line")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        lockX = bLockX.isOn();
                    }
                })
        ;

        lockX=false;

        // LOCK Y CHECKBOX
        bLockY = cw.cp5.addButton("LOCK Y").setValue(0)
                .setPosition(cw.marginLeft + cw.rangeWidth/4, cw.marginTop+ cw.rowStep*9)
                .setSize(cw.rangeWidth/4 -5,cw.controlHeight)
                .setSwitch(true).setOff().setLabel("UNLOCKED Y")
                .moveTo("line")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        lockY = bLockY.isOn();
                    }
                })
        ;

        lockY = false;

        // SAME X CHECKBOX
        bSameX = cw.cp5.addButton("SAME X").setValue(0)
                .setPosition(cw.marginLeft+ 2*cw.rangeWidth/4, cw.marginTop+ cw.rowStep*9)
                .setSize(cw.rangeWidth/4 -5,cw.controlHeight)
                .setSwitch(true).setOff().setLabel("DIFFERENT X")
                .moveTo("line")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        sameX = bSameX.isOn();
                    }
                })
        ;

        // SAME Y CHECKBOX
        bSameY = cw.cp5.addButton("SAME Y").setValue(0)
                .setPosition(cw.marginLeft + 3*cw.rangeWidth/4, cw.marginTop+ cw.rowStep*9)
                .setSize(cw.rangeWidth/4 -5,cw.controlHeight)
                .setSwitch(true).setOff().setLabel("DIFFERENT Y")
                .moveTo("line")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        sameY = bSameY.isOn();
                    }
                })
        ;

        // SLIDER 2D CORNER TL
        s2dLineCorner1 = cw.cp5.addSlider2D("TL CORNER")
                .setPosition(cw.marginLeft,cw.marginTop + cw.rowStep*10)
                .setSize((int)(cw.rangeWidth),(int)(3*cw.rangeWidth/4))
                .setArrayValue(new float[] {Defaults.sceneWidth/2, Defaults.sceneHeight/2}).setCursorX(50).setCursorY(50)
                .setMinX(0).setMaxX(Defaults.sceneWidth).setMinY(0).setMaxY(Defaults.sceneHeight)
                .moveTo("line")
        ;
        corner1 = new PVector(Defaults.sceneWidth/2,Defaults.sceneHeight/2);

        // SLIDER 2D CORNER BR
        s2dLineCorner2 = cw.cp5.addSlider2D("BR CORNER")
                .setPosition(cw.marginLeft,cw.marginTop + cw.rowStep*19)
                .setSize((int)(cw.rangeWidth),(int)(3*cw.rangeWidth/4))
                .setArrayValue(new float[] {Defaults.sceneWidth/2, Defaults.sceneHeight/2})
                .setCursorX(50).setCursorY(50)
                .setMinX(0).setMaxX(Defaults.sceneWidth).setMinY(0).setMaxY(Defaults.sceneHeight)
                .moveTo("line")
        ;
        corner2 = new PVector(Defaults.sceneWidth/2,Defaults.sceneHeight/2);

        // LOCK X2 CHECKBOX
        bLockX2 = cw.cp5.addButton("LOCK X2").setValue(0)
                .setPosition(cw.marginLeft, cw.marginTop+ cw.rowStep*18)
                .setSize(cw.rangeWidth/4 -5,cw.controlHeight)
                .setSwitch(true).setOff().setLabel("UNLOCKED X")
                .moveTo("line")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        lockX2 = bLockX2.isOn();
                    }
                })
        ;

        // LOCK Y CHECKBOX
        bLockY2 = cw.cp5.addButton("LOCK Y2").setValue(0)
                .setPosition(cw.marginLeft + cw.rangeWidth/4, cw.marginTop+ cw.rowStep*18)
                .setSize(cw.rangeWidth/4 -5,cw.controlHeight)
                .setSwitch(true).setOff().setLabel("UNLOCKED Y")
                .moveTo("line")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        lockY2 = bLockY2.isOn();
                    }
                })
        ;

        // CENTRE TL BUTTON
        bCentreTL = cw.cp5.addButton("CENTER TL").setValue(0)
                .setPosition(cw.marginLeft+ 2*cw.rangeWidth/4, cw.marginTop+ cw.rowStep*18)
                .setSize(cw.rangeWidth/4 -5,cw.controlHeight)
                .moveTo("line")
        ;

        // HALF RIGHT ANGLE BUTTON
        bCentreBR = cw.cp5.addButton("CENTER BR")
                .setValue(0).setPosition(cw.marginLeft+ 3*cw.rangeWidth/4, cw.marginTop+ cw.rowStep*18)
                .setSize(cw.rangeWidth/4 -5,cw.controlHeight)
                .moveTo("line")
        ;

        // X VARIABILITY RANGE
        rXVariability = cw.cp5.addRange("X VAR").setBroadcast(false)
                .setPosition(cw.marginLeft,cw.marginTop + cw.rowStep*27)
                .setSize(cw.rangeWidth,cw.controlHeight).setHandleSize(20)
                .setRange(-Defaults.VAR_X,Defaults.VAR_X).setRangeValues(0,0).setBroadcast(true)
                .moveTo("line")
        ;
        xVar = Defaults.rZERO.copy();

        // Y VARIABILITY RANGE
        rYVariability = cw.cp5.addRange("Y VAR").setBroadcast(false)
                .setPosition(cw.marginLeft,cw.marginTop + cw.rowStep*28)
                .setSize(cw.rangeWidth,cw.controlHeight).setHandleSize(20)
                .setRange(-Defaults.VAR_Y,Defaults.VAR_Y).setRangeValues(0,0).setBroadcast(true)
                .moveTo("line")
        ;
        yVar = Defaults.rZERO.copy();
    }

    public void resetControls(){
        bSameX.setOff(); bSameY.setOff(); bLockY.setOff(); bLockX.setOff(); bLockY2.setOff(); bLockX2.setOff();
        s2dLineCorner1.setArrayValue(new float[] {Defaults.sceneWidth/2, Defaults.sceneHeight/2}); corner1 = new PVector(Defaults.sceneWidth/2,Defaults.sceneHeight/2);
        s2dLineCorner2.setArrayValue(new float[] {Defaults.sceneWidth/2, Defaults.sceneHeight/2}); corner2 = new PVector(Defaults.sceneWidth/2,Defaults.sceneHeight/2);
        rXVariability.setRangeValues(0,0); xVar = Defaults.rZERO.copy();
        rYVariability.setRangeValues(0,0); yVar = Defaults.rZERO.copy();
    }

    void checkControlEvents(ControlEvent theControlEvent){
        // LOCK X
        if(theControlEvent.isFrom("LOCK X")) {
            println("Lock X: "+lockX);
            if(lockX) bLockX.setLabel("LOCKED X");
            else bLockX.setLabel("UNLOCKED X");
        }
        // LOCK Y
        else if(theControlEvent.isFrom("LOCK Y")) {
            println("Lock Y: "+lockY);
            if(lockY) bLockY.setLabel("LOCKED Y");
            else bLockY.setLabel("UNLOCKED Y");
        }
        // SAME X
        else if(theControlEvent.isFrom("SAME X")) {
            println("Same X: "+sameX);
            if(sameX) bSameX.setLabel("SAME X");
            else bSameX.setLabel("DIFFERENT X");
        }
        // SAME Y
        else if(theControlEvent.isFrom("SAME Y")) {
            println("Same Y: "+sameY);
            if(sameY) bSameY.setLabel("SAME Y");
            else bSameY.setLabel("DIFFERENT Y");
        }
        // LOCK X2
        else if(theControlEvent.isFrom("LOCK X2")) {
            println("Lock X: "+lockX);
            if(lockX2) bLockX2.setLabel("LOCKED X");
            else bLockX2.setLabel("UNLOCKED X");
        }
        // LOCK Y2
        else if(theControlEvent.isFrom("LOCK Y2")) {
            println("Lock Y2: "+lockY);
            if(lockY2) bLockY2.setLabel("LOCKED Y");
            else bLockY2.setLabel("UNLOCKED Y");
        }
        //CENTER TL BUTTON
        else if(theControlEvent.isFrom("CENTER TL")) {
            float[] o = {Defaults.sceneWidth/2,Defaults.sceneHeight/2};
            s2dLineCorner1.setBroadcast(false); s2dLineCorner1.setArrayValue(o); s2dLineCorner1.setBroadcast(true);
            corner1 = new PVector(o[0], o[1]);
            println("LINE TL CORNER 1 // x: "+o[0]+", y: "+o[1]);
        }
        //CENTER BR BUTTON
        else if(theControlEvent.isFrom("CENTER BR")) {
            float[] o = {Defaults.sceneWidth/2,Defaults.sceneHeight/2};
            s2dLineCorner2.setBroadcast(false); s2dLineCorner2.setArrayValue(o); s2dLineCorner2.setBroadcast(true);
            corner2 = new PVector(o[0], o[1]);
            println("LINE BR CORNER 2 // x: "+o[0]+", y: "+o[1]);
        }
        // TOP-LEFT CORNER
        else if(theControlEvent.isFrom("TL CORNER")) {
            float ox = s2dLineCorner1.getArrayValue()[0];
            float oy = s2dLineCorner1.getArrayValue()[1];
            if(lockX){
                ox = corner1.x;
            }
            if(lockY){
                oy = corner1.y;
            }
            corner1 = new PVector(ox, oy);
            float[] o = {ox,oy};
            s2dLineCorner1.setBroadcast(false);
            s2dLineCorner1.setArrayValue(o);
            s2dLineCorner1.setBroadcast(true);
            if(sameX){
                float y2 = s2dLineCorner2.getArrayValue()[1];
                corner2 = new PVector(ox, y2);
                float[] o2 = {ox,y2};
                s2dLineCorner2.setBroadcast(false);
                s2dLineCorner2.setArrayValue(o2);
                s2dLineCorner2.setBroadcast(true);
            }
            if(sameY){
                float x2 = s2dLineCorner2.getArrayValue()[0];
                corner2 = new PVector(x2, oy);
                float[] o2 = {x2,oy};
                s2dLineCorner2.setBroadcast(false);
                s2dLineCorner2.setArrayValue(o2);
                s2dLineCorner2.setBroadcast(true);
            }
            println("LINE TL CORNER 1 // x: "+ox+", y: "+oy);
        }
        // BOTTOM-RIGHT CORNER
        else if(theControlEvent.isFrom("BR CORNER")) {
            float ox = s2dLineCorner2.getArrayValue()[0];
            float oy = s2dLineCorner2.getArrayValue()[1];
            if(lockX2){
                ox = corner2.x;
            }
            if(lockY2){
                oy = corner2.y;
            }
            corner2 = new PVector(ox, oy);
            float[] o = {ox,oy};
            s2dLineCorner2.setBroadcast(false);
            s2dLineCorner2.setArrayValue(o);
            s2dLineCorner2.setBroadcast(true);
            if(sameX){
                float y1 = s2dLineCorner1.getArrayValue()[1];
                corner1 = new PVector(ox, y1);
                float[] o1 = {ox,y1};
                s2dLineCorner1.setBroadcast(false);
                s2dLineCorner1.setArrayValue(o1);
                s2dLineCorner1.setBroadcast(true);
            }
            if(sameY){
                float x1 = s2dLineCorner1.getArrayValue()[0];
                corner1 = new PVector(x1, oy);
                float[] o1 = {x1,oy};
                s2dLineCorner1.setBroadcast(false);
                s2dLineCorner1.setArrayValue(o1);
                s2dLineCorner1.setBroadcast(true);
            }
            println("LINE BR CORNER 2 // x: "+ox+", y: "+oy);
        }
        //X VAR RANGE
        else if(theControlEvent.isFrom("X VAR")) {
            xVar.setValues(rXVariability.getLowValue(), rXVariability.getHighValue());
            println("X VAR RANGE: "+xVar);
        }
        //Y VAR RANGE
        else if(theControlEvent.isFrom("Y VAR")) {
            yVar.setValues(rYVariability.getLowValue(), rYVariability.getHighValue());
            println("Y VAR RANGE: "+yVar);
        }
    }
}
