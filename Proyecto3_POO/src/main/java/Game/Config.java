
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
     
    public long getSimpleTankSpeed(){
        JSONObject enemyTanks = (JSONObject) configData.get("enemyTanks");
        JSONObject simpleTank = (JSONObject) enemyTanks.get("simpleTank");
        return (long) simpleTank.get("movementSpeed");
    }

    public long getFastTankSpeed(){
        JSONObject enemyTanks = (JSONObject) configData.get("enemyTanks");
        JSONObject fastTank = (JSONObject) enemyTanks.get("fastTank");
        return (long) fastTank.get("movementSpeed");
    }

    public long getPowerTankSpeed(){
        JSONObject enemyTanks = (JSONObject) configData.get("enemyTanks");
        JSONObject strongTank = (JSONObject) enemyTanks.get("strongTank");
        return (long) strongTank.get("movementSpeed");
    }

    public long getTankTankSpeed(){
        JSONObject enemyTanks = (JSONObject) configData.get("enemyTanks");
        JSONObject resistantTank = (JSONObject) enemyTanks.get("resistantTank");
        return (long) resistantTank.get("movementSpeed");
    }

    public long getSimpleTankFireRate() {
        JSONObject enemyTanks = (JSONObject) configData.get("enemyTanks");
        JSONObject simpleTank = (JSONObject) enemyTanks.get("simpleTank");
        return (long) simpleTank.get("fireRate");
    }

    public long getFastTankFireRate() {
        JSONObject enemyTanks = (JSONObject) configData.get("enemyTanks");
        JSONObject fastTank = (JSONObject) enemyTanks.get("fastTank");
        return (long) fastTank.get("fireRate");
    }

    public long getPowerTankFireRate() {
        JSONObject enemyTanks = (JSONObject) configData.get("enemyTanks");
        JSONObject strongTank = (JSONObject) enemyTanks.get("strongTank");
        return (long) strongTank.get("fireRate");
    }

    public long getTankTankFireRate() {
        JSONObject enemyTanks = (JSONObject) configData.get("enemyTanks");
        JSONObject resistantTank = (JSONObject) enemyTanks.get("resistantTank");
        return (long) resistantTank.get("fireRate");
    }
}
