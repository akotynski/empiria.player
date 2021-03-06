package eu.ydp.empiria.player.client.controller.delivery;

public interface IStatefulString {

    /**
     * Get state
     *
     * @return state object
     */
    public String getStateString();

    /**
     * set new state
     *
     * @param newState state object created with getState() function
     */
    public void setStateString(String newState);

}
