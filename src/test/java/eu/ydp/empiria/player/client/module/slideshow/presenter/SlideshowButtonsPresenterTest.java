package eu.ydp.empiria.player.client.module.slideshow.presenter;

import com.google.gwtmockito.GwtMockitoTestRunner;
import eu.ydp.empiria.player.client.module.slideshow.slides.SlideshowController;
import eu.ydp.empiria.player.client.module.slideshow.view.buttons.SlideshowButtonsView;
import eu.ydp.empiria.player.client.module.slideshow.view.player.SlideshowPlayerView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(GwtMockitoTestRunner.class)
public class SlideshowButtonsPresenterTest {

    @InjectMocks
    private SlideshowButtonsPresenter testObj;
    @Mock
    private SlideshowPlayerView playerView;
    @Mock
    private SlideshowButtonsView buttonsView;
    @Mock
    private SlideshowController slidesController;

    @Before
    public void init() {
        testObj.setSlideshowController(slidesController);
    }

    @Test
    public void shouldEnablePreviousButton() {
        // given

        // when
        testObj.setEnabledPreviousButton(true);

        // then
        verify(buttonsView).setEnabledPreviousButton(true);
    }

    @Test
    public void shouldDisablePreviousButton() {
        // given

        // when
        testObj.setEnabledPreviousButton(false);

        // then
        verify(buttonsView).setEnabledPreviousButton(false);
    }

    @Test
    public void shouldEnableNextButton() {
        // given

        // when
        testObj.setEnabledNextButton(true);

        // then
        verify(buttonsView).setEnabledNextButton(true);
    }

    @Test
    public void shouldDisableNextButton() {
        // given

        // when
        testObj.setEnabledNextButton(false);

        // then
        verify(buttonsView).setEnabledNextButton(false);
    }

    @Test
    public void shouldSetPlayButtonToPause() {
        // given

        // when
        testObj.setPlayButtonDown(true);

        // then
        verify(buttonsView).setPlayButtonDown(true);
    }

    @Test
    public void shouldSetPlayButtonToPlay() {
        // given

        // when
        testObj.setPlayButtonDown(false);

        // then
        verify(buttonsView).setPlayButtonDown(false);
    }

    @Test
    public void shouldShowNextSlide() {
        // given

        // when
        testObj.onNextClick();

        // then
        verify(slidesController).showNextSlide();
    }

    @Test
    public void shouldShowPreviousSlide() {
        // given

        // when
        testObj.onPreviousClick();

        // then
        verify(slidesController).showPreviousSlide();
    }

    @Test
    public void shouldPlaySlideshow() {
        // given
        when(buttonsView.isPlayButtonDown()).thenReturn(true);

        // when
        testObj.onPlayPauseClick();

        // then
        verify(slidesController).playSlideshow();
    }

    @Test
    public void shouldPauseSlideshow() {
        // given
        when(buttonsView.isPlayButtonDown()).thenReturn(false);

        // when
        testObj.onPlayPauseClick();

        // then
        verify(slidesController).pauseSlideshow();
    }

    @Test
    public void shouldStopSlideshow() {
        // given

        // when
        testObj.onStopClick();

        // then
        verify(slidesController).stopSlideshow();
    }
}
