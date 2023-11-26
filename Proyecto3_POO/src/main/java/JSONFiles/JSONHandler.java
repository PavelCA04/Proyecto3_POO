
package JSONFiles;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JSONHandler {
    
    // Método para leer un archivo JSON
    public static JSONObject readJSONFile(String filePath) {
        JSONParser parser = new JSONParser();
        JSONObject jsonData = null;
        
        try (FileReader reader = new FileReader(filePath)) {
            Object obj = parser.parse(reader);
            jsonData = (JSONObject) obj;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        
        return jsonData;
    }
    
    // Método para escribir un archivo JSON
    public static void writeJSONFile(JSONObject jsonData, String filePath) {
        try (FileWriter file = new FileWriter(filePath)) {
            file.write(jsonData.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
