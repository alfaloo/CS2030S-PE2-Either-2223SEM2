/**
 * CS2030S PE1 Question 2
 * AY21/22 Semester 2
 *
 * @author A0000000X
 */
import java.util.Map;
import java.util.List;
import java.util.stream.Stream;
import java.util.function.Predicate;

class Query {
    public static <T,S> Stream<Map.Entry<T, S>> getFilteredByKey(Map<T, S> table, Predicate<T> p) {
      return table.entrySet().stream().filter(x -> p.test(x.getKey()));
    }

    public static Stream<Integer> getIdsFromName(Map<String, List<Integer>> map, String name) {
      return map.get(name) == null ? Stream.of() : map.get(name).stream();
    }

    public static Stream<Double> getCostsFromIDs(Map<Integer, Double> table, Stream<Integer> purchases) {
      return purchases.flatMap(x -> table.get(x) == null ? Stream.of() : Stream.of(table.get(x)));
    }

    public static Stream<String> allCustomerCosts(Map<String, List<Integer>> map, Map<Integer, Double> table) {
      return map.keySet().stream().flatMap(x -> getCostsFromIDs(table, getIdsFromName(map, x)).map(y -> x + ": " + y.toString()));
    }
    
    public static Stream<String> totaledCustomerCosts(Map<String, List<Integer>> map, Map<Integer, Double> table) {
      return map.keySet().stream().map(x -> x + ": " + getCostsFromIDs(table, getIdsFromName(map, x)).reduce(0.0, (y, z) -> y + z).toString());
    }
}

