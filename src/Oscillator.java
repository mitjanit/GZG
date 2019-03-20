import processing.core.PApplet;

import static processing.core.PApplet.max;
import static processing.core.PApplet.println;

public class Oscillator {

    RangFloat oscRange;
    float step;
    float value;
    int direction;  //-1 o 1
    int times; // -1, 0, 1 o +  (-1: infinit // 0: de Value a Max o Min // 1: una vegada a Max i una a Min//
    int numTimesMax, numTimesMin;
    boolean enabled;

    float everyFrame;
    float startTime;
    float delay;

    // Constructors

    public Oscillator(PApplet pA){
        this.oscRange = new RangFloat(0);
        this.value = 0; this.step = 0;
        this.direction = 1;
        this.enabled = false;
        this.everyFrame = 1;
        this.startTime = pA.frameCount;
        this.delay=0;
        times = -1; numTimesMin=0; numTimesMax=0;
    }


    public Oscillator(PApplet pA, float minV, float maxV, float v, float step){
        this.oscRange = new RangFloat(minV, maxV);
        this.value = v; this.step = step;
        this.direction = 1;
        this.enabled = false;
        this.everyFrame = 1;
        this.startTime = pA.frameCount;
        this.delay=0;
        times = -1; numTimesMin=0; numTimesMax=0;
    }

    public Oscillator(PApplet pA,float minV, float maxV, float v, float step, int d, boolean b){
        this.oscRange = new RangFloat(minV, maxV);
        this.value = v; this.step = step;
        this.direction = d;
        this.enabled = b;
        this.everyFrame = 1;
        this.startTime = pA.frameCount;
        this.delay=0;
        times = -1; numTimesMin=0; numTimesMax=0;
    }

    public Oscillator(PApplet pA, float minV, float maxV, float v, float step, int d, boolean b, float frames){
        this.oscRange = new RangFloat(minV,maxV);
        this.value = v; this.step = step;
        this.direction = d;
        this.enabled = b;
        this.everyFrame = max(1,frames);
        this.startTime = pA.frameCount; this.delay=0;
        times = -1; numTimesMin=0; numTimesMax=0;
    }

    public Oscillator(PApplet pA, float minV, float maxV, float v, float step, int d, boolean b, float frames, float times, float delay){
        this.oscRange = new RangFloat(minV, maxV);
        this.value = v; this.step = step;
        this.direction = d;
        this.enabled = b;
        this.everyFrame = max(1,frames);
        this.startTime = pA.frameCount;
        this.delay=delay;
        this.times = (int)times; numTimesMin=0; numTimesMax=0;
    }

    //
    public void print(){
        println("OSCILLATOR");
    }

    // Setters

    public void setOscRange(float minV, float maxV){
        this.oscRange = new RangFloat(new Float(minV), new Float(maxV));
    }

    public void setOscMinValue(float minV){
        this.oscRange.setMinValue(minV);
    }

    public void setOscMaxValue(float maxV){
        this.oscRange.setMaxValue(maxV);
    }

    public void setOscStep(float step){
        this.step = step;
    }

    public void setOscValue(float v){
        this.value = v;
    }

    public void setOscDirection(int d){
        this.direction = d;
    }

    public void setOscEnabled(boolean b){
        this.enabled = b;
    }

    public void setOscFrameRate(float v){
        this.everyFrame = (int)v;
    }

    public void setOscDelay(float v){
        this.delay = (int)v;
    }

    public void setOscTimes(int t){
        this.times = t;
        numTimesMin=0; numTimesMax=0;
    }


    // Getters

    public float getMinValue(){
        return this.oscRange.getMinValue();
    }

    public float getMaxValue(){
        return this.oscRange.getMaxValue();
    }

    public float getValue(){
        return this.value;
    }

    public boolean isEnabled(){
        return enabled;
    }

    public boolean isTimeforOsc(PApplet pA){
        return (pA.frameCount%(int)(everyFrame)==0)&&((startTime+delay)<pA.frameCount);
    }

    public boolean isFinished(){
        if(times==-1){
            return false;
        }
        else {
            return ((numTimesMin>=times) && (numTimesMax>=times));
        }
    }


    // Oscillation

    public float oscillate(PApplet pA){
        if(!isFinished() && isEnabled() && isTimeforOsc(pA)){
            value +=step*direction;
            //  DIR: min to max ---->
            if(direction==1 && value>=oscRange.maxValue){
                direction*= -1;
                value = oscRange.maxValue;
                numTimesMax++;
            }
            //  DIR: min to max ---->
            else if(direction==-1 && value<=oscRange.minValue){
                direction*= -1;
                value = oscRange.minValue;
                numTimesMin++;
            }
        }
        println(pA.frameCount+"MAX:"+numTimesMax+" / MIN: "+numTimesMin);
        return value;
    }




}
