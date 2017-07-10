package com.braintags.lookup;


import org.apache.commons.io.input.BoundedInputStream;

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
import java.util.EnumSet;
import java.util.Scanner;

public class Printer {

    private static final int size = 8192;

    public static void print(String path, Token token, PrintStream stream) throws IOException {
        RandomAccessFile file = new RandomAccessFile(path, "r");

        if (token.start != 0) stream.print('0');

        FileInputStream fileInputStream = new FileInputStream(path);
        fileInputStream.skip(token.start);
        BufferedReader br = new BufferedReader(
                new InputStreamReader(new BoundedInputStream(fileInputStream, token.end - token.start)));

        CharBuffer chbuff = CharBuffer.allocate(1024);
        while(br.read(chbuff) > 0){
            chbuff.flip();
            while(chbuff.hasRemaining()){
                char ch =  chbuff.get();
                stream.print(ch);
            }
            chbuff.clear();
        }
        br.close();

//        MappedByteBuffer buffer = file.getChannel().map(FileChannel.MapMode.READ_ONLY, token.start, token.end - token.start);
//
//        while(buffer.hasRemaining()){
//            int remaining = buffer.remaining();
//            int sizeToGet = remaining > size ? size : remaining;
//            ByteBuffer byteBuffer = ByteBuffer.allocate(sizeToGet);
//
//            buffer.get(byteBuffer.array(), 0, sizeToGet);
//            Reader reader = new InputStreamReader(new ByteArrayInputStream(byteBuffer.array()));
//
//            int c;
//
//            while((c = reader.read()) > 0){
//                stream.print((char) c);
//            }
//
//            reader.close();
//        }
        if (token.end < file.length()) stream.print('0');
    }

//    public static void print(String path, Token token, PrintStream stream) throws Throwable {
//        RandomAccessFile file = new RandomAccessFile(path, "r");
//        long fileLength = file.length();
//
//        if (token.start != 0) stream.print('0');
//
//        file.seek(token.start);
//
//        for (int current = 0; current < token.length(); current++) {
//            stream.write((char) file.readByte());
//        }
//
//        if (token.end < fileLength) stream.append('0');
//
//        file.close();
//    }

    public static void print2(String filePath, Token token, PrintStream stream) throws Throwable {
        CharBuffer charBuffer;
        String charEncoding;
        MappedByteBuffer mappedByteBuffer;
        charEncoding = System.getProperty("file.encoding");
        Path path = Paths.get(filePath);
        try (FileChannel fileChannel = (FileChannel) Files.newByteChannel(path, StandardOpenOption.READ)) {
            mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, token.start, token.length());
            if (mappedByteBuffer != null) {
                charBuffer = Charset.forName(charEncoding).decode(mappedByteBuffer);
                stream.append(charBuffer);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }

}
