import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import com.google.gson.Gson;

public class LevelLoader{
	
	public LevelLoader() {
		System.out.println("Obj created");
	}
	
	public Level load(String filePath) { //de serializing
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
	
//	public void save(String filePath, Level level) { //serializing a level object to a file
//		Gson gson = new Gson();
//		try {
//			File f = new File(filePath);
//			FileReader fr = new FileReader(f);
//			
//			String a = gson.toJson(level);
//			System.out.println(a);
//			
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//	}
	
}