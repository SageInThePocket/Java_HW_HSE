package classesForTests.classesForCommonTests;

import annotations.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Constrained
public class NestingCollection {
    private List<@Size(min = 2, max = 3) @NotEmpty List<@Positive Integer>> list1;
    private Map<String, @Negative Integer> map1;
    @Size(min = 1, max = 2)
    private Set<@AnyOf({"lol", "kek", "hochu10"}) String> set1;

    public NestingCollection(List<List<Integer>> list1, Map<String, Integer> map1, Set<String> set1) {
        this.list1 = list1;
        this.map1 = map1;
        this.set1 = set1;
    }
}
