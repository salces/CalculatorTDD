package calc;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringCalculator {

    public int add(String expression) throws NegativesNotAllowedException {
        if (expression.isEmpty()) {
            return 0;
        }

        int sum = sumMultiNumbers(getBodyOf(expression), getDelimitersFrom(expression));

        return sum;

    }

    private int sumMultiNumbers(String numbers, String delimiters) throws NegativesNotAllowedException {
        String[] separatedNumbers = numbers.split(delimiters);

        checkForNegativeValues(separatedNumbers);

        return  Arrays.stream(separatedNumbers)
                .map(Integer::parseInt)
                .filter(number -> number <= 1000)
                .mapToInt(number -> number).sum();
    }

    private void checkForNegativeValues(String[] numbers) throws NegativesNotAllowedException {
        List<String> negatives;
        negatives = Arrays.stream(numbers)
                .filter(number -> number.startsWith("-"))
                .collect(Collectors.toList());

        if (!negatives.isEmpty()) {
            throw new NegativesNotAllowedException(negatives);
        }
    }

    private String getDelimitersFrom(String expression) {
        String delimiters = ",|\n";

        if (!expression.startsWith("//")) {
            return delimiters;
        }

        String delimitersBody = expression.split("\n", 2)[0];
        String newDelimiter = delimitersBody.substring(2);

        delimiters += "|" + Pattern.quote(newDelimiter);

        return delimiters;
    }

    private String getBodyOf(String expression) {
        String body = expression;

        if (expression.startsWith("//")) {
            body = expression.split("\n", 2)[1];
        }

        return body;
    }

    public static class NegativesNotAllowedException extends Exception {

        private String message;

        public NegativesNotAllowedException(List<String> negatives) {
            message = getMessage(negatives);
        }

        private String getMessage(List<String> negatives) {
            StringBuilder message = new StringBuilder("Negative numbers not allowed! (");

            for (String negative : negatives) {
                message.append(negative + ",");
            }

            message.delete(message.length() - 1, message.length());
            message.append(")");

            return message.toString();
        }

        @Override
        public String getMessage() {
            return this.message;
        }
    }

}
