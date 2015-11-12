import java.util.*;

/**
 * Created by Yoones on 10/31/2015.
 */

public abstract class ESParser {

    private List<Map.Entry<String, String>> rules;
    private String initialVariable;
    private boolean couldDerive;
    private Integer[] lastDerivation;


    public ESParser(String initialVariable) {
        this.initialVariable = initialVariable;
        rules = new ArrayList<Map.Entry<String, String>>();
    }

    public void addRule(String left, String right) {
        rules.add(new AbstractMap.SimpleEntry<String, String>(left, right));
    }

    public void canDerive(String target) {

        if (isSpecialCase(target)) {
            couldDerive = true;
            lastDerivation = new Integer[] {1};
            return;
        }

        Set<Pair<String, ArrayList<Integer>>> inters = new HashSet<Pair<String, ArrayList<Integer>>>();
        Set<Pair<String, ArrayList<Integer>>> nexts  = new HashSet<Pair<String, ArrayList<Integer>>>();

        inters.add(new Pair<String, ArrayList<Integer>>(initialVariable, new ArrayList<Integer>()));

        while (inters.size() > 0) {

            for (Pair<String, ArrayList<Integer>> inter : inters) {

                if (!hasHope(inter.first, target)) continue;

                for (int i = 0; i < rules.size(); i++) {

                    Map.Entry<String, String> entry = rules.get(i);

                    if (inter.first.contains(entry.getKey())) {

                        String next = inter.first.replaceFirst( entry.getKey(), entry.getValue() );

                        if (next.equals(target)) {

                            ArrayList<Integer> res = inter.second;
                            res.add(i + 1);

                            couldDerive = true;
                            lastDerivation = res.toArray(new Integer[] {});

                            return;

                        }

                        ArrayList<Integer> seq = new ArrayList<Integer>(inter.second.size() + 1);
                        seq.addAll(inter.second);
                        seq.add(i + 1);
                        nexts.add(new Pair<String, ArrayList<Integer>>(next, seq));

                    }

                }

            }

            inters.clear();
            inters.addAll(nexts);

            nexts.clear();
        }

        couldDerive = false;
    }

    public abstract boolean isSpecialCase(String target);

    public abstract boolean hasHope(String suspect, String target);

    public List<Map.Entry<String, String>> getRules() {
        return rules;
    }

    public String getInitialVariable() {
        return initialVariable;
    }

    public boolean getCouldDerive() {
        return couldDerive;
    }

    public Integer[] getLastDerivation() {
        return lastDerivation;
    }

    public String getLastDerivationStringForm() {

        StringBuilder res = new StringBuilder();

        String sentence = initialVariable;

        for (Integer re : getLastDerivation()) {

            String next = sentence.replaceFirst(rules.get(re - 1).getKey(), rules.get(re - 1).getValue());

            res.append("\t" + sentence + " --> " + next + " (" + re + ")\n");

            sentence = next;

        }

        return res.toString();

    }

}