package eu.ydp.empiria.player.client.module.img.explorable;

import com.google.gwt.dev.util.collect.HashMap;
import com.google.gwt.xml.client.Element;
import com.google.gwtmockito.GwtMockitoTestRunner;
import eu.ydp.empiria.player.client.module.img.explorable.view.ImageProperties;
import eu.ydp.empiria.player.client.module.img.explorable.view.StyleParser;
import eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants;
import eu.ydp.empiria.player.client.style.StyleSocket;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(GwtMockitoTestRunner.class)
public class StyleParserTest {

    @InjectMocks
    private StyleParser testObj;
    @Mock
    private StyleSocket styleSocket;


    @Test
    public void shouldParseStyles() {
        //Given
        Element element = mock(Element.class);
        Map<String, String> styles = new HashMap<String, String>();
        styles.put(EmpiriaStyleNameConstants.EMPIRIA_IMG_EXPLORABLE_SCALE_INITIAL, "110%");
        styles.put(EmpiriaStyleNameConstants.EMPIRIA_IMG_EXPLORABLE_SCALE_STEP, "30%");
        styles.put(EmpiriaStyleNameConstants.EMPIRIA_IMG_EXPLORABLE_SCALE_MAX, "500%");
        styles.put(EmpiriaStyleNameConstants.EMPIRIA_IMG_EXPLORABLE_WINDOW_WIDTH, "800");
        styles.put(EmpiriaStyleNameConstants.EMPIRIA_IMG_EXPLORABLE_WINDOW_HEIGHT, "600");
        when(styleSocket.getStyles(element)).thenReturn(styles);

        //When
        ImageProperties imageProperties = testObj.parseStyles(element);

        //Then
        assertThat(imageProperties.getScale(), equalTo(1.1d));
        assertThat(imageProperties.getScaleStep(), equalTo(1.3d));
        assertThat(imageProperties.getZoomMax(), equalTo(5d));
        assertThat(imageProperties.getWindowWidth(), equalTo(800));
        assertThat(imageProperties.getWindowHeight(), equalTo(600));
    }
}
