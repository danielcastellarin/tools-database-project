package data;

import java.util.*;

public class User {

    // likelyhood to sell
    // likelyhood to lend
    // likelyhood to changeprice
    // likelyhood to return tool
    // category preference

    private Random random;
    private int uid;
    private int lendProb;
    private int sellProb;
    private int returnProb;

    private int priceProb;
    private int incPriceChange;

    private String high;
    private String med;
    private String low;

    public User(int id) {
        this.random = new Random();
        uid = id;

        high = ""; med = ""; low = "";
        determineCategoryPreference();

    }

    private void determineCategoryPreference() {
        String catChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Set<Integer> seen = new HashSet<Integer>();
        for (int i = 0; i < 11; i++) {
            int catIndex = random.nextInt(26);
            if (seen.contains(catIndex)) {
                i--;
                continue;
            }
            seen.add(catIndex);
            if(i < 3) {
                high += catChars.charAt(catIndex);
            } else if(i < 6) {
                med += catChars.charAt(catIndex);
            } else {
                low += catChars.charAt(catIndex);
            }
        }
    }

    public int determineToolNeed(String categories) {
        int need = 0;
        for (char c : categories.toCharArray()) {
            need += (high.indexOf(c) >= 0 ? 5 : (med.indexOf(c) >= 0 ? 3 : 0));
        }
        return need;
    }

}
