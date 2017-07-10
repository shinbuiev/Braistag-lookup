package com.braintags.lookup;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.assertEquals;

public class BigFileIntegrationTests {

    @Rule
    public final TemporaryFolder tempFolder = new TemporaryFolder();

    public void generateFileWithTokenInside(File file, long length, String token) throws Throwable {

        long currentLength = 0;
        while (currentLength < length / 2){
            int seed = ThreadLocalRandom.current().nextInt(10, 10000);
            String noise = RandomStringUtils.random(seed, '0', '1');
            Files.write(file.toPath(), noise.getBytes(), StandardOpenOption.APPEND);
            currentLength += seed;
        }

        String preparedToken = "00" + token + "00";
        Files.write(file.toPath(), preparedToken.getBytes(), StandardOpenOption.APPEND);
        currentLength += preparedToken.length();

        while (currentLength < length){
            int seed = ThreadLocalRandom.current().nextInt(10, 10000);
            String noise = RandomStringUtils.random(seed, '0', '1');
            Files.write(file.toPath(), noise.getBytes(), StandardOpenOption.APPEND);
            currentLength += seed;
        }
    }

    @Test
    public void findTokenIn100mFile() throws Throwable{
        File file = tempFolder.newFile("file100m");
        String token = "111111111111010110101010101010101010101011111010101010101010101010101010101011111111111111010101010" +
                "11111010101010101010101010101010101010101111010101010101010101010111111111111111111101010101111111111";

        generateFileWithTokenInside(file, 1000l * 1024 * 1024, token);

        Main.main(new String[]{file.getPath()});
        file.renameTo(new File("largefile"));
    }

    @Test
    public void findTokenIn200mFile() throws Throwable{
        File file = tempFolder.newFile("file200m");
        String token = "1111111111111101011010101010101010101010101010101010101010101010101010101011111111111111010101010" +
                "1111101010101010101010101010101010111111010101010101010101010101010111111111111111111101010101111111111";

        generateFileWithTokenInside(file, 200l * 1024 * 1024, token);
        Files.write(file.toPath(), ("00" + token + "0").getBytes(), StandardOpenOption.APPEND);

        Main.main(new String[]{file.getPath()});
    }

}
