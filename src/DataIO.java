import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;import java.util.List;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

//import com.sun.xml.internal.txw2.Document;

import java.io.*;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class DataIO {
	
	//The folder containing the raw XML files
	// !!!made a fake file to do testing!!!
	private static String folderWithRaw = "C:/Users/CSC/Documents/ResusciAnneWirelessSkillReporter/HighScore/ClassroomScore";
	
	//This folder value MUST BE CHANGED, for testing only
	private static String schedulingFolder = "C:\\\\Users\\\\CSC\\\\Desktop\\\\QRS Data.csv";
	// change this to any valid folder on my own laptop to test to see 
	// colton sent a new testing file so put it in the pakcage to test it 
	
	//Contains the values of interest for the PrimaryEvalResults pane.
	private static ArrayList<Integer> primaryResults = new ArrayList<>();
	
	//Contains the values of interest from the second round of CPR
	private static ArrayList<Integer> secondaryResults = new ArrayList<>();	

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
    
    public static String scheduleNextSession() {
    	
    	////////////////////////////////////////////////
    	//Start by determining if the file exists; create
    	
    	//one if its doesn't.
    	////////////////////////////////////////////////
    	
    	//State the name of the expected file/the file we will be creating
    	String filename = schedulingFolder + "\\" + Integer.toString(getCurrentCode()) + "_scheduling_data.csv";
    	
    	//Determining if a scheduling file already exists for this participant in schedulingFolder
		//Create file object at folder containing scheduling data
    	File dir = new File (schedulingFolder);
    	File[] matchingFiles = dir.listFiles(new FilenameFilter() {
    		public boolean accept(File dir, String name) {
    			return name.equalsIgnoreCase(Integer.toString(getCurrentCode()) + "_scheduling_data.csv");
    		}
    	});
    	
    	//Initialize file
    	File schedule;

    	//If there are no matching file, create a new scheduling file
    	if (matchingFiles.length == 0) {
    		
    		String firstRow = "Session Number,Date Completed,Time Completed,Pre-Test Result,Post-Test Result," + 
    							"Months Until Next Session";
    		
    		//Create the new file and initialize a BufferedWriter
    		schedule = new File(filename);
    		BufferedWriter writer;
			try {
	    		schedule.createNewFile();
				writer = new BufferedWriter(new FileWriter(schedule));
				writer.append(firstRow);
	    		writer.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
    	} else {
    		schedule = matchingFiles[0];
    	}
    	
    	////////////////////////////////////////////////
    	//Read the last two lines of the file to determine
    	//Session Number, Months Until Next Session and
    	//Next Session Date.
    	////////////////////////////////////////////////
		
		String lastLine = "";
		
		try {
			FileInputStream in = new FileInputStream(filename);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			//Create a temporary string to hold the line being read
			String tmp;
	
			//Start by reading the first line and not saving it, since this line
			//will always contain the column headers
			br.readLine();
			
			while ((tmp = br.readLine()) != null)
			{
//				secondLastLine = lastLine;
			    lastLine = tmp;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// most important step here!!
		String[] nextRow = getNextScheduleRow(lastLine);
		
		//Convert the String array to a single string
		String nextRowString = "";
		for (int i = 0;i<nextRow.length;i++) {
			nextRowString = nextRowString + nextRow[i] + ",";
		}
		
		//Write the new row string to the file
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(schedule,true));
			writer.newLine();
			writer.append(nextRowString);
    		writer.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
        return nextRow[(nextRow.length)-1];
        // what exactly is the result of this string?
        // is this just giving the result or is it also saving the data to the CSV?
    }
    
    public static String[] getNextScheduleRow(String lastLine) {
    	
    	String[] newSchedule = new String[6];
    	Arrays.fill(newSchedule, "");
    	
    	//Get the pass/fail status of each round of CPR completed
    	boolean round1Passed = evaluationOnePassed();
    	
    	//If the first round of CPR wasn't passed then we need the result from the
    	//second round of CPR
    	//**NOTE we must verify that the round2 result exists BEFORE determining
    	//		 whether it exists or not.
    	boolean round2Passed = false;
    	if (!round1Passed) {
    		round2Passed = evaluationTwoPassed();
    	}
    	
    	//Convert the round1 and round2 CPR results to strings for our ArrayList
    	String round1StrResult, round2StrResult;
    	if (round1Passed) {
    		round1StrResult = "PASS";
    		round2StrResult = "N/A";
    	} else {
    		round1StrResult = "FAIL";
    		if (round2Passed) {
    			round2StrResult = "PASS";
    		} else {
    			round2StrResult = "FAIL";
    		}
    	}
    	
    	//Add the round1StrResult and round2StrResult to the ArrayList
    	newSchedule[3] = round1StrResult;
    	newSchedule[4] = round2StrResult;
    	
    	//Generate the date and time stamps needed for a row in the schedule
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		String date = df.format(new Date());
		SimpleDateFormat tf = new SimpleDateFormat("HHmm");
		String time = tf.format(new Date());
		
		//Add the date and time to the corresponding positions in the ArrayList
    	newSchedule[1] = date;
    	newSchedule[2] = time;		
    	
		if (lastLine == "") {
			//Case 1: The file is empty because it was just created.
			//Start by adding the session number, which in this case is 1
			newSchedule[0] = "1";
			
			if (round1Passed) {
				//If the participant passed round 1, schedule in 3 months
				newSchedule[5] = "3";		
				
			} else {
				//In this case, regardless of the result of the second test, the participant must
				//come back in one month (either same interval, ie 1 month, or explicitly 1 month)
				newSchedule[5] = "1";		
			}
			
		} else {
			//Case 2: The file is not empty, and we have the most recent test result
			//Start by converting it to an ArrayList
			ArrayList<String> lastLineList = new ArrayList<String>(Arrays.asList(lastLine.split("\\s*,\\s*")));
			
			//To set the session date, get the first element in LastLineList and add 1
			newSchedule[0] = Integer.toString(Integer.parseInt(lastLineList.get(0))+1);
			
			if (round1Passed) {
				//If the participant passed round 1, take the previous line's "months until next session"
				//and increment appropriately
				int previousInterval = Integer.parseInt(lastLineList.get(5));
				int newInterval;
				
				if (previousInterval == 1)
					newInterval = 3;
				else if (previousInterval == 3)
					newInterval = 6;
				else if (previousInterval == 6)
					newInterval = 12;
				else if (previousInterval == 12)
					newInterval = previousInterval;
				else
					newInterval = previousInterval; //In case there's some unknown interval, double it.
				
				//Add the new interval to the schedule document and determine the nextSessionDate
				newSchedule[5] = Integer.toString(newInterval);		
			} else {
				//If the participant didn't pass round 1, then their return interval is determined
				//by their performance on the second round of CPR
				if (round2Passed) {
					//If the participant passes their second round after failing their first, then
					//they return at the same interval as their most recent session
					int newInterval = Integer.parseInt(lastLineList.get(5));
					newSchedule[5] = Integer.toString(newInterval);					
				} else {
					//If the participant didn't pass either round of CPR, then they must return in
					//1 month. We must also determine whether they failed both rounds of CPR
					//during their previous session
					
					if (lastLineList.get(3).equals("FAIL") && lastLineList.get(4).equals("FAIL")) {
						//In this case the participant has failed both rounds of CPR for 2 sessions
						//in a row
						newSchedule[5] = "1";								
						System.out.println("Please see trainer for further information.");
					} else {
						//In this case the participant failed both rounds this session, but passed
						//at least one round last session. They must come back in 1 month.
						newSchedule[5] = "1";		
					}//end else
				}//end else
			}//end else
		}//end else
		
		return newSchedule;
	}
    
    //******ONLY FOR TESTING
    public static void setTestData(boolean pass, int cprRound) {
    	
    	if (pass) {
    		
    		int compressionRate = 110;
    		int compressionDepth = 5;
    		int correctCompressions = 90;
    		
    		if (cprRound == 1) {
	    		primaryResults.add(compressionRate);
	    		primaryResults.add(compressionDepth);
	    		primaryResults.add(correctCompressions);	    		  			
    		} 
    		else if (cprRound == 2) {
	    		secondaryResults.add(compressionRate);
	    		secondaryResults.add(compressionDepth); 
	    		secondaryResults.add(correctCompressions);	    		  			  			
    		}
    	} else {
    		
    		int compressionRate = 200;
    		int compressionDepth = 2;
    		int correctCompressions = 20;
    		
    		if (cprRound == 1) {
	    		primaryResults.add(compressionRate);
	    		primaryResults.add(compressionDepth);
	    		primaryResults.add(correctCompressions);	    		  			
    		} 
    		else if (cprRound == 2) {
	    		secondaryResults.add(compressionRate);
	    		secondaryResults.add(compressionDepth);
	    		secondaryResults.add(correctCompressions);	    		  			  			
    		}
    		
    	}
    		
    }
    
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
    
    //getPrimaryResults(): This method returns the Integer ArrayList containing the
    //primary results of interest, as assigned in the saveCPRData method. Note that they are
	//in the following order: 1)mean rate  
	//						  2)mean depth  
	//						  3)percent of compressions with adequate recoil 
    public static ArrayList<Integer> getPrimaryResults() {
    	return primaryResults;
    }    
    

    //evaluationOnePassed(): This method returns a boolean indicating whether the
    //first round of CPR meets the test's standards.
    //It checks three things:
    //1. Mean rate of CPR - having a rate 100-120
    //2. Mean depth of CPR - a depth 5-6cm
    //3. Percent of compressions with adequate recoil - target of >80% of their compressions having full recoil 
    public static boolean evaluationOnePassed() {
    	
    	//Verify that the mean rate is between 100 and 120 bpm
    	if (primaryResults.get(0) < 100 || primaryResults.get(0) > 120)
    		return false;
    	
    	//Verify that the mean depth is between 5 and 6 cm
    	if (primaryResults.get(1) < 5 || primaryResults.get(1) > 6 )
    		return false;
    	
    	//Verify that the % of adequate recoil is greater than 80
    	if (primaryResults.get(2) <= 80)
    		return false;
    	
    	//otherwise this evaluation passed
    	return true;
    }
    
    //evaluationTwoPassed(): This method returns a boolean indicating whether the
    //second round of CPR meets the test's standards.
    //It checks three things:
    //1. Mean rate of CPR - having a rate 100-120
    //2. Mean depth of CPR - a depth 5-6cm
    //3. Percent of compressions with adequate recoil - target of >80% of their compressions having full recoil 
    public static boolean evaluationTwoPassed() {
    	
    	//Verify that the mean rate is between 100 and 120 bpm
    	if (secondaryResults.get(0) < 100 || secondaryResults.get(0) > 120)
    		return false;
    	
    	//Verify that the mean depth is between 5 and 6 cm
    	if (secondaryResults.get(1) < 5 || secondaryResults.get(1) > 6 )
    		return false;
    	
    	//Verify that the % of adequate recoil is greater than 80
    	if (secondaryResults.get(2) <= 80)
    		return false;
    	
    	//otherwise this evaluation passed
    	return true;
    }
    

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
    	primaryResults.clear();
    	primaryResults.clear();
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
    		//Get the current date and format into string
    		Date dateFormatted = new Date();
    		DateFormat df = new SimpleDateFormat("ddMMyyyy");
    		String dayMonth = df.format(dateFormatted);
    		
    		//Create file object at folder containing all raw XML results
    		//**PATH MAY NEED TO BE CHANGED DEPENDING ON DRIVE/USERNAMES
	    	File f = new File (folderWithRaw);
	    	File[] matchingFiles = f.listFiles(new FilenameFilter() {
	    		public boolean accept(File dir, String name) {
	    			return name.startsWith(Integer.toString(getCurrentCode()) + "_" + Integer.toString(testNum)+ "_" + dayMonth)
	    					&& name.endsWith(".xml");
	    		}
	    	});
	    	
	    	//If there are multiple matching files then an error has occured
	    	if (matchingFiles.length == 0) {
	    		DataIO.resetData();
	        	VistaNavigator.loadVista(VistaNavigator.SaveErrorVista);
	    	}
	    	
	    	//Create Element object out of XML file for easier parsing.
	    	File saveFile = matchingFiles[0];    	
	    	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	    	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	    	org.w3c.dom.Document doc = dBuilder.parse(saveFile);
	    	doc.getDocumentElement().normalize();
	    	Node result = doc.getFirstChild();
	    	Element eElement = (Element) result;
	    	
	    	//Extract values of interest
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
	    	
	    	//Add all the parsed values to the overall results ArrayList dataRow
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
	    	
	    	//If this is the first test, we assign the values of primaryResults
	    	//in the following order: 1)mean rate  
	    	//						  2)mean depth  
	    	//						  3)percent of compressions with adequate recoil 
	    	if (testNum == 1) {
	    		primaryResults.add(compressionMeanRate);
	    		primaryResults.add(compressionMeanDepth);
	    		primaryResults.add(correctlyReleasedCompressions);	    		
	    	}
	    	else if (testNum == 2) {
	    		secondaryResults.add(compressionMeanRate);
	    		secondaryResults.add(compressionMeanDepth);
	    		secondaryResults.add(correctlyReleasedCompressions);	    		
	    	}
	    	
	    	
    	} catch (IOException ae) {
			// TODO Auto-generated catch block
			ae.printStackTrace();    		
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