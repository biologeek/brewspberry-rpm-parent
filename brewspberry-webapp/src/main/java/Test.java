import java.util.Calendar;

public class Test {
	
	
	
	public static void main (String[] args){
		
		Calendar cal = Calendar.getInstance();

		
		System.out.println(cal.getTime());
		
		
		cal.add(Calendar.SECOND, 50000);
		
		System.out.println(cal.getTime());
		
	}

}
