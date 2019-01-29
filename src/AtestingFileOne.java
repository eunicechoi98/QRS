import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class AtestingFileOne {
	
	// starting point
	private static String csvPath = "C:\\Users\\Colton\\Desktop\\QRSTestFolder\\QRS_Data.csv";	
	
    public static void main(String[] args) {
//    	test(21, true, false, 1, 2);	
    	
    	sort_data();
    }
    
	
    public static void test_1(int code, boolean boolOne, boolean boolTwo, int numOne, int numTwo) {
    	DataIO.setCurrentCode(code);
    	DataIO.setTestData(boolOne, numOne);
    	DataIO.setTestData(boolTwo, numTwo);
    	String result = DataIO.scheduleNextSession();
    	System.out.print(result);
    } 
     
  
    public static void sort_data() {

        File file= new File(csvPath);

        // this gives you a 2-dimensional array of strings
        List<List<String>> lines = new ArrayList<>();
        Scanner inputStream;

        try{
            inputStream = new Scanner(file);

            while(inputStream.hasNext()){
                String line= inputStream.next();
                String[] values = line.split(",");
                // this adds the currently parsed line to the 2-dimensional string array
                lines.add(Arrays.asList(values));
            }

            inputStream.close();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for(List<String> line: lines) {
            System.out.println(line.toString());
        }
        
        //Remove the line of headers for sorting
        List<String> headers = lines.remove(0);
        
        Collections.sort(lines, new Comparator<List<String>>() {    
            @Override
            public int compare(List<String> o1, List<String> o2) {
                return Integer.valueOf(o1.get(0)).compareTo(Integer.valueOf(o2.get(0)));
            }               
        });
        
        lines.add(0, headers);
        
        for(List<String> line: lines) {
            System.out.println(line.toString());
        } 
        
        //Convert lines to a single csv string
        String lines_str = "";
        for(List<String> line: lines) {
        	String collect = line.stream().collect(Collectors.joining(",")) + "\n";
        	lines_str += collect;
        } 
        System.out.println(lines_str);
        
        //False parameter indicates that we wish to overwrite the original file
        try {
        	BufferedWriter outStream= new BufferedWriter(new FileWriter(csvPath, false));
			outStream.write(lines_str);
	        outStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}