package eu.ydp.empiria.player.client.module.math;

import com.google.common.collect.Maps;
import eu.ydp.gwtutil.client.StringUtils;

import java.util.Map;

public class MathGapModel {

    private String uid;

    private Map<String, String> mathStyles;

    public String getUid() {
        return (uid == null) ? StringUtils.EMPTY_STRING : uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Map<String, String> getMathStyles() {
        if (mathStyles == null) {
            mathStyles = Maps.newHashMap();
        }
        return mathStyles;
    }

    public void setMathStyles(Map<String, String> mathStyles) {
        this.mathStyles = mathStyles;
    }

    public boolean containsStyle(String key) {
        return mathStyles.containsKey(key);
    }

    public String getStyle(String key) {
        return mathStyles.get(key);
    }
}
