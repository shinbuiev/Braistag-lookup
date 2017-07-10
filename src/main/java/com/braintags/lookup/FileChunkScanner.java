package com.braintags.lookup;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

public class FileChunkScanner {
    private final String path;
    private final long seed;
    private final long offset;
    private final int size = 8192; //might be optimal size

    public FileChunkScanner(String path, long seed, long offset) {

        this.path = path;
        this.seed = seed;
        this.offset = offset;
    }

    public Callable<Reducer> async() {
        return () -> {
            Reducer reducer = new Reducer();
            scan(reducer::reduce);
            return reducer;
        };
    }

    /**
     * @param queue
     * Method for testing purposes
     */
    public void scan(Queue<Token> queue) throws Exception {
        scan(queue::add);
    }

    public void scan(Consumer<Token> consumer) throws Exception {
        RandomAccessFile file = new RandomAccessFile(path, "r");

        file.seek(seed);
        MappedByteBuffer buffer = file.getChannel().map(FileChannel.MapMode.READ_ONLY, seed, offset);

        int shift = 0;
        while(buffer.hasRemaining()){
            int remaining = buffer.remaining();
            int sizeToGet = remaining > size ? size : remaining;
            ByteBuffer byteBuffer = ByteBuffer.allocate(sizeToGet);

            buffer.get(byteBuffer.array(), 0, sizeToGet);
            Reader reader = new InputStreamReader(new ByteArrayInputStream(byteBuffer.array()));

            Tokenizer.tokenize(reader, seed + shift, consumer);
            shift += sizeToGet;

            reader.close();
        }
    }
//
//    public void scan2(Consumer<Token> consumer) throws Exception {
//        String charEncoding = System.getProperty("file.encoding");
//        Path filePath = Paths.get(path);
//        try (FileChannel fileChannel = (FileChannel) Files.newByteChannel(filePath, StandardOpenOption.READ)) {
//            MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, seed, offset);
//            if (mappedByteBuffer != null) {
//                CharBuffer charBuffer = Charset.forName(charEncoding).decode(mappedByteBuffer);
//                Tokenizer.tokenize(charBuffer, seed, consumer);
//            }
//        } catch (IOException ioe) {
//            ioe.printStackTrace();
//        }
//    }
//
//    public static void print2(String path, Token token, PrintStream stream) throws Exception {
//        String charEncoding = System.getProperty("file.encoding");
//        Path filePath = Paths.get(path);
//        try (FileChannel fileChannel = (FileChannel) Files.newByteChannel(filePath, StandardOpenOption.READ)) {
//            MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, fileChannel.size());
//            if (mappedByteBuffer != null) {
//                CharBuffer charBuffer = Charset.forName(charEncoding).decode(mappedByteBuffer);
//                stream.append(charBuffer, (int)token.start, (int)token.end);
//            }
//        } catch (IOException ioe) {
//            ioe.printStackTrace();
//        }
//    }

}
