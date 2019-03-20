public class RangFloat {

    float minValue;
    float maxValue;

    boolean minEnabled, maxEnabled;


    public RangFloat(float minV, float maxV){
        this.minValue = minV;
        this.maxValue = maxV;
        this.minEnabled = false;
        this.maxEnabled = false;
    }

    public RangFloat(float minV, float maxV, boolean b1, boolean b2){
        this.minValue = minV;
        this.maxValue = maxV;
        this.minEnabled = b1;
        this.maxEnabled = b2;
    }

    public RangFloat(float v){
        this.minValue = v;
        this.maxValue = v;
        this.minEnabled = false;
        this.maxEnabled = false;
    }

    public void setValues(float v){
        this.minValue = v;
        this.maxValue = v;
        this.minEnabled = false;
        this.maxEnabled = false;
    }

    public void setValues(float v, boolean b){
        this.minValue = v;
        this.maxValue = v;
        this.minEnabled = b;
        this.maxEnabled = b;
    }

    public void setValues(float v1, float v2){
        this.minValue = v1;
        this.maxValue = v2;
        this.minEnabled = false;
        this.maxEnabled = false;
    }

    public void setValues(float v1, float v2, boolean b1, boolean b2){
        this.minValue = v1;
        this.maxValue = v2;
        this.minEnabled = b1;
        this.maxEnabled = b2;
    }

    public void setMinValue(float v){
        this.minValue = v;
        this.minEnabled = false;
    }

    public void setMinValue(float v, boolean b){
        this.minValue = v;
        this.minEnabled = b;
    }

    public void setMaxValue(float v){
        this.maxValue = v;
        this.maxEnabled = false;
    }

    public void setMaxValue(float v, boolean b){
        this.maxValue = v;
        this.maxEnabled = b;
    }

    public float getMinValue(){
        return this.minValue;
    }

    public float getMaxValue(){
        return this.maxValue;
    }

    public void setMinEnabled(boolean b){
        this.minEnabled = b;
    }

    public void setMaxEnabled(boolean b){
        this.maxEnabled = b;
    }

    public boolean isMinEnabled(){
        return this.minEnabled;
    }

    public boolean isMaxEnabled(){
        return this.maxEnabled;
    }

    public void applyVariation(RangFloat variation, RangFloat valid){
        if(variation.isMinEnabled()){
            //println("DISPLACE MIN");
            this.minValue +=variation.getRandomValue();
            this.minValue = constrainValue(this.minValue, valid);
        }
        if(variation.isMaxEnabled()){
            //println("DISPLACE MAX");
            this.maxValue +=variation.getRandomValue();
            this.maxValue = constrainValue(this.maxValue, valid);
        }
        //println("NEW VALUES: "+this.minValue+", "+this.maxValue);
    }

    public float constrainValue(float v, RangFloat validRang){
        if(v<validRang.getMinValue()) return validRang.getMinValue();
        else if(v>validRang.getMaxValue()) return validRang.getMaxValue();
        else return v;
    }


        /*
    public Rang<Float> displaceValues(float vMin, float vMax){
        return new Rang<Float>((float)this.minValue+vMin, (float)this.maxValue+vMax);
    }*/

    public RangFloat displaceValues(float v){
        return new RangFloat(this.minValue+v, this.maxValue+v);
    }

    public RangFloat copy(){
        return new RangFloat(this.minValue, this.maxValue, this.minEnabled, this.maxEnabled);
    }

        /*
    public Rang<Float> conv(){
        return new Rang<Float>(this.minValue, this.maxValue, this.minEnabled, this.maxEnabled);
    }*/

    public float getRandomValue(){
        return (float)Defaults.getRandom(this.minValue, this.maxValue);
    }

    public String toString(){
        return "["+minValue+","+maxValue+"]";
    }

}
