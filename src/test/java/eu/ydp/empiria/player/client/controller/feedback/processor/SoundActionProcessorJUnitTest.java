package eu.ydp.empiria.player.client.controller.feedback.processor;

import eu.ydp.empiria.player.client.AbstractTestBase;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.ActionType;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.FeedbackUrlAction;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.ShowTextAction;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SoundActionProcessorJUnitTest extends AbstractTestBase {

    private SoundActionProcessor processor;

    @Before
    public void initialize() {
        processor = injector.getInstance(SoundActionProcessor.class);
    }

    @Test
    public void shouldAcceptAction() {
        FeedbackUrlAction action = mock(FeedbackUrlAction.class);
        when(action.getType()).thenReturn(ActionType.NARRATION.getName());

        boolean accepts = processor.canProcessAction(action);
        assertThat(accepts, is(equalTo(true)));
    }

    @Test
    public void shouldNotAcceptActionWhen_isNotUrlAction() {
        ShowTextAction action = mock(ShowTextAction.class);

        boolean accepts = processor.canProcessAction(action);
        assertThat(accepts, is(equalTo(false)));
    }

    @Test
    public void shouldNotAcceptActionWhen_itReturnsAnotherType() {
        FeedbackUrlAction action = mock(FeedbackUrlAction.class);
        when(action.getType()).thenReturn("unknown");

        boolean accepts = processor.canProcessAction(action);
        assertThat(accepts, is(equalTo(false)));

    }

}
