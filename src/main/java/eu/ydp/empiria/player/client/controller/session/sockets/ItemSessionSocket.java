package eu.ydp.empiria.player.client.controller.session.sockets;

import com.google.gwt.json.client.JSONArray;
import eu.ydp.empiria.player.client.controller.variables.storage.item.ItemOutcomeStorageImpl;

public interface ItemSessionSocket {

    public JSONArray getState(int itemIndex);

    public void setState(int itemIndex, JSONArray state);

    // public void updateItemVariables(int itemIndex, Map<String, Outcome> variablesMap);

    public void beginItemSession(int itemIndex);

    public void endItemSession(int itemIndex);

    public ItemOutcomeStorageImpl getOutcomeVariablesMap(int itemIndex);
}
