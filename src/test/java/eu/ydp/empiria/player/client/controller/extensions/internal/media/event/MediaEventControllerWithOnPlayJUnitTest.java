package eu.ydp.empiria.player.client.controller.extensions.internal.media.event;

import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.AbstractMediaProcessor;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.MediaExecutor;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventTypes;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static junitparams.JUnitParamsRunner.$;
import static org.mockito.Mockito.*;

@RunWith(JUnitParamsRunner.class)
public class MediaEventControllerWithOnPlayJUnitTest {

    @InjectMocks
    private MediaEventControllerWithOnPlay testObj;

    @Mock
    private DefaultMediaEventController defaultMediaEventController;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private MediaEvent event;
    @Mock
    private MediaExecutor<?> executor;
    @Mock
    private AbstractMediaProcessor processor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldProcess_onPlay() {
        // given
        MediaWrapper mediaWrapper = mock(MediaWrapper.class);
        when(event.getAssociatedType().getType()).thenReturn(MediaEventTypes.ON_PLAY);
        when(executor.getMediaWrapper()).thenReturn(mediaWrapper);

        // when
        testObj.onMediaEvent(event, executor, processor);

        // then
        verify(processor).pauseAllOthers(mediaWrapper);
        verifyZeroInteractions(defaultMediaEventController);
    }

    @Test
    @Parameters
    public void shouldDelegateOtherEventsToDefaultController(MediaEventTypes type) {
        // when
        when(event.getAssociatedType().getType()).thenReturn(type);
        testObj.onMediaEvent(event, executor, processor);

        // then
        verify(defaultMediaEventController).onMediaEvent(event, executor, processor);
    }

    public Object[] parametersForShouldDelegateOtherEventsToDefaultController() {
        List<MediaEventTypes> eventsTypes = Lists.newArrayList(MediaEventTypes.values());
        List<MediaEventTypes> eventsToTest = FluentIterable.from(eventsTypes).filter(new Predicate<MediaEventTypes>() {

            @Override
            public boolean apply(MediaEventTypes type) {
                return type != MediaEventTypes.ON_PLAY;
            }

        }).toList();

        return $(eventsToTest.toArray());
    }
}
