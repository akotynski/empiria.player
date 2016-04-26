package eu.ydp.empiria.player.client.controller.variables.storage.item;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONValue;
import eu.ydp.empiria.player.client.controller.variables.VariablePossessorBase;
import eu.ydp.empiria.player.client.controller.variables.objects.Variable;
import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;

import java.util.HashMap;
import java.util.Map;

public class ItemVariableStorageImpl<V extends Variable> extends VariablePossessorBase<V> {

    public ItemVariableStorageImpl() {
        variables = new HashMap<String, V>();
    }

    @Override
    public Map<String, V> getVariablesMap() {
        return variables;
    }

    public void importFromMap(Map<String, V> newValues) {
        variables.putAll(newValues);
    }

    public void putVariable(String key, V variable) {
        variables.put(key, variable);
    }

    public JSONValue toJSON() {
        JSONArray stateArr = new JSONArray();
        int i = 0;
        for (V value : variables.values()) {
            stateArr.set(i, value.toJSON());
            i++;
        }
        return stateArr;
    }

    @SuppressWarnings("unchecked")
    public void fromJSON(JSONValue json) {

        JSONArray jsonArr = json.isArray();

        if (jsonArr != null) {
            for (int i = 0; i < jsonArr.size(); i++) {
                String type = jsonArr.get(i).isArray().get(0).isString().stringValue();
                V currVar = null;
                if (type.equals("Outcome")) {
                    Variable o = new Outcome();
                    o.fromJSON(jsonArr.get(i));
                    try {
                        currVar = (V) o;
                    } catch (Exception e) {
                    }
                }
                if (currVar != null) {
                    variables.put(currVar.identifier, currVar);
                }
            }
        }
    }
}