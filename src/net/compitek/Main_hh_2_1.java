package net.compitek;


import java.util.*;

/**
 * 1. Минимальное рассто¤ние
 * Дан набор из N точек на плоскости (для простоты можно считать, что у всех точек целочисленные координаты). Найдите минимальное расстояние между двум¤ точками из этого набора.
 * <p>
 * ѕример входных данных:
 * 10 10
 * 20 10
 * 20 15
 * <p>
 * ѕример выходных данных:
 *
 */
public class Main_hh_2_1 {


    final static int randomSize = 50000;
    final static long randomMax = 1000000;


    public static void main(String[] args) {
        printInstructions();
        List<Coord> coordList = new ArrayList<>();
        Long x;
        Long y;
        String nextLineArray[];
        try (Scanner in = new Scanner(System.in)) {
            while (in.hasNextLine()) {
                nextLineArray = in.nextLine().trim().split("\\s", 3);
                if (nextLineArray.length == 2 && (x = stringToLong(nextLineArray[0])) != null && (y = stringToLong(nextLineArray[1])) != null) {
                    coordList.add(new Coord(x, y));
                    System.out.println("got it.");
                } else if (nextLineArray.length == 1 && nextLineArray[0].equals("random")) {
                    coordList = new ArrayList<>(Arrays.asList(randomCoordArray(randomSize, randomMax)));
                    printInstructions();
                } else if (nextLineArray.length == 1 && nextLineArray[0].equals("print")) {
                    printCoordList(coordList);
                } else if (nextLineArray.length == 1 && nextLineArray[0].equals("clear")) {
                    coordList = new ArrayList<>();
                    System.out.println("Array of coords is empty;");
                } else if (nextLineArray.length == 1 && (nextLineArray[0].equals("help") || nextLineArray[0].equals("/?"))) {
                    printInstructions();
                } else if (nextLineArray.length == 1 && nextLineArray[0].equals("go")) {
                    System.out.println("Started \"simple\" algorithm");
                    calculationProcess(coordList);
                } else if (nextLineArray.length == 1 && nextLineArray[0].equals("quick")) {
                    System.out.println("Started \"quickie\" algorithm");
                    calculationProcess(coordList, true);
                } else if (nextLineArray.length == 1 && nextLineArray[0].equals("exit")) {
                    break;
                } else {
                    System.out.println("wrong command;");
                }

            }
        }
        catch (NumberFormatException exc){
            System.out.println(" too big value of coordinate. Sorry.");
            exc.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns a {@code Long} object holding the value
     * of the specified {@code String} or null
     * if strInteger can't be interpreted validly.
     *
     * @param strInteger the string to be parsed.
     * @return a {@code Long} object holding the value
     * represented by the string argument
     * or null if representation impossible.
     */
    private static Long stringToLong(String strInteger) {
        Long response;
        try {
            response = Long.valueOf(strInteger);
        } catch (NumberFormatException e) {
            return null;
        }
        return response;
    }


    /**
     * Prints the list of instructions in System.out stream.
     */
    private static void printInstructions() {
        System.out.println(" ");
        System.out.println("you can add pairs of coordinates (e.g. \"150 15\" ); ");
        System.out.println("write \"clear\" to clear array of coordinates;");
        System.out.println("write \"print\" to look to array of coordinates;");
        System.out.println("write \"random\" to clear and write randomised array of coordinates;");
        System.out.println("write \"go\" to start calculation with simple algorithm");
        System.out.println("write \"quick\" to start calculation with more quickie(up to 20%) algorithm");
        System.out.println("write \"exit\" to finish");
    }

    /**
     * calculates with {@code findNearestLengthSimple} method and prints the results,
     * including the calculation of the time (in ms.),
     * the next number of points in a given array
     * and the coordinates of these points
     *
     * @param coordList List of Coords objects.
     */
    private static void calculationProcess(List<Coord> coordList) {
        calculationProcess(coordList, false);
    }


    /**
     * calculates
     * (with {@code findNearestLengthSimple}  method if {@code quick} is false,
     * and {@code findNearestQuickie} method if {@code quick} is true)
     * and prints the results,
     * including the calculation of the time (in ms.),
     * the next number of points in a given array
     * and the coordinates of these points
     *
     * @param coordList List of Coords objects.
     * @param quick     if true, calculating with {@code findNearestQuickie} method,
     *                  otherwise calculating with {@code findNearestLengthSimple}method.
     */
    private static void calculationProcess(List<Coord> coordList, boolean quick) throws IllegalArgumentException {
        if (coordList.size() >= 2) {
            Date startDate = new Date();
            double answer;
            try {
                answer = quick ? findNearestQuickie(coordList) : findNearestLengthSimple(coordList);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                throw new IllegalArgumentException(e.getMessage());
            }
            System.out.println(answer);
            printInstructions();

        } else if (coordList.size() < 2) {
            System.out.println("please, add more pairs of coordinates");
            printInstructions();
        }

    }

    /**
     * return array of Coord array with {@code size} and randomise x and y values from -{@code max} to {@code max}.
     *
     * @param size - size of returned array
     * @param max  define boundary values of coordinates.
     * @return Coord[size] with randomised x and y values.
     */
    private static Coord[] randomCoordArray(int size, long max) {
        Coord[] result = new Coord[size];

        for (int i = 0; i < size; i++) {
            result[i] = new Coord(randomLong(max), randomLong(max));
            System.out.println(i + ". (" + result[i].getX() + ", " + result[i].getY() + ");");
        }
        System.out.println("");
        return result;
    }


    /**
     * generate long value from -{@code max} to {@code max}
     *
     * @param max boundary values of generated long.
     * @return
     */
    private static long randomLong(long max) {
        return Math.round(Math.random() * 2 * max) - max;
    }

    private static void printCoordList(List<Coord> coordList) {
        if (coordList.size() > 0)
            for (Coord coord : coordList) {
                System.out.println(coordList.indexOf(coord) + ". (" + coord.getX() + ", " + coord.getY() + ");");
            }
        else
            System.out.println("Array of coords is empty;");

    }


    /**
     * @return square of length between two {@code Coord-objects}
     */
    private static double squareLength(Coord pixel1, Coord pixel2) {
        return Math.pow((pixel1.getX() - pixel2.getX()), 2) + Math.pow((pixel1.getY() - pixel2.getY()), 2);
    }

    /**
     * @return square of length between two coordinates of one axis
     */
    private static double squareLength(long x1, long x2) {
        return Math.pow((x1 - x2), 2);
    }


    /**
     * find two nearest pixels(like {@code Coord} objects)
     *
     * @param coordList List of Coord objects
     * @return Coord[2] array with two "nearest" points
     */
    private static double findNearestQuickie(List<Coord> coordList) throws IllegalArgumentException {
        if (coordList.size() < 2)
            throw new IllegalArgumentException(" Wrong input data ");


        Coord pixel1;
        Coord pixel2;
        double squaredLength;
        double squaredLengthX;
        double squaredLengthY;
        double squaredNewLength;

        pixel1 = coordList.get(coordList.size() - 2);
        pixel2 = coordList.get(coordList.size() - 1);
        //we get two last pixels
        squaredLength = squareLength(pixel1, pixel2);
        if (coordList.size() > 2) {
            // we compare length between every other pixels and between that last two pixels. The same as in "simple" but...
            for (int i = coordList.size() - 3; i > -1; i--) {
                for (int j = i + 1; j < coordList.size(); j++) {
                    //...but at first we compare length with only x length
                    squaredLengthX = squareLength(coordList.get(i).getX(), coordList.get(j).getX());
                    if (squaredLength <= squaredLengthX) continue;

                    //at second we compare length with only Y length
                    squaredLengthY = squareLength(coordList.get(i).getY(), coordList.get(j).getY());
                    if (squaredLength <= squaredLengthY) continue;

                    //and finally we compare lengths fully
                    squaredNewLength = (squaredLengthX + squaredLengthY);
                    if (squaredLength > squaredNewLength)
                        squaredLength = squaredNewLength;
                }
            }
        }
        return Math.sqrt(squaredLength);
    }

    /**
     * find two nearest pixels(like {@code Coord} objects)
     * with turning over all items in coordList and comparing lengths between  them
     *
     * @param coordList List of Coord objects
     * @return Coord[2] array with two "nearest" points
     */
    private static double findNearestLengthSimple(List<Coord> coordList) throws IllegalArgumentException {
        if (coordList.size() < 2)
            throw new IllegalArgumentException(" Wrong input data ");


        Coord pixel1;
        Coord pixel2;
        double squaredLength;
        double squaredNewLength;

        //we get two last pixels
        pixel1 = coordList.get(coordList.size() - 2);
        pixel2 = coordList.get(coordList.size() - 1);
        squaredLength = squareLength(pixel1, pixel2);

        if (coordList.size() > 2) {
            // we compare length between every other pixels and between that last two pixels
            for (int i = coordList.size() - 3; i > -1; i--) {
                for (int j = i + 1; j < coordList.size(); j++) {
                    pixel1 = coordList.get(i);
                    pixel2 = coordList.get(j);
                    squaredNewLength = squareLength(pixel1, pixel2);
                    if (squaredLength > squaredNewLength)
                        squaredLength = squaredNewLength;
                }
            }
        }
        return Math.sqrt(squaredLength);
    }

}
