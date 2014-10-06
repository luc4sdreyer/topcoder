import java.util.ArrayList;
import java.util.Arrays;

public class WhichDay {

	public String getDay(String[] notOnThisDay) {
		ArrayList<String> days = new ArrayList<String>(Arrays.asList("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"));		
		for (int i = 0; i < notOnThisDay.length; i++) {
			days.remove(notOnThisDay[i]);
		}
		return days.get(0);
	}

}
