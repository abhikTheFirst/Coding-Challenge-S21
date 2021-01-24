import ca.ualberta.stothard.cgview.*;

import java.awt.*;
import java.io.*;
import java.io.IOException;
import java.util.*;

public class CgviewTest1 implements CgviewConstants{
    //This method takes in the base pairing, where it starts and stops and the feature, which is basically the strand
    //and then outputs a specific color for each base(g-red,a-blue,c-green,t-orange) and labels the strand at the first
    //strand
    public static void labelStrand(String gsequence, int start, int stop, Feature feature) {
        for(int i = start - 1; i < stop - 1; i++){
            String base = gsequence.substring(i, i + 1);
            FeatureRange featureRange = new FeatureRange (feature,i , i);
            if(i != start - 1){
                featureRange.setShowLabel(CgviewConstants.LABEL_NONE);
            }
            if(base.equals("g")){
                featureRange.setColor(Color.red);
            } else if(base.equals("a")){
                featureRange.setColor(Color.blue);
            }else if(base.equals("c")){
                featureRange.setColor(Color.green);
            }else if(base.equals("t")){
                featureRange.setColor(Color.orange);
            }
        }
    }

    public static void main(String[] args) {
        //Initialization data for the cgview object, such as how many bases around the backbone(2766), width and height of
        //the png, title at the center of the map, warning labels, line thickness/length, numeric ticks around the circular genomic map,
        //using inner labels, whether they can move outside to avoid covering each other, and width of the strand
        int length = 2766;
        Cgview cgview = new Cgview(length);
        cgview.setWidth(750);
        cgview.setHeight(750);
        cgview.setBackboneRadius(140.0f);
        cgview.setTitle("Tomato Curly Stunt Virus");
        cgview.setLabelPlacementQuality(5);
        cgview.setShowWarning(false);
        cgview.setLabelLineLength(20.0d);
        cgview.setLabelLineThickness(1.0f);
        cgview.setUseInnerLabels(INNER_LABELS_SHOW);
        cgview.setMoveInnerLabelsToOuter(true);
        cgview.setMinimumFeatureLength(1.0d);

        //Sets the title of the circular genomic map
        Legend legend = new Legend(cgview);
        legend.setPosition(LEGEND_UPPER_CENTER);
        LegendItem legendItem = new LegendItem(legend);
        legendItem.setLabel("Coding Challenge S21  g-red, a-blue, c-green, t-orange");
        legendItem.setFont(new Font("SansSerif", Font.BOLD + Font.ITALIC, 22));

        //Slots for the features/strands to go on, which is basically the level in the circular genomic map
        //Level 1 is outermost, level 2 is middle and level 3 is innermost
        FeatureSlot level1 = new FeatureSlot(cgview, DIRECT_STRAND);
        FeatureSlot level2 = new FeatureSlot(cgview, REVERSE_STRAND);
        FeatureSlot level3 = new FeatureSlot(cgview, 3);

        //Features to add to the FeatureSlots
        Feature C3 = new Feature(level1, "C3");
        Feature C2 = new Feature(level2, "C2");
        Feature C1 = new Feature(level3, "C1");
        Feature V2 = new Feature(level2, "V2");
        Feature V1 = new Feature(level3, "V1");

        //Where the Genome.gb file is so we can extract the bases(so we can color each one in
        File f1 = new File("/Users/abhikkumar/Documents/GitHub/Coding-Challenge-S21/Genome.gb");
        String gsequence = null, line;
        try {
            FileInputStream inputStream = new FileInputStream(f1);
            Scanner scanner = new Scanner(inputStream);
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                gsequence = gsequence + line + "\n";//Gets the data from ORIGIN down to // at the end of the file
            }
            gsequence = gsequence.substring(gsequence.indexOf("ORIGIN") + 6, gsequence.indexOf("//"));//Removes ORIGIN and //
            gsequence = gsequence.replaceAll("\\d", "");//Removes labeling digits for the strands
            gsequence = gsequence.replaceAll("\n", "");//Removes any newline indicators
            gsequence = gsequence.replaceAll("\\s", "");//Removes spaces
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        //Fills each strand starting the beginning base and ending base in the coding sequence.
        labelStrand(gsequence, 139,480,V2);
        labelStrand(gsequence,299, 1075,V1);
        labelStrand(gsequence,1072,1476,C3);
        labelStrand(gsequence,1217,1624,C2);
        labelStrand(gsequence,1533,2612,C1);

        try {
            //create a PNG file
            File outputFile = new File("cgm.png");
            CgviewIO.writeToPNGFile(cgview, "cgm.png");
            // /Users/abhikkumar/Desktop
        }
        catch (IOException e) {
            e.printStackTrace(System.err);
            System.exit(1);
        }
    }
}
