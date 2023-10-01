import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Dictionary;
import java.util.Scanner;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

public class DictionaryManagement {
    private Dictionary dictionary;
    private ArrayList<Word> library = new ArrayList<Word>();
    String path = "dictionaries.txt";
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

    public void insertFromFile() {

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while((line = reader.readLine()) != null) {
                String[] data = line.split("\t");
                if (data.length == 2) {
                    String eng = data[0].trim();
                    String vie = data[1].trim();
                    dictionary.addWord(new Word(eng, vie));
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void addWord(Word word) {
        library.add(new Word(word.getWord_target(), word.getWord_explain()));
    }

    public String getWords(){
        return library;
    }
    public String dictionarySearch(String target) {
        for (String[] entry : library) {
            if (entry[0].equals(target)) {
                return entry[1];
            } else if (entry[1].equals(target)) {
                return entry[0];
            }
        }
        String s = "Can't find this word.";
        return  s;
    }
    public void editWord(String tagert) {
        int pos = 0;
        for (int i = 0; i < library.size(); i++) {
            if (library.get(i).getWord_target().equals(tagert)) {
                pos = i;
                break;
            }
        }
        if (library.get(pos).getWord_target().equals(tagert)) {
            library.get(pos).setWord_explain(tagert);
        }
    }
    public void deleteWord(String target) {
        for (int i = 0; i < library.size(); i++) {
            String[] entry = library.get(i);
            if (entry[0].equals(target) || entry[1].equals(target)) {
                library.remove(i);
                break;
            }
        }
    }
    public void dictionaryExportToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            for (String[] entry : library) {
                String word = entry[0];
                String meaning = entry[1];
                String line = word + "\t" + meaning;
                writer.write(line);
                writer.newLine(); // Thêm dấu xuống dòng sau mỗi cặp từ và nghĩa
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
