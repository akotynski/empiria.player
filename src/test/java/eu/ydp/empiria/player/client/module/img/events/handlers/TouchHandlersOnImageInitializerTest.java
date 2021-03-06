package eu.ydp.empiria.player.client.module.img.events.handlers;

import com.google.gwt.event.dom.client.TouchEndEvent;
import com.google.gwt.event.dom.client.TouchMoveEvent;
import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwtmockito.GwtMockitoTestRunner;
import eu.ydp.empiria.player.client.gin.factory.TouchHandlerFactory;
import eu.ydp.empiria.player.client.module.img.events.TouchToImageEvent;
import eu.ydp.empiria.player.client.module.img.events.handlers.touch.TouchEndHandlerOnImage;
import eu.ydp.empiria.player.client.module.img.events.handlers.touch.TouchMoveHandlerOnImage;
import eu.ydp.empiria.player.client.module.img.events.handlers.touch.TouchStartHandlerOnImage;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageEndHandler;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageMoveHandler;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageStartHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class TouchHandlersOnImageInitializerTest {

    @InjectMocks
    private TouchHandlersOnImageInitializer testObj;
    @Mock
    private TouchToImageEvent touchEventsCoordinates;
    @Mock
    private Widget listenOn;
    @Mock
    private TouchHandlerFactory touchHandlerFactory;

    @Test
    public void shouldAddTouchMoveHandlerOnImage() {
        // given
        TouchOnImageMoveHandler touchOnImageMoveHandler = mock(TouchOnImageMoveHandler.class);
        TouchMoveHandlerOnImage touchMoveHandlerOnImage = mock(TouchMoveHandlerOnImage.class);
        when(touchHandlerFactory.createTouchMoveHandlerOnImage(touchOnImageMoveHandler)).thenReturn(touchMoveHandlerOnImage);

        // when
        testObj.addTouchOnImageMoveHandler(touchOnImageMoveHandler, listenOn);

        // then
        verify(listenOn).addDomHandler(touchMoveHandlerOnImage, TouchMoveEvent.getType());
    }

    @Test
    public void shouldAddTouchStartHandlerOnImage() {
        // given
        TouchOnImageStartHandler touchOnImageStartHandler = mock(TouchOnImageStartHandler.class);
        TouchStartHandlerOnImage touchStartHandlerOnImage = mock(TouchStartHandlerOnImage.class);
        when(touchHandlerFactory.createTouchStartHandlerOnImage(touchOnImageStartHandler)).thenReturn(touchStartHandlerOnImage);

        // when
        testObj.addTouchOnImageStartHandler(touchOnImageStartHandler, listenOn);

        // then
        verify(listenOn).addDomHandler(touchStartHandlerOnImage, TouchStartEvent.getType());
    }

    @Test
    public void shouldAddTouchEndHandlerOnImage() {
        // given
        TouchOnImageEndHandler touchOnImageEndHandler = mock(TouchOnImageEndHandler.class);
        TouchEndHandlerOnImage touchEndHandlerOnImage = mock(TouchEndHandlerOnImage.class);
        when(touchHandlerFactory.createTouchEndHandlerOnImage(touchOnImageEndHandler)).thenReturn(touchEndHandlerOnImage);

        // when
        testObj.addTouchOnImageEndHandler(touchOnImageEndHandler, listenOn);

        // then
        verify(listenOn).addDomHandler(touchEndHandlerOnImage, TouchEndEvent.getType());
    }
}
