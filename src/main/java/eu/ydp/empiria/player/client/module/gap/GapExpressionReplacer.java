package eu.ydp.empiria.player.client.module.gap;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.expression.ExpressionReplacer;
import eu.ydp.empiria.player.client.module.expression.PipedReplacementsParser;

import java.util.Map;

public class GapExpressionReplacer {

    @Inject
    private ExpressionReplacer replacer;
    @Inject
    private PipedReplacementsParser parser;

    public void useCharacters(String charactersSet) {
        Map<String, String> replacements = parser.parse(charactersSet);
        replacer.useReplacements(replacements);
    }

    public ExpressionReplacer getReplacer() {
        return replacer;
    }

    public String ensureReplacement(String text) {
        if (replacer.isEligibleForReplacement(text)) {
            return replacer.replace(text);
        }
        return text;
    }

}
