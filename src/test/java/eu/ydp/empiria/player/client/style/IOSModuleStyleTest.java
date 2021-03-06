package eu.ydp.empiria.player.client.style;

import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.Map;

import static org.fest.assertions.api.Assertions.assertThat;

public class IOSModuleStyleTest {

    @Test
    public void get() throws Exception {
        Map<String, String> map = Maps.newHashMap();
        map.put("x", "xx");
        map.put("x2", "xx2");
        IOSModuleStyle moduleStyle = new IOSModuleStyle(map);
        assertThat(map).isEqualTo(moduleStyle);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String entryKey = entry.getKey();
            String moduleStyleValue = moduleStyle.get(entryKey);
            assertThat(entry.getValue()).isEqualTo(moduleStyleValue);
        }
    }

    @Test
    public void getWithNull() throws Exception {
        Map<String, String> map = Maps.newHashMap();
        map.put("x", "xx");
        map.put("x2", "xx2");
        IOSModuleStyle moduleStyle = new IOSModuleStyle(map);
        assertThat(moduleStyle.get(null)).isNull();
    }
}
