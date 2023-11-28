
package Game;


import JSONFiles.JSONHandler;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/*
0 - Blank
1 - Eagle
2 - Brick
3 - MetalBrick
4 - Bush
5 - Water
*/

public class LevelMaker {

    public static int[][] getLevelMap(int level) {
        System.out.println(""+ level);
        JSONObject jsonData = JSONHandler.readJSONFile("src\\main\\java\\JSONFiles\\Maps.json");
        if (jsonData != null) {
            JSONArray levels = (JSONArray) jsonData.get("levels");
            for (Object obj : levels) {
                JSONObject levelData = (JSONObject) obj;
                long levelNumber = (long) levelData.get("levelNumber");
                if (level == levelNumber) {
                    JSONArray mapArray = (JSONArray) levelData.get("map");
                    int[][] map = new int[mapArray.size()][((JSONArray) mapArray.get(0)).size()];

                    for (int i = 0; i < mapArray.size(); i++) {
                        JSONArray row = (JSONArray) mapArray.get(i);
                        for (int j = 0; j < row.size(); j++) {
                            map[i][j] = ((Long) row.get(j)).intValue();
                        }
                    }

                    return map;
                }
            }
        }

        return null; // O maneja el caso cuando no se encuentra el nivel
    }
}



