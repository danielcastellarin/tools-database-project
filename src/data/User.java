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
        printCategoryPreference();

    }

    private void determineCategoryPreference() {
        String catChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Set<Integer> seen = new HashSet<Integer>();
        // FIXME something is up with this loop, when debugging, it adds the character to a string one iteration after it should
        //  still functional tho
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
    
    public int determineToolNeed(List<String> categories) {
        int need = 0;
        for (String c : categories) {
            need += (high.contains(c) ? 5 : (med.contains(c) ? 3 : (low.contains(c) ? 1 : 0)));
        }
        return need;
    }

    public void printCategoryPreference() {
        System.out.println("UID: " + uid);
        System.out.println("High pref: " + high);
        System.out.println("Med pref: " + med);
        System.out.println("Low pref: " + low);
        System.out.println("############################");
    }
}
