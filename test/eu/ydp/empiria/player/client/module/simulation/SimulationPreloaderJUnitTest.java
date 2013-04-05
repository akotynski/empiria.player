package eu.ydp.empiria.player.client.module.simulation;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import com.google.gwt.junit.GWTMockUtilities;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Binder;
import com.google.inject.Module;

import eu.ydp.empiria.player.client.AbstractTestBaseWithoutAutoInjectorInit;
import eu.ydp.empiria.player.client.GuiceModuleConfiguration;
import eu.ydp.empiria.player.client.preloader.Preloader;
import eu.ydp.empiria.player.client.preloader.view.ProgressView;

@SuppressWarnings("PMD")
public class SimulationPreloaderJUnitTest extends AbstractTestBaseWithoutAutoInjectorInit {
	private class CustomGinModule implements Module {
		@Override
		public void configure(Binder binder) {
			binder.bind(Preloader.class).toInstance(mock(Preloader.class));
			binder.bind(ProgressView.class).toInstance(createProgressViewMock());
		}

		private ProgressView createProgressViewMock() {
			ProgressView progressView = mock(ProgressView.class);
			when(progressView.asWidget()).thenReturn(mock(Widget.class));
			return progressView;
		}
	}

	@BeforeClass
	public static void disarm() {
		GWTMockUtilities.disarm();
	}

	@AfterClass
	public static void rearm() {
		GWTMockUtilities.restore();
	}

	private SimulationPreloader instance;
	private Preloader preloader;
	private final Widget widget = mock(Widget.class);

	@Before
	public void before() {
		setUpAndOverrideMainModule(new GuiceModuleConfiguration(), new CustomGinModule());
		instance = injector.getInstance(SimulationPreloader.class);
		preloader = injector.getInstance(Preloader.class);
		doReturn(widget).when(preloader).asWidget();
	}

	@Test
	public void testHidePreloaderAndRemoveFromParent() {
		instance.hidePreloaderAndRemoveFromParent();
		verify(preloader).hide();
		verify(widget).removeFromParent();
	}

	@Test
	public void testShow() {
		instance.show(10, 20);
		verify(preloader).setPreloaderSize(Mockito.eq(10), Mockito.eq(20));
		verify(preloader).show();
	}

	@Test
	public void testAsWidget() {
		verify(preloader.asWidget());
	}

}