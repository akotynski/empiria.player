package eu.ydp.empiria.player.client.controller.session;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONValue;
import eu.ydp.empiria.player.client.controller.session.datasockets.ItemSessionDataSocket;
import eu.ydp.empiria.player.client.controller.variables.VariableProviderSocket;
import eu.ydp.empiria.player.client.controller.variables.objects.Cardinality;
import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;
import eu.ydp.empiria.player.client.controller.variables.storage.item.ItemOutcomeStorageImpl;

import java.util.Date;
import java.util.Map;

public class ItemSessionData implements ItemSessionDataSocket {

    private static final int NUMBER_OF_ELEMENTS_IN_STATE = 3;
    private static final double MILI_TO_SECONDS_RATE = 0.001;
    private long timeStarted;
    private int time;
    private JSONArray itemBodyState;
    private final ItemOutcomeStorageImpl outcomeStorage;

    public ItemSessionData() {
        timeStarted = 0;
        time = 0;
        itemBodyState = new JSONArray();
        outcomeStorage = new ItemOutcomeStorageImpl();
    }

    public void begin() {
        timeStarted = getCurrentTimeInSeconds();
        outcomeStorage.putVariable("VISITED", new Outcome("VISITED", Cardinality.SINGLE, "TRUE"));
    }

    public void end() {
        time += (getCurrentTimeInSeconds() - timeStarted);
        timeStarted = 0;
    }

    private long getCurrentTimeInSeconds() {
        return (long) ((new Date()).getTime() * MILI_TO_SECONDS_RATE);
    }

    @Override
    public int getActualTime() {
        int actualTime = 0;
        if (timeStarted != 0) {
            actualTime = time + (int) (getCurrentTimeInSeconds() - timeStarted);
        } else if (time != 0) {
            actualTime = time;
        }

        return actualTime;
    }

    public JSONValue getState() {
        JSONArray stateArr = new JSONArray();
        stateArr.set(0, itemBodyState);
        stateArr.set(1, outcomeStorage.toJSON());

        // instead of time return zero value - related to scorm documentation empiria should always return times of last session
        // zero is returned to keep compatibility with older player version and between states generated with different players
        stateArr.set(2, new JSONNumber(0));
        return stateArr;
    }

    public void setState(JSONValue value) {
        JSONArray stateArr = (JSONArray) value;
        if (stateArr.size() == NUMBER_OF_ELEMENTS_IN_STATE) {
            itemBodyState = stateArr.get(0).isArray();
            outcomeStorage.fromJSON(stateArr.get(1));
        }
    }

    public JSONArray getItemBodyState() {
        return itemBodyState;
    }

    public void setItemBodyState(JSONArray newState) {
        itemBodyState = newState;
    }

    public void updateVariables(Map<String, Outcome> variablesMap) {
        outcomeStorage.importFromMap(variablesMap);
    }

    public ItemOutcomeStorageImpl getOutcomeStorage() {
        return outcomeStorage;
    }

    public ItemSessionDataSocket getItemSessionDataSocket() {
        return this;
    }

    @Override
    public JavaScriptObject getJsSocket() {
        return createJsObject();
    }

    private native JavaScriptObject createJsObject()/*-{
        var obj = [];
        var instance = this;
        obj.getVariableManagerSocket = function () {
            return instance.@eu.ydp.empiria.player.client.controller.session.ItemSessionData::getVariableStorageJsSocket()();
        };
        obj.getTime = function () {
            return instance.@eu.ydp.empiria.player.client.controller.session.ItemSessionData::getActualTime()();
        };
        return obj;
    }-*/;

    private JavaScriptObject getVariableStorageJsSocket() {
        return outcomeStorage.getJsSocket();
    }

    @Override
    public VariableProviderSocket getVariableProviderSocket() {
        return outcomeStorage;
    }

    public void resetItemState() {
        itemBodyState = new JSONArray();
    }
}
