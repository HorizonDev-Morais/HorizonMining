package com.horizondev.mining.utils;

import com.horizondev.mining.MiningPlugin;
import java.text.DecimalFormat;

//RETIRADO DO HEROVENDER

public class NumberUtil {
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("###,###.##");
    private static final String[] numberFormatShortSuffix = MiningPlugin.getMinePlugin().getConfig().getString("number-formatting").split(";");

    public static String format(double value) {
        return DECIMAL_FORMAT.format(value);
    }

    public static String formatShort(double value) {
        return formatShort(value, 0);
    }

    private static String formatShort(double n, int iteration) {
        double f = ((long) n / 100) / 10.0D;
        return f < 1000 || iteration >= numberFormatShortSuffix.length - 1 ? format(f) + numberFormatShortSuffix[iteration] : formatShort(f, iteration + 1);
    }

}