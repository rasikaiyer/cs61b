import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindrome() {
        assertTrue(palindrome.isPalindrome("racecar"));
        assertTrue(palindrome.isPalindrome("a"));
        assertTrue(palindrome.isPalindrome(""));
        assertTrue(palindrome.isPalindrome("23432"));
        assertTrue(palindrome.isPalindrome("AbbA"));

        assertFalse(palindrome.isPalindrome("a1"));
        assertFalse(palindrome.isPalindrome("cat"));
        assertFalse(palindrome.isPalindrome("racecaR"));
    }

    @Test
    public void testOffByOnePalindrome() {
        OffByOne obo = new OffByOne();
        assertTrue(palindrome.isPalindrome("a", obo));
        assertTrue(palindrome.isPalindrome("", obo));
        assertTrue(palindrome.isPalindrome("rq", obo));
        assertTrue(palindrome.isPalindrome("racefdbq", obo));
        assertTrue(palindrome.isPalindrome("detrac1dbqude", obo));
        assertTrue(palindrome.isPalindrome("detrude", obo));

        assertFalse(palindrome.isPalindrome("racecar", obo));
        assertFalse(palindrome.isPalindrome("23432", obo));
        assertFalse(palindrome.isPalindrome("AbbA", obo));
        assertFalse(palindrome.isPalindrome("a1", obo));
    }
}
