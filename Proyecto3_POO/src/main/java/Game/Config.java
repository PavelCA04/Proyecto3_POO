
package Game;

import JSONFiles.JSONHandler;
import org.json.simple.JSONObject;





public class Config { // Singleton class
    private static Config instance;
    private JSONObject configData;

    private Config() {
        this.configData = JSONHandler.readJSONFile("src\\main\\java\\JSONFiles\\config.json");
    }

    public static Config getInstance() {
        if (instance == null) {
            instance = new Config();
        }
        return instance;
    }

    public long getPlayerMovementSpeed() {
        JSONObject playerTank = (JSONObject) configData.get("playerTank");
        return (long) playerTank.get("movementSpeed");
    }

    public long getPlayerFireRate() {
        JSONObject playerTank = (JSONObject) configData.get("playerTank");
        return (long) playerTank.get("fireRate");
    }

    public long getEnemyAppearanceInterval() {
        JSONObject enemyTanks = (JSONObject) configData.get("enemyTanks");
        return (long) enemyTanks.get("appearanceInterval");
    }
}
