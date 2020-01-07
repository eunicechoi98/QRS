import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;import java.util.List;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.*;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
 
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class DataIO {
	
	
	//The folder containing the raw XML files
//	private static String folderWithRaw = "C:/Users/CPR QRS/Documents/ResusciAnneWirelessSkillReporter/HighScore/ClassroomScore";
	private static String folderWithRaw = "C:\\Users\\colto\\OneDrive\\Desktop\\QRS_Test_Folder\\Raw_Folder";
	
	//This folder value MUST BE CHANGED, for testing only
//	private static String schedulingFolder = "C:\\Users\\cbarr\\Desktop\\QRS_Test_Folder";
	private static String schedulingFile = "C:\\Users\\colto\\OneDrive\\Desktop\\QRS_Test_Folder\\Scheduling_File.csv";
	
	//Data file path and password
	private static String dataFilePath = "C:\\Users\\colto\\OneDrive\\Desktop\\QRS_Test_Folder\\TestWorkBook.xlsx";
	private static String dataPassword = "testpassword";

	//Schedule file path and password
	private static String scheduleFilePath = "C:\\Users\\colto\\OneDrive\\Desktop\\QRS_Test_Folder\\Scheduling_File.xlsx";
	private static String schedulePassword = "testpassword";
	
	//Username file path and password
	private static String usernameFilePath = "C:\\Users\\colto\\OneDrive\\Desktop\\QRS_Test_Folder\\Usernames.xlsx";
	private static String usernamePassword = "testpassword";

	//Contains the results of the two survey questions
	private static ArrayList<String> surveyData = new ArrayList<>();
	
	//Contains the values of interest for the PrimaryEvalResults pane.
	private static ArrayList<Integer> primaryResults = new ArrayList<>();
    private static ArrayList<String> primaryData = new ArrayList<>();
	
	//Contains the values of interest from the second round of CPR
	private static ArrayList<Integer> secondaryResults = new ArrayList<>();	
    private static ArrayList<String> secondaryData = new ArrayList<>();

	//Main directory of the user data in csv
	private static String csvPath = "C:\\Users\\colto\\OneDrive\\Desktop\\QRS_Test_Folder\\QRSTesting.csv";
    private static BufferedWriter bw = null;
    
    private static String username = "";
    private static String anonymousCode = "";
    
    public static boolean validateUsername(String inputUsername) {
    	
        try {
        	//Create the input stream for the xlsx file
        	InputStream inp = new FileInputStream(usernameFilePath);
        	//Open the workbook and get the first sheet
            Workbook workbook = WorkbookFactory.create(inp, usernamePassword);
            Sheet firstSheet = workbook.getSheetAt(0);
            
            String tempUsername;
            
            DataFormatter dataFormatter = new DataFormatter();
            
            System.out.println("inputUsername: " + inputUsername);
            
            //Iterate through all rows of the sheet
            for (Row row: firstSheet) {
            	tempUsername = dataFormatter.formatCellValue(row.getCell(0));
            	System.out.println("current temp: " + tempUsername);
            	if (tempUsername.equals(inputUsername)) {
            		//In this case, the input username is found in the Username spreadsheet
            		System.out.println("Username found!");
            		setUsername(tempUsername);
            		setAnonCode(dataFormatter.formatCellValue(row.getCell(1)));
                    workbook.close();
                    inp.close();
                    return true;
            	}
            }
            
            System.out.println("Code was invalid");
            
            workbook.close();
            inp.close();
        } catch (EncryptedDocumentException | IOException
                ex) {
            ex.printStackTrace();
        } 	
    	
    	return false;
    }
    
    public static String scheduleNextSession() {
		
		String studyCodeLastLine = "";
		int latestSessionNumber = -1;
		
        try {
        	//Create the input stream for the xlsx file
        	InputStream inp = new FileInputStream(scheduleFilePath);
        	//Open the workbook and get the first sheet
            Workbook workbook = WorkbookFactory.create(inp, schedulePassword);
            Sheet firstSheet = workbook.getSheetAt(0);
            
            DataFormatter dataFormatter = new DataFormatter();
            
            String tempUsername;
            int tempSessionNum;
            Row row;
            
            
            //Iterate through all rows of the sheet
            for (int i = 1; i <= firstSheet.getLastRowNum(); i++) {
            	
            	row = firstSheet.getRow(i);
            	
            	tempUsername = dataFormatter.formatCellValue(row.getCell(0));
            	tempSessionNum = Integer.parseInt(dataFormatter.formatCellValue(row.getCell(1)));
            	
				//If the study code is equal to our current study code and the session number is larger than latestSessionNumber
				if (tempUsername.equals(username) && tempSessionNum > latestSessionNumber) {
					System.out.println("in if block");
					latestSessionNumber = tempSessionNum;
					studyCodeLastLine = "";
					for (Cell cell: row) {
						studyCodeLastLine = studyCodeLastLine + dataFormatter.formatCellValue(cell) + ",";
					}
				}
            }
				
			workbook.close();
			inp.close();

        } catch (EncryptedDocumentException | IOException
                ex) {
            ex.printStackTrace();
        } 	
		
		// most important step here!!
		ArrayList<String> nextRow = getNextScheduleRow(studyCodeLastLine);
		
		//Export the scheduling data to the locked scheduling file
		exportToLockedXLSX(nextRow, scheduleFilePath, schedulePassword);
		
//		//Convert the String array to a single string
//		String nextRowString = "";
//		for (int i = 0;i<nextRow.length;i++) {
//			nextRowString = nextRowString + nextRow[i] + ",";
//		}
//		
//		//Write the new row string to the file
//		BufferedWriter writer;
//		try {
//			writer = new BufferedWriter(new FileWriter(schedulingFile,true));
//			writer.append(nextRowString);
//			writer.newLine();
//    		writer.close();
//		} catch (IOException e1) {
//			e1.printStackTrace();
//		}
		
        return nextRow.get(6);
        // what exactly is the result of this string?
        // is this just giving the result or is it also saving the data to the CSV?
    } 
    
    public static ArrayList<String> getNextScheduleRow(String lastLine) {
    	
    	System.out.println("Last line: " + lastLine);
    	
    	ArrayList<String> newSchedule = new ArrayList<String>(Collections.nCopies(7,""));
    	
    	//Add the current study code to the 0th position
    	newSchedule.set(0, getUsername());
    	
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
    	newSchedule.set(4, round1StrResult);
    	newSchedule.set(5, round2StrResult);
    	
    	//Generate the date and time stamps needed for a row in the schedule
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		String date = df.format(new Date());
		SimpleDateFormat tf = new SimpleDateFormat("HHmm");
		String time = tf.format(new Date());
		
		//Add the date and time to the corresponding positions in the ArrayList
		newSchedule.set(2, date);
		newSchedule.set(3, time);		
    	
		if (lastLine == "") {
			//Case 1: The file is empty because it was just created.
			//Start by adding the session number, which in this case is 1
			newSchedule.set(1, "1");
			
			if (round1Passed) {
				//If the participant passed round 1, schedule in 3 months
				newSchedule.set(6, "3");		
				
			} else {
				//In this case, regardless of the result of the second test, the participant must
				//come back in one month (either same interval, ie 1 month, or explicitly 1 month)
				newSchedule.set(6, "1");		
			}
			
		} else {
			//Case 2: The file is not empty, and we have the most recent test result
			//Start by converting it to an ArrayList
			ArrayList<String> lastLineList = new ArrayList<String>(Arrays.asList(lastLine.split("\\s*,\\s*")));
			
			//To set the session date, get the first element in LastLineList and add 1
			newSchedule.set(1, Integer.toString(Integer.parseInt(lastLineList.get(1))+1));
			
			if (round1Passed) {
				//If the participant passed round 1, take the previous line's "months until next session"
				//and increment appropriately
				int previousInterval = Integer.parseInt(lastLineList.get(6));
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
				newSchedule.set(6, Integer.toString(newInterval));		
			} else {
				//If the participant didn't pass round 1, then their return interval is determined
				//by their performance on the second round of CPR
				if (round2Passed) {
					//If the participant passes their second round after failing their first, then
					//they return at the same interval as their most recent session
					int newInterval = Integer.parseInt(lastLineList.get(5));
					newSchedule.set(6, Integer.toString(newInterval));					
				} else {
					//If the participant didn't pass either round of CPR, then they must return in
					//1 month. We must also determine whether they failed both rounds of CPR
					//during their previous session
					
					if (lastLineList.get(3).equals("FAIL") && lastLineList.get(4).equals("FAIL")) {
						//In this case the participant has failed both rounds of CPR for 2 sessions
						//in a row
						newSchedule.set(6, "1");								
						System.out.println("Please see trainer for further information.");
					} else {
						//In this case the participant failed both rounds this session, but passed
						//at least one round last session. They must come back in 1 month.
						newSchedule.set(6, "1");		
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
    
    //getPrimaryResults(): This method returns the Integer ArrayList containing the
    //primary results of interest, as assigned in the saveCPRData method. Note that they are
	//in the following order: 1)mean rate  
	//						  2)mean depth  
	//						  3)percent of compressions with adequate recoil 
    public static ArrayList<Integer> getPrimaryResults() {
    	return primaryResults;
    }   
    
    //getSecondaryResults(): This method returns the Integer ArrayList containing the
    //secondary results of interest, as assigned in the saveCPRData method. Note that they are
	//in the following order: 1)mean rate  
	//						  2)mean depth  
	//						  3)percent of compressions with adequate recoil 
    public static ArrayList<Integer> getSecondaryResults() {
    	return secondaryResults;
    }    

    //evaluationOnePassed(): This method returns a boolean indicating whether the
    //first round of CPR meets the test's standards.
    //It checks three things:
    //1. Mean rate of CPR - having a rate 100-120
    //2. Mean depth of CPR - a depth 5-6cm
    //3. Percent of compressions with adequate recoil - target of >80% of their compressions having full recoil 
    public static boolean evaluationOnePassed() {
    	
    	System.out.println(primaryResults.toString());
    	
    	//Verify that the mean rate is between 100 and 120 bpm
    	if (primaryResults.get(0) < 100 || primaryResults.get(0) > 120) {
    		System.out.println("failed 1");
    		return false;
    	}
    	
    	//Verify that the mean depth is between 5 and 6.5 cm
    	if (primaryResults.get(1) < 50 || primaryResults.get(1) > 65 ) {
    		System.out.println("failed 2");
    		return false;
    	}
    	
    	//Verify that the % of adequate recoil is greater than 80
    	if (primaryResults.get(2) <= 80) {
    		System.out.println("failed 3");
    		return false;
    	}
    	
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
    	if (secondaryResults.get(1) < 5 || secondaryResults.get(1) > 6.5 )
    		return false;
    	
    	//Verify that the % of adequate recoil is greater than 80
    	if (secondaryResults.get(2) <= 80)
    		return false;
    	
    	//otherwise this evaluation passed
    	return true;
    }
    

    public static void setAnonCode(String code) {
    	anonymousCode = code;
    }
    
    public static String getAnonCode() {
    	return anonymousCode;
    }
    
    public static void setUsername(String newUsername) {
    	username = newUsername;
    }
    
    public static String getUsername() {
    	return username;
    }
    
    public static void savePreQuestions(String exposure, String comfort) {
		surveyData.add(exposure);
		surveyData.add(comfort);
    }
    
    public static void resetData() {
    	username = "";
    	anonymousCode = "";
    	surveyData.clear();
    	primaryResults.clear();
    	primaryData.clear();
    	secondaryResults.clear();
    	secondaryData.clear();
    }
    
    public static void exportToCSV(ArrayList<Integer> data) {
		ArrayList<String> stringDataRow = getStringArray(data);
		String newRow;
		newRow = anonymousCode + "," + stringDataRow.stream().collect(Collectors.joining(",")) + "\n";
		System.out.println(newRow);
    	try {
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
    
    public static void exportToLockedXLSX(ArrayList<String> data, String filePath, String password) {
		
        try {
        	//Create the input stream for the xlsx file
        	InputStream inp = new FileInputStream(filePath);
        	//Open the workbook and get the first sheet
            Workbook workbook = WorkbookFactory.create(inp, password);
            Sheet firstSheet = workbook.getSheetAt(0);
            //Create a new row at the sheet's last row num in the sheet
            int lastRow = firstSheet.getLastRowNum();
            Row row = firstSheet.createRow(++lastRow);
            
            System.out.println("lastRow: " + Integer.toString(lastRow));
            
            //Go through each value and write it to the column that corresponds with it's index in data
            int index = 0;
            for (String val : data) {
            	row.createCell(index).setCellValue(val);
            	index++;
            	System.out.println("Current val: " + val);
            }

            //Create output stream
            FileOutputStream out = new FileOutputStream(filePath);
            workbook.write(out);
            out.close();
            workbook.close();
            inp.close();
        } catch (EncryptedDocumentException | IOException
                ex) {
            ex.printStackTrace();
        }
  
    }
    
    
    public static void saveCPRData(int testNum) {
    	/*FIX THIS IMPORT TECHNIQUE BEFORE REPEATING TESTING
    	 * - May be prone to reimporting the same files that appear first under a participant's
    	 * study code and test #.
    	 */
    	String totalCompressions;
    	String correctlyReleasedCompressions;
    	String compressionMeanDepth;
    	String averageHandsOffTime;
    	String[] depthPercentageData = new String[3]; //[too shallow %,adequate depth %,too deep %]
    	String[] depthRawData = new String[3]; //[too shallow,adequate depth,too deep]
    	String[] ratePercentageData = new String[3]; //[too slow %, adequate speed %, too fast %]
    	String[] rateRawData = new String[3]; //[too slow,adequate speed,too fast]
    	String compressionMeanRate;
    	String compressionMeanRatio;
    	String corrrectHandPosition;
    	String sessionDuration;
    	String totalCprScore;
    	String compressionScore;
    	String flowFractionScore;
    	String flowFractionPercent;
    	String date;
    	String time;
    	
    	try {
    		//Get the current date and format into string
    		Date dateFormatted = new Date();
    		DateFormat df = new SimpleDateFormat("ddMMyyyy");
    		String dayMonth = df.format(dateFormatted);
    		
    		//Create file object at folder containing all raw XML results
    		//**PATH MAY NEED TO BE CHANGED DEPENDING ON DRIVE/USERNAMES
	    	File f = new File (folderWithRaw);
//	    	File[] matchingFiles = f.listFiles(new FilenameFilter() {
//	    		public boolean accept(File dir, String name) {
//	    			return name.startsWith(Integer.toString(getCurrentCode()) + "_" + Integer.toString(testNum)+ "_" + dayMonth)
//	    					&& name.endsWith(".xml");
//	    		}
//	    	});
	    	
	    	File[] matchingFiles = f.listFiles(new FilenameFilter() {
	    		public boolean accept(File dir, String name) {
	    			return name.startsWith(getAnonCode() + "_" + Integer.toString(testNum))
	    					&& name.endsWith(".xml");
	    		}
	    	});

	    	
	    	//If there are multiple matching files then an error has occured
	    	if (matchingFiles.length == 0) {
	    		DataIO.resetData();
	        	VistaNavigator.loadVista(VistaNavigator.SaveErrorVista);
	        	return;
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
	    	totalCompressions = eElement.getElementsByTagName("TotalCompressions").item(0).getTextContent();
	    	correctlyReleasedCompressions = eElement.getElementsByTagName("CorrectlyReleasedCompression").item(0).getTextContent();
	    	compressionMeanDepth = eElement.getElementsByTagName("CompressionMeanDepth").item(0).getTextContent();
	    	averageHandsOffTime = eElement.getElementsByTagName("AverageHandsOffTime").item(0).getTextContent();

	    	Node depthPercentageList = eElement.getElementsByTagName("CompressionDepthStatsPercentage").item(0);
	    	Element eDepthPercentageList = (Element) depthPercentageList;
	    	depthPercentageData[0] = eDepthPercentageList.getElementsByTagName("TooShallowPercentage").item(0).getTextContent();
	    	depthPercentageData[1] = eDepthPercentageList.getElementsByTagName("AdequateDepthPercentage").item(0).getTextContent();
	    	depthPercentageData[2] = eDepthPercentageList.getElementsByTagName("TooDeepPercentage").item(0).getTextContent();

	    	Node depthRawList = eElement.getElementsByTagName("CompressionDepthStats").item(0);
	    	Element eDepthRawList = (Element) depthRawList;
	    	depthRawData[0] = eDepthRawList.getElementsByTagName("TooShallow").item(0).getTextContent();
	    	depthRawData[1] = eDepthRawList.getElementsByTagName("AdequateDepth").item(0).getTextContent();
	    	depthRawData[2] = eDepthRawList.getElementsByTagName("TooDeep").item(0).getTextContent();
	    	
	    	Node ratePercentageList = eElement.getElementsByTagName("CompressionRateStatsPercentage").item(0);
	    	Element eRatePercentageList = (Element) ratePercentageList;
	    	ratePercentageData[0] = eRatePercentageList.getElementsByTagName("TooSlowPercentage").item(0).getTextContent();
	    	ratePercentageData[1] = eRatePercentageList.getElementsByTagName("AdequateRatePercentage").item(0).getTextContent();
	    	ratePercentageData[2] = eRatePercentageList.getElementsByTagName("TooFastPercentage").item(0).getTextContent();
	    	
	    	Node rateRawList = eElement.getElementsByTagName("CompressionRateStats").item(0);
	    	Element eRateRawList = (Element) rateRawList;
	    	rateRawData[0] = eRateRawList.getElementsByTagName("TooSlow").item(0).getTextContent();
	    	rateRawData[1] = eRateRawList.getElementsByTagName("AdequateRate").item(0).getTextContent();
	    	rateRawData[2] = eRateRawList.getElementsByTagName("TooFast").item(0).getTextContent();

	    	compressionMeanRate = eElement.getElementsByTagName("CompressionMeanRate").item(0).getTextContent();
	    	compressionMeanRatio = eElement.getElementsByTagName("CompressionMeanRatio").item(0).getTextContent();
	    	corrrectHandPosition = eElement.getElementsByTagName("CorrectHandPosition").item(0).getTextContent();
	    	sessionDuration = eElement.getElementsByTagName("SessionDuration").item(0).getTextContent();
	    	totalCprScore = eElement.getElementsByTagName("TotalCprScore").item(0).getTextContent();
	    	compressionScore = eElement.getElementsByTagName("CompressionScore").item(0).getTextContent();
	    	flowFractionScore = eElement.getElementsByTagName("FlowFractionScore").item(0).getTextContent();
	    	flowFractionPercent = eElement.getElementsByTagName("FlowFractionPercent").item(0).getTextContent();
	    	
	    	String tempDate = eElement.getElementsByTagName("Date").item(0).getTextContent();
	    	String tempTime = eElement.getElementsByTagName("Time").item(0).getTextContent();
	    	
	    	date = tempDate.replaceAll("\\p{P}", "");
	    	time = tempTime.replaceAll("\\p{P}", "");
	    	

	    	if (testNum == 1) {
	    		primaryResults.add(Integer.parseInt(compressionMeanRate));
	    		primaryResults.add(Integer.parseInt(compressionMeanDepth));
	    		primaryResults.add(Integer.parseInt(correctlyReleasedCompressions));	  
	    		
	    		primaryData.add("1"); //Add the CPR round number to the start of the row
	    		primaryData.add(date);
	    		primaryData.add(time);
	    		primaryData.add(surveyData.get(0));
	    		primaryData.add(surveyData.get(1));
	    		primaryData.add(totalCompressions);
	    		primaryData.add(correctlyReleasedCompressions);
	    		primaryData.add(compressionMeanDepth);
	    		primaryData.add(averageHandsOffTime);
	    		primaryData.add(depthPercentageData[0]);
	    		primaryData.add(depthPercentageData[1]);
	    		primaryData.add(depthPercentageData[2]);
	    		primaryData.add(depthRawData[0]);
	    		primaryData.add(depthRawData[1]);
	    		primaryData.add(depthRawData[2]);
	    		primaryData.add(ratePercentageData[0]);
	    		primaryData.add(ratePercentageData[1]);
	    		primaryData.add(ratePercentageData[2]);
	    		primaryData.add(rateRawData[0]);
	    		primaryData.add(rateRawData[1]);
	    		primaryData.add(rateRawData[2]);
	    		primaryData.add(compressionMeanRate);
	    		primaryData.add(compressionMeanRatio);
	    		primaryData.add(corrrectHandPosition);
	    		primaryData.add(sessionDuration);
	    		primaryData.add(totalCprScore);
	    		primaryData.add(compressionScore);
	    		primaryData.add(flowFractionScore);
	    		primaryData.add(flowFractionPercent);
	    		
	    		//Add the current code to the start of the ArrayList
	    		primaryData.add(0, getUsername());
	    		
	    		exportToLockedXLSX(primaryData, dataFilePath, dataPassword);
	    	}
	    	else if (testNum == 2) {
	    		secondaryResults.add(Integer.parseInt(compressionMeanRate));
	    		secondaryResults.add(Integer.parseInt(compressionMeanDepth));
	    		secondaryResults.add(Integer.parseInt(correctlyReleasedCompressions));	    
	    		
	    		secondaryData.add("2"); //Add the CPR round number to the start of the row
	    		secondaryData.add(date);
	    		secondaryData.add(time);
	    		secondaryData.add("-1");
	    		secondaryData.add("-1");
	    		secondaryData.add(totalCompressions);
	    		secondaryData.add(correctlyReleasedCompressions);
	    		secondaryData.add(compressionMeanDepth);
	    		secondaryData.add(averageHandsOffTime);
	    		secondaryData.add(depthPercentageData[0]);
	    		secondaryData.add(depthPercentageData[1]);
	    		secondaryData.add(depthPercentageData[2]);
	    		secondaryData.add(depthRawData[0]);
	    		secondaryData.add(depthRawData[1]);
	    		secondaryData.add(depthRawData[2]);
	    		secondaryData.add(ratePercentageData[0]);
	    		secondaryData.add(ratePercentageData[1]);
	    		secondaryData.add(ratePercentageData[2]);
	    		secondaryData.add(rateRawData[0]);
	    		secondaryData.add(rateRawData[1]);
	    		secondaryData.add(rateRawData[2]);
	    		secondaryData.add(compressionMeanRate);
	    		secondaryData.add(compressionMeanRatio);
	    		secondaryData.add(corrrectHandPosition);
	    		secondaryData.add(sessionDuration);
	    		secondaryData.add(totalCprScore);
	    		secondaryData.add(compressionScore);
	    		secondaryData.add(flowFractionScore);
	    		secondaryData.add(flowFractionPercent);
	    		
	    		//Add the current code to the start of the ArrayList
	    		secondaryData.add(0, getUsername());
	    		
	    		exportToLockedXLSX(secondaryData, dataFilePath, dataPassword);
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