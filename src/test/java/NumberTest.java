import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class NumberTest {
    @Test
    public void testGreaterThan_greaterThanCase() {
        int num1 = 3;
        int num2 = 4;
        int expectedGreater = 4;

        assert Numbers.greaterNum(num1, num2) == expectedGreater;
    }

    @Test
    public void testGreaterThan_lessThanCase() {
        int num1 = 0;
        int num2 = -1;
        int expectedGreater = 0;

        assert Numbers.greaterNum(num1, num2) == expectedGreater;
    }

    @Test
    public void testGreaterThan_equalCase() {
        int num1 = 2;
        int num2 = 2;
        int expectedGreater = 2;

        assert Numbers.greaterNum(num1, num2) == expectedGreater;
    }

    @Test
    public void testGreaterThan_bothNegative() {
        int num1 = -1;
        int num2 = -100;
        int expectedGreater = -1;

        assert Numbers.greaterNum(num1, num2) == expectedGreater;
    }

    @Test
    public void testGreaterThan_AbsoluteMin() {
        int num1 = Integer.MIN_VALUE;
        int num2 = 2;
        int expectedGreater = 2;

        assert Numbers.greaterNum(num1, num2) == expectedGreater;
    }

    @Test
    public void testGreaterThan_AbsoluteMax() {
        int num1 = Integer.MAX_VALUE;
        int num2 = -173;
        int expectedGreater = Integer.MAX_VALUE;

        assert Numbers.greaterNum(num1, num2) == expectedGreater;
    }

    @Test
    public void testGreaterThan_AbsoluteMinAndMax() {
        int num1 = Integer.MIN_VALUE;
        int num2 = Integer.MAX_VALUE;
        int expectedGreater = Integer.MAX_VALUE;

        assert Numbers.greaterNum(num1, num2) == expectedGreater;
    }
}
