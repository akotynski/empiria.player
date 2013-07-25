package eu.ydp.empiria.player.client.module.media.progress;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Lists;

import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.style.ComputedStyle;

@RunWith(MockitoJUnitRunner.class)
public class MediaProgressBarPositionCalculatorTest {
	@Mock private MediaProgressBar mediaProgressBar;
	@Mock private ComputedStyle computedStyle;
	@Mock private MediaWrapper<?> mediaWrapper;
	@InjectMocks private MediaProgressBarPositionCalculator instance;

	@Before
	public void before() {
		doReturn("rtl").when(computedStyle).getDirectionFromBody();
	}

	@Test
	public void isRTL() {
		assertThat(instance.isRTL()).isTrue();
		doReturn(" ").when(computedStyle).getDirectionFromBody();
		assertThat(instance.isRTL()).isFalse();
	}

	@Test
	public void calculateCurrentPositionRTL() throws Exception {
		// given
		int scrollWidth = 200;
		doReturn(scrollWidth).when(mediaProgressBar).getScrollWidth();
		doReturn(mediaWrapper).when(mediaProgressBar).getMediaWrapper();
		double duration = 400d;
		doReturn(duration).when(mediaWrapper).getDuration();
		double steep = duration / scrollWidth;

		// test
		for (int position : Lists.newArrayList(14, 28, 96, 200)) {
			double calculated = instance.calculateCurrentPosistion(position);
			assertThat(calculated).isEqualTo(steep * (scrollWidth - position));
		}

		double calculated = instance.calculateCurrentPosistion(-2);
		assertThat(calculated).isEqualTo(duration);

		calculated = instance.calculateCurrentPosistion(900);
		assertThat(calculated).isEqualTo(0);

	}

	@Test
	public void calculateCurrentPositionLTR() throws Exception {
		// given
		int scrollWidth = 200;
		doReturn(" ").when(computedStyle).getDirectionFromBody();
		doReturn(scrollWidth).when(mediaProgressBar).getScrollWidth();
		doReturn(mediaWrapper).when(mediaProgressBar).getMediaWrapper();
		double duration = 400d;
		doReturn(duration).when(mediaWrapper).getDuration();
		double steep = duration / scrollWidth;

		// test
		for (int position : Lists.newArrayList(14, 28, 96, 200)) {
			double calculated = instance.calculateCurrentPosistion(position);
			assertThat(calculated).isEqualTo(steep * position);
		}

		double calculated = instance.calculateCurrentPosistion(-2);
		assertThat(calculated).isEqualTo(0);

		calculated = instance.calculateCurrentPosistion(900);
		assertThat(calculated).isEqualTo(duration);

	}

	@Test
	public void calculateCurrentPosistionForScrollRTL() throws Exception {
		// given
		int scrollWidth = 200;
		doReturn(scrollWidth).when(mediaProgressBar).getScrollWidth();
		doReturn(mediaWrapper).when(mediaProgressBar).getMediaWrapper();
		double duration = 400d;
		doReturn(duration).when(mediaWrapper).getDuration();

		// test
		for (int position : Lists.newArrayList(14, 28, 96, 200)) {
			int calculated = instance.calculateCurrentPosistionForScroll(position);
			assertThat(calculated).isEqualTo(scrollWidth - position);
		}

		int calculated = instance.calculateCurrentPosistionForScroll(-2);
		assertThat(calculated).isEqualTo(scrollWidth);

		calculated = instance.calculateCurrentPosistionForScroll(900);
		assertThat(calculated).isEqualTo(0);

	}

	@Test
	public void calculateCurrentPosistionForScrollLTR() throws Exception {
		// given
		doReturn(" ").when(computedStyle).getDirectionFromBody();
		int scrollWidth = 200;
		doReturn(scrollWidth).when(mediaProgressBar).getScrollWidth();
		doReturn(mediaWrapper).when(mediaProgressBar).getMediaWrapper();
		double duration = 400d;
		doReturn(duration).when(mediaWrapper).getDuration();

		// test
		for (int position : Lists.newArrayList(14, 28, 96, 200)) {
			int calculated = instance.calculateCurrentPosistionForScroll(position);
			assertThat(calculated).isEqualTo( position);
		}

		int calculated = instance.calculateCurrentPosistionForScroll(-2);
		assertThat(calculated).isEqualTo(0);

		calculated = instance.calculateCurrentPosistionForScroll(900);
		assertThat(calculated).isEqualTo(scrollWidth);
	}

	@Test
	public void getLeftPositionForAfterProgressElementRTL() throws Exception {
		int leftPositionForAfterProgressElement = instance.getLeftPositionForAfterProgressElement(23);
		assertThat(leftPositionForAfterProgressElement).isEqualTo(0);
	}

	@Test
	public void getLeftPositionForAfterProgressElementLTR() throws Exception {
		doReturn(" ").when(computedStyle).getDirectionFromBody();
		int buttonWidth = 20;
		doReturn(buttonWidth).when(mediaProgressBar).getButtonWidth();
		int leftPositionForAfterProgressElement = instance.getLeftPositionForAfterProgressElement(23);
		assertThat(leftPositionForAfterProgressElement).isEqualTo(23 + buttonWidth / 2);
	}

	@Test
	public void getLeftPositionForBeforeProgressElementLTR() throws Exception {
		doReturn(" ").when(computedStyle).getDirectionFromBody();
		int buttonWidth = 20;
		doReturn(buttonWidth).when(mediaProgressBar).getButtonWidth();
		int scrollWidth = 200;
		doReturn(scrollWidth).when(mediaProgressBar).getScrollWidth();

		int leftPosition = instance.getLeftPositionForBeforeProgressElement(40);
		assertThat(leftPosition).isEqualTo(0);
	}

	@Test
	public void getLeftPositionForBeforeProgressElementRTL() throws Exception {
		int buttonWidth = 20;
		doReturn(buttonWidth).when(mediaProgressBar).getButtonWidth();
		int scrollWidth = 200;
		doReturn(scrollWidth).when(mediaProgressBar).getScrollWidth();

		int leftPosition = instance.getLeftPositionForBeforeProgressElement(40);
		assertThat(leftPosition).isEqualTo(scrollWidth - 40 + buttonWidth / 2);
	}

	@Test
	public void getWidthForAfterProgressElementLTR() throws Exception {
		doReturn(" ").when(computedStyle).getDirectionFromBody();
		int buttonWidth = 20;
		doReturn(buttonWidth).when(mediaProgressBar).getButtonWidth();
		int scrollWidth = 200;
		doReturn(scrollWidth).when(mediaProgressBar).getScrollWidth();

		int leftPosition = instance.getWidthForAfterProgressElement(40);
		assertThat(leftPosition).isEqualTo(scrollWidth - 40 + buttonWidth / 2);

	}

	@Test
	public void getWidthForAfterProgressElementRTL() throws Exception {
		int buttonWidth = 20;
		doReturn(buttonWidth).when(mediaProgressBar).getButtonWidth();
		int scrollWidth = 200;
		doReturn(scrollWidth).when(mediaProgressBar).getScrollWidth();

		int leftPosition = instance.getWidthForAfterProgressElement(40);
		assertThat(leftPosition).isEqualTo(scrollWidth - 40 + buttonWidth / 2);
	}

}
