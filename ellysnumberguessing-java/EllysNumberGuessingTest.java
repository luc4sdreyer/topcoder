import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EllysNumberGuessingTest {

    protected EllysNumberGuessing solution;

    @Before
    public void setUp() {
        solution = new EllysNumberGuessing();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        int[] guesses = new int[]{600, 594};
        int[] answers = new int[]{6, 12};

        int expected = 606;
        int actual = solution.getNumber(guesses, answers);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        int[] guesses = new int[]{100, 50, 34, 40};
        int[] answers = new int[]{58, 8, 8, 2};

        int expected = 42;
        int actual = solution.getNumber(guesses, answers);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int[] guesses = new int[]{500000, 600000, 700000};
        int[] answers = new int[]{120013, 220013, 79987};

        int expected = -2;
        int actual = solution.getNumber(guesses, answers);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        int[] guesses = new int[]{500000000};
        int[] answers = new int[]{133742666};

        int expected = -1;
        int actual = solution.getNumber(guesses, answers);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        int[] guesses = new int[]{76938260, 523164588, 14196746, 296286419, 535893832, 41243148, 364561227, 270003278, 472017422, 367932361, 395758413, 301278456, 186276934, 316343129, 336557549, 52536121, 98343562, 356769915, 89249181, 335191879};
        int[] answers = new int[]{466274085, 20047757, 529015599, 246925926, 7318513, 501969197, 178651118, 273209067, 71194923, 175279984, 147453932, 241933889, 356935411, 226869216, 206654796, 490676224, 444868783, 186442430, 453963164, 208020466};

        int expected = 543212345;
        int actual = solution.getNumber(guesses, answers);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase5() {
        int[] guesses = new int[]{42};
        int[] answers = new int[]{42};

        int expected = 84;
        int actual = solution.getNumber(guesses, answers);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase6() {
        int[] guesses = new int[]{999900000};
        int[] answers = new int[]{100001};

        int expected = 999799999;
        int actual = solution.getNumber(guesses, answers);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase7() {
        int[] guesses = new int[]{42, 50};
        int[] answers = new int[]{42, 50};

        int expected = -2;
        int actual = solution.getNumber(guesses, answers);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase8() {
        int[] guesses = new int[]{2, 1};
        int[] answers = new int[]{2, 3};

        int expected = 4;
        int actual = solution.getNumber(guesses, answers);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase9() {
        int[] guesses = new int[]{4, 1};
        int[] answers = new int[]{2, 5};

        int expected = 6;
        int actual = solution.getNumber(guesses, answers);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000000)
    public void testCase10() {
        int[] guesses = new int[]{999999999, 1};
        int[] answers = new int[]{1, 999999999};

        int expected = 1000000000;
        int actual = solution.getNumber(guesses, answers);

        Assert.assertEquals(expected, actual);
    }

}
