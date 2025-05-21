import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import org.json.JSONObject;
import com.google.gson.Gson;

public class LevelLoader{
	
	public LevelLoader() {
		System.out.println("Obj created");
	}
	
	public Level load(String filePath) {	
		Gson gson = new Gson();
		try {
			
			File f = new File(filePath);
			FileReader fr = new FileReader(f);
			
			Level result = gson.fromJson(fr, Level.class);
			System.out.println(result);
			
			result.loadBlocks(); //populating the class arraylists after parsing the json
			result.loadEntities();
			
			return result;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
}