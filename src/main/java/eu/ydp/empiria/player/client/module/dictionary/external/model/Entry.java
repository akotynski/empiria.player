package eu.ydp.empiria.player.client.module.dictionary.external.model;

public class Entry {

    private final String entry;
    private final String entryDescription;
    private final String type;
    private final String entryExample;
    private final String entrySound;
    private final String entryExampleSound;
    private final String label;

    Entry(String entry, String entryDescription, String type, String entryExample, String entrySound, String entryExampleSound, String label) {
        this.entry = entry;
        this.entryDescription = entryDescription;
        this.type = type;
        this.entryExample = entryExample;
        this.entrySound = entrySound;
        this.entryExampleSound = entryExampleSound;
        this.label = label;
    }

    public String getEntry() {
        return entry;
    }

    public String getEntryDescription() {
        return entryDescription;
    }

    public String getType() {
        return type;
    }

    public String getEntryExample() {
        return entryExample;
    }

    public String getEntrySound() {
        return entrySound;
    }

    public String getEntryExampleSound() {
        return entryExampleSound;
    }

    public String getLabel() {
        return label;
    }
}
