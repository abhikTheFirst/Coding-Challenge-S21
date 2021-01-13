import ca.ualberta.stothard.cgview.*;

import java.awt.*;
import java.io.*;
import java.io.IOException;
import java.util.*;

public class CgviewTest1 implements CgviewConstants{
    public static void main(String[] args){
        int length = 24156;
        Cgview cgview = new Cgview(length);

        cgview.setWidth(1600);
        cgview.setHeight(1600);
        cgview.setBackboneRadius(140.0f);
        cgview.setTitle("Tomato Curly Stunt Virus");
        cgview.setLabelPlacementQuality(5);
        cgview.setShowWarning(false);
        cgview.setLabelLineLength(1.0d);
        cgview.setLabelLineThickness(1.0f);
        cgview.setUseInnerLabels(INNER_LABELS_SHOW);
        cgview.setMoveInnerLabelsToOuter(true);
        cgview.setMinimumFeatureLength(1.0d);

        Legend legend = new Legend(cgview);
        legend.setPosition(LEGEND_UPPER_CENTER);
        LegendItem legendItem = new LegendItem(legend);
        legendItem.setLabel("Coding Challenge S21");
        legendItem.setFont(new Font("SansSerif", Font.BOLD + Font.ITALIC, 22));

        FeatureSlot directSlot0 = new FeatureSlot(cgview, DIRECT_STRAND);
        //FeatureSlot reverseSlot0 = new FeatureSlot(cgview, REVERSE_STRAND);

        //Features to add to the FeatureSlots
        Feature feature0 = new Feature(directSlot0, "G");
        feature0.setColor(Color.blue);

        Feature feature1 = new Feature(directSlot0, "A");
        feature1.setColor(Color.red);

        Feature feature2 = new Feature(directSlot0, "T");
        feature2.setColor(Color.green);

        Feature feature3 = new Feature(directSlot0, "C");
        feature3.setColor(Color.pink);

        File f1 = new File("/Users/abhikkumar/Documents/GitHub/Coding-Challenge-S21/Genome.gb");
        String gsequence = null, line;
        try{
            FileInputStream inputStream = new FileInputStream(f1);
            Scanner scanner = new Scanner(inputStream);
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                gsequence = gsequence + line + "\n";
            }
            gsequence = gsequence.substring(gsequence.indexOf("ORIGIN") + 6, gsequence.indexOf("//"));
            //System.out.println(gsequence);
        }
        catch(IOException e){
            e.printStackTrace();
        }

        for(int i = 0; i < gsequence.length() - 1; i++){
            String base = gsequence.substring(i, i + 1);
            int j = (int)(Math.random() * 24000) + 1;
            if(base.equals("g")){
                FeatureRange featureRange0 = new FeatureRange (feature0,j, j + 1);
            }else if(base.equals("a")){
                FeatureRange featureRange1 = new FeatureRange (feature1,j, j + 1);
            }else if(base.equals("t")){
                FeatureRange featureRange2 = new FeatureRange (feature2, j, j + 1);
            } else if(base.equals("c")){
                FeatureRange featureRange3 = new FeatureRange (feature3, j, j + 1);
            }
        }
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
