import java.util.Scanner;

public class DictionaryManagement {
    private Dictionary dictionary;

    public DictionaryManagement(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public void insertFromCommandLine() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of words: ");
        int n = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < n; i++) {
            System.out.println("Enter English word: ");
            String eng = sc.nextLine();
            System.out.println("Enter Vietnamese meaning");
            String vie = sc.nextLine();
            dictionary.addWord(new Word(eng, vie));
        }
    }

    public void insertFromFile() {}
    public void addWord(Word word) {}
    public Word dictionarySearch(String target) {}
    public void editWord() {}
    public void deleteWord(String target) {}
    public void dictionaryExportToFile() {}
}
