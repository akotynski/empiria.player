package eu.ydp.empiria.player.client.module.dictionary.external.model;

import com.google.common.base.Optional;

import java.util.*;

public class Words {
    private final Map<String, List<String>> wordsByLetter;
    private final Map<String, Integer> baseIndexes;

    public Words(LinkedHashMap<String, List<String>> wordsByLetter, TreeMap<String, Integer> baseIndexes) {
        this.wordsByLetter = wordsByLetter;
        this.baseIndexes = baseIndexes;
    }

    public Map<String, Integer> getBaseIndexes() {
        return baseIndexes;
    }

    public Set<String> getFirstLetters() {
        return wordsByLetter.keySet();
    }

    public Optional<String> getFirstIndex() {
        if (baseIndexes.isEmpty()) {
            return Optional.absent();
        }
        Set<String> indexesSet = baseIndexes.keySet();
        return Optional.of(indexesSet.iterator().next());
    }

    public List<String> getWordsByLetter(String firstLetter) {
        return wordsByLetter.get(firstLetter);
    }
}
