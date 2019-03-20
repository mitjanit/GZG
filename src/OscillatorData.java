import java.util.Arrays;
import java.util.List;

import static processing.core.PApplet.println;

public class OscillatorData {

    List oscillationModes = Arrays.asList("NONE", "POSITION X", "POSITION Y","GRAVITY", "SPIN ANGLE", "RADIUS", "ANGLE");


    int oscMode, oscDirection;
    RangFloat oscMin, oscMax, oscInitValue, oscStep, oscNumFrames, oscNumTimes, oscDelay;
    boolean oscEnabled;

    public OscillatorData(int oscmode, RangFloat oscmin, RangFloat oscmax,
                          RangFloat oscinit, RangFloat oscstep, RangFloat oscnf, RangFloat oscnt, RangFloat oscdelay,
                          boolean osce, int oscdir ){
        this.oscMode = oscmode;
        this.oscMin = oscmin.copy();
        this.oscMax = oscmax.copy();
        this.oscInitValue = oscinit.copy();
        this.oscStep = oscstep.copy();
        this.oscNumFrames = oscnf.copy();
        this.oscNumTimes = oscnt.copy();
        this.oscDelay = oscdelay.copy();
        this.oscEnabled = osce;
        this.oscDirection = oscdir;
    }

    void print(){
        println("OSCILLATOR "+oscillationModes.get(oscMode)+"MIN:"+oscMin+", MAX:"+oscMax+", STEP:"+oscStep+", FRAMES:"+oscNumFrames+", TIMES:"+oscNumTimes+", ENABLED:"+oscEnabled+", DELAY:"+oscDelay);
    }



}

