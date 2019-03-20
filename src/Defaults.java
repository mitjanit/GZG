import processing.core.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static processing.core.PApplet.max;
import static processing.core.PConstants.HALF_PI;
import static processing.core.PConstants.PI;
import static processing.core.PConstants.TWO_PI;

public class Defaults {


    //********************** Basic Settings ***********************//

    public static int sceneWidth = 1400;
    public static int sceneHeight = 800;

    public static int screenWidth = 1400;
    public static int screenHeight = 800;

    public static float BIG_SCALE = 10f;
    public static PGraphics big;

    public static int bgColor = 255;

    public static String pathDATA = "data/";

    public static boolean displayPoints = true;
    public static boolean displaySources = true;
    public static boolean displayParticles = true;
    public static boolean resetBackground = false;

    public static boolean greyScale = false;
    public static boolean equalWidthAndHeight = true;

    public static boolean divGravity = true;
    public static boolean divSpinAngle = true;
    public static boolean bigExport = false;

    public static float minDistance = 10.0f;


    public static PVector TopLeft = new PVector(0,0);
    public static PVector BottomRight = new PVector(Defaults.screenWidth, Defaults.screenHeight);
    public static PVector Center = new PVector(Defaults.screenWidth/2, Defaults.screenHeight/2);

    //************** RANGS HABITUALS ***************//
    public static RangFloat rZERO = new RangFloat(0);
    public static RangFloat rONE = new RangFloat(1);
    public static RangFloat Zero2TwoPI = new RangFloat(0, TWO_PI);


    public static float getRandom(float min, float max){
        return (float)(Math.random()*(max-min))+min;
    }
    public static float getRandom(float max){
        return (float)(Math.random()*(max));
    }

    //**** BEZIER ****//
    public static PVector getCentroide(ArrayList<PVector> ps) {
        float x=0, y=0;
        for (PVector p : ps) {
            x += p.x;
            y += p.y;
        }
        return new PVector(x/ps.size(), y/ps.size());
    }

    public static ArrayList<PVector> getAnchors(ArrayList<BezierCurve> bcs) {
        ArrayList<PVector> anchors = new ArrayList<PVector>();
        for (BezierCurve bc : bcs) {
            anchors.add(bc.a1.copy());
            anchors.add(bc.a2.copy());
        }
        return anchors;
    }

    public static PVector getCentroideFromBeziers(ArrayList<BezierCurve> bcs) {
        return getCentroide(getAnchors(bcs));
    }

    public static ArrayList<BezierCurve> dilateBeziers(ArrayList<BezierCurve> bcs, float d){
        PVector centroide = getCentroideFromBeziers(bcs);
        ArrayList<BezierCurve> dbs = new ArrayList<BezierCurve>();
        for(BezierCurve bc : bcs){
            dbs.add(bc.getDilatedBezierCurve(centroide, d));
        }
        return dbs;
    }

    public static ArrayList<PVector> getBezierPoints(ArrayList<BezierCurve> bs, int np){
        ArrayList<PVector> ps = new ArrayList<PVector>();
        for(BezierCurve c: bs){
            for(PVector p : c.getPoints(max(np, 4))){
                if(!ps.contains(p)){
                    ps.add(p);
                }
            }
        }
        return ps;
    }

    // timestamp
    public static String timestamp() {
        Calendar now = Calendar.getInstance();
        return String.format("%1$ty%1$tm%1$td_%1$tH%1$tM%1$tS", now);
    }

    //****************************************************//

    public static List mapOptions = Arrays.asList("NONE", "DISTANCE", "DISTANCE INV", "AGE", "AGE INV", "ORIENTATION X", "ORIENTATION X INV", "ORIENTATION Y", "ORIENTATION Y INV", "POSITION X", "POSITION X INV", "POSITION Y", "POSITION Y INV", "DISTANCE REF", "DISTANCE REF INV");
    public static List blendOptions = Arrays.asList("BLEND", "ADD", "SUBTRACT", "DARKEST", "LIGHTEST", "DIFFERENCE", "EXCLUSION", "MULTIPLY", "SCREEN", "REPLACE");


    // DEFAULT VALUES FOR GUI CONTROLS
    public static int MIN_LAYER = 0, MAX_LAYER = 50;
    public static int MIN_VERTEXOS = 3, MAX_VERTEXOS = 15;
    public static int MIN_POINTS = 0, MAX_POINTS = 200;
    public static float MIN_MASS_ATT = 0, MAX_MASS_ATT = 500;
    public static float MIN_MASS_REP = 0, MAX_MASS_REP = 500;
    public static int MIN_RATIO = 0, MAX_RATIO = 100;
    public static float MIN_SPIN = -TWO_PI, MAX_SPIN = TWO_PI;
    public static float MIN_COLLAPSE = 0, MAX_COLLAPSE = 200;

