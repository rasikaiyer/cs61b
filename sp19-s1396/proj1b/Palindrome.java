public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> wordDeque = new ArrayDeque<>();
        for (int i = 0; i < word.length(); i++) {
            wordDeque.addLast(word.charAt(i));
        }
        return wordDeque;
    }

    private boolean isPalindrome(Deque wordDeque) {
        if (wordDeque.size() == 0 || wordDeque.size() == 1) {
            return true;
        }
        if (wordDeque.removeLast() != wordDeque.removeFirst()) {
            return false;
        }
        return isPalindrome(wordDeque);
    }

    public boolean isPalindrome(String word) {
        Deque<Character> wordDeque = wordToDeque(word);
        return isPalindrome(wordDeque);
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        if (word.length() == 0 || word.length() == 1) {
            return true;
        }

        for (int i = 0; i < word.length(); i++) {
            if (i != (word.length() - i - 1)) {
                if (!cc.equalChars(word.charAt(i), word.charAt(word.length() - i - 1))) {
                    return false;
                }
            }
        }
        return true;
    }
}
