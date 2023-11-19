package application;

import java.util.List;

public class EnhancedWord extends Word{
    private List<String> examples;
    private List<String> synonyms;
    private List<String> antonyms;

    public EnhancedWord(String text, String meaning, List<String> examples, List<String> synonyms, List<String> antonyms) {
        super(text, meaning);
        this.examples = examples;
        this.synonyms = synonyms;
        this.antonyms = antonyms;
    }

    public List<String> getExamples() {
        return examples;
    }

    public void setExamples(List<String> examples) {
        this.examples = examples;
    }

    public List<String> getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(List<String> synonyms) {
        this.synonyms = synonyms;
    }

    public List<String> getAntonyms() {
        return antonyms;
    }

    public void setAntonyms(List<String> antonyms) {
        this.antonyms = antonyms;
    }
}
