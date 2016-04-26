package Classes;

import java.util.Comparator;
import java.util.HashMap;

/**
 * Compare values, this method comes from online resource: http://www.programcreek.com/2013/03/java-sort-map-by-value/
 */
public class ValueComparator implements Comparator {
    HashMap<Integer, Double> map;

    public ValueComparator(HashMap<Integer, Double> map) {
        this.map = map;
    }

    public int compare(Object keyA, Object keyB) {
        Comparable valueA = (Comparable) map.get(keyA);
        Comparable valueB = (Comparable) map.get(keyB);
        return valueB.compareTo(valueA);
    }
}
