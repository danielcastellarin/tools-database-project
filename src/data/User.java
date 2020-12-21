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

//    public int determineToolNeed(String categories) {
//        int need = 0;
//        for (char c : categories.toCharArray()) {
//            need += (high.indexOf(c) >= 0 ? 5 : (med.indexOf(c) >= 0 ? 3 : (low.indexOf(c) >= 0 ? 1 : 0)));
//        }
//        return need;
//    }
    
    public int determineToolNeed(List<String> categories) {
        int need = 0;
        for (String c : categories) {
            need += (high.contains(c) ? 5 : (med.contains(c) ? 3 : (low.contains(c) ? 1 : 0)));
        }
        return need;
    }


    /*
    min - the minimum skewed value possible
    max - the maximum skewed value possible
    skew - the degree to which the values cluster around the mode of the distribution; higher values mean tighter clustering
    bias - the tendency of the mode to approach the min, max or midpoint value; positive values bias toward max, negative values toward min
     */
    static public double nextSkewedBoundedDouble(double min, double max, double skew, double bias) {
        Random random = new Random();
        double range = max - min;
        double mid = min + range / 2.0;
        double unitGaussian = random.nextGaussian();
        double biasFactor = Math.exp(bias);
        double retval = mid+(range*(biasFactor/(biasFactor+Math.exp(-unitGaussian/skew))-0.5));
        return retval;
    }

    public static void main(String[] args) {

//        HashMap<Integer, Integer> map = new HashMap<>();
//        for (int i = 1; i < 55; i++) {
//            map.put(i, 0);
//        }
//
//        for (int i = 0; i < 1000; i++) {
//            int val = (int)nextSkewedBoundedDouble(1, 55, 1, 3);
//            map.put(val, map.get(val) + 1);
//        }
//
//        for (Integer key : map.keySet()) {
//            System.out.println(key + ": " + map.get(key));
//        }

        System.out.println((int)nextSkewedBoundedDouble(1, 55, 1, 3));
    }




}
