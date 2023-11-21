package application.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BKTree {
    private String word;
    private Map<Integer, BKTree> children;

    public BKTree(String word) {
        this.word = word;
        this.children = new HashMap<>();
    }

    public void insert(String other) {
        int distance = levenshteinDistance(this.word, other);
        BKTree child = children.get(distance);
        if (child != null) {
            child.insert(other);
        } else {
            children.put(distance, new BKTree(other));
        }
    }

    public void search(String searchWord, int mistakeThreshold, List<String> results) {
        int distance = levenshteinDistance(this.word, searchWord);

        if (distance <= mistakeThreshold) {
            results.add(this.word);
        }

        for (int i = Math.max(1, distance - mistakeThreshold); i <= distance + mistakeThreshold; i++) {
            BKTree child = children.get(i);
            if (child != null) {
                child.search(searchWord, mistakeThreshold, results);
            }
        }
    }

    private int levenshteinDistance(String str1, String str2) {
        int lenStr1 = str1.length();
        int lenStr2 = str2.length();

        int[][] dp = new int[lenStr1 + 1][lenStr2 + 1];

        for (int i = 0; i <= lenStr1; i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= lenStr2; j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= lenStr1; i++) {
            for (int j = 1; j <= lenStr2; j++) {
                int cost = (str1.charAt(i - 1) == str2.charAt(j - 1)) ? 0 : 1;
                dp[i][j] = Math.min(Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1), dp[i - 1][j - 1] + cost);
            }
        }
        return dp[lenStr1][lenStr2];
    }
}
