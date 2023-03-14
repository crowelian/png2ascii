package com.harriahola;

/**
 * Hello world!
 */
public final class App {
    private App() {
    }

    /**
     * Says hello to the world.
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        String param1 = args.length > 0 ? args[0] : "image.png";
        Boolean param2 = true;
        System.out.println("Converting png " + param1 + "to ascii...");

        PngToAsciiConverter.convert(param1, param2);
    }
}
