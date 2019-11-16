import java.util.ArrayList;
import java.util.List;

import java.io.*;

public class Authenticate {

	private static String authFilePath = "";
	
	private static String demoSaveDataCode = "demoCode";
	private static String demoDontSaveCode = "noSaveCode";
	
	private static List<String> codes = new ArrayList<>();
	
	public Authenticate(String filePath) throws IOException {
		authFilePath = filePath;
		codes = importCodes(authFilePath);
	}
	
	private List<String> importCodes(String filePath) throws IOException {
		
		List<String> allCodes = new ArrayList<>();
		try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"))) {
			String code = "";
			while ((code = br.readLine()) != null)
				allCodes.add(code);
		}
		return allCodes;
	}
	
	public static boolean authenticateCode(String inputCode) {
		
		if (inputCode.equals(demoSaveDataCode))
			return true;
		else if (inputCode.equals(demoDontSaveCode))
			return true;
		else
			return codes.contains(inputCode);
	}
}