package eu.ydp.empiria.player.client.module.textentry;

import com.google.gwt.xml.client.Element;
import eu.ydp.empiria.player.client.controller.item.ResponseSocket;
import eu.ydp.empiria.player.client.controller.variables.objects.response.ResponseBuilder;
import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistManager;
import eu.ydp.empiria.player.client.module.textentry.DragContentController;
import eu.ydp.empiria.player.client.module.textentry.TextEntryGapModule;
import eu.ydp.empiria.player.client.module.textentry.TextEntryModulePresenter;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.scope.CurrentPageScope;
import eu.ydp.empiria.player.client.util.events.internal.state.StateChangeEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TextEntryGapBaseTest {

    @InjectMocks
    private TextEntryGapModule testObj;

    @Mock
    private SourcelistManager sourcelistManager;
    @Mock
    private DragContentController dragContentController;
    @Mock
    private TextEntryModulePresenter textEntryPresenter;
    @Mock
    private ResponseSocket responseSocket;
    @Mock
    private EventsBus eventsBus;
    @Mock
    private Element element;
    @Mock
    private PageScopeFactory pageScopeFactory;

    @Test
    public void shouldUpdateStateAfterDragTest() {
        // given
        when(responseSocket.getResponse(anyString())).thenReturn(new ResponseBuilder().build());
        testObj.addElement(element);

        // when
        testObj.setDragItem("aa");

        // then
        verify(eventsBus).fireEvent(any(StateChangeEvent.class), any(CurrentPageScope.class));
    }

}
