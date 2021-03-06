package no.kristiania.pgr200.jlw.server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class HttpServerConfig {

    public static String WEB_ROOT, DEFAULT_FILE, FILE_NOT_FOUND, METHOD_NOT_SUPPORTED, SERVER_NAME;
    public static HashSet<String> SUPPORTED_METHODS = new HashSet<>();
    public static HashMap<String, String> serverConfig = new HashMap<>();

    static {
        String line;
        String configFile = "server.config";
        try {
            BufferedReader br = new BufferedReader(new FileReader(configFile));
            while ((line = br.readLine()) != null)
            {
                String[] parts = line.split(":", 2);
                if (parts.length >= 2)
                {
                    String key = parts[0];
                    String value = parts[1];
                    serverConfig.put(key, value);
                } else {
                    System.out.println("ignoring line: " + line);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error opening server.config.");
        } catch (IOException e) {
            System.out.println("Error reading server.config.");
        }

        WEB_ROOT = serverConfig.get("WEB_ROOT").trim();
        DEFAULT_FILE = serverConfig.get("DEFAULT_FILE").trim();
        FILE_NOT_FOUND = serverConfig.get("FILE_NOT_FOUND").trim();
        METHOD_NOT_SUPPORTED = serverConfig.get("METHOD_NOT_SUPPORTED").trim();
        SERVER_NAME = serverConfig.get("SERVER_NAME").trim();
        SUPPORTED_METHODS.add("GET");
        SUPPORTED_METHODS.add("POST");
    }
}
