package org.example;

import java.math.BigDecimal;
import java.util.*;

public class StatsCollector {
    private int count = 0;
    private double sum = 0;
    private Double min = null;
    private Double max = null;

    private int minLength = Integer.MAX_VALUE;
    private int maxLength = Integer.MIN_VALUE;

    private boolean isNumber = false;
    private boolean isFloat = false;

    public void update(String value) {
        if (isIntFormat(value)) {
            try {
                double v = new BigDecimal(value).doubleValue();
                isNumber = true;
                addNumber(v);
                count++;
            } catch (NumberFormatException e) {
                System.err.println("WARNING: слишком большое целое число для статистики: " + value);
            }
        } else if (isFloatFormat(value)) {
            try {
                double v = new BigDecimal(value).doubleValue();
                isNumber = true;
                isFloat = true;
                addNumber(v);
                count++;
            } catch (NumberFormatException e) {
                System.err.println("WARNING: слишком большое вещественное число для статистики: " + value);
            }
        } else {
            int len = value.length();
            minLength = Math.min(minLength, len);
            maxLength = Math.max(maxLength, len);
            count++;
        }
    }

    private void addNumber(double v) {
        sum += v;
        if (min == null || v < min) min = v;
        if (max == null || v > max) max = v;
    }

    public void printFull() {
        System.out.println("Количество: " + count);
        if (count != 0) {
            if (isNumber) {
                System.out.printf("Минимум: %.3f\n", min);
                System.out.printf("Максимум: %.3f\n", max);
                System.out.printf("Сумма: %.3f\n", sum);
                System.out.printf("Среднее: %.3f\n", sum / count);
            } else {
                System.out.printf("Минимальная длина: %d\n", minLength);
                System.out.printf("Максимальная длина: %d\n", maxLength);
            }
        }
    }

    private boolean isIntFormat(String s) {
        return Qualifier.isInteger(s);
    }

    private boolean isFloatFormat(String s) {
        return Qualifier.isFloat(s);
    }
}
