package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Names;

import eu.ydp.empiria.player.client.controller.AssessmentControllerFactory;
import eu.ydp.empiria.player.client.controller.Page;
import eu.ydp.empiria.player.client.controller.body.IPlayerContainersAccessor;
import eu.ydp.empiria.player.client.controller.body.ModuleHandlerManager;
import eu.ydp.empiria.player.client.controller.body.PlayerContainersAccessor;
import eu.ydp.empiria.player.client.controller.data.DataSourceManager;
import eu.ydp.empiria.player.client.controller.data.StyleDataSourceManager;
import eu.ydp.empiria.player.client.controller.extensions.internal.bookmark.BookmarkPopup;
import eu.ydp.empiria.player.client.controller.extensions.internal.bookmark.IBookmarkPopupView;
import eu.ydp.empiria.player.client.controller.extensions.internal.media.external.ExternalFullscreenVideoConnector;
import eu.ydp.empiria.player.client.controller.extensions.internal.media.external.FullscreenVideoConnector;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.DefaultMediaProcessorExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.external.ExternalMediaEngine;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.external.connector.JsMediaConnector;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.external.connector.MediaConnector;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.external.connector.MediaConnectorListener;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.IStickieProperties;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.IStickieView;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.StickieFactory;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.StickieProperties;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.StickieView;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.presenter.IStickiePresenter;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.presenter.StickiePresenter;
import eu.ydp.empiria.player.client.controller.feedback.FeedbackRegistry;
import eu.ydp.empiria.player.client.controller.feedback.matcher.MatcherRegistry;
import eu.ydp.empiria.player.client.controller.feedback.matcher.MatcherRegistryFactory;
import eu.ydp.empiria.player.client.controller.feedback.processor.SoundActionProcessor;
import eu.ydp.empiria.player.client.controller.flow.MainFlowProcessor;
import eu.ydp.empiria.player.client.controller.multiview.MultiPageController;
import eu.ydp.empiria.player.client.controller.multiview.PanelCache;
import eu.ydp.empiria.player.client.controller.multiview.touch.MultiPageTouchHandler;
import eu.ydp.empiria.player.client.controller.multiview.touch.TouchController;
import eu.ydp.empiria.player.client.controller.multiview.touch.TouchReservationHandler;
import eu.ydp.empiria.player.client.controller.report.AssessmentReportFactory;
import eu.ydp.empiria.player.client.controller.session.SessionDataManager;
import eu.ydp.empiria.player.client.controller.session.times.SessionTimeUpdater;
import eu.ydp.empiria.player.client.controller.style.StyleSocketAttributeHelper;
import eu.ydp.empiria.player.client.controller.variables.processor.results.ProcessingResultsToOutcomeMapConverterFactory;
import eu.ydp.empiria.player.client.gin.factory.AssessmentFactory;
import eu.ydp.empiria.player.client.gin.factory.DragDropObjectFactory;
import eu.ydp.empiria.player.client.gin.factory.IdentificationModuleFactory;
import eu.ydp.empiria.player.client.gin.factory.LinkModuleFactory;
import eu.ydp.empiria.player.client.gin.factory.MediaWrapperFactory;
import eu.ydp.empiria.player.client.gin.factory.MediaWrappersPairFactory;
import eu.ydp.empiria.player.client.gin.factory.ModuleFactory;
import eu.ydp.empiria.player.client.gin.factory.ModuleProviderFactory;
import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.gin.factory.SingleFeedbackSoundPlayerFactory;
import eu.ydp.empiria.player.client.gin.factory.SingleModuleInstanceProvider;
import eu.ydp.empiria.player.client.gin.factory.SlideshowPlayerModuleFactory;
import eu.ydp.empiria.player.client.gin.factory.TemplateParserFactory;
import eu.ydp.empiria.player.client.gin.factory.TextTrackFactory;
import eu.ydp.empiria.player.client.gin.factory.TouchRecognitionFactory;
import eu.ydp.empiria.player.client.gin.factory.TouchReservationFactory;
import eu.ydp.empiria.player.client.gin.factory.VideoTextTrackElementFactory;
import eu.ydp.empiria.player.client.gin.providers.NewFlowPanelProvider;
import eu.ydp.empiria.player.client.gin.providers.UniqIdStringProvider;
import eu.ydp.empiria.player.client.gin.scopes.UniqueId;
import eu.ydp.empiria.player.client.media.texttrack.VideoTextTrackElementPresenter;
import eu.ydp.empiria.player.client.media.texttrack.VideoTextTrackElementView;
import eu.ydp.empiria.player.client.module.feedback.image.ImageFeedback;
import eu.ydp.empiria.player.client.module.feedback.image.ImageFeedbackPresenter;
import eu.ydp.empiria.player.client.module.feedback.text.TextFeedback;
import eu.ydp.empiria.player.client.module.feedback.text.TextFeedbackPresenter;
import eu.ydp.empiria.player.client.module.info.VariableInterpreterFactory;
import eu.ydp.empiria.player.client.module.info.handler.FieldValueHandlerFactory;
import eu.ydp.empiria.player.client.module.labelling.view.LabellingChildView;
import eu.ydp.empiria.player.client.module.labelling.view.LabellingChildViewImpl;
import eu.ydp.empiria.player.client.module.labelling.view.LabellingView;
import eu.ydp.empiria.player.client.module.labelling.view.LabellingViewImpl;
import eu.ydp.empiria.player.client.module.media.MediaControllerFactory;
import eu.ydp.empiria.player.client.module.media.MediaControllerFactoryImpl;
import eu.ydp.empiria.player.client.module.media.fullscreen.VideoFullScreenHelper;
import eu.ydp.empiria.player.client.preloader.view.InfinityProgressWidget;
import eu.ydp.empiria.player.client.preloader.view.ProgressView;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.empiria.player.client.util.dom.drag.DragDropHelper;
import eu.ydp.empiria.player.client.util.dom.drag.DragDropHelperImpl;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.bus.PlayerEventsBus;
import eu.ydp.empiria.player.client.util.events.dom.emulate.HasTouchHandlers;
import eu.ydp.empiria.player.client.util.events.dom.emulate.TouchRecognition;
import eu.ydp.empiria.player.client.util.style.NativeStyleHelper;
import eu.ydp.empiria.player.client.util.style.NativeStyleHelperImpl;
import eu.ydp.empiria.player.client.util.style.StyleToPropertyMappingHelper;
import eu.ydp.empiria.player.client.view.player.PageControllerCache;
import eu.ydp.empiria.player.client.view.player.PageViewCache;
import eu.ydp.empiria.player.client.view.player.PlayerContentView;
import eu.ydp.empiria.player.client.view.player.PlayerViewSocket;
import eu.ydp.gwtutil.client.dom.DOMTreeWalker;
import eu.ydp.gwtutil.client.json.NativeMethodInvocator;
import eu.ydp.gwtutil.client.json.NativeMethodInvocatorImpl;
import eu.ydp.gwtutil.client.json.js.YJsJsonConverter;
import eu.ydp.gwtutil.client.scheduler.Scheduler;
import eu.ydp.gwtutil.client.scheduler.SchedulerImpl;
import eu.ydp.gwtutil.client.service.json.IJSONService;
import eu.ydp.gwtutil.client.service.json.JSONService;
import eu.ydp.gwtutil.client.timer.Timer;
import eu.ydp.gwtutil.client.timer.TimerImpl;
import eu.ydp.gwtutil.client.ui.GWTPanelFactory;
import eu.ydp.gwtutil.client.ui.GWTPanelFactoryImpl;
import eu.ydp.gwtutil.client.util.BooleanUtils;
import eu.ydp.gwtutil.client.util.BrowserNativeInterface;
import eu.ydp.gwtutil.client.util.BrowserNativeInterfaceImpl;
import eu.ydp.gwtutil.client.util.UserAgentUtil;
import eu.ydp.gwtutil.client.util.UserAgentUtilImpl;
import eu.ydp.gwtutil.client.xml.XMLParser;

