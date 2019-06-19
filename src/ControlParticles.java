import controlP5.*;
import processing.core.PApplet;
import processing.core.PVector;
import select.files.SelectLibrary;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//import static processing.core.PApplet.*;
import static processing.core.PApplet.*;
import static processing.core.PConstants.CENTER;
import static processing.core.PConstants.TAB;

public class ControlParticles {

    static List mapOptions = Arrays.asList("NONE", "DISTANCE", "DISTANCE INV", "AGE", "AGE INV", "ORIENTATION X", "ORIENTATION X INV", "ORIENTATION Y", "ORIENTATION Y INV", "POSITION X", "POSITION X INV", "POSITION Y", "POSITION Y INV", "DISTANCE REF", "DISTANCE REF INV");
    static List distOptions = Arrays.asList("RANDOM", "FILL RECT", "BORDER RECT", "FILL CIRCLE", "LINE", "POLYGON", "SPIRAL", "WAVE");

    ScrollableList slParticleDist, slMapRed, slMapGreen, slMapBlue, slMapOpac, slMapWidth, slMapHeight, slParticleStyle;
    int particleDist, mapRed, mapGreen, mapBlue, mapOpac, mapWidth, mapHeight;
    Range rParticleStep, rParticleDelay, rFadeInSize, rRedIn, rRedOut, rGreenIn, rGreenOut, rBlueIn, rBlueOut, rOpacIn, rOpacOut, rWidthIn, rWidthOut, rHeightIn, rHeightOut;
    RangFloat particleStep, particleDelay, fadeInSize, redIn, redOut, greenIn, greenOut, blueIn, blueOut, opacIn, opacOut, widthIn, widthOut,heightIn, heightOut;
    Button bParticleMode, bGreyscale, bEqualWidthAndHeight, bLoadStyle, bRandStyle, bSaveStyle, bExportStyles, bImportStyles;

    int particleStyle; // random, painters, removers
    boolean greyScale, equalWidthAndHeight;
    Slider2D s2dRefColor;
    PVector  refColor;

    Textfield tStyleName;
    String styleName;
    String pathStylesFile=Defaults.DEFAULT_STYLE_FILE_PATH;

    Slider sFrameWidth, sXRand, sYRand;
    float frameWidth, xRand, yRand;

    Slider sNumVertexos;
    int numVertexos=3;

    String pathFile, fileName;
    SelectLibrary files;
    File file;
    ControlWindow cw;

    ArrayList<AttractedParticleStyle> pStyles;



    public ControlParticles(ControlWindow cw){
        setupParticleControls(cw);
        this.cw = cw;
        files = new SelectLibrary((PApplet)cw);
    }