    public static float MIN_X = 0, MIN_Y = 0, MAX_X=sceneWidth*2, MAX_Y= sceneHeight*2, RAND_VAL=3;
    public static int VAR_X = 50, VAR_Y = 50, VAR_R = 200;
    public static int MIN_COLS=1, MAX_COLS = 200;
    public static int MIN_ROWS=1, MAX_ROWS = 200;
    public static int MIN_TIMES=1, MAX_TIMES = 50;
    public static float OFFSET_X=500, OFFSET_Y=500, OFFSET_R=500, OFFSET_A=PI;

    public static float MIN_R=0, MAX_R=2000;
    public static float MIN_RSTEP=0, MAX_RSTEP = 100;
    public static float MIN_XSTEP=0, MAX_XSTEP = 50;

    public static float MIN_A = 0, MAX_A = 8*TWO_PI;
    public static float MIN_ASTEP=0.001f, MAX_ASTEP = PI;
    public static float MIN_A_RAND=-PI, MAX_A_RAND = PI;

    public static float MIN_DIST=0, MAX_DIST = 200;
    public static float MIN_FONTSIZE=0, MAX_FONTSIZE = sceneHeight;
    public static float MIN_AMP=0, MAX_AMP = 1000;

    static float MIN_POLY_LENGTH=0, MAX_POLY_LENGTH=200;
    static float MIN_POLY_STEP=0, MAX_POLY_STEP=10;
    static float MIN_POLY_ANGLE=0, MAX_POLY_ANGLE=HALF_PI;
    static float MIN_ADAPT_SCALE=0, MAX_ADAPT_SCALE=10;
    static float MIN_ADAPT_OFFSET=0, MAX_ADAPT_OFFSET=1;
    static float MIN_ROT_ANG = -PI, MAX_ROT_ANG= PI;

    public static String DEFAULT_IMG = "foto00.jpg";
    public static String DEFAULT_STYLE_FILE_PATH = "data/styles.txt";
    public static float MIN_THRESHOLD = -1, MAX_THRESHOLD = 256;

    public static float MIN_OSC = 0, MAX_OSC = 300;
    public static float MIN_OSC_INIT = 0, MAX_OSC_INIT = 200;
    public static float MIN_OSC_STEP = 0, MAX_OSC_STEP = 20;
    public static float MIN_OSC_FRAMES = 1, MAX_OSC_FRAMES = 180;
    public static float MIN_OSC_TIMES = -1, MAX_OSC_TIMES = 10;
    public static float MIN_OSC_DELAY = 0, MAX_OSC_DELAY = 180;

    public static float MIN_COLOR_IN = 0, MAX_COLOR_IN = sceneWidth;
    public static float MIN_SIZE_IN = 0, MAX_SIZE_IN = sceneWidth;
    public static float MIN_WIDTH = 0, MAX_WIDTH = sceneWidth;
    public static float MIN_HEIGHT = 0, MAX_HEIGHT = sceneHeight;
    public static float MIN_ORIENT = -1, MAX_ORIENT= 1;
    public static float MIN_COLOR_OUT = 0, MAX_COLOR_OUT = 255;
    public static float MIN_SIZE_OUT = 0, MAX_SIZE_OUT = 3;
    public static float MIN_STEP = 0.01f, MAX_STEP = 5;
    public static float MIN_FRAME_WIDTH = 1, MAX_FRAME_WIDTH = 300;

    public static float MIN_TRANS_X= -sceneWidth*2, MAX_TRANS_X=sceneWidth*2;
    public static float MIN_TRANS_Y= -sceneHeight*2, MAX_TRANS_Y=sceneHeight*2;
    public static float MIN_TRANS_Z= -sceneWidth*2, MAX_TRANS_Z=sceneWidth*2;
    public static float MIN_ROT=-TWO_PI, MAX_ROT=TWO_PI;
    public static float MIN_SCALE=0, MAX_SCALE=5;

    // SUPERFORMULA
    public static float MIN_FORMULA_M = 0, MAX_FORMULA_M = 10;
    public static float MIN_FORMULA_N1 = 0, MAX_FORMULA_N1 = 10;
    public static float MIN_FORMULA_N2 = 0, MAX_FORMULA_N2 = 10;
    public static float MIN_FORMULA_N3 = 0, MAX_FORMULA_N3 = 10;
    public static float MIN_FORMULA_LAPS = 1, MAX_FORMULA_LAPS = 16;
    public static float MIN_FORMULA_STEP = 0.01f, MAX_FORMULA_STEP = 1;
    public static float MIN_FORMULA_SIZE = 0, MAX_FORMULA_SIZE = sceneWidth*2;

    // WHITNEY
    public static float MIN_WHITNEY_MAGNIFY = 0, MAX_WHITNEY_MAGNIFY = sceneWidth*2;
    public static float MIN_WHITNEY_PHASE = 0, MAX_WHITNEY_PHASE = 50;
    public static float MIN_WHITNEY_AMPLITUDE = 0, MAX_WHITNEY_AMPLITUDE = 50;

    // POISSON
    public static float MIN_POISSON_DIST = 1, MAX_POISSON_DIST = sceneWidth/10;
    public static float MIN_POISSON_K = 10, MAX_POISSON_K = 50;
}
