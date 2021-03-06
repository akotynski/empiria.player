package eu.ydp.empiria.player.client.module.components.multiplepair;

/**
 * {@link #CORRECT} - correct answer, {@link #WRONG} - wrong answer, {@link #NONE} - nothing selected when in marking answer mode, {@link #NORMAL} - user's
 * answer
 */
public enum MultiplePairModuleConnectType {
    /**
     * wrong answer
     */
    WRONG,
    /**
     * correct answer
     */
    CORRECT,

    /**
     * nothing selected when in marking answer mode
     */
    NONE,

    /**
     * user's answer
     */
    NORMAL
}
