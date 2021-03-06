package eu.ydp.empiria.player.client.style;

import com.google.common.base.Objects;

import java.util.HashMap;
import java.util.Map;

public class IOSModuleStyle extends HashMap<String, String> implements ModuleStyle {
    private static final long serialVersionUID = 1L;

    public IOSModuleStyle(Map<String, String> styles) {
        putAll(styles);
    }

    /*
     * HACK for IOS
     */
    @Override
    public String get(Object key) {
        for (Map.Entry<String, String> entry : entrySet()) {
            if (Objects.equal(key, entry.getKey())) {
                return entry.getValue();
            }
        }
        return null;
    }

}
