package eu.ydp.empiria.player.client.controller.feedback;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import org.junit.*;

public class FeedbackMarkTest {

    private FeedbackProperties properties;

    @Before
    public void setUp() {
        properties = mock(FeedbackProperties.class);
    }

    @Test
    public void shouldGetAllOkMark() {
        // given
        when(properties.getBooleanProperty(FeedbackPropertyName.ALL_OK)).thenReturn(true);

        // when
        FeedbackMark mark = FeedbackMark.getMark(properties);

        // then
        assertThat(mark).isEqualTo(FeedbackMark.ALL_OK);
    }

    @Test
    public void shouldGetAllOkMark_whenOkAndAllOkIsTrue() {
        // given
        when(properties.getBooleanProperty(FeedbackPropertyName.ALL_OK)).thenReturn(true);
        when(properties.getBooleanProperty(FeedbackPropertyName.OK)).thenReturn(true);

        // when
        FeedbackMark mark = FeedbackMark.getMark(properties);

        // then
        assertThat(mark).isEqualTo(FeedbackMark.ALL_OK);
    }

    @Test
    public void shouldGetOkMark() {
        // given
        when(properties.getBooleanProperty(FeedbackPropertyName.OK)).thenReturn(true);

        // when
        FeedbackMark mark = FeedbackMark.getMark(properties);

        // then
        assertThat(mark).isEqualTo(FeedbackMark.OK);
    }

    @Test
    public void shouldGetWrongMark() {
        // given
        when(properties.getBooleanProperty(FeedbackPropertyName.WRONG)).thenReturn(true);

        // when
        FeedbackMark mark = FeedbackMark.getMark(properties);

        // then
        assertThat(mark).isEqualTo(FeedbackMark.WRONG);
    }

    @Test
    public void shouldGetDefaultMark() {
        // when
        FeedbackMark mark = FeedbackMark.getMark(properties);

        // then
        assertThat(mark).isEqualTo(FeedbackMark.DEFAULT);
    }
}