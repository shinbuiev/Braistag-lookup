package com.braintags.lookup;


import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import static org.junit.Assert.*;

public class IndexLookupIntegrationTests {

//    @Test
//    public void indexLookupTest() throws Exception{
//        ClassLoader classLoader = getClass().getClassLoader();
//        Reader reader = new InputStreamReader(classLoader.getResourceAsStream("42.txt"));
//        BufferedReader br = new BufferedReader(reader);
//
//        //long[] indexes = Main.findIndexes(br);
//
//        assertEquals(2, indexes.length);
//
//        reader.close();
//        br.close();
//
//        reader = new InputStreamReader(classLoader.getResourceAsStream("42.txt"));
//        br = new BufferedReader(reader);
//
//        ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
//        PrintStream out = new PrintStream(baos);
//
//        //Main.printResult(br, indexes[0], indexes[1], out);
//
//        assertEquals("111111110111111111111111111111111111111111111111111111111111111111111111101111111111111111111111111111111111111111111111111111111111111111011111111111111111111111111111111111111111111111111111111111111110111111111111111111111111111111111111111111111111111111111111111101111111111111111111111111111111111111111111111111111111111111111011111111111111111111111111111111111111111111111111111111111111111010", baos.toString());
//        assertTrue(!baos.toString().contains("0"));
//
//        //assertEquals(3906366918L, indexes[1] - indexes[0]);
//        reader.close();
//        br.close();
//    }
//
//    @Test   //read file contains only one digits (111...11)
//    public void indexLookupCornerCaseTest() throws Exception{
//        ClassLoader classLoader = getClass().getClassLoader();
//        Reader reader = new InputStreamReader(classLoader.getResourceAsStream("4242.txt"));
//        BufferedReader br = new BufferedReader(reader);
//
//        //long[] indexes = Main.findIndexes(br);
//
//        assertEquals(2, indexes.length);
//
//        reader.close();
//        br.close();
//
//        reader = new InputStreamReader(classLoader.getResourceAsStream("file.txt"));
//        br = new BufferedReader(reader);
//
//        ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
//        PrintStream out = new PrintStream(baos);
//
//        Main.printResult(br, indexes[0], indexes[1], out);
//
//        assertTrue(!baos.toString().contains("0"));
//
//        assertEquals(3906366918L, indexes[1] - indexes[0]);
//        reader.close();
//        br.close();
//    }


}
