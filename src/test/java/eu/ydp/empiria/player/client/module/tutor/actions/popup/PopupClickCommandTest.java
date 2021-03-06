package eu.ydp.empiria.player.client.module.tutor.actions.popup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PopupClickCommandTest {

    @Mock
    private PersonaViewDto personaViewDto;
    @Mock
    private TutorPopupPresenterImpl tutorPopupPresenterImpl;

    private PopupClickCommand clickCommand;

    @Before
    public void setUp() {
        clickCommand = new PopupClickCommand(personaViewDto, tutorPopupPresenterImpl);
    }

    @Test
    public void testExecute() throws Exception {
        // when
        clickCommand.execute(null);

        // then
        verify(tutorPopupPresenterImpl).clicked(personaViewDto);
    }

}
