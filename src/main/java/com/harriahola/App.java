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
        
        String inputImageParam = args.length > 0 ? args[0] : "image.png";
        Boolean printAsAsciiParam = args.length > 1 ? (args[1].contains("false") ? false : true) : true;
        int defaultHeight = printAsAsciiParam ? 80 : 160;
        String outputImageParam = args.length > 2 ? args[2] : "output.png";
        int desiredHeightParam = args.length > 3 ? parseDesiredHeight(args[3], defaultHeight) : defaultHeight;

        System.out.println("Converting png " + inputImageParam + " to ascii...");
        if (printAsAsciiParam == false) {
            System.out.println("saving as png...");
        }
        
        PngToAsciiConverter.convert(inputImageParam, printAsAsciiParam, outputImageParam, desiredHeightParam);

    }

    static <T> int parseDesiredHeight(T value, int defaultValue) {
        int parsedValue = defaultValue;
        try {
            parsedValue = Integer.parseInt((String) value);
        } catch (NumberFormatException error) {
            System.out.println("Error: "+error+", Please provide the desired height as a numeric string or int... Using default value: " + defaultValue);
        }
        return parsedValue;
    }
}
