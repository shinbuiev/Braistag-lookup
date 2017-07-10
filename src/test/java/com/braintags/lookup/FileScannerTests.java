package com.braintags.lookup;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.LinkedList;
import java.util.Queue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FileScannerTests {

    @Rule
    public final TemporaryFolder tempFolder = new TemporaryFolder();

    public final int DefaultLargeFileSize = 32 * 1024 * 1024;

    public void prepareTestFile(File file, String content) throws Throwable {
        Files.write(file.toPath(), content.getBytes(), StandardOpenOption.WRITE);
        assertEquals(file.length(), content.length());
        assertEquals(Files.readAllLines(file.toPath()).get(0), content);
    }

    @Test
    @Ignore
    public void testSimpleFile() throws Throwable {
        File file = tempFolder.newFile("simple");
        String content = "010101010";

        prepareTestFile(file, content);

        LinkedList<Token> queue = new LinkedList<>();
        FileChunkScanner task = new FileChunkScanner(file.getPath(), 0, file.length());
        task.scan(queue);
        assertEquals(queue.getFirst().getTokenString(), content);
    }

    @Test
    public void testLongFileWith0() throws Throwable {
        File file = tempFolder.newFile("large_with_0");
        String content = RandomStringUtils.random(DefaultLargeFileSize, '0');

        prepareTestFile(file, content);

        LinkedList<Token> queue = new LinkedList<>();
        FileChunkScanner task = new FileChunkScanner(file.getPath(), 0, file.length());
        task.scan(queue);
        assertTrue(queue.isEmpty());
    }

    @Test
    public void testLongFileWith1() throws Throwable {
        File file = tempFolder.newFile("large_with_1");
        String content = RandomStringUtils.random(DefaultLargeFileSize, '1');

        prepareTestFile(file, content);

        LinkedList<Token> queue = new LinkedList<>();
        FileChunkScanner task = new FileChunkScanner(file.getPath(), 0, file.length());
        task.scan(queue);

        Queue<Token> reduced = Reducer.reduce(queue);

        assertEquals(reduced.size(), 1);
        Token actual = reduced.peek();
        assertTrue(actual.start == 0 && actual.end == content.length());
    }

    @Test
    public void testLongFileWithResultInTheMiddle() throws Throwable {
        File file = tempFolder.newFile("large_with_result_in_the_middle");
        String content = new StringBuilder()
                .append(RandomStringUtils.random(DefaultLargeFileSize / 2, '0'))
                .append("10101010101")
                .append(RandomStringUtils.random(DefaultLargeFileSize / 2, '0')).toString();

        prepareTestFile(file, content);

        LinkedList<Token> queue = new LinkedList<>();
        FileChunkScanner task = new FileChunkScanner(file.getPath(), 0, file.length());
        task.scan(queue);

        assertEquals(queue.size(), 1);
        assertEquals(queue.getFirst().getTokenString(), "10101010101");
    }
}
