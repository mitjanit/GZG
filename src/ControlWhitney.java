import controlP5.*;

import static processing.core.PApplet.println;

public class ControlWhitney {

    //************ WHITNEY CONTROLS ***************//
    Slider rWMagnify, rWPhase, rWAmplitude;
    float wMagnify, wPhase, wAmplitude;

    public ControlWhitney(ControlWindow cw){
        setupWhitneyControls(cw);
    }

    public void setupWhitneyControls(ControlWindow cw){
        // WHITNEY MAGNIFY
        rWMagnify = cw.cp5.addSlider("W MAGNIFY").setBroadcast(false)
                .setPosition(cw.marginLeft,cw.marginTop + cw.rowStep*19)
                .setSize(cw.rangeWidth,cw.controlHeight).setHandleSize(20)
                .setRange(Defaults.MIN_WHITNEY_MAGNIFY,Defaults.MAX_WHITNEY_MAGNIFY)
                .setValue(0).setBroadcast(true)
                .moveTo("whitney")
        ;

        wMagnify = 0;

        // WHITNEY PHASE
        rWPhase = cw.cp5.addSlider("W PHASE").setBroadcast(false)
                .setPosition(cw.marginLeft,cw.marginTop + cw.rowStep*20)
                .setSize(cw.rangeWidth,cw.controlHeight).setHandleSize(20)
                .setRange(Defaults.MIN_WHITNEY_PHASE,Defaults.MAX_WHITNEY_PHASE)
                .setValue(0).setBroadcast(true)
                .moveTo("whitney")
        ;

        wPhase = 0;

        // WHITNEY AMPLITUDE
        rWAmplitude = cw.cp5.addSlider("W AMPLITUDE").setBroadcast(false)
                .setPosition(cw.marginLeft,cw.marginTop + cw.rowStep*21)
                .setSize(cw.rangeWidth,cw.controlHeight).setHandleSize(20)
                .setRange(Defaults.MIN_WHITNEY_AMPLITUDE,Defaults.MAX_WHITNEY_AMPLITUDE)
                .setValue(0).setBroadcast(true)
                .moveTo("whitney")
        ;

        wAmplitude = 0;
    }

    public void resetControls(){

    }

    public void checkControlEvents(ControlEvent theControlEvent){
        // W MAGNIFY
        if(theControlEvent.isFrom("W MAGNIFY")) {
            wMagnify = rWMagnify.getValue();
            println("WHITNEY MAGNIFY RANGE: "+wMagnify);
        }
        // W PHASE
        else if(theControlEvent.isFrom("W PHASE")) {
            wPhase = rWPhase.getValue();
            println("WHITNEY PHASE RANGE: "+wPhase);
        }
        // W AMPLITUDE
        else if(theControlEvent.isFrom("W AMPLITUDE")) {
            wAmplitude = rWAmplitude.getValue();
            println("WHITNEY AMPLITUDE RANGE: "+wAmplitude);
        }
    }
}
