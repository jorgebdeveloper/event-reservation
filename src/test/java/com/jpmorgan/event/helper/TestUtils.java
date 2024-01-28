package com.jpmorgan.event.helper;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class TestUtils {

    private static final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private static final PrintStream originalOut = System.out;

    public static void redirectSystemOut(Runnable task) {
        System.setOut(new PrintStream(outputStream));
        try {
            task.run();
        } finally {
            System.setOut(originalOut);
        }
    }

    public static String getSystemOut() {
        return outputStream.toString().trim();
    }

}
