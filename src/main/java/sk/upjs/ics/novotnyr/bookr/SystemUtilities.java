package sk.upjs.ics.novotnyr.bookr;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;


public class SystemUtilities {
    public static boolean isRunningOnWindows() {
        String operatingSystem = System.getProperty("os.name").toLowerCase();
        return operatingSystem.contains("win");
    }

    public byte[] load(File file) throws FileNotFoundException, IOException {
        RandomAccessFile fileReader = new RandomAccessFile(file, "r");
        byte[] buffer = new byte[(int) fileReader.length()];
        fileReader.readFully(buffer);

        return buffer;
    }
}
