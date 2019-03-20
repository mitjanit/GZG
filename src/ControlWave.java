import controlP5.*;

import static processing.core.PApplet.println;

public class ControlWave {

    Range rAmplitud, rXStep;
    RangFloat amplitud, xStep;
    Button bAmpAsc;
    boolean ampAsc;

    public ControlWave(ControlWindow cw){
        setupWaveControls(cw);
    }

    public void setupWaveControls(ControlWindow cw){
        // X STEP RANGE
        rXStep = cw.cp5.addRange("X STEP").setBroadcast(false)
                .setPosition(cw.marginLeft,cw.marginTop + cw.rowStep*31)
                .setSize(cw.rangeWidth,cw.controlHeight).setHandleSize(20)
                .setRange(Defaults.MIN_XSTEP,Defaults.MAX_XSTEP)
                .setRangeValues(Defaults.MIN_XSTEP,Defaults.MAX_XSTEP).setBroadcast(true)
                .moveTo("wave")
        ;
        xStep = new RangFloat(Defaults.MIN_XSTEP, Defaults.MAX_XSTEP);

        // AMPLITUD RANGE
        rAmplitud = cw.cp5.addRange("AMPLITUDE").setBroadcast(false)
                .setPosition(cw.marginLeft + cw.rangeWidth/4,cw.marginTop + cw.rowStep*32)
                .setSize(3*cw.rangeWidth/4,cw.controlHeight).setHandleSize(20)
                .setRange(Defaults.MIN_AMP,Defaults.MAX_AMP)
                .setRangeValues(Defaults.MIN_AMP,Defaults.MAX_AMP).setBroadcast(true)
                .moveTo("wave")
        ;
        amplitud = new RangFloat(Defaults.MIN_AMP, Defaults.MAX_AMP);



        // AMPLITUDE ASC / DESC CHECKBOX
        bAmpAsc = cw.cp5.addButton("AMP ASC").setValue(0).setPosition(cw.marginLeft, cw.marginTop+ cw.rowStep*32).setSize(cw.rangeWidth/4 -5,cw.controlHeight)
                .setSwitch(true).setOff().setLabel("AMB DESC")
                .moveTo("wave")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        ampAsc = bAmpAsc.isOn();
                    }
                })
        ;

        ampAsc=false;
    }

    public void resetControls(){
        rXStep.setRangeValues(Defaults.MIN_XSTEP,Defaults.MAX_XSTEP);
        xStep = new RangFloat(Defaults.MIN_XSTEP, Defaults.MAX_XSTEP);
        rAmplitud.setRangeValues(Defaults.MIN_AMP,Defaults.MAX_AMP);
        amplitud = new RangFloat(Defaults.MIN_AMP, Defaults.MAX_AMP);
        bAmpAsc.setOff(); ampAsc=false;
    }

    void checkControlEvents(ControlEvent theControlEvent){
        // AMPLITUDE
        if(theControlEvent.isFrom("AMPLITUDE")) {
            amplitud.setValues(rAmplitud.getLowValue(), rAmplitud.getHighValue());
            println("AMPLITUDE RANGE: "+amplitud);
        }
        // X STEP
        else if(theControlEvent.isFrom("X STEP")) {
            xStep.setValues(rXStep.getLowValue(), rXStep.getHighValue());
            println("X STEP: "+xStep);
        }
        // AMPLITUDE ASC/DESC
        else if(theControlEvent.isFrom("AMP ASC")) {
            if(ampAsc) bAmpAsc.setLabel("AMP ASC");
            else bAmpAsc.setLabel("AMP DESC");
            println("AMP ASC: "+ampAsc);
        }
    }
}
