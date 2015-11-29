import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by Yoones on 11/15/2015.
 */
public class RuleTransformer {

    private final static String landaChar = "~";

    private RuleList rules;
    private Set<String> variables;
    private Set<String> terminals;

    private String initialVar;

    public void removeUselessVars() {
        removeNonTerminateables();
        removeNonReachables();
    }

    public void transformToCNF() {

    }

    public void removeLandaProductions() {

    }

    public void removeUnitProductions() {

    }

    public RuleList getRules() {
        return rules;
    }

    public void removeNonTerminateables() {

        Set<String> terminateables = new HashSet<String>();

        boolean hasChanged = true;

        for (Map.Entry<String, String> rule : rules) {
            if (!containsAny(rule.getValue(), variables) && !terminateables.contains(rule.getKey())) {
                terminateables.add(rule.getKey());
            }
        }

        terminateables.addAll(terminals);

        while (hasChanged) {

            hasChanged = false;

            for (Map.Entry<String, String> rule : rules) {

                if (!terminateables.contains(rule.getKey()) && isMadeOf(rule.getValue(), terminateables)) {
                    terminateables.add(rule.getKey());
                    hasChanged = true;
                }

            }

        }

        terminateables.removeAll(terminals);

        this.variables = terminateables;

        RuleList newRules = new RuleList();

        for (Map.Entry<String, String> rule : rules) {
            if (isMadeOf(rule.getKey(), terminateables))
                newRules.add(rule);
        }

        this.rules = newRules;

    }

    public void removeNonReachables() {

        Set<String> reachables = new HashSet<String>();
        Set<String> checkedRules = new HashSet<String>();

        reachables.add(initialVar);

        boolean hasChanged = true;

        while (hasChanged) {

            hasChanged = false;

            for (Map.Entry<String, String> rule : rules) {
                if (!checkedRules.contains(rule.getKey()) && isMadeOf(rule.getKey(), reachables)) {
                    reachables.addAll(getAllVariablesFrom(rule.getValue()));
                    checkedRules.add(rule.getKey());
                    hasChanged = true;
                }
            }

        }

        this.variables = reachables;

        RuleList newRules = new RuleList();

        for (Map.Entry<String, String> rule : rules) {
            if (isMadeOf(rule.getKey(), reachables)) {
                newRules.add(rule);
            }
        }

        this.rules = newRules;

    }


    private Set<String> getAllVariablesFrom(String text) {

        Set<String> res = new HashSet<String>();

        for (char c : text.toCharArray()) {
            if (Character.isUpperCase(c))
                res.add(String.valueOf(c));
        }

        return res;

    }

    private boolean containsAny(String hay, Iterable<String> needles) {
        for (String needle : needles)
            if (hay.contains(needle)) return true;
        return false;
    }

    private boolean isMadeOf(String text, Iterable<String> elements) {

        Set<Character> elemSet = new HashSet<Character>();

        for (String element : elements) {
            for (char c : element.toCharArray()) {
                elemSet.add(c);
            }
        }

        for (char c : text.toCharArray()) {
            if (!elemSet.contains(c)) return false;
        }

        return true;

    }
}