    public void setupParticleControls(ControlWindow cw){

        pStyles = new ArrayList<AttractedParticleStyle>();

        // FRAME WIDTH (FRAME OF PARTICLES)
        frameWidth = Defaults.MIN_FRAME_WIDTH;

        sFrameWidth = cw.cp5.addSlider("FRAME WIDTH")
                .setPosition(cw.marginLeft,cw.marginTop + cw.rowStep*6)
                .setSize(cw.rangeWidth,cw.controlHeight)
                .setRange(Defaults.MIN_FRAME_WIDTH, Defaults.MAX_FRAME_WIDTH)
                .setValue(Defaults.MIN_FRAME_WIDTH).setValueLabel(""+(int)frameWidth)
                .moveTo("particles")
        ;


        // PARTICLE STEP RANGE
        rParticleStep = cw.cp5.addRange("PART STEP")
                .setBroadcast(false).setPosition(cw.marginLeft,cw.marginTop + cw.rowStep*3)
                .setSize(cw.rangeWidth,cw.controlHeight)
                .setRange(Defaults.MIN_STEP, Defaults.MAX_STEP)
                .setRangeValues(Defaults.MIN_STEP, Defaults.MAX_STEP).setBroadcast(true)
                .moveTo("particles")
        ;

        particleStep = new RangFloat(Defaults.MIN_STEP, Defaults.MAX_STEP);

        // PARTICLE DELAY RANGE
        rParticleDelay = cw.cp5.addRange("PART DELAY").setBroadcast(false)
                .setPosition(cw.marginLeft,cw.marginTop + cw.rowStep*4)
                .setSize(cw.rangeWidth,cw.controlHeight)
                .setRange(Defaults.MIN_OSC_DELAY, Defaults.MAX_OSC_DELAY)
                .setRangeValues(Defaults.MIN_OSC_DELAY, Defaults.MAX_OSC_DELAY).setBroadcast(true)
                .moveTo("particles")
        ;

        particleDelay = new RangFloat(Defaults.MIN_OSC_DELAY, Defaults.MAX_OSC_DELAY);

        // PARTICLE NUM VERTEXS
        sNumVertexos = cw.cp5.addSlider("NUM VERTEXS")
                .setPosition(cw.marginLeft,cw.marginTop  + cw.rowStep*7)
                .setSize(cw.rangeWidth,cw.controlHeight)
                .setRange(Defaults.MIN_VERTEXOS,Defaults.MAX_VERTEXOS)
                //.snapToTickMarks(true).setNumberOfTickMarks(6)
                .setValue(Defaults.MIN_VERTEXOS).setValueLabel(""+Defaults.MIN_VERTEXOS)
                .moveTo("particles")
        ;

        numVertexos = Defaults.MIN_VERTEXOS;

        // PARTICLE DISTRIBUTION
        slParticleDist = cw.cp5.addScrollableList("PARTICLE DISTRIBUTION")
                .setPosition(cw.marginLeft,cw.marginTop + cw.rowStep*5)
                .setWidth(cw.rangeWidth).setItemHeight(cw.controlHeight)
                .setBarHeight(cw.controlHeight)
                .addItems(distOptions)
                .setType(ScrollableList.DROPDOWN)//.setType(ScrollableList.LIST)
                .moveTo("particles")
        ;

        slParticleDist.setValue(0.0f); particleDist = 0;

        // X RAND
        sXRand = cw.cp5.addSlider("X RAND").setBroadcast(false)
                .setPosition(cw.marginLeft,cw.marginTop + cw.rowStep*29)
                .setSize(cw.rangeWidth,cw.controlHeight)
                .setRange(0,Defaults.RAND_VAL).setValue(0).setBroadcast(true)
                .moveTo("particles")
        ;
        xRand= 0.0f;

        // Y RAND
        sYRand = cw.cp5.addSlider("Y RAND").setBroadcast(false)
                .setPosition(cw.marginLeft,cw.marginTop + cw.rowStep*30)
                .setSize(cw.rangeWidth,cw.controlHeight).setHandleSize(20)
                .setRange(0,Defaults.RAND_VAL).setValue(0).setBroadcast(true)
                .moveTo("particles")
        ;
        yRand = 0.0f;

        // RED

        // RED MAP IN
        rRedIn = cw.cp5.addRange("RED IN").setBroadcast(false)
                .setPosition(cw.marginLeft + cw.rangeWidth + cw.colStep/4,cw.marginTop + cw.rowStep*1)
                .setSize(cw.rangeWidth,cw.controlHeight)
                .setRange(Defaults.MIN_COLOR_IN, Defaults.MAX_COLOR_IN)
                .setRangeValues(Defaults.MIN_COLOR_IN, Defaults.MAX_COLOR_IN).setBroadcast(true)
                .moveTo("particles")
        ;

        redIn = new RangFloat(Defaults.MIN_COLOR_IN, Defaults.MAX_COLOR_IN);

        // RED MAP OUT
        rRedOut = cw.cp5.addRange("RED OUT").setBroadcast(false)
                .setPosition(cw.marginLeft + cw.rangeWidth + cw.colStep/4,cw.marginTop + cw.rowStep*2)
                .setSize(cw.rangeWidth,cw.controlHeight)
                .setRange(Defaults.MIN_COLOR_OUT, Defaults.MAX_COLOR_OUT)
                .setRangeValues(Defaults.MIN_COLOR_OUT, Defaults.MAX_COLOR_OUT).setBroadcast(true)
                .moveTo("particles")
        ;

        redOut = new RangFloat(Defaults.MIN_COLOR_OUT, Defaults.MAX_COLOR_OUT);

        // GREEN

        // GREEN MAP IN
        rGreenIn = cw.cp5.addRange("GREEN IN").setBroadcast(false)
                .setPosition(cw.marginLeft + cw.rangeWidth + cw.colStep/4,cw.marginTop + cw.rowStep*4)
                .setSize(cw.rangeWidth,cw.controlHeight)
                .setRange(Defaults.MIN_COLOR_IN, Defaults.MAX_COLOR_IN)
                .setRangeValues(Defaults.MIN_COLOR_IN, Defaults.MAX_COLOR_IN).setBroadcast(true)
                .moveTo("particles")
        ;

        greenIn = new RangFloat(Defaults.MIN_COLOR_IN, Defaults.MAX_COLOR_IN);

        // GREEN MAP OUT
        rGreenOut = cw.cp5.addRange("GREEN OUT").setBroadcast(false)
                .setPosition(cw.marginLeft + cw.rangeWidth + cw.colStep/4,cw.marginTop + cw.rowStep*5)
                .setSize(cw.rangeWidth,cw.controlHeight)
                .setRange(Defaults.MIN_COLOR_OUT, Defaults.MAX_COLOR_OUT)
                .setRangeValues(Defaults.MIN_COLOR_OUT, Defaults.MAX_COLOR_OUT).setBroadcast(true)
                .moveTo("particles")
        ;

        greenOut = new RangFloat(Defaults.MIN_COLOR_OUT, Defaults.MAX_COLOR_OUT);

        // BLUE

        // BLUE MAP IN
        rBlueIn = cw.cp5.addRange("BLUE IN").setBroadcast(false)
                .setPosition(cw.marginLeft + cw.rangeWidth + cw.colStep/4,cw.marginTop + cw.rowStep*7)
                .setSize(cw.rangeWidth,cw.controlHeight)
                .setRange(Defaults.MIN_COLOR_IN, Defaults.MAX_COLOR_IN)
                .setRangeValues(Defaults.MIN_COLOR_IN, Defaults.MAX_COLOR_IN).setBroadcast(true)
                .moveTo("particles")
        ;

        blueIn = new RangFloat(Defaults.MIN_COLOR_IN, Defaults.MAX_COLOR_IN);

        // BLUE MAP OUT
        rBlueOut = cw.cp5.addRange("BLUE OUT").setBroadcast(false)
                .setPosition(cw.marginLeft + cw.rangeWidth + cw.colStep/4,cw.marginTop + cw.rowStep*8)
                .setSize(cw.rangeWidth,cw.controlHeight)
                .setRange(Defaults.MIN_COLOR_OUT, Defaults.MAX_COLOR_OUT)
                .setRangeValues(Defaults.MIN_COLOR_OUT, Defaults.MAX_COLOR_OUT).setBroadcast(true)
                .moveTo("particles")
        ;

        blueOut = new RangFloat(Defaults.MIN_COLOR_OUT, Defaults.MAX_COLOR_OUT);

        // OPAC

        // OPACITY MAP IN
        rOpacIn = cw.cp5.addRange("OPAC IN").setBroadcast(false)
                .setPosition(cw.marginLeft + cw.rangeWidth + cw.colStep/4,cw.marginTop + cw.rowStep*11)
                .setSize(cw.rangeWidth,cw.controlHeight)
                .setRange(Defaults.MIN_COLOR_IN, Defaults.MAX_COLOR_IN)
                .setRangeValues(Defaults.MIN_COLOR_IN, Defaults.MAX_COLOR_IN).setBroadcast(true)
                .moveTo("particles")
        ;

        opacIn = new RangFloat(Defaults.MIN_COLOR_IN, Defaults.MAX_COLOR_IN);

        // OPACITY MAP OUT
        rOpacOut = cw.cp5.addRange("OPAC OUT").setBroadcast(false)
                .setPosition(cw.marginLeft + cw.rangeWidth + cw.colStep/4,cw.marginTop + cw.rowStep*12)
                .setSize(cw.rangeWidth,cw.controlHeight)
                .setRange(Defaults.MIN_COLOR_OUT, Defaults.MAX_COLOR_OUT)
                .setRangeValues(Defaults.MIN_COLOR_OUT, Defaults.MAX_COLOR_OUT).setBroadcast(true)
                .moveTo("particles")
        ;

        opacOut = new RangFloat(Defaults.MIN_COLOR_OUT, Defaults.MAX_COLOR_OUT);



        // REF COLOR POINT
        s2dRefColor = cw.cp5.addSlider2D("REF COLOR")
                .setPosition(cw.marginLeft + cw.rangeWidth + cw.colStep/4,cw.marginTop + cw.rowStep*13)
                .setSize(cw.rangeWidth,3*cw.rangeWidth/4)
                .setArrayValue(new float[] {Defaults.sceneWidth/2, Defaults.sceneHeight/2})
                .setCursorX(50).setCursorY(50).setMinX(0).setMaxX(Defaults.sceneWidth).setMinY(0).setMaxY(Defaults.sceneHeight)
                .moveTo("particles")
        ;
        refColor = new PVector(Defaults.sceneWidth/2,Defaults.sceneHeight/2);

        // OPAC MAP MODE
        slMapOpac = cw.cp5.addScrollableList("MAP OPAC")
                .setPosition(cw.marginLeft + cw.rangeWidth + cw.colStep/4,cw.marginTop + cw.rowStep*10)//.setSize(cw.rangeWidth,cw.controlHeight)
                .setWidth(cw.rangeWidth).setItemHeight(cw.controlHeight).setBarHeight(cw.controlHeight)
                .addItems(mapOptions)
                //.setType(ScrollableList.LIST)
                .setType(ScrollableList.DROPDOWN)
                .moveTo("particles")
        ;

        slMapOpac.setValue(0.0f);  mapOpac = 0;

        // BLUE MAP MODE
        slMapBlue = cw.cp5.addScrollableList("MAP BLUE").setPosition(cw.marginLeft + cw.rangeWidth + cw.colStep/4,cw.marginTop + cw.rowStep*6)//.setSize(cw.rangeWidth,cw.controlHeight)
                .setWidth(cw.rangeWidth).setItemHeight(cw.controlHeight).setBarHeight(cw.controlHeight)
                .addItems(mapOptions)
                //.setType(ScrollableList.LIST)
                .setType(ScrollableList.DROPDOWN)
                .moveTo("particles")
        ;

        slMapBlue.setValue(0.0f); mapBlue = 0;

        // GREEN MAP MODE
        slMapGreen = cw.cp5.addScrollableList("MAP GREEN").setPosition(cw.marginLeft + cw.rangeWidth + cw.colStep/4,cw.marginTop + cw.rowStep*3)//.setSize(cw.rangeWidth,cw.controlHeight)
                .setWidth(cw.rangeWidth).setItemHeight(cw.controlHeight).setBarHeight(cw.controlHeight)
                .addItems(mapOptions)
                //.setType(ScrollableList.LIST)
                .setType(ScrollableList.DROPDOWN)
                .moveTo("particles")
        ;

        slMapGreen.setValue(0.0f); mapGreen = 0;

        // RED MAP MODE
        slMapRed = cw.cp5.addScrollableList("MAP RED").setPosition(cw.marginLeft + cw.rangeWidth + cw.colStep/4,cw.marginTop + cw.rowStep*0)//.setSize(cw.rangeWidth,cw.controlHeight)
                .setWidth(cw.rangeWidth).setItemHeight(cw.controlHeight).setBarHeight(cw.controlHeight)
                .addItems(mapOptions)
                //.setType(ScrollableList.LIST)
                .setType(ScrollableList.DROPDOWN)
                .moveTo("particles")
        ;

        slMapRed.setValue(0.0f); mapRed = 0;

        // BLACK & WHITE BUTTON
        bGreyscale = cw.cp5.addButton("GREY SCALE").setValue(0).setPosition(cw.marginLeft + cw.rangeWidth + cw.colStep/4, cw.marginTop+ cw.rowStep*9)
                .setSize(cw.rangeWidth/3 - 2,cw.controlHeight)
                .setSwitch(true).setOff().setLabel("COLOR")
                .moveTo("particles")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        greyScale = bGreyscale.isOn();
                    }
                })
        ;

        greyScale = false;

        // WIDTH

        // EQUAL WIDTH & HEIGHT BUTTON
        bEqualWidthAndHeight = cw.cp5.addButton("EQUAL W&H").setValue(0).setPosition(cw.marginLeft + cw.rangeWidth + cw.colStep/4, cw.marginTop+ cw.rowStep*21)
                .setSize(cw.rangeWidth/3 - 2,cw.controlHeight)
                .setSwitch(true).setOff().setLabel("DIFF W&H")
                .moveTo("particles")
                .addListener(new ControlListener() {
                    public void controlEvent(ControlEvent theEvent){
                        equalWidthAndHeight = bEqualWidthAndHeight.isOn();
                    }
                })
        ;

        equalWidthAndHeight = false;


        // FADE IN SIZE RANGE
        rFadeInSize = cw.cp5.addRange("FADE IN SIZE").setBroadcast(false).setPosition(cw.marginLeft + 4*cw.rangeWidth/3 + cw.colStep/4,cw.marginTop + cw.rowStep*21)
                .setSize(2*cw.rangeWidth/3,cw.controlHeight)
                .setRange(Defaults.MIN_OSC_DELAY, Defaults.MAX_OSC_DELAY).setRangeValues(Defaults.MIN_OSC_DELAY, Defaults.MAX_OSC_DELAY).setBroadcast(true)
                .moveTo("particles")
        ;

        fadeInSize = new RangFloat(Defaults.MIN_OSC_DELAY, Defaults.MAX_OSC_DELAY);

        // WIDTH MAP IN
        rWidthIn = cw.cp5.addRange("WIDTH IN").setBroadcast(false).setPosition(cw.marginLeft + cw.rangeWidth + cw.colStep/4,cw.marginTop + cw.rowStep*23).setSize(cw.rangeWidth,cw.controlHeight)
                .setRange(Defaults.MIN_SIZE_IN, Defaults.MAX_SIZE_IN).setRangeValues(Defaults.MIN_SIZE_IN, Defaults.MAX_SIZE_IN).setBroadcast(true)
                .moveTo("particles")
        ;

        widthIn = new RangFloat(Defaults.MIN_SIZE_IN, Defaults.MAX_SIZE_IN);

        // BLUE MAP OUT
        rWidthOut = cw.cp5.addRange("WIDTH OUT").setBroadcast(false).setPosition(cw.marginLeft + cw.rangeWidth + cw.colStep/4,cw.marginTop + cw.rowStep*24).setSize(cw.rangeWidth,cw.controlHeight)
                .setRange(Defaults.MIN_SIZE_OUT, Defaults.MAX_SIZE_OUT).setRangeValues(Defaults.MIN_SIZE_OUT, Defaults.MAX_SIZE_OUT).setBroadcast(true)
                .moveTo("particles")
        ;

        widthOut = new RangFloat(Defaults.MIN_SIZE_OUT, Defaults.MAX_SIZE_OUT);



        // HEIGHT

        // HEIGHT MAP IN
        rHeightIn = cw.cp5.addRange("HEIGHT IN").setBroadcast(false)
                .setPosition(cw.marginLeft + cw.rangeWidth + cw.colStep/4,cw.marginTop + cw.rowStep*26)
                .setSize(cw.rangeWidth,cw.controlHeight)
                .setRange(Defaults.MIN_SIZE_IN, Defaults.MAX_SIZE_IN)
                .setRangeValues(Defaults.MIN_SIZE_IN, Defaults.MAX_SIZE_IN).setBroadcast(true)
                .moveTo("particles")
        ;

        heightIn = new RangFloat(Defaults.MIN_SIZE_IN, Defaults.MAX_SIZE_IN);

        // HEIGHT MAP OUT
        rHeightOut = cw.cp5.addRange("HEIGHT OUT").setBroadcast(false)
                .setPosition(cw.marginLeft + cw.rangeWidth + cw.colStep/4,cw.marginTop + cw.rowStep*27)
                .setSize(cw.rangeWidth,cw.controlHeight)
                .setRange(Defaults.MIN_SIZE_OUT, Defaults.MAX_SIZE_OUT)
                .setRangeValues(Defaults.MIN_SIZE_OUT, Defaults.MAX_SIZE_OUT).setBroadcast(true)
                .moveTo("particles")
        ;

        heightOut = new RangFloat(Defaults.MIN_SIZE_OUT, Defaults.MAX_SIZE_OUT);

        // HEIGHT MAP MODE
        slMapHeight = cw.cp5.addScrollableList("MAP HEIGHT")
                .setPosition(cw.marginLeft + cw.rangeWidth + cw.colStep/4,cw.marginTop + cw.rowStep*25)//.setSize(cw.rangeWidth,cw.controlHeight)
                .setWidth(cw.rangeWidth).setItemHeight(cw.controlHeight).setBarHeight(cw.controlHeight)
                .addItems(mapOptions)
                //.setType(ScrollableList.LIST)
                .setType(ScrollableList.DROPDOWN)
                .moveTo("particles")
        ;

        slMapHeight.setValue(0.0f); mapHeight = 0;

        // WIDTH MAP MODE
        slMapWidth = cw.cp5.addScrollableList("MAP WIDTH")
                .setPosition(cw.marginLeft + cw.rangeWidth + cw.colStep/4,cw.marginTop + cw.rowStep*22)//.setSize(cw.rangeWidth,cw.controlHeight)
                .setWidth(cw.rangeWidth).setItemHeight(cw.controlHeight).setBarHeight(cw.controlHeight)
                .addItems(mapOptions)
                //.setType(ScrollableList.LIST)
                .setType(ScrollableList.DROPDOWN)
                .moveTo("particles")
        ;

        slMapWidth.setValue(0.0f); mapWidth = 0;




        // LOAD STYLE BUTTON
        bLoadStyle = cw.cp5.addButton("LOAD STYLE")
                .setPosition(cw.marginLeft + cw.rangeWidth + cw.colStep/4,cw.marginTop + cw.rowStep*28)
                .setSize(cw.rangeWidth/3 - 3 ,cw.controlHeight)
                .moveTo("particles")
        ;

        // Save STYLE BUTTON
        bSaveStyle = cw.cp5.addButton("SAVE STYLE").setPosition(cw.marginLeft + cw.rangeWidth + cw.colStep/4,cw.marginTop + cw.rowStep*29).setSize(cw.rangeWidth/3 - 3 ,cw.controlHeight)
                .moveTo("particles")
        ;

        // STYLE NAME
        tStyleName = cw.cp5.addTextfield("STYLE NAME")
                .setPosition(cw.marginLeft + 4*cw.rangeWidth/3  + cw.colStep/4,cw.marginTop + cw.rowStep*29)
                .setSize(2*cw.rangeWidth/3,cw.controlHeight)
                .setAutoClear(false) //.setFont(font)
                .moveTo("particles")
        ;

        Label label3 = tStyleName.getCaptionLabel();
        label3.setText("STYLE").align(ControlP5.RIGHT_OUTSIDE, CENTER).getStyle().setPaddingLeft(5);

        styleName = "DEFAULT_STYLE";

        // RANDOM STYLE BUTTON
        bRandStyle = cw.cp5.addButton("RANDOM STYLE")
                .setPosition(cw.marginLeft + cw.rangeWidth + cw.colStep/4,cw.marginTop + cw.rowStep*30)
                .setSize(cw.rangeWidth/3 -2 ,cw.controlHeight)
                .moveTo("particles")
        ;

        // IMPORT STYLES BUTTON
        bImportStyles = cw.cp5.addButton("IMPORT STYLES")
                .setPosition(cw.marginLeft + 4*cw.rangeWidth/3 + cw.colStep/4,cw.marginTop + cw.rowStep*30)
                .setSize(cw.rangeWidth/3 -2 ,cw.controlHeight)
                .moveTo("particles")
        ;

        // EXPORT STYLES BUTTON
        bExportStyles = cw.cp5.addButton("EXPORT STYLES")
                .setPosition(cw.marginLeft + 5*cw.rangeWidth/3 + cw.colStep/4,cw.marginTop + cw.rowStep*30)
                .setSize(cw.rangeWidth/3 -2 ,cw.controlHeight)
                .moveTo("particles")
        ;

        // PARTICLE STYLE
        int numStyle=0;
        String[] styleNames = new String[pStyles.size()];
        for(AttractedParticleStyle s : pStyles){
            styleNames[numStyle] = s.name;
            numStyle++;
        }
        List styles = Arrays.asList(styleNames);
        slParticleStyle = cw.cp5.addScrollableList("PARTICLE STYLE")
                .setPosition(cw.marginLeft + cw.rangeWidth + cw.colStep/4 + cw.rangeWidth/3,cw.marginTop + cw.rowStep*28)
                .setWidth(2*cw.rangeWidth/3).setItemHeight(cw.controlHeight).setBarHeight(cw.controlHeight)
                .addItems(styles)
                //.setType(ScrollableList.LIST)
                .setType(ScrollableList.DROPDOWN)
                .moveTo("particles")
        ;

        slParticleStyle.setValue(0.0f); particleStyle = 0;
    }

    public void resetControls(){
        rParticleStep.setRangeValues(Defaults.MIN_STEP, Defaults.MAX_STEP); particleStep = new RangFloat(Defaults.MIN_STEP, Defaults.MAX_STEP);
        rParticleDelay.setRangeValues(Defaults.MIN_OSC_DELAY, Defaults.MAX_OSC_DELAY); particleDelay = new RangFloat(Defaults.MIN_OSC_DELAY, Defaults.MAX_OSC_DELAY);
        sXRand.setValue(0); xRand= 0.0f; sYRand.setValue(0);yRand = 0.0f;
        rRedIn.setRangeValues(Defaults.MIN_COLOR_IN, Defaults.MAX_COLOR_IN); redIn = new RangFloat(Defaults.MIN_COLOR_IN, Defaults.MAX_COLOR_IN);
        rRedOut.setRangeValues(Defaults.MIN_COLOR_OUT, Defaults.MAX_COLOR_OUT); redOut = new RangFloat(Defaults.MIN_COLOR_OUT, Defaults.MAX_COLOR_OUT);
        rGreenIn.setRangeValues(Defaults.MIN_COLOR_IN, Defaults.MAX_COLOR_IN); greenIn = new RangFloat(Defaults.MIN_COLOR_IN, Defaults.MAX_COLOR_IN);
        rGreenOut.setRangeValues(Defaults.MIN_COLOR_OUT, Defaults.MAX_COLOR_OUT); greenOut = new RangFloat(Defaults.MIN_COLOR_OUT, Defaults.MAX_COLOR_OUT);
        rBlueIn.setRangeValues(Defaults.MIN_COLOR_IN, Defaults.MAX_COLOR_IN); blueIn = new RangFloat(Defaults.MIN_COLOR_IN, Defaults.MAX_COLOR_IN);
        rBlueOut.setRangeValues(Defaults.MIN_COLOR_OUT, Defaults.MAX_COLOR_OUT); blueOut = new RangFloat(Defaults.MIN_COLOR_OUT, Defaults.MAX_COLOR_OUT);
        rOpacIn.setRangeValues(Defaults.MIN_COLOR_IN, Defaults.MAX_COLOR_IN); opacIn = new RangFloat(Defaults.MIN_COLOR_IN, Defaults.MAX_COLOR_IN);
        rOpacOut.setRangeValues(Defaults.MIN_COLOR_OUT, Defaults.MAX_COLOR_OUT); opacOut = new RangFloat(Defaults.MIN_COLOR_OUT, Defaults.MAX_COLOR_OUT);
        s2dRefColor.setArrayValue(new float[] {Defaults.sceneWidth/2, Defaults.sceneHeight/2}); refColor = new PVector(Defaults.sceneWidth/2,Defaults.sceneHeight/2);
        slMapOpac.setValue(0.0f);  mapOpac = 0;
        slMapBlue.setValue(0.0f); mapBlue = 0;
        slMapGreen.setValue(0.0f); mapGreen = 0;
        slMapRed.setValue(0.0f); mapRed = 0;
        bGreyscale.setOff(); greyScale = false;
        bEqualWidthAndHeight.setOff(); equalWidthAndHeight = false;
        rWidthIn.setRangeValues(Defaults.MIN_SIZE_IN, Defaults.MAX_SIZE_IN); widthIn = new RangFloat(Defaults.MIN_SIZE_IN, Defaults.MAX_SIZE_IN);
        rWidthOut.setRangeValues(Defaults.MIN_SIZE_OUT, Defaults.MAX_SIZE_OUT); widthOut = new RangFloat(Defaults.MIN_SIZE_IN, Defaults.MAX_SIZE_IN);
        rHeightIn.setRangeValues(Defaults.MIN_SIZE_IN, Defaults.MAX_SIZE_IN); heightIn = new RangFloat(Defaults.MIN_SIZE_IN, Defaults.MAX_SIZE_IN);
        rHeightOut.setRangeValues(Defaults.MIN_SIZE_OUT, Defaults.MAX_SIZE_OUT); heightOut = new RangFloat(Defaults.MIN_SIZE_OUT, Defaults.MAX_SIZE_OUT);
        slMapHeight.setValue(0.0f); mapHeight = 0;
        slMapWidth.setValue(0.0f); mapWidth = 0;
        slParticleStyle.setValue(0.0f); particleStyle = 0;
        tStyleName.setValue(""); styleName = "DEFAULT STYLE";
    }

    void checkControlEvents(ControlEvent theControlEvent, ControlWindow cw){
        // PART STEP
        if(theControlEvent.isFrom("PART STEP")) {
            particleStep.setValues(rParticleStep.getLowValue(), rParticleStep.getHighValue());
            //println("PART STEP : "+particleStep);
        }
        // PART DELAY
        else if(theControlEvent.isFrom("PART DELAY")) {
            particleDelay.setValues(rParticleDelay.getLowValue(), rParticleDelay.getHighValue());
            //println("PART DELAY : "+particleDelay);
        }
        // FRAME WIDTH
        else if(theControlEvent.isFrom("FRAME WIDTH")) {
            frameWidth = sFrameWidth.getValue();
            sFrameWidth.setValueLabel(""+(int)frameWidth);
            println("FRAME WIDTH: "+frameWidth);
        }
        // NUM VERTEXOS (POLYGON DIST)
        else if(theControlEvent.isFrom("NUM VERTEXS")) {
            numVertexos = (int)sNumVertexos.getValue();
            sNumVertexos.setValueLabel(""+(int)numVertexos);
            println("NUM VERTEXOS: "+numVertexos);
        }
        // X RAND
        else if(theControlEvent.isFrom("X RAND")) {
            xRand = sXRand.getValue();
            println("X RAND: "+xRand);
        }
        // Y RAND
        else if(theControlEvent.isFrom("Y RAND")) {
            yRand = sYRand.getValue();
            println("Y RAND: "+yRand);
        }
        // PARTICLE DISTRIBUTION
        else if(theControlEvent.isFrom("PARTICLE DISTRIBUTION")) {
            particleDist = (int)slParticleDist.getValue();
            println("PARTICLE DISTRIBUTION: "+particleDist+" "+distOptions.get(particleDist));
            switch(particleDist){
                case 0: // RANDOM
                case 1: // FILL RECT
                case 2: // BORDER RECT
                case 4: // LINE
                    // Posar
                    cw.cp5.getController("TL CORNER").moveTo("particles");
                    cw.cp5.getController("BR CORNER").moveTo("particles");
                    cw.cp5.getController("CENTER TL").moveTo("particles");
                    cw.cp5.getController("CENTER BR").moveTo("particles");
                    cw.cp5.getController("COLS").moveTo("particles");
                    cw.cp5.getController("ROWS").moveTo("particles");
                    cw.cp5.getController("LOCK X").moveTo("particles");
                    cw.cp5.getController("LOCK X2").moveTo("particles");
                    cw.cp5.getController("LOCK Y").moveTo("particles");
                    cw.cp5.getController("LOCK Y2").moveTo("particles");
                    cw.cp5.getController("SAME X").moveTo("particles");
                    cw.cp5.getController("SAME Y").moveTo("particles");
                    //Llevar
                    cw.cp5.getController("CENTRE").moveTo("circle");
                    cw.cp5.getController("MIN RADIUS").moveTo("circle");
                    cw.cp5.getController("MAX RADIUS").moveTo("circle");
                    cw.cp5.getController("VAR RADIUS").moveTo("circle");
                    cw.cp5.getController("RADIUS STEP").moveTo("circle");
                    cw.cp5.getController("ANGLE").moveTo("circle");
                    cw.cp5.getController("ANGLE").setPosition(cw.marginLeft,cw.marginTop + cw.rowStep*23);
                    cw.cp5.getController("ANGLE STEP").moveTo("circle");
                    cw.cp5.getController("ANGLE STEP").setPosition(cw.marginLeft,cw.marginTop + cw.rowStep*24);
                    cw.cp5.getController("RANDOM ANGLE").moveTo("circle");
                    cw.cp5.getController("MIN DIST").moveTo("circle");
                    cw.cp5.getController("MIN DIST").setPosition(cw.marginLeft,cw.marginTop + cw.rowStep*26);
                    cw.cp5.getController("FULL").moveTo("circle");
                    cw.cp5.getController("HALF B").moveTo("circle");
                    cw.cp5.getController("HALF T").moveTo("circle");
                    cw.cp5.getController("HALF R").moveTo("circle");
                    cw.cp5.getController("HALF L").moveTo("circle");
                    cw.cp5.getController("ORIGIN").moveTo("circle");
                    cw.cp5.getController("SINGLE").moveTo("circle");
                    break;
                case 3: // FILL CIRCLE
                case 5: // POLYGON
                case 6: // SPIRAL
                    // Llevar
                    cw.cp5.getController("TL CORNER").moveTo("grid");
                    cw.cp5.getController("BR CORNER").moveTo("grid");
                    cw.cp5.getController("CENTER TL").moveTo("grid");
                    cw.cp5.getController("CENTER BR").moveTo("grid");
                    cw.cp5.getController("COLS").moveTo("grid");
                    cw.cp5.getController("ROWS").moveTo("grid");
                    cw.cp5.getController("LOCK X2").moveTo("grid");
                    cw.cp5.getController("LOCK Y2").moveTo("grid");
                    cw.cp5.getController("SAME X").moveTo("grid");
                    cw.cp5.getController("SAME Y").moveTo("grid");

                    //Posar
                    cw.cp5.getController("CENTRE").moveTo("particles");
                    cw.cp5.getController("LOCK X").moveTo("particles");
                    cw.cp5.getController("LOCK Y").moveTo("particles");
                    cw.cp5.getController("MIN RADIUS").moveTo("particles");
                    cw.cp5.getController("MAX RADIUS").moveTo("particles");
                    cw.cp5.getController("VAR RADIUS").moveTo("particles");
                    cw.cp5.getController("RADIUS STEP").moveTo("particles");
                    cw.cp5.getController("ANGLE").moveTo("particles");
                    cw.cp5.getController("ANGLE").setPosition(cw.marginLeft,cw.marginTop + cw.rowStep*23);
                    cw.cp5.getController("ANGLE STEP").moveTo("particles");
                    cw.cp5.getController("ANGLE STEP").setPosition(cw.marginLeft,cw.marginTop + cw.rowStep*24);
                    cw.cp5.getController("RANDOM ANGLE").moveTo("particles");
                    cw.cp5.getController("RANDOM ANGLE").setPosition(cw.marginLeft,cw.marginTop + cw.rowStep*25);
                    cw.cp5.getController("MIN DIST").moveTo("particles");
                    cw.cp5.getController("MIN DIST").setPosition(cw.marginLeft,cw.marginTop + cw.rowStep*26);
                    cw.cp5.getController("FULL").moveTo("particles");
                    cw.cp5.getController("HALF B").moveTo("particles");
                    cw.cp5.getController("HALF T").moveTo("particles");
                    cw.cp5.getController("HALF R").moveTo("particles");
                    cw.cp5.getController("HALF L").moveTo("particles");
                    cw.cp5.getController("ORIGIN").moveTo("particles");
                    cw.cp5.getController("SINGLE").moveTo("particles");
                    break;

                case 7: // WAVE
                    // Llevar
                    cw.cp5.getController("MIN RADIUS").moveTo("circle");
                    cw.cp5.getController("MAX RADIUS").moveTo("circle");
                    cw.cp5.getController("VAR RADIUS").moveTo("circle");
                    cw.cp5.getController("RADIUS STEP").moveTo("circle");
                    cw.cp5.getController("COLS").moveTo("grid");
                    cw.cp5.getController("ROWS").moveTo("grid");
                    cw.cp5.getController("MIN DIST").moveTo("circle");
                    cw.cp5.getController("FULL").moveTo("circle");
                    cw.cp5.getController("HALF B").moveTo("circle");
                    cw.cp5.getController("HALF T").moveTo("circle");
                    cw.cp5.getController("HALF R").moveTo("circle");
                    cw.cp5.getController("HALF L").moveTo("circle");
                    cw.cp5.getController("ORIGIN").moveTo("circle");
                    cw.cp5.getController("SINGLE").moveTo("circle");
                    //Posar
                    cw.cp5.getController("AMPLITUDE").moveTo("particles");
                    cw.cp5.getController("X STEP").moveTo("particles");
                    cw.cp5.getController("AMP ASC").moveTo("particles");
                    cw.cp5.getController("LOCK X").moveTo("particles");
                    cw.cp5.getController("LOCK Y").moveTo("particles");
                    cw.cp5.getController("LOCK X2").moveTo("particles");
                    cw.cp5.getController("LOCK Y2").moveTo("particles");
                    cw.cp5.getController("ANGLE").moveTo("particles");
                    cw.cp5.getController("ANGLE").setPosition(cw.marginLeft,cw.marginTop + cw.rowStep*29);
                    cw.cp5.getController("ANGLE STEP").moveTo("particles");
                    cw.cp5.getController("ANGLE STEP").setPosition(cw.marginLeft,cw.marginTop + cw.rowStep*30);
                    cw.cp5.getController("TL CORNER").moveTo("particles");
                    cw.cp5.getController("BR CORNER").moveTo("particles");
                    cw.cp5.getController("CENTER TL").moveTo("particles");
                    cw.cp5.getController("CENTER BR").moveTo("particles");
                    cw.cp5.getController("X VAR").moveTo("particles");
                    cw.cp5.getController("Y VAR").moveTo("particles");
                    break;
                default :
            }
            //cw.cp5.getController("PARTICLE DISTRIBUTION").moveTo("particles");
        }
    }

    void checkColorControlEvents(ControlEvent theControlEvent, ControlWindow cw){
        // RED IN
        if(theControlEvent.isFrom("RED IN")) {
            redIn.setValues(rRedIn.getLowValue(), rRedIn.getHighValue());
            println("RED IN : "+redIn);
        }
        // RED OUT
        else if(theControlEvent.isFrom("RED OUT")) {
            redOut.setValues(rRedOut.getLowValue(), rRedOut.getHighValue());
            println("RED OUT : "+redOut);
        }
        // GREEN IN
        else if(theControlEvent.isFrom("GREEN IN")) {
            greenIn.setValues(rGreenIn.getLowValue(), rGreenIn.getHighValue());
            println("GREEN IN : "+greenIn);
        }
        // GREEN OUT
        else if(theControlEvent.isFrom("GREEN OUT")) {
            greenOut.setValues(rGreenOut.getLowValue(), rGreenOut.getHighValue());
            println("GREEN OUT : "+greenOut);
        }
        // BLUE IN
        else if(theControlEvent.isFrom("BLUE IN")) {
            blueIn.setValues(rBlueIn.getLowValue(), rBlueIn.getHighValue());
            println("BLUE IN : "+blueIn);
        }
        // BLUE OUT
        else if(theControlEvent.isFrom("BLUE OUT")) {
            blueOut.setValues(rBlueOut.getLowValue(), rBlueOut.getHighValue());
            println("BLUE OUT : "+blueOut);
        }
        // OPAC IN
        else if(theControlEvent.isFrom("OPAC IN")) {
            opacIn.setValues(rOpacIn.getLowValue(), rOpacIn.getHighValue());
            //println("OPAC IN : "+opacIn);
        }
        // OPAC OUT
        else if(theControlEvent.isFrom("OPAC OUT")) {
            opacOut.setValues(rOpacOut.getLowValue(), rOpacOut.getHighValue());
            println("OPAC OUT : "+opacOut);
        }
        // FADE IN SIZE
        else if(theControlEvent.isFrom("FADE IN SIZE")) {
            fadeInSize.setValues(rFadeInSize.getLowValue(), rFadeInSize.getHighValue());
            println("FADE IN SIZE : "+fadeInSize);
        }
        // WIDTH IN
        else if(theControlEvent.isFrom("WIDTH IN")) {
            widthIn.setValues(rWidthIn.getLowValue(), rWidthIn.getHighValue());
            println("WIDTH IN : "+widthIn);
        }
        // WIDTH OUT
        else if(theControlEvent.isFrom("WIDTH OUT")) {
            widthOut.setValues(rWidthOut.getLowValue(), rWidthOut.getHighValue());
            println("WIDTH OUT : "+widthOut);
        }
        // HEIGHT IN
        else if(theControlEvent.isFrom("WIDTH IN")) {
            heightIn.setValues(rHeightIn.getLowValue(), rHeightIn.getHighValue());
            println("HEIGHT IN : "+heightIn);
        }
        // HEIGHT OUT
        else if(theControlEvent.isFrom("WIDTH OUT")) {
            heightOut.setValues(rHeightOut.getLowValue(), rHeightOut.getHighValue());
            println("HEIGHT OUT : "+heightOut);
        }
        // GREYSCALE BUTTON
        else if(theControlEvent.isFrom("GREY SCALE")) {
            if(greyScale) bGreyscale.setLabel("GREY SCALE");
            else bGreyscale.setLabel("COLOR");
            //println("Grey Scale: "+greyScale);
        }
        // EQUAL WIDTH & HEIGHT
        else if(theControlEvent.isFrom("EQUAL W&H")) {
            if(equalWidthAndHeight) bEqualWidthAndHeight.setLabel("EQUAL W&H");
            else bEqualWidthAndHeight.setLabel("DIFF W&H");
            println("Equal Width & Height: "+equalWidthAndHeight);
        }
        // REF COLOR SLIDER 2D
        else if(theControlEvent.isFrom("REF COLOR") || theControlEvent.isFrom("REF")){
            float ox = s2dRefColor.getArrayValue()[0];
            float oy = s2dRefColor.getArrayValue()[1];
            refColor = new PVector(ox, oy);
            println("REF COLOR // x: "+refColor.x+", y: "+refColor.y);
        }
        // MAP RED MODE
        else if(theControlEvent.isFrom("MAP RED")) {
            mapRed = (int)slMapRed.getValue();
            println("Map Red: "+mapRed+" "+mapOptions.get(mapRed));
            if(mapRed>=5 && mapRed<=8){ // RED MAPPED TO ORIENTATION X o Y
                rRedIn.setBroadcast(false).setRange(Defaults.MIN_ORIENT, Defaults.MAX_ORIENT)
                        .setRangeValues(Defaults.MIN_ORIENT, Defaults.MAX_ORIENT).setBroadcast(true);
                redIn = new RangFloat(Defaults.MIN_ORIENT, Defaults.MAX_ORIENT);
            }
            else if(mapRed>=9 && mapRed<=10){ // RED MAPPED TO POS X o POS X INV
                rRedIn.setBroadcast(false).setRange(Defaults.MIN_WIDTH, Defaults.MAX_WIDTH)
                        .setRangeValues(Defaults.MIN_WIDTH, Defaults.MAX_WIDTH).setBroadcast(true);
                redIn = new RangFloat(Defaults.MIN_WIDTH, Defaults.MAX_WIDTH);
            }
            else if(mapRed>=11 && mapRed<=12){ // RED MAPPED TO POS Y o POS Y INV
                rRedIn.setBroadcast(false).setRange(Defaults.MIN_HEIGHT, Defaults.MAX_HEIGHT)
                        .setRangeValues(Defaults.MIN_HEIGHT, Defaults.MAX_HEIGHT).setBroadcast(true);
                redIn = new RangFloat(Defaults.MIN_HEIGHT, Defaults.MAX_HEIGHT);
            }
            else {
                rRedIn.setBroadcast(false).setRange(Defaults.MIN_COLOR_IN, Defaults.MAX_COLOR_IN)
                        .setRangeValues(Defaults.MIN_COLOR_IN, Defaults.MAX_COLOR_IN).setBroadcast(true);
                redIn = new RangFloat(Defaults.MIN_COLOR_IN, Defaults.MAX_COLOR_IN);
            }
        }
        // MAP GREEN MODE
        else if(theControlEvent.isFrom("MAP GREEN")) {
            mapGreen = (int)slMapGreen.getValue();
            println("Map Green: "+mapGreen+" "+mapOptions.get(mapGreen));
            if(mapGreen>=5 && mapGreen<=8){ // GREEN MAPPED TO ORIENTATION X o Y
                rGreenIn.setBroadcast(false).setRange(Defaults.MIN_ORIENT, Defaults.MAX_ORIENT)
                        .setRangeValues(Defaults.MIN_ORIENT, Defaults.MAX_ORIENT).setBroadcast(true);
                greenIn = new RangFloat(Defaults.MIN_ORIENT, Defaults.MAX_ORIENT);
            }
            else if(mapGreen>=9 && mapGreen<=10){ // GREEN MAPPED TO POS X o POS X INV
                rGreenIn.setBroadcast(false).setRange(Defaults.MIN_WIDTH, Defaults.MAX_WIDTH)
                        .setRangeValues(Defaults.MIN_WIDTH, Defaults.MAX_WIDTH).setBroadcast(true);
                greenIn = new RangFloat(Defaults.MIN_WIDTH, Defaults.MAX_WIDTH);
            }
            else if(mapGreen>=11 && mapGreen<=12){ // GREEN MAPPED TO POS Y o POS Y INV
                rGreenIn.setBroadcast(false).setRange(Defaults.MIN_HEIGHT, Defaults.MAX_HEIGHT)
                        .setRangeValues(Defaults.MIN_HEIGHT, Defaults.MAX_HEIGHT).setBroadcast(true);
                greenIn = new RangFloat(Defaults.MIN_HEIGHT, Defaults.MAX_HEIGHT);
            }
            else {
                rGreenIn.setBroadcast(false).setRange(Defaults.MIN_COLOR_IN, Defaults.MAX_COLOR_IN)
                        .setRangeValues(Defaults.MIN_COLOR_IN, Defaults.MAX_COLOR_IN).setBroadcast(true);
                greenIn = new RangFloat(Defaults.MIN_COLOR_IN, Defaults.MAX_COLOR_IN);
            }
        }
        // MAP BLUE MODE
        else if(theControlEvent.isFrom("MAP BLUE")) {
            mapBlue = (int)slMapBlue.getValue();
            println("Map Blue: "+mapBlue+" "+mapOptions.get(mapBlue));
            if(mapBlue>=5 && mapBlue<=8){ // BLUE MAPPED TO ORIENTATION X o Y
                rBlueIn.setBroadcast(false).setRange(Defaults.MIN_ORIENT, Defaults.MAX_ORIENT)
                        .setRangeValues(Defaults.MIN_ORIENT, Defaults.MAX_ORIENT).setBroadcast(true);
                blueIn = new RangFloat(Defaults.MIN_ORIENT, Defaults.MAX_ORIENT);
            }
            else if(mapBlue>=9 && mapBlue<=10){ // BLUE MAPPED TO POS X o POS X INV
                rBlueIn.setBroadcast(false).setRange(Defaults.MIN_WIDTH, Defaults.MAX_WIDTH)
                        .setRangeValues(Defaults.MIN_WIDTH, Defaults.MAX_WIDTH).setBroadcast(true);
                blueIn = new RangFloat(Defaults.MIN_WIDTH, Defaults.MAX_WIDTH);
            }
            else if(mapBlue>=11 && mapBlue<=12){ // BLUE MAPPED TO POS Y o POS Y INV
                rBlueIn.setBroadcast(false).setRange(Defaults.MIN_HEIGHT, Defaults.MAX_HEIGHT)
                        .setRangeValues(Defaults.MIN_HEIGHT, Defaults.MAX_HEIGHT).setBroadcast(true);
                blueIn = new RangFloat(Defaults.MIN_HEIGHT, Defaults.MAX_HEIGHT);
            }
            else {
                rBlueIn.setBroadcast(false).setRange(Defaults.MIN_COLOR_IN, Defaults.MAX_COLOR_IN)
                        .setRangeValues(Defaults.MIN_COLOR_IN, Defaults.MAX_COLOR_IN).setBroadcast(true);
                blueIn = new RangFloat(Defaults.MIN_COLOR_IN, Defaults.MAX_COLOR_IN);
            }
        }
        // MAP OPAC MODE
        else if(theControlEvent.isFrom("MAP OPAC")) {
            mapOpac = (int)slMapOpac.getValue();
            println("Map Opac: "+mapOpac+" "+mapOptions.get(mapOpac));
            if(mapOpac>=5 && mapOpac<=8){ // OPAC MAPPED TO ORIENTATION X o Y
                rOpacIn.setBroadcast(false).setRange(Defaults.MIN_ORIENT, Defaults.MAX_ORIENT)
                        .setRangeValues(Defaults.MIN_ORIENT, Defaults.MAX_ORIENT).setBroadcast(true);
                opacIn = new RangFloat(Defaults.MIN_ORIENT, Defaults.MAX_ORIENT);
            }
            else if(mapOpac>=9 && mapOpac<=10){ // OPAC MAPPED TO POS X o POS X INV
                rOpacIn.setBroadcast(false).setRange(Defaults.MIN_WIDTH, Defaults.MAX_WIDTH)
                        .setRangeValues(Defaults.MIN_WIDTH, Defaults.MAX_WIDTH).setBroadcast(true);
                opacIn = new RangFloat(Defaults.MIN_WIDTH, Defaults.MAX_WIDTH);
            }
            else if(mapOpac>=11 && mapOpac<=12){ // OPAC MAPPED TO POS Y o POS Y INV
                rOpacIn.setBroadcast(false).setRange(Defaults.MIN_HEIGHT, Defaults.MAX_HEIGHT)
                        .setRangeValues(Defaults.MIN_HEIGHT, Defaults.MAX_HEIGHT).setBroadcast(true);
                opacIn = new RangFloat(Defaults.MIN_HEIGHT, Defaults.MAX_HEIGHT);
            }
            else {
                rOpacIn.setBroadcast(false).setRange(Defaults.MIN_COLOR_IN, Defaults.MAX_COLOR_IN)
                        .setRangeValues(Defaults.MIN_COLOR_IN, Defaults.MAX_COLOR_IN).setBroadcast(true);
                opacIn = new RangFloat(Defaults.MIN_COLOR_IN, Defaults.MAX_COLOR_IN);
            }
        }
        // MAP WIDTH MODE
        else if(theControlEvent.isFrom("MAP WIDTH")) {
            mapWidth = (int)slMapWidth.getValue();
            println("Map Width: "+mapWidth+" "+mapOptions.get(mapWidth));
            if(mapWidth>=5 && mapWidth<=8){ // WIDTH MAPPED TO ORIENTATION X o Y
                rWidthIn.setBroadcast(false).setRange(Defaults.MIN_ORIENT, Defaults.MAX_ORIENT)
                        .setRangeValues(Defaults.MIN_ORIENT, Defaults.MAX_ORIENT).setBroadcast(true);
                widthIn = new RangFloat(Defaults.MIN_ORIENT, Defaults.MAX_ORIENT);
            }
            else if(mapWidth>=9 && mapWidth<=10){ // WIDTH MAPPED TO POS X o POS X INV
                rWidthIn.setBroadcast(false).setRange(Defaults.MIN_WIDTH, Defaults.MAX_WIDTH)
                        .setRangeValues(Defaults.MIN_WIDTH, Defaults.MAX_WIDTH).setBroadcast(true);
                widthIn = new RangFloat(Defaults.MIN_WIDTH, Defaults.MAX_WIDTH);
            }
            else if(mapWidth>=11 && mapWidth<=12){ // WIDTH MAPPED TO POS Y o POS Y INV
                rWidthIn.setBroadcast(false).setRange(Defaults.MIN_HEIGHT, Defaults.MAX_HEIGHT)
                        .setRangeValues(Defaults.MIN_HEIGHT, Defaults.MAX_HEIGHT).setBroadcast(true);
                widthIn = new RangFloat(Defaults.MIN_HEIGHT, Defaults.MAX_HEIGHT);
            }
            else {
                rWidthIn.setBroadcast(false).setRange(Defaults.MIN_COLOR_IN, Defaults.MAX_COLOR_IN)
                        .setRangeValues(Defaults.MIN_COLOR_IN, Defaults.MAX_COLOR_IN).setBroadcast(true);
                widthIn = new RangFloat(Defaults.MIN_COLOR_IN, Defaults.MAX_COLOR_IN);
            }
        }
        // MAP HEIGHT MODE
        else if(theControlEvent.isFrom("MAP HEIGHT")) {
            mapHeight = (int)slMapHeight.getValue();
            println("Map Height: "+mapHeight+" "+mapOptions.get(mapHeight));
            if(mapHeight>=5 && mapHeight<=8){ // HEIGHT MAPPED TO ORIENTATION X o Y
                rHeightIn.setBroadcast(false).setRange(Defaults.MIN_ORIENT, Defaults.MAX_ORIENT)
                        .setRangeValues(Defaults.MIN_ORIENT, Defaults.MAX_ORIENT).setBroadcast(true);
                heightIn = new RangFloat(Defaults.MIN_ORIENT, Defaults.MAX_ORIENT);
            }
            else if(mapHeight>=9 && mapHeight<=10){ // HEIGHT MAPPED TO POS X o POS X INV
                rHeightIn.setBroadcast(false).setRange(Defaults.MIN_WIDTH, Defaults.MAX_WIDTH)
                        .setRangeValues(Defaults.MIN_WIDTH, Defaults.MAX_WIDTH).setBroadcast(true);
                heightIn = new RangFloat(Defaults.MIN_WIDTH, Defaults.MAX_WIDTH);
            }
            else if(mapHeight>=11 && mapHeight<=12){ // HEIGHT MAPPED TO POS Y o POS Y INV
                rHeightIn.setBroadcast(false).setRange(Defaults.MIN_HEIGHT, Defaults.MAX_HEIGHT)
                        .setRangeValues(Defaults.MIN_HEIGHT, Defaults.MAX_HEIGHT).setBroadcast(true);
                heightIn = new RangFloat(Defaults.MIN_HEIGHT, Defaults.MAX_HEIGHT);
            }
            else {
                rHeightIn.setBroadcast(false).setRange(Defaults.MIN_COLOR_IN, Defaults.MAX_COLOR_IN)
                        .setRangeValues(Defaults.MIN_COLOR_IN, Defaults.MAX_COLOR_IN).setBroadcast(true);
                heightIn = new RangFloat(Defaults.MIN_COLOR_IN, Defaults.MAX_COLOR_IN);
            }
        }

    }

    public void checkStyleControlEvents(ControlEvent theControlEvent, ControlWindow cw){
        // PARTICLE STYLE
        if(theControlEvent.isFrom("PARTICLE STYLE")) {
            particleStyle = (int)slParticleStyle.getValue();
            println("PARTICLE STYLE: "+particleStyle+" "+pStyles.get(particleStyle).name);
        }
        // LOAD STYLE BUTTON
        else if(theControlEvent.isFrom("LOAD STYLE")) {
            println("LOADING STYLE");
            //AttractedParticleStyle styleNow = pStyles.get(particleStyle);
            //styleNow.setGUI();  // Falta carregar l'estil
            //println("LOAD STYLE: "+particleStyle+" "+styleNow.name);
        }
        // SAVE STYLE BUTTON
        else if(theControlEvent.isFrom("SAVE STYLE")) {
            println("SAVING STYLE");
            AttractedParticleStyle styleNow = AttractedParticleStyle.createAttractedParticleStyleFromGUI(cw);
            pStyles.add(styleNow);
            particleStyle = pStyles.size()-1;
            slParticleStyle.addItem(styleName, particleStyle);
            slParticleStyle.setValue(particleStyle);
            println("SAVED STYLE: "+styleNow.name);
        }
        else if(theControlEvent.isFrom("STYLE NAME")) {
            styleName = tStyleName.getStringValue();
            println("STYLE NAME : "+styleName);
        }
        // RANDOM STYLE BUTTON
        else if(theControlEvent.isFrom("RANDOM STYLE")) {
            println("RANDOMIZING STYLE");
            AttractedParticleStyle styleNow = new AttractedParticleStyle();
            //styleNow.randomStyle();
            //styleNow.setGUI();  // Falta actualizar GUI amb dades estil
            println("RANDOM STYLE: "+styleNow.name);
        }
        // EXPORT STYLES BUTTON
        else if(theControlEvent.isFrom("EXPORT STYLES")) {
            println("EXPORTING STYLES.");
            exportStyles();
            println("EXPORT STYLES.");
        }
        // IMPORT STYLES BUTTON
        else if(theControlEvent.isFrom("IMPORT STYLES")) {
            println("LOADING STYLES FILE!!! ");
            this.cw.selectInput("Select Styles File:","selectStylesFileImport", file, this);
            println("IMPORTED STYLES FROM FILE.");
        }
    }

    public void selectStylesFileImport(File selection){
        if(selection==null){
            pathStylesFile=Defaults.DEFAULT_STYLE_FILE_PATH;
        }
        else {
            pathStylesFile = selection.getAbsolutePath();
            // Remove styles form scrollableList
            for(AttractedParticleStyle aps : pStyles){
                String nameAPS = aps.name;
                slParticleStyle.removeItem(nameAPS);
            }
            // Load styles from file
            importStylesFromFile(pathStylesFile);
            println("LOADED STYLES FROM FILE: "+pathStylesFile);
        }
    }

    void importStylesFromFile(String fileName){
        pStyles = new ArrayList<AttractedParticleStyle>();
        ArrayList<String> lines = new ArrayList<String>();
        try {
            FileInputStream fis = new FileInputStream(fileName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader reader = new BufferedReader(isr);
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        for (int i = 0 ; i < lines.size(); i++) {
            println(lines.get(i));
            String[] pieces = split(lines.get(i), TAB);
            AttractedParticleStyle newStyle = new AttractedParticleStyle();
            newStyle = newStyle.getDataFromLine(pieces);
            pStyles.add(newStyle);
            slParticleStyle.addItem(newStyle.name, i);
        }
        slParticleStyle.setValue(0);
    }
}
