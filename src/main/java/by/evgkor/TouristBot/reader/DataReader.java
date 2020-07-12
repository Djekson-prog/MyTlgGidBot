package by.evgkor.TouristBot.reader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.Scanner;

public class DataReader {
    private static final Logger LOGGER = LogManager.getLogger();

    public static String read(String path) throws FileNotFoundException {
        if (path == null || !Paths.get(path).toFile().exists()) {
            LOGGER.fatal("FILEPATH " + path + " is wrong.");
            throw new FileNotFoundException();
        }
        return new Scanner(new File(path)).useDelimiter("\\Z").next();
    }
}
