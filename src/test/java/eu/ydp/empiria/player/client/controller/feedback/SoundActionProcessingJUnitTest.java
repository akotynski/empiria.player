package eu.ydp.empiria.player.client.controller.feedback;

import eu.ydp.empiria.player.client.controller.feedback.structure.action.ActionType;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.FeedbackAction;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.ShowUrlAction;
import eu.ydp.empiria.player.client.jaxb.XmlContentMock;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class SoundActionProcessingJUnitTest extends ProcessingFeedbackActionTestBase {

    @Test
    public void shouldProcessSingleSoundAction() {
        List<FeedbackAction> actions = ActionListBuilder.create()
                .addUrlAction(ActionType.NARRATION, "good.mp3")
                .addTextAction(new XmlContentMock("Good"))
                .getList();
        initializeWithActions(actions);
        ArgumentCaptor<FeedbackAction> argument = ArgumentCaptor.forClass(FeedbackAction.class);

        processor.processActions(source);
        verify(soundProcessor).processSingleAction(argument.capture(), Mockito.isA(FeedbackMark.class));
        FeedbackAction processedAction = argument.getValue();

        assertThat(argument.getAllValues().size(), is(equalTo(1)));
        assertThat(processedAction, is(instanceOf(ShowUrlAction.class)));
        assertThat(((ShowUrlAction) processedAction).getType(), is(equalTo(ActionType.NARRATION.getName())));
        assertThat(((ShowUrlAction) processedAction).getHref(), is(equalTo("good.mp3")));
        assertThat(collector.getActions().size(), is(equalTo(1)));
    }

    @Test
    public void shouldProcessTwoSoundActions() {
        String[] audioUrl = new String[]{"good.mp3", "allok.mp3"};
        List<FeedbackAction> actions = ActionListBuilder.create()
                .addUrlAction(ActionType.NARRATION, audioUrl[0])
                .addTextAction(new XmlContentMock("Good"))
                .addUrlAction(ActionType.NARRATION, audioUrl[1])
                .getList();

        initializeWithActions(actions);
        ArgumentCaptor<FeedbackAction> argument = ArgumentCaptor.forClass(FeedbackAction.class);

        processor.processActions(source);
        verify(soundProcessor, times(2)).processSingleAction(argument.capture(), Mockito.isA(FeedbackMark.class));
        List<FeedbackAction> processedActions = argument.getAllValues();

        assertThat(processedActions.size(), is(equalTo(2)));
        assertThat(collector.getActions().size(), is(equalTo(1)));

        for (int i = 0; i < audioUrl.length; i++) {
            FeedbackAction processedAction = processedActions.get(i);
            String url = audioUrl[i];

            assertThat(processedAction, is(instanceOf(ShowUrlAction.class)));
            assertThat(((ShowUrlAction) processedAction).getHref(), is(equalTo(url)));
            assertThat(((ShowUrlAction) processedAction).getType(), is(equalTo(ActionType.NARRATION.getName())));
        }
    }
}
