import java.util.*;


public class Dictionary {
    private ArrayList<Word> words = new ArrayList<>();

    public Dictionary() {
        this.words = new ArrayList<>();
    }
    public ArrayList<Word> getWords() {
        return words;
    }

    public void setWords(ArrayList<Word> words) {
        this.words = words;
    }

    public int getIndex(String word_target) {
        int l = 0, r = words.size();
        while (l < r) {
            int m = (l + r) / 2;
            Word midValue = words.get(m);
            if (midValue.getWord_target().compareToIgnoreCase(word_target) > 0) {
                r = m;
            } else if (midValue.getWord_target().compareToIgnoreCase(word_target) < 0) {
                l = m + 1;
            } else {
                return m;
            }
        }
        return l;
    }

    public void addWordtoDictionary(Word word) {
        int index = getIndex(word.getWord_target());
        if (index >= 0) {
            words.add(index, word);
        }
    }

}