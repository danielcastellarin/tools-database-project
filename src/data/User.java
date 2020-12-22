package data;

import java.util.*;

public class User {

    private Random random;
    private int uid;

    private int lendProb;
    private int sellProb;
    private int doNothingProb;
    private int priceProb;

    private double returnMod;
    private int priceMod;

    private String high;
    private String med;
    private String low;

    public User(int id) {
        this.random = new Random();
        uid = id;

        doNothingProb = random.nextInt(101) + 1;

        priceProb = random.nextInt(5) + 1;
        sellProb = random.nextInt(priceProb + 15) + priceProb;
        lendProb = random.nextInt(sellProb + 25) + sellProb;

        priceMod = random.nextInt(11) - 5;
        returnMod = (random.nextInt(31) - 15) * .1;

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

    public int getPriceModifier() {
        return priceMod;
    }

    public int getPriceProb() {
        return priceProb;
    }

    public int getSellProb() {
        return sellProb;
    }

    public int getLendProb() {
        return lendProb;
    }

    public double getReturnMod() {
        return returnMod;
    }
}
