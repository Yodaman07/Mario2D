import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Level {
	
	private String type;
	private String name;
	private int id;
	private ArrayList<HashMap<String, Integer>> layout;
	
	
	public Level() {
	}


	@Override
	public String toString() {
		return "Level [type=" + type + ", name=" + name + ", id=" + id + ", layout=" + layout + "]";
	}
	
}


