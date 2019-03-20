import processing.core.PVector;

public class SetOfPointsStyle {

    //Valor d'abilitat
    boolean enable;

    // Rang de valors X e Y vàlis
    RangFloat xRange, yRange;

    // Variabilitat X e Y alhora de definir la posició del spunts
    RangFloat xVar, yVar;

    // Valor del ratio de la relació entre atractors i repulsors del conjunt
    float ratioAttRep;

    // Valors de mapeig dels Attractors
    int mapMassAtt;
    RangFloat mapInMassAtt, mapOutMassAtt;

    // Valors de mapeig dels Repulsors
    int mapMassRep;
    RangFloat mapInMassRep, mapOutMassRep;



    // Valors de mapeig dels paràmetres de collapse
    boolean collapseable;
    int mapNPCollapse;
    RangFloat mapInNPCollapse, mapOutNPCollapse;

    // Valors de mapeig dels paràmetres de l'angle de spin
    int mapSpinAngle;
    RangFloat mapInSpinAngle, mapOutSpinAngle;

    PVector ref;


    SetOfPointsStyle(boolean e, RangFloat xR, RangFloat yR,RangFloat xV, RangFloat yV, float ratio,
                        int ma, RangFloat mai, RangFloat mao,
                        int mr, RangFloat mri, RangFloat mro,
                        boolean c, int mc, RangFloat mci, RangFloat mco,
                        int ms, RangFloat msi, RangFloat mso, PVector ref){
        this.enable = e;
        this.xRange = xR.copy(); this.yRange = yR.copy();
        this.xVar = xV.copy(); this.yVar = yV.copy();
        this.ratioAttRep = ratio;
        this.mapMassAtt = ma;   this.mapInMassAtt = mai;    this.mapOutMassAtt = mao;
        this.mapMassRep = mr;   this.mapInMassRep = mri;    this.mapOutMassRep = mro;
        this.collapseable = c;
        this.mapNPCollapse = mc;    this.mapInNPCollapse = mci; this.mapOutNPCollapse = mco;
        this.mapSpinAngle = ms; this.mapInSpinAngle = msi;  this.mapOutSpinAngle = mso;
        this.ref = ref.copy();
    }

    SetOfPointsStyle(){
        this.enable = true;
        this.xRange = new RangFloat(0, Defaults.screenWidth); this.yRange = new RangFloat(0, Defaults.screenHeight);
        this.xVar = new RangFloat(0); this.yVar = new RangFloat(0);
        this.ratioAttRep = 50.0f;
        this.mapMassAtt = 1;   this.mapInMassAtt = new RangFloat(0);    this.mapOutMassAtt = new RangFloat(50,400);
        this.mapMassRep = 1;   this.mapInMassRep = new RangFloat(0);    this.mapOutMassRep = new RangFloat(50,150);
        this.collapseable = false;
        this.mapNPCollapse = 0;    this.mapInNPCollapse = new RangFloat(0); this.mapOutNPCollapse = new RangFloat(1000);
        this.mapSpinAngle = 0; this.mapInSpinAngle = new RangFloat(0);  this.mapOutSpinAngle = new RangFloat(0);
        this.ref = new PVector(Defaults.screenWidth/2, Defaults.screenHeight/2);
    }

}
