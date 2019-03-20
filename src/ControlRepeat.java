
import controlP5.*;

import static processing.core.PApplet.println;

public class ControlRepeat {

    //********* REPEAT CONTROLS ************//
    Slider sNumTimes;
    int numTimes;
    Range rDisplaceX, rDisplaceY, rDisplaceR, rDisplaceA;
    RangFloat displaceX, displaceY, displaceR, displaceA;
    Button bCloneArea, bSymmetryX, bSymmetryY, bSymmetryR;
    boolean cloneArea = false, symmetryX, symmetryY, symmetryR;

    public ControlRepeat(ControlWindow cw){
        setupRepeatControls(cw);
    }

    void setupRepeatControls(ControlWindow cw) {

        ControlP5 cp5 = cw.cp5;
        float marginLeft = cw.marginLeft;
        float marginTop = cw.marginTop;
        float rowStep = cw.rowStep;
        float colStep = cw.colStep;
        int rangeWidth = cw.rangeWidth;
        int controlHeight = cw.controlHeight;

        // NUM times
        sNumTimes = cp5.addSlider("TIMES")
                .setPosition(marginLeft + rangeWidth + 1.35f * colStep, marginTop + rowStep * 0)
                .setSize(rangeWidth, controlHeight).setRange(Defaults.MIN_TIMES, Defaults.MAX_TIMES)
                .setValue(Defaults.MIN_TIMES).setValueLabel("" + Defaults.MIN_TIMES)
                .moveTo("line")
        ;
        numTimes = Defaults.MIN_TIMES;

        // DISPLACEMENT X RANGE
        rDisplaceX = cp5.addRange("OFFSET X").setBroadcast(false)
                .setPosition(marginLeft + rangeWidth + 1.35f * colStep, marginTop + rowStep * 1)
                .setSize(rangeWidth, controlHeight).setHandleSize(20)
                .setRange(-Defaults.OFFSET_X, Defaults.OFFSET_X).setRangeValues(0, 0).setBroadcast(true)
                .moveTo("line")
        ;
        displaceX = Defaults.rZERO.copy();

        // DISPLACEMENT Y RANGE
        rDisplaceY = cp5.addRange("OFFSET Y").setBroadcast(false)
                .setPosition(marginLeft + rangeWidth + 1.35f * colStep, marginTop + rowStep * 2)
                .setSize(rangeWidth, controlHeight).setHandleSize(20)
                .setRange(-Defaults.OFFSET_Y, Defaults.OFFSET_Y).setRangeValues(0, 0).setBroadcast(true)
                .moveTo("line")
        ;
        displaceY = Defaults.rZERO.copy();

        // DISPLACEMENT R RANGE
        rDisplaceR = cp5.addRange("OFFSET R").setBroadcast(false)
                .setPosition(marginLeft + rangeWidth + 1.35f * colStep, marginTop + rowStep * 3)
                .setSize(rangeWidth, controlHeight).setHandleSize(20)
                .setRange(-Defaults.OFFSET_R, Defaults.OFFSET_R).setRangeValues(0, 0).setBroadcast(true)
                .moveTo("line")
        ;
        displaceR = Defaults.rZERO.copy();

        // DISPLACE ANGLE RANGE
        rDisplaceA = cw.cp5.addRange("OFFSET A").setBroadcast(false)
                .setPosition(cw.marginLeft + cw.rangeWidth + 1.35f*cw.colStep,cw.marginTop + cw.rowStep*4)
                .setSize(cw.rangeWidth,cw.controlHeight).setHandleSize(20)
                .setRange(-Defaults.OFFSET_A, Defaults.OFFSET_A)
                .setRangeValues(0,0).setBroadcast(true)
                .moveTo("wave")
        ;
        displaceA = Defaults.rZERO.copy();

        // SYMMETRY  X CHECKBOX
        bSymmetryX = cp5.addButton("SYMMETRY X").setValue(0)
                .setPosition(marginLeft + rangeWidth + 1.35f * colStep, marginTop + rowStep * 5)
                .setSize(rangeWidth / 4 - 4, controlHeight)
                .setSwitch(true).setOff().setLabel("SYM X OFF")
                .moveTo("line")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent) {
                        symmetryX = bSymmetryX.isOn();
                    }
                })
        ;
        symmetryX = false;

        // SYMMETRY Y CHECKBOX
        bSymmetryY = cp5.addButton("SYMMETRY Y").setValue(0)
                .setPosition(marginLeft + rangeWidth + 1.35f * colStep + rangeWidth / 4, marginTop + rowStep * 5)
                .setSize(rangeWidth / 4 - 4, controlHeight)
                .setSwitch(true).setOff().setLabel("SYM Y OFF")
                .moveTo("line")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent) {
                        symmetryY = bSymmetryY.isOn();
                    }
                })
        ;
        symmetryY = false;

        // SYMMETRY Y CHECKBOX
        bSymmetryR = cp5.addButton("SYMMETRY R").setValue(0)
                .setPosition(marginLeft + rangeWidth + 1.35f * colStep + 2 * rangeWidth / 4, marginTop + rowStep * 5)
                .setSize(rangeWidth / 4 - 4, controlHeight)
                .setSwitch(true).setOff().setLabel("SYM R OFF")
                .moveTo("line")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent) {
                        symmetryR = bSymmetryR.isOn();
                    }
                })
        ;
        symmetryR = false;
    }

    void resetControls(){
        // REPEAT
        sNumTimes.setValue(Defaults.MIN_TIMES).setValueLabel(""+Defaults.MIN_TIMES); numTimes = Defaults.MIN_TIMES;
        rDisplaceX.setRangeValues(0,0); displaceX = Defaults.rZERO.copy();
        rDisplaceY.setRangeValues(0,0); displaceY = Defaults.rZERO.copy();
        rDisplaceR.setRangeValues(0,0); displaceR = Defaults.rZERO.copy();
        rDisplaceA.setRangeValues(0,0); displaceA = Defaults.rZERO.copy();
        bSymmetryX.setOff(); symmetryX = false;
        bSymmetryY.setOff(); symmetryY = false;
        bSymmetryR.setOff(); symmetryR = false;
    }

    void checkControlEvents(ControlEvent theControlEvent){
        // NUM TIMES
        if(theControlEvent.isFrom("TIMES")) {
            numTimes = (int)sNumTimes.getValue();
            sNumTimes.setBroadcast(false); sNumTimes.setValueLabel(""+numTimes); sNumTimes.setBroadcast(true);
            println("Num.Times : "+numTimes);
        }
        //DISPLACEMENT X RANGE
        else if(theControlEvent.isFrom("OFFSET X")) {
            displaceX.setValues(rDisplaceX.getLowValue(), rDisplaceX.getHighValue());
            println("DISPLACE X RANGE: "+displaceX);
        }
        //DISPLACEMENT Y RANGE
        else if(theControlEvent.isFrom("OFFSET Y")) {
            displaceY.setValues(rDisplaceY.getLowValue(), rDisplaceY.getHighValue());
            println("DISPLACE Y RANGE: "+displaceY);
        }
        //DISPLACEMENT R RANGE
        else if(theControlEvent.isFrom("OFFSET R")) {
            displaceR.setValues(rDisplaceR.getLowValue(), rDisplaceR.getHighValue());
            println("DISPLACE R RANGE: "+displaceR);
        }
        //DISPLACEMENT A RANGE
        else if(theControlEvent.isFrom("OFFSET A")) {
            displaceA.setValues(rDisplaceA.getLowValue(), rDisplaceA.getHighValue());
            println("DISPLACE A RANGE: "+displaceA);
        }
        // SYMMETRY X MODE
        else if(theControlEvent.isFrom("SYMMETRY X")) {
            if(symmetryX) bSymmetryX.setLabel("SYM X ON");
            else bSymmetryX.setLabel("SYM X OFF");
            println("SYMMETRY X: "+symmetryX);
        }
        // SYMMETRY Y MODE
        else if(theControlEvent.isFrom("SYMMETRY Y")) {
            if(symmetryY) bSymmetryY.setLabel("SYM Y ON");
            else bSymmetryY.setLabel("SYM Y OFF");
            println("SYMMETRY Y: "+symmetryY);
        }
        // SYMMETRY R MODE
        else if(theControlEvent.isFrom("SYMMETRY R")) {
            if(symmetryR) bSymmetryR.setLabel("SYM R ON");
            else bSymmetryR.setLabel("SYM R OFF");
            println("SYMMETRY R: "+symmetryR);
        }
        // **************** RESET CONTROLS ***********************//
        else if(theControlEvent.isFrom("RESET")) {
            println("RESET CONTROLS");
            resetControls();
        }
    }
}
