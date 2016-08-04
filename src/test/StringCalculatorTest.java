package test;


import calc.StringCalculator;
import calc.StringCalculator.NegativesNotAllowedException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class StringCalculatorTest {

    private StringCalculator calculator;


    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        calculator = new StringCalculator();
    }

    @Test
    public void emptyString() throws Throwable {
        int sum = calculator.add("");
        assertEquals(0,sum);
    }

    @Test
    public void oneNumber() throws Throwable{
        int sum = calculator.add("60");
        assertEquals(60,sum);
    }

    @Test
    public void twoNumbers() throws Throwable{
        int sum = calculator.add("60,100");
        assertEquals(160,sum);
    }

    @Test
    public void unknownAmountOfNumbers() throws Throwable{
        int sum = calculator.add("10,40,20,16,64");
        assertEquals(150,sum);
    }

    @Test
    public void allowNewLineInsteadComma() throws Throwable{
        int sum = calculator.add("10\n40,20,16,64\n13");
        assertEquals(163,sum);
    }

    @Test
    public void differentDelimitersSupport() throws Throwable{
        int sum = calculator.add("//$\n10$40$20$16,64\n200");

        assertEquals(350,sum);
    }

    @Test
    public void shouldThrowExceptionOnNegativeNumberInput() throws Throwable{
        expectedException.expect(NegativesNotAllowedException.class);
        expectedException.expectMessage("Negative numbers not allowed! (-12,-1910,-98)");

        calculator.add("10,20,-12,-1910\n-98");
    }

    @Test
    public void ignoreOverThousandNumbers() throws Throwable{
        int sum = calculator.add("10,15,1001,1000");
        assertEquals(1025,sum);
    }

    @Test
    public void allowMultiCharacterDelimiters() throws Throwable{
        int sum = calculator.add("//asd\n10asd20asd100,3");
        assertEquals(133,sum);
    }


}
