import controlP5.*;

import java.util.Arrays;
import java.util.List;

import static processing.core.PApplet.println;
import static processing.core.PConstants.PI;
import static processing.core.PConstants.TWO_PI;

public class ControlOscillation {

    OscillatorGroup oscGroup;
    ScrollableList slOscMode;
    int oscMode;
    Range rOscMin, rOscMax, rOscInitValue, rOscStep, rOscNumTimes, rOscNumFrames, rOscDelay;
    RangFloat oscMin, oscMax, oscInitValue, oscStep, oscNumFrames, oscNumTimes, oscDelay;
    Button bOscAbsolute, bOscDirection, bOscEnabled, bOscillate;
    boolean oscAbsolute, oscEnabled;
    int oscDirection = 0;

    List oscillationModes = Arrays.asList("NONE", "POSITION X", "POSITION Y","GRAVITY", "SPIN ANGLE", "RADIUS", "ANGLE");


    public ControlOscillation(ControlWindow cw){
        setupOscillationControls(cw);
    }

    public void setupOscillationControls(ControlWindow cw){
        // GRUP D'OSCILLADORS PER GUARDAR
        oscGroup = new OscillatorGroup(oscillationModes.size()-1);

        // OSCILLATION MODE (ABSOLUTE/RELATIVE) BUTTON
        bOscAbsolute = cw.cp5.addButton("ABSOLUTE").setValue(0)
                .setPosition(cw.marginLeft + cw.rangeWidth + 1.35f*cw.colStep, cw.marginTop+ cw.rowStep*7)
                .setSize(cw.rangeWidth/3 - 2,cw.controlHeight)
                .setSwitch(true).setOff().setLabel("RELATIVE")
                .moveTo("text")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        oscAbsolute = bOscAbsolute.isOn();
                    }
                })
        ;

        oscAbsolute = false;

        // OSCILLATION DIRECTION (FORWARD/BACKWARD) BUTTON
        bOscDirection = cw.cp5.addButton("OSC DIR").setValue(0)
                .setPosition(cw.marginLeft + cw.rangeWidth + 1.35f*cw.colStep +cw.rangeWidth/3, cw.marginTop+ cw.rowStep*7)
                .setSize(cw.rangeWidth/3 - 2,cw.controlHeight)
                .setSwitch(true).setOff().setLabel("RAND DIR")
                .moveTo("text")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        //oscDirection = bOscDirection.isOn();
                        oscDirection++; if(oscDirection>1) oscDirection=-1;
                    }
                })
        ;

        oscDirection = 0;

        // OSCILLATION ENABLE BUTTON
        bOscEnabled = cw.cp5.addButton("OSC ON").setValue(0)
                .setPosition(cw.marginLeft + cw.rangeWidth + 1.35f*cw.colStep + 2*cw.rangeWidth/3, cw.marginTop+ cw.rowStep*7)
                .setSize(cw.rangeWidth/3 - 2,cw.controlHeight)
                .setSwitch(true).setOff().setLabel("OSC OFF")
                .moveTo("text")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        oscEnabled = bOscEnabled.isOn();
                    }
                })
        ;

        oscEnabled = false;
        // OSCILLATION MODE

        slOscMode = cw.cp5.addScrollableList("OSC MODE")
                .setPosition(cw.marginLeft + cw.rangeWidth + 1.35f*cw.colStep,cw.marginTop + cw.rowStep*8)//.setSize(cw.rangeWidth,cw.controlHeight)
                .setWidth(cw.rangeWidth/3 -2).setItemHeight(cw.controlHeight).setBarHeight(cw.controlHeight)
                .addItems(oscillationModes)
                .setType(ScrollableList.LIST)
                .moveTo("text")
        ;

        slOscMode.setValue(0.0f); oscMode = 0;

        // OSCILLATION MIN RANGE
        rOscMin = cw.cp5.addRange("OSC MIN").setBroadcast(false)
                .setPosition(cw.marginLeft + cw.rangeWidth + 1.35f*cw.colStep + cw.rangeWidth/3 + 2,cw.marginTop + cw.rowStep*8)
                .setSize(2*cw.rangeWidth/3-2,cw.controlHeight).setHandleSize(20)
                .setRange(-Defaults.MIN_OSC,Defaults.MAX_OSC).setRangeValues(0,0).setBroadcast(true)
                .moveTo("text")
        ;
        oscMin = Defaults.rZERO.copy();

        // OSCILLATION MAX RANGE
        rOscMax = cw.cp5.addRange("OSC MAX").setBroadcast(false)
                .setPosition(cw.marginLeft + cw.rangeWidth + 1.35f*cw.colStep + cw.rangeWidth/3 + 2,cw.marginTop + cw.rowStep*9)
                .setSize(2*cw.rangeWidth/3-2,cw.controlHeight).setHandleSize(20)
                .setRange(-Defaults.MIN_OSC,Defaults.MAX_OSC).setRangeValues(0,0).setBroadcast(true)
                .moveTo("text")
        ;
        oscMax = Defaults.rZERO.copy();

        // OSCILLATION VALUE
        rOscInitValue = cw.cp5.addRange("OSC INIT").setBroadcast(false)
                .setPosition(cw.marginLeft + cw.rangeWidth + 1.35f*cw.colStep + cw.rangeWidth/3 + 2,cw.marginTop + cw.rowStep*10)
                .setSize(2*cw.rangeWidth/3-2,cw.controlHeight)
                .setRange(Defaults.MIN_OSC_INIT,Defaults.MAX_OSC_INIT)
                .setRangeValues(Defaults.MIN_OSC_INIT,Defaults.MAX_OSC_INIT)
                .setBroadcast(true)
                .moveTo("text")
        ;
        oscInitValue = Defaults.rZERO.copy();

        // OSCILLATION STEP
        rOscStep = cw.cp5.addRange("OSC STEP").setBroadcast(false)
                .setPosition(cw.marginLeft + cw.rangeWidth + 1.35f*cw.colStep + cw.rangeWidth/3 + 2,cw.marginTop + cw.rowStep*11)
                .setSize(2*cw.rangeWidth/3-2,cw.controlHeight)
                .setRange(Defaults.MIN_OSC_STEP,Defaults.MAX_OSC_STEP)
                .setRangeValues(Defaults.MIN_OSC_STEP,Defaults.MAX_OSC_STEP).setBroadcast(true)
                .moveTo("text")
        ;
        oscStep = Defaults.rZERO.copy();

        // OSCILLATION NUM TIMES
        rOscNumTimes= cw.cp5.addRange("OSC TIMES")
                .setBroadcast(false).setPosition(cw.marginLeft + cw.rangeWidth + 1.35f*cw.colStep + cw.rangeWidth/3 + 2,cw.marginTop + cw.rowStep*12)
                .setSize(2*cw.rangeWidth/3-2,cw.controlHeight)
                .setRange(Defaults.MIN_OSC_TIMES,Defaults.MAX_OSC_TIMES)
                .setRangeValues(Defaults.MIN_OSC_TIMES,Defaults.MIN_OSC_TIMES).setBroadcast(true)
                .moveTo("text")
        ;
        oscNumTimes = new RangFloat(Defaults.MIN_OSC_TIMES, Defaults.MIN_OSC_TIMES);

        // OSCILLATION NUM FRAMES
        rOscNumFrames = cw.cp5.addRange("OSC FRAMES").setBroadcast(false)
                .setPosition(cw.marginLeft + cw.rangeWidth + 1.35f*cw.colStep + cw.rangeWidth/3 + 2,cw.marginTop + cw.rowStep*13)
                .setSize(2*cw.rangeWidth/3-2,cw.controlHeight)
                .setRange(Defaults.MIN_OSC_FRAMES,Defaults.MAX_OSC_FRAMES)
                .setRangeValues(Defaults.MIN_OSC_FRAMES,Defaults.MAX_OSC_FRAMES).setBroadcast(true)
                .moveTo("text")
        ;
        oscNumFrames = new RangFloat(Defaults.MIN_OSC_FRAMES, Defaults.MAX_OSC_FRAMES);

        // OSCILLATION DELAY FRAMES
        rOscDelay = cw.cp5.addRange("OSC DELAY").setBroadcast(false)
                .setPosition(cw.marginLeft + cw.rangeWidth + 1.35f*cw.colStep + cw.rangeWidth/3 + 2,cw.marginTop + cw.rowStep*14)
                .setSize(2*cw.rangeWidth/3-2,cw.controlHeight)
                .setRange(Defaults.MIN_OSC_DELAY, Defaults.MAX_OSC_DELAY)
                .setRangeValues(Defaults.MIN_OSC_DELAY, Defaults.MAX_OSC_DELAY).setBroadcast(true)
                .moveTo("text")
        ;
        oscDelay = new RangFloat(Defaults.MIN_OSC_DELAY, Defaults.MAX_OSC_DELAY);

        // OSCILLATION ENABLE BUTTON
        bOscillate = cw.cp5.addButton("OSCILLATE").setValue(0)
                .setPosition(cw.marginLeft + cw.rangeWidth + 1.35f*cw.colStep + cw.rangeWidth/3 + 2, cw.marginTop+ cw.rowStep*15)
                .setSize(cw.rangeWidth/4 - 2,cw.controlHeight)
                .moveTo("text")
        ;
    }

    public void resetControls(){
        slOscMode.setValue(0.0f); oscMode = 0;
        rOscMin.setRangeValues(0,0); oscMin = Defaults.rZERO.copy();
        rOscMax.setRangeValues(0,0); oscMax = Defaults.rZERO.copy();
        rOscInitValue.setRangeValues(Defaults.MIN_OSC_INIT, Defaults.MAX_OSC_INIT); oscInitValue = new RangFloat(Defaults.MIN_OSC_INIT, Defaults.MAX_OSC_INIT);
        rOscStep.setRangeValues(Defaults.MIN_OSC_STEP, Defaults.MAX_OSC_STEP); oscStep = new RangFloat(Defaults.MIN_OSC_STEP, Defaults.MAX_OSC_STEP);
        rOscNumTimes.setRangeValues(Defaults.MIN_OSC_TIMES, Defaults.MIN_OSC_TIMES); oscNumTimes = new RangFloat(Defaults.MIN_OSC_TIMES, Defaults.MIN_OSC_TIMES);
        rOscNumFrames.setRangeValues(Defaults.MIN_OSC_FRAMES, Defaults.MAX_OSC_FRAMES); oscNumFrames = new RangFloat(Defaults.MIN_OSC_FRAMES, Defaults.MAX_OSC_FRAMES);
        rOscDelay.setRangeValues(Defaults.MIN_OSC_DELAY, Defaults.MAX_OSC_DELAY); oscNumFrames = new RangFloat(Defaults.MIN_OSC_DELAY, Defaults.MAX_OSC_DELAY);
        bOscAbsolute.setOff(); oscAbsolute = false;
        bOscDirection.setOff(); oscDirection = 0;
        bOscEnabled.setOff(); oscEnabled = false;
    }

    public void checkControlEvents(ControlEvent theControlEvent){
        if(theControlEvent.isFrom("OSC MIN")) {
            oscMin.setValues(rOscMin.getLowValue(), rOscMin.getHighValue());
            println("OSCILLATION MIN: "+oscMin);
        }
        else if(theControlEvent.isFrom("OSC MAX")) {
            oscMax.setValues(rOscMax.getLowValue(), rOscMax.getHighValue());
            println("OSCILLATION MAX: "+oscMax);
        }
        // OSC INIT VALUE
        else if(theControlEvent.isFrom("OSC INIT")) {
            oscInitValue.setValues(rOscInitValue.getLowValue(), rOscInitValue.getHighValue());
            println("Osc Init Value : "+oscInitValue);
        }
        // OSC STEP VALUE
        else if(theControlEvent.isFrom("OSC STEP")) {
            oscStep.setValues(rOscStep.getLowValue(), rOscStep.getHighValue());
            println("Osc Step : "+oscStep);
        }
        // OSC NUM TIMES
        else if(theControlEvent.isFrom("OSC TIMES")) {
            oscNumTimes.setValues(rOscNumTimes.getLowValue(), rOscNumTimes.getHighValue());
            println("Num Times : "+oscNumTimes);
        }
        // OSC NUM FRAMES
        else if(theControlEvent.isFrom("OSC FRAMES")) {
            oscNumFrames.setValues(rOscNumFrames.getLowValue(), rOscNumFrames.getHighValue());
            println("Num frames : "+oscNumFrames);
        }
        // OSC DELAY
        else if(theControlEvent.isFrom("OSC DELAY")) {
            oscDelay.setValues(rOscDelay.getLowValue(), rOscDelay.getHighValue());
            println("Delay : "+oscDelay);
        }

        // OSCILLATION MODE
        else if(theControlEvent.isFrom("OSC MODE")) {
            oscMode = (int)slOscMode.getValue();
            println("Osc Mode: "+oscMode+" "+oscillationModes.get(oscMode));
            if(oscMode==6 || oscMode==4){
                // OSC ANGLE
                rOscMin.setBroadcast(false).setRange(-TWO_PI,TWO_PI).setRangeValues(0,0).setBroadcast(true); oscMin = Defaults.rZERO.copy();
                rOscMax.setBroadcast(false).setRange(-TWO_PI,TWO_PI).setRangeValues(0,0).setBroadcast(true); oscMax = Defaults.rZERO.copy();
                rOscStep.setBroadcast(false).setRange(0,PI/4).setRangeValues(0,0).setBroadcast(true); oscStep = Defaults.rZERO.copy();
            }
            else {
                rOscMin.setBroadcast(false).setRange(-Defaults.MIN_OSC,Defaults.MAX_OSC).setRangeValues(0,0).setBroadcast(true); oscMin = Defaults.rZERO.copy();
                rOscMax.setBroadcast(false).setRange(-Defaults.MIN_OSC,Defaults.MAX_OSC).setRangeValues(0,0).setBroadcast(true); oscMax = Defaults.rZERO.copy();
                rOscStep.setBroadcast(false).setRange(Defaults.MIN_OSC_STEP,Defaults.MAX_OSC_STEP).setRangeValues(0,0).setBroadcast(true); oscStep = Defaults.rZERO.copy();
            }
        }

        // ABSOLUTE / RELATIVE
        else if(theControlEvent.isFrom("ABSOLUTE")) {
            if(oscAbsolute) bOscAbsolute.setLabel("ABSOLUTE");
            else bOscAbsolute.setLabel("RELATIVE");
            println("Absolute: "+oscAbsolute + ((oscAbsolute)?" Absolute":" Relative"));
        }
        // FORWARD / BACKWARD
        else if(theControlEvent.isFrom("OSC DIR")) {
            if(oscDirection==0) bOscDirection.setLabel("RAND DIR");
            else if(oscDirection==1) bOscDirection.setLabel("FORWARD");
            else if(oscDirection==-1) bOscDirection.setLabel("BACKWARD");
            else bOscDirection.setLabel("???");
            println("Direction: "+oscDirection);
        }
        // OSC ON / OFF
        else if(theControlEvent.isFrom("OSC ON")) {
            if(oscEnabled) bOscEnabled.setLabel("OSC ON");
            else bOscEnabled.setLabel("OSC OFF");
            println("OSC Enabled: "+oscEnabled);
        }
        // OSCILLATE BUTTON
        else if(theControlEvent.isFrom("OSCILLATE")) {
            if(oscMode!=0){
                println("OSCILLATE");
                OscillatorData oscData = new OscillatorData(oscMode, oscMin, oscMax, oscInitValue, oscStep, oscNumFrames, oscNumTimes, oscDelay, oscEnabled, oscDirection);
                oscGroup.addOscillatorData(oscMode-1, oscData);
                oscGroup.printOscillatorData();
            }
        }
    }
}
