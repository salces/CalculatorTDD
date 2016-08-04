package calc;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

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

        int sum = 0;
        for (String s : separatedNumbers) {
            int intFromString = Integer.parseInt(s);

            if (intFromString <= 1000)
                sum += intFromString;
        }

        return sum;
    }

    private void checkForNegativeValues(String[] numbers) throws NegativesNotAllowedException {
        List<String> negatives = new ArrayList<>();
        for (String number : numbers) {
            if (number.startsWith("-"))
                negatives.add(number);
        }

        if (!negatives.isEmpty())
            throw new NegativesNotAllowedException(negatives);
    }

    private String getDelimitersFrom(String expression) {
        String delimiters = ",|\n";
        if (!expression.startsWith("//"))
            return delimiters;

        String delimitersBody = expression.split("\n", 2)[0];
        String newDelimiter = delimitersBody.substring(2);
//        Pattern validDelimiterPattern = Pattern.compile("^//([.*])|(\\S)");
//        Matcher matcher = validDelimiterPattern.matcher(delimitersBody);


        delimiters += "|" + Pattern.quote(newDelimiter);


        return delimiters;
    }

    private String getBodyOf(String expression) {
        String body = expression;
        if (expression.startsWith("//"))
            body = expression.split("\n", 2)[1];
        return body;
    }

    public static class NegativesNotAllowedException extends Exception {

        String message;

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
