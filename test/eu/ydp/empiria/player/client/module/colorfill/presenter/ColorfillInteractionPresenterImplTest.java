package eu.ydp.empiria.player.client.module.colorfill.presenter;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import static org.fest.assertions.api.Assertions.*;

import static org.mockito.Mockito.*;

import eu.ydp.empiria.player.client.module.ShowAnswersType;
import eu.ydp.empiria.player.client.module.colorfill.ColorfillInteractionModuleModel;
import eu.ydp.empiria.player.client.module.colorfill.ColorfillViewBuilder;
import eu.ydp.empiria.player.client.module.colorfill.model.ColorModel;
import eu.ydp.empiria.player.client.module.colorfill.structure.Area;
import eu.ydp.empiria.player.client.module.colorfill.structure.AreaContainer;
import eu.ydp.empiria.player.client.module.colorfill.structure.ColorfillInteractionBean;
import eu.ydp.empiria.player.client.module.colorfill.view.ColorfillInteractionView;
import eu.ydp.empiria.player.client.test.utils.ReflectionsUtils;

@RunWith(MockitoJUnitRunner.class)
public class ColorfillInteractionPresenterImplTest {

	private static final ReflectionsUtils reflectionsUtils = new ReflectionsUtils();

	private ColorfillInteractionPresenterImpl presenter;
	@Mock private ResponseUserAnswersConverter responseUserAnswersConverter;
	@Mock private ResponseAnswerByViewBuilder responseAnswerByViewBuilder;
	@Mock private ColorfillViewBuilder colorfillViewBuilder;
	@Mock private ColorfillInteractionView interactionView;
	@Mock private ColorfillInteractionModuleModel model;
	@Mock private ColorButtonsController colorButtonController;
	private ColorfillInteractionBean bean;

	
	@Before
	public void setUp() throws Exception {
		presenter = new ColorfillInteractionPresenterImpl(
				responseUserAnswersConverter, 
				responseAnswerByViewBuilder,
				colorfillViewBuilder, 
				colorButtonController,
				interactionView, 
				model);

		prepareBean();
		presenter.setBean(bean);
	}
	
	private void prepareBean(){
		bean = new ColorfillInteractionBean();
		AreaContainer areasContainer = new AreaContainer();
		List<Area> areas = Lists.newArrayList(new Area(1,1), new Area(2,2));
		areasContainer.setAreas(areas);
		bean.setAreas(areasContainer);
	}

	@Test
	public void shouldBuildView() throws Exception {
		presenter.bindView();
		
		verify(colorfillViewBuilder).buildView(bean);
		@SuppressWarnings("unchecked")
		List<Area> areas = (List<Area>) reflectionsUtils.getValueFromFiledInObject("areas", presenter);
		assertThat(areas).isNotNull();
		assertThat(areas.size()).isEqualTo(2);
	}
	
	@Test
	public void shouldRedrawAreaWithCurrentColorAndRebuildResponses() throws Exception {
		//given
		Area clickedArea = new Area(123, 253);

		ColorModel currentColor = ColorModel.createFromRgbString("00ff00");
		when(colorButtonController.getCurrentSelectedButtonColor())
			.thenReturn(currentColor);
		
		List<String> rebuildedAnswers = Lists.newArrayList("a", "b");
		when(responseAnswerByViewBuilder.buildNewResponseAnswersByCurrentImage(bean.getAreas().getAreas()))
			.thenReturn(rebuildedAnswers);
		
		//when
		presenter.imageColorChanged(clickedArea);
		
		//them
		verify(interactionView).setColor(clickedArea, currentColor);
		verify(model).setNewUserAnswers(rebuildedAnswers);
	}
	
	@Test
	public void shouldIgnoreAreaClickWhenNoColorIsCurrentlySelected() throws Exception {
		//given
		Area clickedArea = new Area(123, 253);
		when(colorButtonController.getCurrentSelectedButtonColor())
			.thenReturn(null);
		
		//when
		presenter.imageColorChanged(clickedArea);
		
		//then
		verify(colorButtonController).getCurrentSelectedButtonColor();
		verifyNoMoreInteractionsOnAllMocks();
	}

	@Test
	public void shouldReactOnColorButtonClicked() throws Exception {
		ColorModel color = ColorModel.createFromRgbString("00ff00");
		
		presenter.buttonClicked(color);
		
		verify(colorButtonController).colorButtonClicked(color);
		verifyNoMoreInteractionsOnAllMocks();
	}
	
	@Test
	public void shouldShowUserAnswers() throws Exception {
		List<String> currentAnswers = Lists.newArrayList("a", "b");
		when(model.getCurrentAnswers())
			.thenReturn(currentAnswers);
		
		Map<Area, ColorModel> areaToColorMap = Maps.newHashMap();
		when(responseUserAnswersConverter.convertResponseAnswersToAreaColorMap(currentAnswers))
			.thenReturn(areaToColorMap);
		
		presenter.showAnswers(ShowAnswersType.USER);
		
		verify(interactionView).setColors(areaToColorMap);
	}
	
	@Test
	public void shouldIgnoreShowingAnswersInModeOthenThanUser() throws Exception {
		presenter.showAnswers(ShowAnswersType.CORRECT);
		verifyNoMoreInteractionsOnAllMocks();
	}
	
	private void verifyNoMoreInteractionsOnAllMocks() {
		Mockito.verifyNoMoreInteractions(responseUserAnswersConverter, 
				responseAnswerByViewBuilder,
				colorfillViewBuilder, 
				colorButtonController,
				interactionView, 
				model);
	}
}