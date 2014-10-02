package enumTypes;

import java.util.ArrayList;
import java.util.Collections;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<Apple> apples = new ArrayList<>();
		apples.add(Apple.valueOf("PIPPIN"));
		apples.add(Enum.valueOf(Apple.class, "GRANNY_SMITH"));
		apples.add(Apple.valueOf("FUJI"));
		System.out.println(apples);
		Collections.sort(apples);
		System.out.println(apples);
		System.out.println(apples.get(0).ordinal());
	}

}
