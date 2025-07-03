package org.example;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

public class DataFilter {
    private static final String DEFAULT_PREFIX = "";
    private static final String DEFAULT_DIR = ".";
    private static final String INTEGER_FILE = "integers.txt";
    private static final String FLOAT_FILE = "floats.txt";
    private static final String STRING_FILE = "strings.txt";

    public static void main(String[] args) {
        List<String> files = new ArrayList<>();
        String outDir = DEFAULT_DIR;
        String prefix = DEFAULT_PREFIX;
        boolean append = false;
        boolean fullStats = false;
        boolean shortStats = false;

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-o" -> outDir = args[++i];
                case "-p" -> prefix = args[++i];
                case "-a" -> append = true;
                case "-f" -> fullStats = true;
                case "-s" -> shortStats = true;
                default -> files.add(args[i]);
            }
        }

        //if (!shortStats && !fullStats) shortStats = true;

        FilterResult integers = new FilterResult();
        FilterResult floats = new FilterResult();
        FilterResult strings = new FilterResult();

        for (String file : files) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty()) continue;

                    if (isInteger(line)) {
                        integers.add(line);
                    } else if (isFloat(line)) {
                        floats.add(line);
                    } else {
                        strings.add(line);
                    }
                }
            } catch (IOException e) {
                System.err.println("Ошибка чтения файла " + file + ": " + e.getMessage());
            }
        }

        try {
            Path outputDirPath = Paths.get(outDir);
            Files.createDirectories(outputDirPath);
            writeResults(outDir, prefix + INTEGER_FILE, integers, append);
            writeResults(outDir, prefix + FLOAT_FILE, floats, append);
            writeResults(outDir, prefix + STRING_FILE, strings, append);
        } catch (IOException e) {
            System.err.println("Ошибка записи результатов: " + e.getMessage());
        }


        if (fullStats) {
            integers.printFull("Целые");
            floats.printFull("Вещественные");
            strings.printFull("Строки");
        } else if (shortStats) {
            integers.printShort("Целые");
            floats.printShort("Вещественные");
            strings.printShort("Строки");
        }
    }

    private static boolean isInteger(String s) {
        try {
            return Qualifier.isInteger(s);
        } catch (Exception e) { return false; }
    }

    private static boolean isFloat(String s) {
        try {
            return Qualifier.isFloat(s);
        } catch (Exception e) { return false; }
    }

    private static void writeResults(String outDir, String name, FilterResult result, boolean append) throws IOException {
        if (result.isEmpty()) return;
        Path path = Paths.get(outDir, name);
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                append ? StandardOpenOption.APPEND : StandardOpenOption.TRUNCATE_EXISTING
        )) {
            for (String line : result.getData()) {
                writer.write(line);
                writer.newLine();
            }
        }
    }
}
