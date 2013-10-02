package eu.ydp.empiria.player.client.module.progressbonus;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.module.model.image.ShowImageDTO;
import eu.ydp.empiria.player.client.module.progressbonus.presenter.ProgressBonusPresenter;
import eu.ydp.empiria.player.client.module.progressbonus.view.ProgressBonusView;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.scope.EventScope;
import eu.ydp.gwtutil.client.event.EventHandler;
import eu.ydp.gwtutil.client.event.EventImpl;

@RunWith(MockitoJUnitRunner.class)
public class ProgressBonusModuleTest {

	private ProgressBonusModule progressBonusModule;

	private ProgressBonusView view = mock(ProgressBonusView.class);
	private ProgressBonusPresenter presenter = mock(ProgressBonusPresenter.class);
	private ProgressAssetProvider assetProvider = mock(ProgressAssetProvider.class);;
	private ProgressCalculator progressCalculator = mock(ProgressCalculator.class);
	private EventsBus eventsBus = mock(EventsBus.class);
	private PageScopeFactory pageScopeFactory = mock(PageScopeFactory.class, Mockito.RETURNS_DEEP_STUBS);

	private PlayerEventHandler playerEventHandler;

	@Before
	public void before() {
		initEventHandlersInterception();
		progressBonusModule = new ProgressBonusModule(view, presenter, assetProvider, progressCalculator, eventsBus, pageScopeFactory);
	}

	@Test
	public void shouldReturnIdentifier() {
		// given
		Element element = mock(Element.class);
		when(element.getAttribute(eq("progressBonusId"))).thenReturn("BONUS_ID");
		progressBonusModule.initModule(element);

		// when
		String identifier = progressBonusModule.getIdentifier();

		// then
		assertThat(identifier).isEqualTo("BONUS_ID");
	}

	@Test
	public void shouldRestoreAssetFromState() {
		// given
		JSONArray newState = mock(JSONArray.class, Mockito.RETURNS_DEEP_STUBS);
		when(newState.get(0).isNumber().doubleValue()).thenReturn(7.0);
		progressBonusModule.setState(newState);

		// when
		progressBonusModule.onSetUp();

		// then
		verify(assetProvider).createFrom(7);
	}

	@Test
	public void shouldCreateRandomAssetIfStateNotSet() {
		// when
		progressBonusModule.onSetUp();

		// then
		verify(assetProvider).createRandom();
	}

	@Test
	public void shouldSetAssetOnPresenter() {
		// given
		ProgressAsset asset = mock(ProgressAsset.class);
		when(assetProvider.createRandom()).thenReturn(asset);
		ShowImageDTO imageDTO = mock(ShowImageDTO.class);
		when(progressCalculator.getProgress()).thenReturn(45);
		when(asset.getImageForProgress(eq(45))).thenReturn(imageDTO);
		progressBonusModule.onSetUp();

		// when
		playerEventHandler.onPlayerEvent(mock(PlayerEvent.class));

		// then
		verify(progressCalculator).getProgress();
		verify(presenter).showImage(imageDTO);
	}

	@SuppressWarnings({ "unchecked" })
	private void initEventHandlersInterception() {
		doAnswer(new Answer<Void>() {

			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				Object handler = invocation.getArguments()[1];

				if (handler instanceof PlayerEventHandler) {
					playerEventHandler = (PlayerEventHandler) handler;
				}

				return null;
			}
		}).when(eventsBus).addHandler(any(EventImpl.Type.class), any(EventHandler.class), any(EventScope.class));
	}
}
