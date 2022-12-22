package agh.ics.oop.project1.world.engine;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;
import java.util.Stack;

public class WorldEngineConfig {
    private static WorldEngineConfig instance;
    private static final String CONFIGS_DIRECTORY = "src/main/resources/configs";
    private Properties properties = new Properties();

    private WorldEngineConfig() {
        loadFromFile("main1.conf");
    }

    public static WorldEngineConfig getInstance() {
        if (instance == null)
            instance = new WorldEngineConfig();

        return instance;
    }

    public static String[] getConfigNames() {
        File[] configs = Objects.requireNonNull(new File(CONFIGS_DIRECTORY).listFiles());
        String[] names = new String[configs.length];

        for (int i = 0; i < configs.length; i++) {
            names[i] = configs[i].getName();
        }

        return names;
    }

    public String getProperty(String key) {
        if (properties.getProperty(key, "").equals(""))
            throw new IllegalArgumentException();
        return properties.getProperty(key, "");
    }

    public int getInt(String key) {
        return Integer.parseInt(getProperty(key));
    }

    public void loadFromFile(String configName) {
        try (FileInputStream fileStream = new FileInputStream(CONFIGS_DIRECTORY + "/" + configName)) {
            properties.load(fileStream);
        }
        catch (FileNotFoundException ex) {
            System.out.println("XD1");
        }
        catch (IOException ex) {
            System.out.println("XD2");
        }
    }

    public void setProperties(Properties properties) {
        this.properties = new Properties(properties);
    }
}
