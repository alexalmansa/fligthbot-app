package Model;

import java.util.Arrays;

public final class WordList {

    final String[] store;

    public WordList(String... keywords) {
        this.store = keywords;
        Arrays.sort(store);
    }

    public boolean contains(String s) {
        if (s == null || s.length() < 2)
            return false;
        return Arrays.binarySearch(store, s) >= 0;
    }

}
