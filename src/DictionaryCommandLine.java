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
                    return;
                case "2":
                    return;
                case "3":
                    return;
                case "4":
                    showAllWords();
                    break;
                case "5":
                    break;
                case "6":
                    break;
                case "7":
                    break;
                case "8":
                    break;
                case "9":
                    break;
                default:
                    System.out.print("Action not supported");
                    break;
            }
        }
    }
}
