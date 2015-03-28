import java.util.Calendar;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class ExtractDate {
/*
 * Usage: ExtractDate.extract(String)
 * Returns an ArrayList<String> object
 * If the input String does not include a date, an empty ArrayList<String> object will be returned
*/	
	public static void main(String[] args) {
		String str = "atesDIF Creation Date: 2 Last DIF Revision Date: 2015-01-01";
		ArrayList<String> res = ExtractDate.extract(str);
		if(res==null){
			System.out.println("Nooooooooooooo");
		}
		for(String s : res){
			System.out.println(s);
		}
	}
	
	
	public static ArrayList<String> extract(String str){
		ArrayList<String> ret = new ArrayList<String>();
		// ######## ADD YOUR NEW FORMAT HERE 1 ########
		String regex = "(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](19[0-9]{2}|20[0-9]{2})"			// MM/DD/YYYY
				+ "|(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19[0-9]{2}|20[0-9]{2})"				// DD/MM/YYYY
				+ "|(19[0-9]{2}|20[0-9]{2})[- /.](0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])" 			// YYYY/MM/DD
				+ "|(19[0-9]{2}|20[0-9]{2})[- /.](0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])";			// YYYY/DD/MM
		// Create a Pattern object======================================
		Pattern pattern = Pattern.compile(regex);
		
		// Find matching dates and add to res arraylist=================
		Matcher matcher = pattern.matcher(str);
		ArrayList<String> res = new ArrayList<String>();
		while (matcher.find( )) {
			res.add(matcher.group());
		}

		// Refromat the date into uniform format YYYY/MM/DD=============
		if(res.isEmpty())	return res;
		Calendar cal = Calendar.getInstance();
		for(String s : res){
			try {
				String[] format = null;
				// ######## ADD YOUR NEW FORMAT HERE 2 ########
				if(s.matches("(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](19[0-9]{2}|20[0-9]{2})")){
					// MM/DD/YYYY
					format = new String[]{"MM","dd","yyyy"};
				}
				else if(s.matches("|(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19[0-9]{2}|20[0-9]{2})")){
					// DD/MM/YYYY
					format = new String[]{"dd","MM","yyyy"};
				}else if(s.matches("|(19[0-9]{2}|20[0-9]{2})[- /.](0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])")){
					// YYYY/MM/DD
					format = new String[]{"yyyy","MM","dd"};
				}else{
					// YYYY/DD/MM
					format = new String[]{"yyyy","dd","MM"};
				}
				SimpleDateFormat sdf = new SimpleDateFormat(findSplitFormat(s,format));
				Date date = sdf.parse(s);
				cal.setTime(date);
				// Calender.MONTH starts from 0
				String new_date = cal.get(Calendar.YEAR)+"/"+(cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.DATE);
				ret.add(new_date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return ret;
	}
	
	private static String findSplitFormat(String str, String[] format){
		// Find out the split symbol, and return the newly created format with the right split symbol
		String symbol = " ";
		char[] arr = str.toCharArray();
		for(int i=0; i<arr.length; i++){
			if(!Character.isDigit(arr[i])){
				symbol = String.valueOf(arr[i]);
			}
		}
		String ret = format[0]+symbol+format[1]+symbol+format[2];
		return ret;
	}
}


