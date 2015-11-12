/**
 * Created by Yoones on 10/31/2015.
 */
public class LandaFree extends ESParser {

    public LandaFree(String initialVariable) {
        super(initialVariable);
    }

    @Override
    public boolean isSpecialCase(String target) {
        return target.equals("");
    }

    @Override
    public boolean hasHope(String suspect, String target) {
        return suspect.length() <= target.length();
    }
}
