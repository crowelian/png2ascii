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
        String param3 = args.length > 2 ? args[2] : "output.png";

        if (args.length > 1) {
            if (args[1] == "false" || args[1].contains("false")) {
                param2 = false;
            }
        }

        System.out.println("Converting png " + param1 + " to ascii...");
        if (param2 == false) {
            System.out.println("saving as png...");
        }
        

        PngToAsciiConverter.convert(param1, param2, param3);

    }
}
