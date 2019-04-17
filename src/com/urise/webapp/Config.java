package com.urise.webapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final Config INSTANCE = new Config();
    protected static final File PROPS = new File("./config/resumes.properties");
    private Properties props = new Properties();
    private String storageDir;

    public static Config get() {
        return INSTANCE;
    }

    public String getStorageDir() {
        return storageDir;
    }

    private Config() {
        try (InputStream is = new FileInputStream(PROPS)) {
            props.load(is);
            storageDir = props.getProperty("storage.dir");
        } catch (IOException e) {
            throw new IllegalStateException("invalid config file " + PROPS.getAbsolutePath());
        }
    }
}
