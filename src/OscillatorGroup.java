import java.util.ArrayList;

public class OscillatorGroup {

    OscillatorData[] oscs;


    public OscillatorGroup(int n){
        oscs = new OscillatorData[n];
    }


    public void addOscillatorData(int oscType, OscillatorData od){
        oscs[oscType]=od;
    }

    public void printOscillatorData(){
        for(int i=0; i<oscs.length; i++){
            if(oscs[i]!=null){
                oscs[i].print();
            }
        }
    }

    public ArrayList<OscillatorData> getOscillatorData(){
        ArrayList<OscillatorData> oData = new ArrayList<OscillatorData>();
        for(int i=0; i<oscs.length; i++){
            if(oscs[i]!=null){
                oData.add(oscs[i]);
            }
        }
        return oData;
    }

    public void clear(){
        for(int i=0; i<oscs.length; i++){
            oscs[i]=null;
        }
    }

}
