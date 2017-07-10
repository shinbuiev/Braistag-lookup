package com.braintags.lookup;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws Throwable {
        if (args.length == 0) {
            throw new IllegalArgumentException("Path to file should be provided");
        }

        Path filePath = Paths.get(args[0]);
        if (Files.notExists(filePath))
            throw new FileNotFoundException("File not found by path specified: " + filePath);

        int cores = Runtime.getRuntime().availableProcessors();
        long size = Files.size(filePath);
        long maxMemory = Runtime.getRuntime().maxMemory();
        ExecutorService executor = Executors.newWorkStealingPool(cores * 2);

        System.out.println("running search of longest sequence of characters not containing 2 consecutive “0” characters");
        System.out.println(String.format("cores: %d, max memory: %d\nfile: %s, size: %d", cores, maxMemory, filePath, size));

        // Logic:
        // split file to chunks
        // scan each chunk in parallel
        // reduce the tokens in each chunk
        // await chunk processing and merge the tokens from each chunk
        // determine the longest sequence
        // print results

        long chunkSize = size / (cores);

        List<FileChunkScanner> scanners = new ArrayList<>();

        for (long seed = 0; seed < size; seed += chunkSize) {
            long capacity = seed + chunkSize < size ? chunkSize : size - seed;
            scanners.add(new FileChunkScanner(filePath.toString(), seed, capacity));
        }

        long timeMillis = System.currentTimeMillis();
        System.out.println(String.format("starting %d threads to scan the file", scanners.size()));

        List<Callable<Reducer>> callables = scanners.stream().map(FileChunkScanner::async).collect(Collectors.toList());

        List<Reducer> scannerResults = executor.invokeAll(callables).stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                }).collect(Collectors.toList());

        if (scannerResults.contains(null)) {
            System.out.println("scan completed with error");
            return;
        } else {
            float elapsedSeconds = (System.currentTimeMillis() - timeMillis) / 1000f;
            System.out.println(String.format("scan completed successfully in %f seconds", elapsedSeconds));
        }

        List<Token> tokens = scannerResults.stream()
                .filter(reducer -> !reducer.tokens.isEmpty())
                .reduce(new Reducer(), (acc, next) -> {
                    next.tokens.forEach(acc::reduce);
                    acc.compact();
                    return acc;
                }).tokens;

        Token oneOfTheLongest = tokens.stream().max(Token.byLengthComparator).get();
        List<Token> longest = tokens.stream()
                .filter(token -> token.length() == oneOfTheLongest.length())
                .collect(Collectors.toList());
//        System.out.println("the longest 10 tokens:");
//        longest.stream()
//                .limit(10)
//                .forEach(token -> {
//                    try {
//                        Printer.print(filePath.toString(), token, System.out);
//                    } catch (Throwable throwable) {
//                        throwable.printStackTrace();
//                    }
//                });

        executor.shutdown();
    }

}
