package application.model;

import application.model.Word;

import java.util.List;

public class EnhancedWord extends Word {
    private String pronounce;
    private List<String> synonyms;
    private List<String> antonyms;

    public EnhancedWord(String text, String meaning, String pronounce) {
        super(text, meaning);
        this.pronounce = pronounce;
    }

    public EnhancedWord(String text, String meaning, String pronounce, List<String> synonyms, List<String> antonyms) {
        super(text, meaning);
        this.pronounce = pronounce;
        this.synonyms = synonyms;
        this.antonyms = antonyms;
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

    public String getPronounce() {
        return pronounce;
    }

    public void setPronounce(String pronounce) {
        this.pronounce = pronounce;
    }
}
