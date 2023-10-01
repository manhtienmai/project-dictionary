import java.util.ArrayList;
import java.util.Scanner;

public class DictionaryCommandLine {
    private DictionaryManagement dictionaryManagement;

    public void showAllWords() {
        ArrayList<Word> words = dictionaryManagement.getWords();
        System.out.println("No | English | Vietnamese");
        for (int i = 0; i < words.size(); i++) {
            Word word = words.get(i);
            System.out.println((i + 1) + " | " + word.getWord_target() + " | " + word.getWord_explain());
        }
    }

    public void dictionaryBasic() {
        dictionaryManagement.insertFromCommandLine();
        showAllWords();
    }

    public void dictionaryAdvanced() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("Welcome to my application!");
            System.out.println("[0] Exit\n[1] Add\n[2] Remove\n[3] Update\n[4] Display\n[5] Lookup\n[6] Search\n[7] Game\n[8] Import from file");
            System.out.println("Your action: ");
            String action = sc.nextLine();

            switch (action) {
                case "0":
                    System.out.print("Goodbye!");
                    return;
                case "1":
                    System.out.println("Enter English word:");
                    String eng = sc.nextLine();
                    System.out.print("Enter Vietnamese meaning:");
                    String vie = sc.nextLine();
                    dictionaryManagement.addWord(new Word(eng, vie));
                    System.out.println("Word added!");
                    break;
                case "2":
                    System.out.println("Enter English word to remove:");
                    String wordToRemove = sc.nextLine();
                    dictionaryManagement.deleteWord(wordToRemove);
                    System.out.println("Successfully delete word");
                    break;
                case "3":
                    System.out.println("Enter English word to update:");
                    String presentWord = sc.nextLine();
                    System.out.println("Enter new English word:");
                    String newWord = sc.nextLine();
                    System.out.println("Enter new Vietnamese meaning:");
                    String newMeaning = sc.nextLine();
                    dictionaryManagement.updateWord(presentWord, newWord, newMeaning);
                    System.out.println("Successfully update word");
                    break;
                case "4":
                    showAllWords();
                    break;
                case "5":
                    System.out.println("Enter English word to lookup:");
                    String wordToLookup = sc.nextLine();
                    Word result = dictionaryManagement.dictionarySearch(wordToLookup);
                    if (result != null) {
                        System.out.println(wordToLookup + ": " + result.getWord_explain());
                    } else {
                        System.out.println("Not found word.");
                    }
                    break;
                case "6":
                    System.out.println("Enter prefix to search:");
                    String prefix = sc.nextLine();
                    System.out.println(dictionaryManagement.dictionarySearch(prefix));
                    break;
                case "7":
                    System.out.println("To be continue");
                    break;
                case "8":
                    dictionaryManagement.insertFromFile();
                    System.out.println("Imported successfully");
                    break;
                case "9":
                    dictionaryManagement.dictionaryExportToFile();
                    System.out.println("Exported successfully");
                    break;
                default:
                    System.out.print("Action not supported");
                    break;
            }
        }
    }
}
