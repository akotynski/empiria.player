package eu.ydp.empiria.player.client.module.expression;

import com.google.common.base.CharMatcher;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.Maps;

import java.util.Iterator;
import java.util.Map;

public class PipedReplacementsParser {

    private static final Character SEPARATOR = '|';

    public Map<String, String> parse(String charactersSet) {
        Preconditions.checkArgument(isValidCharactersSet(charactersSet), "Invalid characters set for expression replacement.");
        Iterator<String> characters = getReplacementParts(charactersSet);
        return createReplacementsMap(characters);
    }

    private Iterator<String> getReplacementParts(String charactersSet) {
        String charactersSetTrimmed = charactersSet.trim();
        return Splitter.on(SEPARATOR).omitEmptyStrings().split(charactersSetTrimmed).iterator();
    }

    private Map<String, String> createReplacementsMap(Iterator<String> characters) {
        Map<String, String> replacements = Maps.newHashMap();
        while (characters.hasNext()) {
            replacements.put(characters.next(), characters.next());
        }
        return replacements;
    }

    private boolean isValidCharactersSet(String charactersSet) {
        return (containsOddNumberOfSeparators(charactersSet) && containsOnlyValidParts(charactersSet)) || charactersSet.isEmpty();
    }

    private boolean containsOnlyValidParts(String charactersSet) {
        return !charactersSet.contains("||");
    }

    private boolean containsOddNumberOfSeparators(String charactersSet) {
        return CharMatcher.is(SEPARATOR).countIn(charactersSet) % 2 == 1;
    }
}
