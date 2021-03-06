package eu.ydp.empiria.player.client.module.dictionary.external.controller;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseUpHandler;
import eu.ydp.empiria.player.client.gin.factory.DictionaryModuleFactory;
import eu.ydp.empiria.player.client.module.dictionary.external.model.Entry;
import eu.ydp.empiria.player.client.module.dictionary.external.view.ExplanationView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ExplanationControllerTest {

    @Mock
    private ExplanationDescriptionSoundController explanationDescriptionSoundController;

    @Mock
    private EntryDescriptionSoundController entryDescriptionSoundController;

    private ExplanationController testObj;
    @Mock
    private ExplanationView explanationView;

    @Mock
    private DictionaryModuleFactory dictionaryModuleFactory;


    @Mock
    private Entry entry;

    private ClickHandler clickHandler;
    private MouseUpHandler mouseUpHandler;

    @Before
    public void setUp() {
        when(dictionaryModuleFactory.getExplanationDescriptionSoundController(explanationView)).thenReturn(explanationDescriptionSoundController);
        when(dictionaryModuleFactory.geEntryDescriptionSoundController(explanationView)).thenReturn(entryDescriptionSoundController);
        testObj = new ExplanationController(explanationView, dictionaryModuleFactory);
    }

    @Test
    public void shouldProcessNotNullEntry() {
        // given
        Entry entry = mock(Entry.class);

        // when
        testObj.processEntry(entry);

        // then
        verify(explanationView).processEntry(entry);
    }

    @Test
    public void shouldShow() {
        // when
        testObj.show();

        // then
        verify(explanationView).show();
    }

    @Test
    public void shouldStopPlayingSoundWhenHidingView() {
        // when
        testObj.hide();

        // then
        verify(explanationDescriptionSoundController).stop();
        verify(entryDescriptionSoundController).stop();
        verify(explanationView).hide();
    }

    @Test
    public void shouldAddViewHandlersOnInit() {
        // given
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) {
                mouseUpHandler = (MouseUpHandler) invocation.getArguments()[0];
                return null;
            }
        }).when(explanationView).addEntryExamplePanelHandler(any(MouseUpHandler.class));

        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) {
                clickHandler = (ClickHandler) invocation.getArguments()[0];
                return null;
            }
        }).when(explanationView).addPlayButtonHandler(any(ClickHandler.class));

        // when
        testObj.init();

        // then
        verify(explanationView).addPlayButtonHandler(clickHandler);
        verify(explanationView).addEntryExamplePanelHandler(mouseUpHandler);
    }

    @Test
    public void shouldCallPlayOrStopDescriptionOnPlayButtonClick() {
        // given
        String file = "test.mp3";
        Entry entry = mock(Entry.class);
        when(entry.getEntryExampleSound()).thenReturn(file);

        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) {
                clickHandler = (ClickHandler) invocation.getArguments()[0];
                return null;
            }
        }).when(explanationView).addPlayButtonHandler(any(ClickHandler.class));

        // when
        testObj.init();
        testObj.processEntry(entry);
        clickHandler.onClick(null);

        // then
        verify(explanationDescriptionSoundController).playOrStopExplanationSound(entry.getEntryExampleSound());
    }

    @Test
    public void shouldCallPlayOrStopEntryOnPlayButtonClick() {
        // given
        String file = "test.mp3";
        Entry entry = mock(Entry.class);
        when(entry.getEntrySound()).thenReturn(file);

        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) {
                clickHandler = (ClickHandler) invocation.getArguments()[0];
                return null;
            }
        }).when(explanationView).addEntryPlayButtonHandler(any(ClickHandler.class));

        // when
        testObj.init();
        testObj.processEntry(entry);
        clickHandler.onClick(null);

        // then
        verify(entryDescriptionSoundController).playOrStopEntrySound(entry.getEntrySound());
    }
}
