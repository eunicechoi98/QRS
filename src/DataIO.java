import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.sun.xml.internal.txw2.Document;

import java.io.*;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class DataIO {

	private static String csvPath = "C:\\Users\\CSC\\Desktop\\QRS Data.csv";
    private static BufferedReader br = null;
    private static BufferedWriter bw = null;
    private static String line = "";
    private static String cvsSplitBy = ",";
    
    private static int[] controlCodes = new int[20];
    private static int[] trainingCodes = new int[40];
    private static int currentCode = -1;
    private static ArrayList<Integer> dataRow = new ArrayList<>();
    
    private static boolean forgotCode = false;
    private static int forgotCodeGroup = -1; //0 for control, 1 for training, -1 if code not forgotten
    private static String forgotCodeName = "";
    
    public static boolean correctCode() {
    	if (forgotCode == false) {
        	for (int val : controlCodes) {
        		if (val == currentCode)
        			return true;
        	}
        	return false;
    	}
    	else 
    		return forgotCodeGroup == 0;
    }   
    
    
    /*
     * New function needed to show the users their results
     * 1. mean rate  2. mean depth  3. percent of compressions with adequate recoil 
     * 
     */
    
    
    
    
    /* New function created to check if they passed the initial CPR evaluation:
     * Has to check 3 things:
     * 1. Mean rate of CPR - target of >80% of their compressions having a rate 100-120
     * 2. Mean depth of CPR - a depth 5-6cm
     * 3. Percent of compressions with adequate recoil -  having full recoil 
     */
    public static boolean evaluationOnePassed(int testNum) {
    	int totalCompressions;
    	int correctlyReleasedCompressions;
    	int compressionMeanDepth;
    	int[] depthData = new int[3]; //[too shallow,adequate depth,too deep]
    	int[] rateData = new int[3]; //[too slow,adequate speed,too fast]
    	int compressionMeanRate;
    	int corrrectHandPosition;
    	int compressionScore;
    	int date;
    	int time;
    	
    	try {
    		Date dateFormatted = new Date();
    		DateFormat df = new SimpleDateFormat("ddMMyyyy");
    		String dayMonth = df.format(dateFormatted);
    		//System.out.println(dayMonth);
	    	File f = new File ("C:/Users/CSC/Documents/ResusciAnneWirelessSkillReporter/HighScore/ClassroomScore");
	    	File[] matchingFiles = f.listFiles(new FilenameFilter() {
	    		public boolean accept(File dir, String name) {
	    			return name.startsWith(Integer.toString(getCurrentCode()) + "_" + Integer.toString(testNum)+ "_" + dayMonth)
	    					&& name.endsWith(".xml");
	    		}
	    	});
	    	if (matchingFiles.length == 0) {
	    		DataIO.resetData();
	        	VistaNavigator.loadVista(VistaNavigator.SaveErrorVista);
	    	}
	    	File saveFile = matchingFiles[0];    	
	    	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	    	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	    	org.w3c.dom.Document doc = dBuilder.parse(saveFile);
	    	doc.getDocumentElement().normalize();
	    	
	    	Node result = doc.getFirstChild();
	    	Element eElement = (Element) result;
	    	
	    	
	    	compressionMeanDepth = Integer.parseInt(eElement.getElementsByTagName("CompressionMeanDepth").item(0).getTextContent());
	    	compressionMeanRate = Integer.parseInt(eElement.getElementsByTagName("CompressionMeanRate").item(0).getTextContent());
	   
	    	
	    	
    	
    }
    
    public static boolean inControlGroup() {
    	if (forgotCode == false) {
        	for (int val : controlCodes) {
        		if (val == currentCode)
        			return true;
        	}
        	return false;
    	}
    	else 
    		return forgotCodeGroup == 0;
    }
    
    
    /*
    public static boolean inTrainingGroup() {
    	if (forgotCode == false) {
    		for (int val : trainingCodes) {
        		if (val == currentCode)
        			return true;
        	}
        	return false;
    	}
    	else 
    		return forgotCodeGroup == 1;
    }
    */
    
    public static void setCurrentCode(int code) {
    	currentCode = code;
    }
    
    public static int getCurrentCode() {
    	return currentCode;
    }
    
    public static void forgotCode(String name, int group) {
    	forgotCode = true;
    	forgotCodeGroup = group;
    	forgotCodeName = name;
    	//This version of QRS Project saves the currentCode as -2 if the individual has forgotten their code,
    	//and instead saved their data under their name in the .csv
    	for (int i=0;i<name.length();i++) {
    		currentCode += Character.getNumericValue(name.charAt(i));
    	}
    }

    
    public static void savePreQuestions(int exposure, int comfort) {
		dataRow.add(exposure);
		dataRow.add(comfort);
    }
    
    public static void savePostQuestion(int comfort2) {
		dataRow.add(comfort2);
    }
    
    public static void resetData() {
    	currentCode = -1;
    	dataRow.clear();
    	forgotCode = false;
    	forgotCodeGroup = -1;
    	forgotCodeName = "";
    }
    
    public static void exportToCSV() {
    	try {
    		ArrayList<String> stringDataRow = getStringArray(dataRow);
    		String newRow;
    		if (forgotCode == true) {
    			newRow = forgotCodeName + "," + stringDataRow.stream().collect(Collectors.joining(",")) + "\n";
    		}
    		else {
    			newRow = Integer.toString(currentCode) + "," + stringDataRow.stream().collect(Collectors.joining(",")) + "\n";
    		}
    		System.out.println(newRow);
            FileWriter pw = new FileWriter(csvPath, true);
            pw.append(newRow);
            pw.close();
            
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }    	
    }
    
    public static void saveCPRData(int testNum) {
    	/*FIX THIS IMPORT TECHNIQUE BEFORE REPEATING TESTING
    	 * - May be prone to reimporting the same files that appear first under a participant's
    	 * study code and test #.
    	 */
    	int totalCompressions;
    	int correctlyReleasedCompressions;
    	int compressionMeanDepth;
    	int[] depthData = new int[3]; //[too shallow,adequate depth,too deep]
    	int[] rateData = new int[3]; //[too slow,adequate speed,too fast]
    	int compressionMeanRate;
    	int corrrectHandPosition;
    	int compressionScore;
    	int date;
    	int time;
    	
    	try {
    		Date dateFormatted = new Date();
    		DateFormat df = new SimpleDateFormat("ddMMyyyy");
    		String dayMonth = df.format(dateFormatted);
    		//System.out.println(dayMonth);
	    	File f = new File ("C:/Users/CSC/Documents/ResusciAnneWirelessSkillReporter/HighScore/ClassroomScore");
	    	File[] matchingFiles = f.listFiles(new FilenameFilter() {
	    		public boolean accept(File dir, String name) {
	    			return name.startsWith(Integer.toString(getCurrentCode()) + "_" + Integer.toString(testNum)+ "_" + dayMonth)
	    					&& name.endsWith(".xml");
	    		}
	    	});
	    	if (matchingFiles.length == 0) {
	    		DataIO.resetData();
	        	VistaNavigator.loadVista(VistaNavigator.SaveErrorVista);
	    	}
	    	File saveFile = matchingFiles[0];    	
	    	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	    	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	    	org.w3c.dom.Document doc = dBuilder.parse(saveFile);
	    	doc.getDocumentElement().normalize();
	    	
	    	Node result = doc.getFirstChild();
	    	Element eElement = (Element) result;
	    	
	    	totalCompressions = Integer.parseInt(eElement.getElementsByTagName("TotalCompressions").item(0).getTextContent());
	    	correctlyReleasedCompressions = Integer.parseInt(eElement.getElementsByTagName("CorrectlyReleasedCompression").item(0).getTextContent());
	    	compressionMeanDepth = Integer.parseInt(eElement.getElementsByTagName("CompressionMeanDepth").item(0).getTextContent());
	    	compressionMeanRate = Integer.parseInt(eElement.getElementsByTagName("CompressionMeanRate").item(0).getTextContent());
	    	corrrectHandPosition = Integer.parseInt(eElement.getElementsByTagName("CorrectHandPosition").item(0).getTextContent());
	    	compressionScore = Integer.parseInt(eElement.getElementsByTagName("CompressionScore").item(0).getTextContent());
	    	
	    	Node depthList = eElement.getElementsByTagName("CompressionDepthStats").item(0);
	    	Element eDepthList = (Element) depthList;
	    	depthData[0] = Integer.parseInt(eDepthList.getElementsByTagName("TooShallow").item(0).getTextContent());
	    	depthData[1] = Integer.parseInt(eDepthList.getElementsByTagName("AdequateDepth").item(0).getTextContent());
	    	depthData[2] = Integer.parseInt(eDepthList.getElementsByTagName("TooDeep").item(0).getTextContent());
	    	
	    	Node rateList = eElement.getElementsByTagName("CompressionRateStats").item(0);
	    	Element eRateList = (Element) rateList;
	    	rateData[0] = Integer.parseInt(eRateList.getElementsByTagName("TooSlow").item(0).getTextContent());
	    	rateData[1] = Integer.parseInt(eRateList.getElementsByTagName("AdequateRate").item(0).getTextContent());
	    	rateData[2] = Integer.parseInt(eRateList.getElementsByTagName("TooFast").item(0).getTextContent());
	    	
	    	String tempDate = eElement.getElementsByTagName("Date").item(0).getTextContent();
	    	String tempTime = eElement.getElementsByTagName("Time").item(0).getTextContent();
	    	
	    	date = Integer.parseInt(tempDate.replaceAll("\\p{P}", ""));
	    	time = Integer.parseInt(tempTime.replaceAll("\\p{P}", ""));
	    	
	    	dataRow.add(totalCompressions);
	    	dataRow.add(correctlyReleasedCompressions);
	    	//dataRow.add(-1); Already in percentage form
	    	dataRow.add(compressionMeanDepth);
	    	dataRow.add(depthData[0]);
	    	dataRow.add(-1);
	    	dataRow.add(depthData[1]);
	    	dataRow.add(-1);
	    	dataRow.add(depthData[2]);
	    	dataRow.add(-1);
	    	dataRow.add(rateData[0]);
	    	dataRow.add(-1);
	    	dataRow.add(rateData[1]);
	    	dataRow.add(-1);
	    	dataRow.add(rateData[2]);
	    	dataRow.add(-1);
	    	dataRow.add(compressionMeanRate);
	    	dataRow.add(corrrectHandPosition);
	    	dataRow.add(compressionScore);
	    	dataRow.add(date);
	    	dataRow.add(time);
	    	
    	} catch (IOException ae) {
    		
    	} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
        
    public static void loadCSV() {
    	
    	for (int lineCount = 1;lineCount<=60;lineCount++) {
    		if (lineCount <= 40)
            	trainingCodes[lineCount-1] = lineCount;
            else {
            	controlCodes[lineCount-41] = lineCount;        	
    	}
    	
    	
    	
    	/*
    	 * This method will be useful when the study codes don't directly correspond to the row numbers,
    	 * and the study codes need to be loaded from the .csv file.
    	 * 
    	 * For the time being, the .csv file will be blank other than the headers, and the study
    	 * codes will be appended to the start of the appended data row.
    	 * 
    	try {
    		int lineCount = 1;
            br = new BufferedReader(new FileReader(csvPath));
            br.readLine();
            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] temp = line.split(cvsSplitBy);
                /*
                 * This is where we make some distinction between control
                 * and training data.
                 * In this version:
                 *      Rows 1-20: Group A - Training sessions every 2 months
                 *     Rows 21-40: Group B - Training sessions every 4 months
                 *     Rows 41-60: Group C - Control.
                 * 
                 * For now the first 10 data will be control, the second 10 training. 
                 
                if (lineCount <= 40)
                	trainingCodes[lineCount-1] = Integer.parseInt(temp[0]);
                	//System.out.println(temp[0]);
                else {
                	controlCodes[lineCount-41] = Integer.parseInt(temp[0]);        	
                	//System.out.println(temp[0]);
                }
                //System.out.println("Country [code= " + temp[2] + " , name=" + country[5] + "]");
                lineCount++;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        */
    	}
    }
    	
	private static ArrayList<String> getStringArray(ArrayList<Integer> intArray) {
        ArrayList<String> result = new ArrayList<String>();
        for(Integer intValue : intArray) {
            try {
                //Convert String to Integer, and store it into integer array list.
                result.add(Integer.toString(intValue));
            } catch(NumberFormatException nfe) {
               //System.out.println("Could not parse " + nfe);
            } 
        }       
        return result;
    	
    }
}