@SuppressWarnings("PMD")
public class PlayerGinModule extends AbstractGinModule {

	@SuppressWarnings("unchecked")
	@Override
	protected void configure() {
		bind(StyleDataSourceManager.class).in(Singleton.class);
		bind(StyleSocket.class).to(StyleDataSourceManager.class).in(Singleton.class);
		bind(PlayerViewSocket.class).to(PlayerContentView.class).in(Singleton.class);
		bind(PlayerContentView.class).in(Singleton.class);

		// this is unnecessary, but left for clarity - if GIN can't find a
		// binding for a class, it falls back to calling GWT.create() on that
		// class
		bind(DataSourceManager.class).in(Singleton.class);
		bind(EventsBus.class).to(PlayerEventsBus.class).in(Singleton.class);
		bind(DefaultMediaProcessorExtension.class).in(Singleton.class);
		bind(MultiPageController.class).in(Singleton.class);
		bind(MultiPageTouchHandler.class).in(Singleton.class);
		bind(TouchController.class).in(Singleton.class);

		bind(PageViewCache.class).in(Singleton.class);
		bind(PageControllerCache.class).in(Singleton.class);
		bind(StyleNameConstants.class).in(Singleton.class);
		bind(MainFlowProcessor.class).in(Singleton.class);
		bind(Scheduler.class).to(SchedulerImpl.class).in(Singleton.class);
		bind(Page.class).in(Singleton.class);
		bind(PanelCache.class).in(Singleton.class);
		bind(SoundActionProcessor.class).in(Singleton.class);
		bind(DOMTreeWalker.class);
		bind(GWTPanelFactory.class).to(GWTPanelFactoryImpl.class).in(Singleton.class);
		bind(MediaControllerFactory.class).to(MediaControllerFactoryImpl.class).in(Singleton.class);
		bind(VideoFullScreenHelper.class).in(Singleton.class);
		bind(VideoTextTrackElementPresenter.class).to(VideoTextTrackElementView.class);
		bind(NativeStyleHelper.class).to(NativeStyleHelperImpl.class);
		bind(StyleToPropertyMappingHelper.class);
		bind(ModuleHandlerManager.class).in(Singleton.class);
		bind(IBookmarkPopupView.class).to(BookmarkPopup.class);
		bind(XMLParser.class);
		bind(StyleSocketAttributeHelper.class).in(Singleton.class);
		bind(BooleanUtils.class).in(Singleton.class);
		bind(FeedbackRegistry.class).in(Singleton.class);
		bind(MatcherRegistry.class).in(Singleton.class);
		bind(IPlayerContainersAccessor.class).to(PlayerContainersAccessor.class).in(Singleton.class);
		bind(DragDropHelper.class).to(DragDropHelperImpl.class).in(Singleton.class);
		bind(TextFeedback.class).to(TextFeedbackPresenter.class);
		bind(ImageFeedback.class).to(ImageFeedbackPresenter.class);
		bind(ProgressView.class).to(InfinityProgressWidget.class);
		bind(BrowserNativeInterface.class).to(BrowserNativeInterfaceImpl.class);
		bind(UserAgentUtil.class).to(UserAgentUtilImpl.class);
		bind(MediaConnector.class).to(JsMediaConnector.class);
		bind(ExternalMediaEngine.class).in(Singleton.class);
		bind(MediaConnectorListener.class).to(ExternalMediaEngine.class);
		bind(Timer.class).to(TimerImpl.class);
		bind(NativeMethodInvocator.class).to(NativeMethodInvocatorImpl.class);
		bind(FlowPanel.class).annotatedWith(Names.named("multiPageControllerMainPanel")).toProvider(NewFlowPanelProvider.class).in(Singleton.class);
		bind(FullscreenVideoConnector.class).to(ExternalFullscreenVideoConnector.class).in(Singleton.class);
		bind(SingleModuleInstanceProvider.class);
		bind(SessionDataManager.class).in(Singleton.class);
		bind(SessionTimeUpdater.class).in(Singleton.class);
		bind(YJsJsonConverter.class).in(Singleton.class);
		bind(IJSONService.class).to(JSONService.class).in(Singleton.class);
		bind(LabellingView.class).to(LabellingViewImpl.class);
		bind(LabellingChildView.class).to(LabellingChildViewImpl.class);
		bind(String.class).annotatedWith(UniqueId.class).toProvider(UniqIdStringProvider.class);
		install(new GinFactoryModuleBuilder().build(VideoTextTrackElementFactory.class));
		install(new GinFactoryModuleBuilder().build(MediaWrapperFactory.class));
		install(new GinFactoryModuleBuilder().build(PageScopeFactory.class));
		install(new GinFactoryModuleBuilder().build(TextTrackFactory.class));
		install(new GinFactoryModuleBuilder().build(AssessmentFactory.class));
		install(new GinFactoryModuleBuilder().build(ModuleFactory.class));
		bind(ModuleProviderFactory.class).in(Singleton.class);
		install(new GinFactoryModuleBuilder().build(AssessmentControllerFactory.class));
		install(new GinFactoryModuleBuilder().build(DragDropObjectFactory.class));
		install(new GinFactoryModuleBuilder().build(MatcherRegistryFactory.class));
		install(new GinFactoryModuleBuilder().build(TemplateParserFactory.class));
		install(new GinFactoryModuleBuilder().implement(HasTouchHandlers.class, TouchRecognition.class).build(TouchRecognitionFactory.class));
		install(new GinFactoryModuleBuilder().build(MediaWrappersPairFactory.class));
		install(new GinFactoryModuleBuilder().build(FieldValueHandlerFactory.class));
		install(new GinFactoryModuleBuilder().build(VariableInterpreterFactory.class));
		install(new GinFactoryModuleBuilder().build(AssessmentReportFactory.class));
		install(new GinFactoryModuleBuilder().build(SingleFeedbackSoundPlayerFactory.class));
		install(new GinFactoryModuleBuilder().build(ProcessingResultsToOutcomeMapConverterFactory.class));
		install(new GinFactoryModuleBuilder().build(LinkModuleFactory.class));
		install(new GinFactoryModuleBuilder().implement(IStickieView.class, StickieView.class).implement(IStickiePresenter.class, StickiePresenter.class)
				.build(StickieFactory.class));
		install(new GinFactoryModuleBuilder().build(SlideshowPlayerModuleFactory.class));
		install(new GinFactoryModuleBuilder().implement(HandlerRegistration.class, TouchReservationHandler.class).build(TouchReservationFactory.class));
		install(new GinFactoryModuleBuilder().build(IdentificationModuleFactory.class));
	}

	@Provides
	public IStickieProperties provideStickieProperties() {
		return StickieProperties.newInstance();
	}
}
