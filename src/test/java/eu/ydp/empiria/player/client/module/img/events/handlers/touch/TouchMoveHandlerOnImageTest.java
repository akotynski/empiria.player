package eu.ydp.empiria.player.client.module.img.events.handlers.touch;

import com.google.gwt.event.dom.client.TouchMoveEvent;
import eu.ydp.empiria.player.client.module.img.events.TouchToImageEvent;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageEvent;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageMoveHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TouchMoveHandlerOnImageTest {

    @InjectMocks
    private TouchMoveHandlerOnImage testObj;
    @Mock
    private TouchToImageEvent touchToImageEvent;
    @Mock
    private TouchOnImageMoveHandler touchOnImageMoveHandler;
    @Mock
    private TouchMoveEvent event;
    @Mock
    private TouchOnImageEvent touchOnImageEvent;

    @Test
    public void shouldRunOnMove() {
        // given
        when(touchToImageEvent.getTouchOnImageEvent(event)).thenReturn(touchOnImageEvent);

        // when
        testObj.onTouchMove(event);

        // then
        verify(touchOnImageMoveHandler).onMove(touchOnImageEvent);
        verify(event).preventDefault();
    }
}
