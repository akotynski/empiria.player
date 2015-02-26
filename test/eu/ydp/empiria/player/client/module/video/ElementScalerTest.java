package eu.ydp.empiria.player.client.module.video;

import static org.mockito.AdditionalMatchers.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import com.google.gwt.dom.client.*;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwtmockito.GwtMockitoTestRunner;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class ElementScalerTest {

	@InjectMocks
	private ElementScaler testObj;
	@Mock
	private Element element;
	@Mock
	private Element parentElement;
	@Mock
	private Style style;
	@Mock
	private Style parentStyle;

	private final double DELTA = 0.01;
	private final int HEIGHT = 9;
	private final int WIDTH = 16;
	private final double EXPECTED_RATIO = 56.25;

	@Before
	public void setUp() throws Exception {
		when(element.getClientWidth()).thenReturn(WIDTH);
		when(element.getClientHeight()).thenReturn(HEIGHT);
		when(element.getStyle()).thenReturn(style);
		when(element.getParentElement()).thenReturn(parentElement);
		when(parentElement.getStyle()).thenReturn(parentStyle);
		testObj = new ElementScaler(element);
	}

	@Test
	public void shouldClearOldSizesAndSetTopPadding() {
		// when
		testObj.setRatio();

		// then
		verify(style).clearWidth();
		verify(style).clearHeight();

		verify(style).setPaddingTop(eq(EXPECTED_RATIO, DELTA), eq(Unit.PCT));
	}

	@Test
	public void shouldClearTopPadding() {
		// when
		testObj.clearRatio();

		// then
		verify(style).clearPaddingTop();
	}

	@Test
	public void shouldSetMaxWidth() {
		// when
		testObj.setMaxWidthToParentDiv(WIDTH);

		// then
		verify(parentStyle).setProperty("maxWidth", WIDTH, Unit.PX);
	}

	@Test
	public void shouldClearMaxWidth() {
		// when
		testObj.clearMaxWidth();

		// then
		verify(parentStyle).clearProperty("maxWidth");
	}
}
