package agh.ics.project1.world.engine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class WorldEngineConfig {
    private static WorldEngineConfig instance;
    private Properties config = new Properties();

    private WorldEngineConfig() {
        load();
    }

    public static WorldEngineConfig getInstance() {
        if (instance == null)
            instance = new WorldEngineConfig();

        return instance;
    }

    public String getProperty(String key) {
        if (config.getProperty(key, "").equals(""))
            throw new IllegalArgumentException();
        return config.getProperty(key, "");
    }

    public int getInt(String key) {
        int num = Integer.parseInt(getProperty(key));
        return num;
    }

    private void load() {
        try (FileInputStream fileStream = new FileInputStream("./main.conf")) {
            config.load(fileStream);
        }
        catch (FileNotFoundException ex) {

        }
        catch (IOException ex) {

        }
    }
}
