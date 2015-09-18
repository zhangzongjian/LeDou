package test;

import java.util.LinkedHashMap;
import java.util.Map;

public class MapTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("1", "abc");
		map.put("3", "opq");
		map.put("2", "efg");
		System.out.println(map.keySet());
		System.out.println(map.values());
	}

}
