package agh.ics.oop.project1.world.config;

import agh.ics.oop.project1.utils.Pair;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

public class WorldConfig {
    private static final String CONFIGS_DIRECTORY = "src/main/resources/configs";
    private Properties properties = new Properties();

    public WorldConfig() {
        loadFromFile("main1.conf");
    }

    public WorldConfig(String configName) {
        loadFromFile(configName);
    }

    public WorldConfig(Properties configOptions) {
        properties = configOptions;
    }

    public static String[] getConfigNames() {
        File[] configs = Objects.requireNonNull(new File(CONFIGS_DIRECTORY).listFiles());
        String[] names = new String[configs.length];

        for (int i = 0; i < configs.length; i++) {
            names[i] = configs[i].getName();
        }

        return names;
    }

    public static List<Pair<WorldConfigOptions, String>> validate(Properties configOptions) throws NumberFormatException {
        final int genomeSize = Integer.parseInt(configOptions.getProperty(WorldConfigOptions.ANIMAL_GENOME_SIZE.getName()));
        final int minMutations = Integer.parseInt(configOptions.getProperty(WorldConfigOptions.MINIMUM_MUTATIONS_NUMBER.getName()));
        final int maxMutations = Integer.parseInt(configOptions.getProperty(WorldConfigOptions.MAXIMUM_MUTATIONS_NUMBER.getName()));
        List<Pair<WorldConfigOptions, String>> list = new ArrayList<>();

        if (minMutations > genomeSize)
            list.add(new Pair<>(WorldConfigOptions.MINIMUM_MUTATIONS_NUMBER, "Value is greater than the genome size"));
        else if (minMutations > maxMutations)
            list.add(new Pair<>(WorldConfigOptions.MINIMUM_MUTATIONS_NUMBER, "Value is greater than the maximum number of mutations"));
        if (maxMutations > genomeSize)
            list.add(new Pair<>(WorldConfigOptions.MAXIMUM_MUTATIONS_NUMBER, "Value is greater than the genome size"));
        else if (maxMutations < minMutations)
            list.add(new Pair<>(WorldConfigOptions.MAXIMUM_MUTATIONS_NUMBER, "Value is less than the minimum number of mutations"));

        return list;
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
        catch (IOException ex) {
            System.out.println("File not found");
            System.out.println(ex);
        }
    }

    public void setProperties(Properties properties) {
        this.properties = new Properties(properties);
    }
}
