package org.example;

import java.util.*;

public class FilterResult {
    private final List<String> data = new ArrayList<>();
    private final StatsCollector stats = new StatsCollector();

    public void add(String value) {
        data.add(value);
        stats.update(value);
    }

    public boolean isEmpty() { return data.isEmpty(); }

    public List<String> getData() { return data; }

    public void printShort(String label) {
        System.out.printf("%s: %d элементов\n", label, data.size());
    }

    public void printFull(String label) {
        System.out.printf("\n[%s]\n", label);
        stats.printFull();
    }
}