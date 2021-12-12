package bullscows;

import java.util.Scanner;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        RandomGenerator randomGenerator =  new RandomGenerator();
        String secretNumber = randomGenerator.numberGenerator();
        int randomLength = scanner.nextInt();

        if (randomLength > 10) {
            System.out.printf("Error: can't generate a secret number with a length of %d because there aren't enough unique digits", randomLength);
        } else {
            randomGenerator.setRandomLength(randomLength);
            System.out.printf("The random secret number is %s.", randomGenerator.numberGenerator());
        }
        /*
        String guess = scanner.next();
        String message = new Grader().evaluateGuess(secretNumber, guess);
        System.out.println(message);

         */
    }
}

class Grader {
    public Grader() {
    }

    public String evaluateGuess(String secretNumber, String guess) {
        String message;
        int bulls = 0;
        int cows = 0;
        String number = secretNumber;
        String check = guess;

        for (int i = 0; i < number.length(); i++) {
            if (check.substring(i, i + 1).equals(number.substring(i, i + 1))) {
                number.replace(check.charAt(i), 'B');
                bulls++;
            } else if (number.contains(String.valueOf(guess.charAt(i)))) {
                number.replace(check.charAt(i),'C');
                cows++;
            }
        }

        if (bulls > 0 && cows > 0) {
            message = String.format("Grade: %d bull(s) and %d cow(s).",bulls, cows);

        } else if (bulls > 0) {
            message = String.format("Grade: %d bull(s).", bulls);

        } else if ( cows > 0) {
            message = String.format("Grade: %d cow(s).", cows);

        } else {
            message = String.format("Grade: None.",bulls, cows);

        }
        message += String.format(" The secret code is %s.", secretNumber);
        return message;
    }
}

final class RandomGenerator {
    private static long lowerLimit;
    private static long upperLimit;
    private static int randomLength;
    private static boolean repeatedDigitAllowed = false;

    public RandomGenerator() {
        setup(10);

    }

    private void setup(int randomLengthValue) {
        RandomGenerator.randomLength = randomLengthValue;
        setRepeatedDigitAllowed(false);
        if (randomLength == 1) {
            lowerLimit = randomLength;
            upperLimit = 9;

        } else {
            lowerLimit = Long.parseLong(("1" + String.format("%" + (randomLength - 1) + "s", "").replace(" ", "0")));
            upperLimit = Long.parseLong(String.format("%" + (randomLength) + "s", "").replace(" ", "9"));
        }
    }

    public String numberGenerator() {
        Random random = new Random();
        boolean isRepeated;
        int newDigit;
        String number = String.format("%0" + randomLength + "d", Math.abs((long) (random.nextDouble() * (upperLimit - lowerLimit + 1) + lowerLimit)));

        if (!isRepeatedDigitAllowed()) {
            for (int i = 0; i < number.length(); i++) {
                isRepeated = number.substring(i + 1).contains(String.valueOf(number.charAt(i)));
                if (i == 0) {
                    newDigit = 0;
                } else {
                    newDigit = -1;
                }
                if (isRepeated) {
                    do {
                        newDigit++;
                        isRepeated = number.contains(String.valueOf(newDigit));
                    } while (isRepeated);

                    number = new StringBuilder(number).replace(i, i + 1, String.valueOf(newDigit)).toString();
                }
            }
        }
        return number;
    }

    public long getLowerLimit() {
        return lowerLimit;
    }

    public void setLowerLimit(long lowerLimit) {
        RandomGenerator.lowerLimit = lowerLimit;
    }

    public long getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(long upperLimit) {
        RandomGenerator.upperLimit = upperLimit;
    }

    public long getRandomLength() {
        return randomLength;
    }

    public void setRandomLength(int randomLength) {
        setup(randomLength);
    }

    public boolean isRepeatedDigitAllowed() {
        return repeatedDigitAllowed;
    }

    public void setRepeatedDigitAllowed(boolean repeatedDigitAllowed) {
        RandomGenerator.repeatedDigitAllowed = repeatedDigitAllowed;
    }
}