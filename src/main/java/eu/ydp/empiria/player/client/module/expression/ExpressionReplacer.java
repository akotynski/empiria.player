package eu.ydp.empiria.player.client.module.expression;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class ExpressionReplacer {

    private Map<String, String> replacements = ImmutableMap.of();

    public void useReplacements(Map<String, String> replacements) {
        this.replacements = replacements;
    }

    public boolean isEligibleForReplacement(String text) {
        return replacements.containsKey(text);
    }

    public String replace(String text) {
        return replacements.get(text);
    }
}
