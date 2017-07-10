package com.braintags.lookup;


import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.boot.test.rule.OutputCapture;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.braintags.lookup.Printer.print;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

public class PrinterTests {

    @Rule
    public OutputCapture capture = new OutputCapture();

    @Before
    public void initFile() throws Exception{

        Path filePath = Paths.get("test.txt");
        if (Files.notExists(filePath)){
            File file = new File(filePath.toString());

            FileWriter writer = new FileWriter(file);
            writer.write("011111110101010101010111111010010101001010100110101010101010000000000000111111111111010010101111001010100110101010101010000000000000111111111111111011111110101010101010111111010010101001010100110101010101010000000000000111111111111010010101111001010100110101010101010000000000000111111111111111011111110101010101010111111010010101001010100110101010101010000000000000111111111111010010101111001010100110101010101010000000000000111111111111111011111110101010101010111111010010101001010100110101010101010000000000000111111111111010010101111001010100110101010101010000000000000111111111111111011111110101010101010111111010010101001010100110101010101010000000000000111111111111010010101111001010100110101010101010000000000000111111111111111011111110101010101010111111010010101001010100110101010101010000000000000111111111111010010101111001010100110101010101010000000000000111111111111111011111110101010101010111111010010101001010100110101010101010000000000000111111111111010010101111001010100110101010101010000000000000111111111111111011111110101010101010111111010010101001010100110101010101010000000000000111111111111010010101111001010100110101010101010000000000000111111111111111011111110101010101010111111010010101001010100110101010101010000000000000111111111111010010101111001010100110101010101010000000000000111111111111111011111110101010101010111111010010101001010100110101010101010000000000000111111111111010010101111001010100110101010101010000000000000111111111111111011111110101010101010111111010010101001010100110101010101010000000000000111111111111010010101111001010100110101010101010000000000000111111111111111011111110101010101010111111010010101001010100110101010101010000000000000111111111111010010101111001010100110101010101010000000000000111111111111111011111110101010101010111111010010101001010100110101010101010000000000000111111111111010010101111001010100110101010101010000000000000111111111111111011111110101010101010111111010010101001010100110101010101010000000000000111111111111010010101111001010100110101010101010000000000000111111111111111011111110101010101010111111010010101001010100110101010101010000000000000111111111111010010101111001010100110101010101010000000000000111111111111111011111110101010101010111111010010101001010100110101010101010000000000000111111111111010010101111001010100110101010101010000000000000111111111111111011111110101010101010111111010010101001010100110101010101010000000000000111111111111010010101111001010100110101010101010000000000000111111111111111011111110101010101010111111010010101001010100110101010101010000000000000111111111111010010101111001010100110101010101010000000000000111111111111111011111110101010101010111111010010101001010100110101010101010000000000000111111111111010010101111001010100110101010101010000000000000111111111111111011111110101010101010111111010010101001010100110101010101010000000000000111111111111010010101111001010100110101010101010000000000000111111111111111011111110101010101010111111010010101001010100110101010101010000000000000111111111111010010101111001010100110101010101010000000000000111111111111111011111110101010101010111111010010101001010100110101010101010000000000000111111111111010010101111001010100110101010101010000000000000111111111111111011111110101010101010111111010010101001010100110101010101010000000000000111111111111010010101111001010100110101010101010000000000000111111111111111011111110101010101010111111010010101001010100110101010101010000000000000111111111111010010101111001010100110101010101010000000000000111111111111111011111110101010101010111111010010101001010100110101010101010000000000000111111111111010010101111001010100110101010101010000000000000111111111111111011111110101010101010111111010010101001010100110101010101010000000000000111111111111010010101111001010100110101010101010000000000000111111111111111011111110101010101010111111010010101001010100110101010101010000000000000111111111111010010101111001010100110101010101010000000000000111111111111111011111110101010101010111111010010101001010100110101010101010000000000000111111111111010010101111001010100110101010101010000000000000111111111111111011111110101010101010111111010010101001010100110101010101010000000000000111111111111010010101111001010100110101010101010000000000000111111111111111011111110101010101010111111010010101001010100110101010101010000000000000111111111111010010101111001010100110101010101010000000000000111111111111111011111110101010101010111111010010101001010100110101010101010000000000000111111111111010010101111001010100110101010101010000000000000111111111111111011111110101010101010111111010010101001010100110101010101010000000000000111111111111010010101111001010100110101010101010000000000000111111111111111011111110101010101010111111010010101001010100110101010101010000000000000111111111111010010101111001010100110101010101010000000000000111111111111111011111110101010101010111111010010101001010100110101010101010000000000000111111111111010010101111001010100110101010101010000000000000111111111111111011111110101010101010111111010010101001010100110101010101010000000000000111111111111010010101111001010100110101010101010000000000000111111111111111011111110101010101010111111010010101001010100110101010101010000000000000111111111111010010101111001010100110101010101010000000000000111111111111111011111110101010101010111111010010101001010100110101010101010000000000000111111111111010010101111001010100110101010101010000000000000111111111111111011111110101010101010111111010010101001010100110101010101010000000000000111111111111010010101111001010100110101010101010000000000000111111111111111011111110101010101010111111010010101001010100110101010101010000000000000111111111111010010101111001010100110101010101010000000000000111111111111111011111110101010101010111111010010101001010100110101010101010000000000000111111111111010010101111001010100110101010101010000000000000111111111111111011111110101010101010111111010010101001010100110101010101010000000000000111111111111010010101111001010100110101010101010000000000000111111111111111011111110101010101010111111010010101001010100110101010101010000000000000111111111111010010101111001010100110101010101010000000000000111111111111111011111110101010101010111111010010101001010100110101010101010000000000000111111111111010010101111001010100110101010101010000000000000111111111111111011111110101010101010111111010010101001010100110101010101010000000000000111111111111010010101111001010100110101010101010000000000000111111111111111011111110101010101010111111010010101001010100110101010101010000000000000111111111111010010101111001010100110101010101010000000000000111111111111111011111110101010101010111111010010101001010100110101010101010000000000000111111111111010010101111001010100110101010101010000000000000111111111111111011111110101010101010111111010010101001010100110101010101010000000000000111111111111010010101111001010100110101010101010000000000000111111111111111011111110101010101010111111010010101001010100110101010101010000000000000111111111111010011");
            writer.flush();
            writer.close();
        }
    }

    @Test
    public void printBeginTokenTest() throws Throwable {

        String filePath = "test.txt";
        print(filePath, new Token(0, 10), System.out);
        assertThat(capture.toString(), containsString("01111111010"));
    }

    @Test
    public void printMiddleTokenTest() throws Throwable {

        String path = "test.txt";
        print(path, new Token(10, 20), System.out);
        assertThat(capture.toString(), containsString("001010101010"));
    }

    @Test
    public void printEndTokenTest() throws Throwable {

        String path = "test.txt";
        print(path, new Token(6990, 7000), System.out);
        assertThat(capture.toString(), containsString("0111010011"));
    }
}