import java.util.*;

public class Dictionary {
    private ArrayList<Word> words = new ArrayList<>();

    public ArrayList<Word> getWords() {
        return words;
    }

    public void setWords(ArrayList<Word> words) {
        this.words = words;
    }

//    public void addWord(Word word) {
//        words.add(word);
//    }
//
//    public void removeWord(int index) {
//        if (index >= 0 && index < words.size()) {
//            words.remove(index);
//        }
//    }
//
//    public void editWord(int index, Word word) {
//        if (index >= 0 && index < words.size()) {
//            words.set(index, word);
//        }
//    }
}
