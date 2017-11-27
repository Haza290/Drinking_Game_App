package utility;

        import java.util.HashMap;
        import java.util.Random;

        import schneidernetwork.drinkinggameapp.GameLogic;

public class TextParser {


    /**
     * Add's player names and changes [x-y] to a int between x and y and [x/y/z] to x or y or z
     * @param text
     * @param playersMap
     * @return
     */
    public static StringAndHashMap evaluateString(String text, HashMap<String, String> playersMap) {
        text = parseText(text);
        StringAndHashMap stringAndHashMap = addPlayerNamesToText(text, playersMap);

        return stringAndHashMap;
    }

    public static StringAndHashMap evaluateString(String text) {
        return evaluateString(text, new HashMap<String, String>());
    }

    public static int calculateNumOfPlayersNeeded(String text) {

        // Splits the text into strings which starting with a players name
        String[] strings = text.split("~");
        int numOfPlayersNeeded = strings.length - 1;

        for (String string : strings) {
            if (string.length() == 0) {
                numOfPlayersNeeded--;
            }
        }

        return numOfPlayersNeeded;
    }

    /**
     * Changes all "~" to a players names
     * @param text
     * @param playersMap
     * @return
     */
    private static StringAndHashMap addPlayerNamesToText(String text, HashMap<String, String> playersMap) {

        // Splits the text into strings which starting with a players name
        String[] strings = text.split("~");

        // Ignores the first string as it
        for (int i = 1; i < strings.length; i++) {
            String[] words = strings[i].split(" ");

            // Fixes error that occurs when "test ~A is ~ ~ ~D" is used as text
            // TODO rewrite this comment
            if(words.length == 0) {
                continue;
            }

            // If key isn't in players then add it with a random players name
            // that isn't already in the hashmap
            if (!playersMap.containsKey(words[0])) {
                String randomPlayer;
                do {
                    randomPlayer = getRandomPlayerName();
                } while (playersMap.containsValue(randomPlayer));
                playersMap.put(words[0], randomPlayer);
            }

            // Replace the key with the value recreate the string and add it
            // Back to strings
            words[0] = playersMap.get(words[0]);
            String string = "";
            for (String word : words) {
                string += word + " ";
            }
            strings[i] = string;
        }

        // Recreate the text from strings
        text = "";
        for (String string : strings) {
            text += string;
        }

        return new StringAndHashMap(text, playersMap);
    }

    /**
     * Returns a random players name
     */
    private static String getRandomPlayerName() {
        Random random = new Random();
        return GameLogic.playerNames.get(random.nextInt(GameLogic.playerNames.size()));
    }

    /**
     * Charges [x-y] to a random int between x and y and charges [x/y/z] to x or y or z
     * @param text
     * @return
     */
    private static String parseText(String text) {

        // If text doesn't contain an expression to evaluate return the text
        if(!text.contains("[")) {
            return text;
        }

        String[] stringsSplitbeforeBracket = text.split("\\[");

        for (int i = 1; i < stringsSplitbeforeBracket.length; i++) {
            String[] stringsSplitAroundBracket = stringsSplitbeforeBracket[i].split("\\]");
            stringsSplitAroundBracket[0] = evaluateExpresion(stringsSplitAroundBracket[0]);

            // Recreates string
            String string = "";
            for (String string2 : stringsSplitAroundBracket) {
                string += string2;
            }
            stringsSplitbeforeBracket[i] = string;
        }

        text = "";
        for (String string : stringsSplitbeforeBracket) {
            text += string;
        }

        return text;
    }

    private static String evaluateExpresion(String expression) {

        Random random = new Random();

        // If the expression is [x-y] return a random int between x and y
        if(expression.contains("-")) {
            String[] ints = expression.split("-");

            // If expression is not in the form [X-Y] print err
            if(ints.length != 2) {
                System.err.println("expression is formatted wrong: " + expression);
            }

            // Turn strings to ints
            int x = Integer.parseInt(ints[0]);
            int y = Integer.parseInt(ints[1]);

            // Swap x and y is x > y
            if (x > y) {
                int temp = x;
                x = y;
                y = temp;
            }

            // Generate a random number between x and y and turn it into a string
            int randInt = random.nextInt(y-x + 1) + x;
            expression = Integer.toString(randInt);
        }
        // Else if expression is [x/y/z] return x or y or z at random
        else if (expression.contains("/")) {
            String[] options = expression.split("/");
            expression = options[random.nextInt(options.length)];
        }
        return expression;
    }
}

