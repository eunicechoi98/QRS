public class AtestingFileOne {
	
	// starting point
	
    public static void main(String[] args) {
    	test(21, true, false, 1, 2);	
    }
    
	
    public static void test(int code, boolean boolOne, boolean boolTwo, int numOne, int numTwo) {
    	DataIO.setCurrentCode(code);
    	DataIO.setTestData(boolOne, numOne);
    	DataIO.setTestData(boolTwo, numTwo);
    	String result = DataIO.scheduleNextSession();
    	System.out.print(result);
    }
}