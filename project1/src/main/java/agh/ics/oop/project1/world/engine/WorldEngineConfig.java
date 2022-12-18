package agh.ics.oop.project1.world.engine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class WorldEngineConfig {
    private static WorldEngineConfig instance;
    private final Properties config = new Properties();

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
        return Integer.parseInt(getProperty(key));
    }

    private void load() {
        try (FileInputStream fileStream = new FileInputStream("src/main/resources/main.conf")) {
            config.load(fileStream);
        }
        catch (FileNotFoundException ex) {
            System.out.println("XD1");
        }
        catch (IOException ex) {
            System.out.println("XD2");
        }
    }
}
