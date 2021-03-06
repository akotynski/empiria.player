package eu.ydp.empiria.player.client.gin.factory;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.controller.extensions.internal.TutorApiExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.modules.AudioMuteButtonModuleConnectorExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.modules.CheckButtonModuleConnectorExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.modules.ResetButtonModuleConnectorExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.modules.ShowAnswersButtonModuleConnectorExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.DefaultMediaProcessorExtension;
import eu.ydp.empiria.player.client.controller.feedback.processor.ImageActionProcessor;
import eu.ydp.empiria.player.client.module.audio.AudioModule;
import eu.ydp.empiria.player.client.module.core.base.InlineContainerModule;
import eu.ydp.empiria.player.client.controller.feedback.processor.TextActionProcessor;
import eu.ydp.empiria.player.client.module.accordion.AccordionModule;
import eu.ydp.empiria.player.client.module.bonus.BonusModule;
import eu.ydp.empiria.player.client.module.button.download.ButtonModule;
import eu.ydp.empiria.player.client.module.choice.ChoiceModule;
import eu.ydp.empiria.player.client.module.colorfill.ColorfillInteractionModule;
import eu.ydp.empiria.player.client.module.connection.ConnectionModule;
import eu.ydp.empiria.player.client.module.containers.DivModule;
import eu.ydp.empiria.player.client.module.containers.SubHtmlContainerModule;
import eu.ydp.empiria.player.client.module.containers.SupHtmlContainerModule;
import eu.ydp.empiria.player.client.module.containers.TextInteractionModule;
import eu.ydp.empiria.player.client.module.containers.group.GroupModule;
import eu.ydp.empiria.player.client.module.dictionary.DictionaryModule;
import eu.ydp.empiria.player.client.module.draggap.math.MathDragGapModule;
import eu.ydp.empiria.player.client.module.draggap.standard.DragGapModule;
import eu.ydp.empiria.player.client.module.drawing.DrawingModule;
import eu.ydp.empiria.player.client.module.external.interaction.ExternalInteractionModule;
import eu.ydp.empiria.player.client.module.external.presentation.ExternalPresentationModule;
import eu.ydp.empiria.player.client.module.flash.FlashModule;
import eu.ydp.empiria.player.client.module.identification.math.IdentificationMathModule;
import eu.ydp.empiria.player.client.module.identification.IdentificationModule;
import eu.ydp.empiria.player.client.module.img.ImgModule;
import eu.ydp.empiria.player.client.module.inlinechoice.InlineChoiceModule;
import eu.ydp.empiria.player.client.module.inlinechoice.math.InlineChoiceMathGapModule;
import eu.ydp.empiria.player.client.module.labelling.LabellingModule;
import eu.ydp.empiria.player.client.module.math.MathModule;
import eu.ydp.empiria.player.client.module.mathjax.inline.InlineMathJaxModule;
import eu.ydp.empiria.player.client.module.mathjax.interaction.InteractionMathJaxModule;
import eu.ydp.empiria.player.client.module.mathtext.MathTextModule;
import eu.ydp.empiria.player.client.module.menu.MenuModule;
import eu.ydp.empiria.player.client.module.object.ObjectModule;
import eu.ydp.empiria.player.client.module.ordering.OrderInteractionModule;
import eu.ydp.empiria.player.client.module.pageinpage.PageInPageModule;
import eu.ydp.empiria.player.client.module.progressbonus.ProgressBonusModule;
import eu.ydp.empiria.player.client.module.prompt.PromptModule;
import eu.ydp.empiria.player.client.module.report.ReportModule;
import eu.ydp.empiria.player.client.module.selection.SelectionModule;
import eu.ydp.empiria.player.client.module.shape.ShapeModule;
import eu.ydp.empiria.player.client.module.simpletext.SimpleTextModule;
import eu.ydp.empiria.player.client.module.simulation.SimulationModule;
import eu.ydp.empiria.player.client.module.slideshow.SlideshowPlayerModule;
import eu.ydp.empiria.player.client.module.sourcelist.SourceListModule;
import eu.ydp.empiria.player.client.module.span.SpanModule;
import eu.ydp.empiria.player.client.module.speechscore.SpeechScoreModule;
import eu.ydp.empiria.player.client.module.table.TableModule;
import eu.ydp.empiria.player.client.module.test.reset.TestResetButtonModule;
import eu.ydp.empiria.player.client.module.test.submit.TestPageSubmitButtonModule;
import eu.ydp.empiria.player.client.module.texteditor.TextEditorModule;
import eu.ydp.empiria.player.client.module.textentry.TextEntryGapModule;
import eu.ydp.empiria.player.client.module.textentry.math.TextEntryMathGapModule;
import eu.ydp.empiria.player.client.module.tutor.TutorModule;
import eu.ydp.empiria.player.client.module.video.VideoModule;

@Singleton
public class ModuleProviderFactory {
    @Inject
    private Provider<ConnectionModule> connectionModule;
    @Inject
    private Provider<SourceListModule> sourceListModule;
    @Inject
    private Provider<ObjectModule> objectModule;
    @Inject
    private Provider<PageInPageModule> pageInPageModule;
    @Inject
    private Provider<TextActionProcessor> textActionProcessor;
    @Inject
    private Provider<ImageActionProcessor> imageActionProcessor;
    @Inject
    private Provider<ImgModule> imgModule;
    @Inject
    private Provider<SelectionModule> selectionModule;
    @Inject
    private Provider<InlineContainerModule> inlineContainerModule;
    @Inject
    private Provider<DefaultMediaProcessorExtension> mediaProcessor;
    @Inject
    private Provider<MathModule> mathModule;
    @Inject
    private Provider<CheckButtonModuleConnectorExtension> checkButtonModuleConnectorExtension;
    @Inject
    private Provider<ShowAnswersButtonModuleConnectorExtension> showAnswersButtonModuleConnectorExtension;
    @Inject
    private Provider<AudioMuteButtonModuleConnectorExtension> audioMuteButtonModuleConnectorExtension;
    @Inject
    private Provider<ResetButtonModuleConnectorExtension> resetButtonModuleConnectorExtension;
    @Inject
    private Provider<InlineChoiceModule> inlineChoiceModule;
    @Inject
    private Provider<IdentificationModule> identificationModule;
    @Inject
    private Provider<SimulationModule> simulationModule;
    @Inject
    private Provider<SlideshowPlayerModule> slideshowPlayerModule;
    @Inject
    private Provider<LabellingModule> labellingModule;
    @Inject
    private Provider<OrderInteractionModule> orderInteractionModule;
    @Inject
    private Provider<ChoiceModule> choiceModule;
    @Inject
    private Provider<InlineChoiceMathGapModule> inlineChoiceMathGapModule;
    @Inject
    private Provider<TextEntryGapModule> textEntryGapModule;
    @Inject
    private Provider<TextEntryMathGapModule> textEntryMathGapModule;
    @Inject
    private Provider<DragGapModule> dragGapModule;
    @Inject
    private Provider<MathDragGapModule> mathDragGapModule;
    @Inject
    private Provider<DivModule> divModule;
    @Inject
    private Provider<GroupModule> groupModule;
    @Inject
    private Provider<SpanModule> spanModule;
    @Inject
    private Provider<TextInteractionModule> textInteractionModule;
    @Inject
    private Provider<ColorfillInteractionModule> colorfillInteractionModule;
    @Inject
    private Provider<SimpleTextModule> simpleTextModule;
    @Inject
    private Provider<MathTextModule> mathTextModule;
    @Inject
    private Provider<InlineMathJaxModule> inlineMathJaxModule;
    @Inject
    private Provider<InteractionMathJaxModule> interactionMathJaxModule;
    @Inject
    private Provider<FlashModule> flashModule;
    @Inject
    private Provider<PromptModule> promptModule;
    @Inject
    private Provider<TableModule> tableModule;
    @Inject
    private Provider<ShapeModule> shapeModule;
    @Inject
    private Provider<SupHtmlContainerModule> supHtmlContainerModule;
    @Inject
    private Provider<SubHtmlContainerModule> subHtmlContainerModule;
    @Inject
    private Provider<TutorModule> tutor;
    @Inject
    private Provider<ButtonModule> buttonModule;
    @Inject
    private Provider<TutorApiExtension> tutorApiExtension;
    @Inject
    private Provider<DrawingModule> drawingModule;
    @Inject
    private Provider<BonusModule> bonusModule;
    @Inject
    private Provider<ProgressBonusModule> progressBonusModule;
    @Inject
    private Provider<VideoModule> videoModule;
    @Inject
    private Provider<AudioModule> audioModule;
    @Inject
    private Provider<DictionaryModule> dictionaryModule;
    @Inject
    private Provider<TextEditorModule> textEditorModule;
    @Inject
    private Provider<TestPageSubmitButtonModule> testPageSubmitButtonModule;
    @Inject
    private Provider<TestResetButtonModule> testResetButtonModule;
    @Inject
    private Provider<SpeechScoreModule> speechScoreModule;
    @Inject
    private Provider<ExternalInteractionModule> externalInteractionModuleProvider;
    @Inject
    private Provider<ExternalPresentationModule> externalPresentationModuleProvider;
    @Inject
    private Provider<AccordionModule> accordionModule;
    @Inject
    private Provider<ReportModule> reportModuleProvider;
    @Inject
    private Provider<MenuModule> menuModule;
    @Inject
    private Provider<IdentificationMathModule> identificationMathModule;

    public Provider<ConnectionModule> getConnectionModule() {
        return connectionModule;
    }

    public Provider<SourceListModule> getSourceListModule() {
        return sourceListModule;
    }

    public Provider<ObjectModule> getObjectModule() {
        return objectModule;
    }

    public Provider<PageInPageModule> getPageInPageModule() {
        return pageInPageModule;
    }

    public Provider<TextActionProcessor> getTextActionProcessor() {
        return textActionProcessor;
    }

    public Provider<ImageActionProcessor> getImageActionProcessor() {
        return imageActionProcessor;
    }

    public Provider<ImgModule> getImgModule() {
        return imgModule;
    }

    public Provider<InlineContainerModule> getInlineContainerModule() {
        return inlineContainerModule;
    }

    public Provider<SelectionModule> getSelectionModule() {
        return selectionModule;
    }

    public Provider<DefaultMediaProcessorExtension> getMediaProcessor() {
        return mediaProcessor;
    }

    public Provider<MathModule> getMathModule() {
        return mathModule;
    }

    public Provider<InlineChoiceModule> getInlineChoiceModule() {
        return inlineChoiceModule;
    }

    public Provider<MathDragGapModule> getMathDragGapModule() {
        return mathDragGapModule;
    }

    public Provider<CheckButtonModuleConnectorExtension> getCheckButtonModuleConnectorExtension() {
        return checkButtonModuleConnectorExtension;
    }

    public Provider<ShowAnswersButtonModuleConnectorExtension> getShowAnswersButtonModuleConnectorExtension() {
        return showAnswersButtonModuleConnectorExtension;
    }

    public Provider<AudioMuteButtonModuleConnectorExtension> getAudioMuteButtonModuleConnectorExtension() {
        return audioMuteButtonModuleConnectorExtension;
    }

    public Provider<ResetButtonModuleConnectorExtension> getResetButtonModuleConnectorExtension() {
        return resetButtonModuleConnectorExtension;
    }

    public Provider<IdentificationModule> getIdentificationModule() {
        return identificationModule;
    }

    public Provider<SimulationModule> getSimulationModule() {
        return simulationModule;
    }

    public Provider<SlideshowPlayerModule> getSlideshowPlayerModule() {
        return slideshowPlayerModule;
    }

    public Provider<LabellingModule> getLabellingModule() {
        return labellingModule;
    }

    public Provider<OrderInteractionModule> getOrderInteractionModule() {
        return orderInteractionModule;
    }

    public Provider<ChoiceModule> getChoiceModule() {
        return choiceModule;
    }

    public Provider<InlineChoiceMathGapModule> getInlineChoiceMathGapModule() {
        return inlineChoiceMathGapModule;
    }

    public Provider<TextEntryGapModule> getTextEntryGapModule() {
        return textEntryGapModule;
    }

    public Provider<TextEntryMathGapModule> getTextEntryMathGapModule() {
        return textEntryMathGapModule;
    }

    public Provider<DragGapModule> getDragGapModule() {
        return dragGapModule;
    }

    public Provider<ColorfillInteractionModule> getColorfillInteractionModule() {
        return colorfillInteractionModule;
    }

    public Provider<DivModule> getDivModule() {
        return divModule;
    }

    public Provider<GroupModule> getGroupModule() {
        return groupModule;
    }

    public Provider<SpanModule> getSpanModule() {
        return spanModule;
    }

    public Provider<TextInteractionModule> getTextInteractionModule() {
        return textInteractionModule;
    }

    public Provider<SimpleTextModule> getSimpleTextModule() {
        return simpleTextModule;
    }

    public Provider<MathTextModule> getMathTextModule() {
        return mathTextModule;
    }

    public Provider<InlineMathJaxModule> getInlineMathJaxModule() {
        return inlineMathJaxModule;
    }

    public Provider<FlashModule> getFlashModule() {
        return flashModule;
    }

    public Provider<PromptModule> getPromptModule() {
        return promptModule;
    }

    public Provider<TableModule> getTableModule() {
        return tableModule;
    }

    public Provider<ShapeModule> getShapeModule() {
        return shapeModule;
    }

    public Provider<SupHtmlContainerModule> getSupHtmlContainerModule() {
        return supHtmlContainerModule;
    }

    public Provider<SubHtmlContainerModule> getSubHtmlContainerModule() {
        return subHtmlContainerModule;
    }

    public Provider<TutorModule> getTutorModule() {
        return tutor;
    }

    public Provider<ButtonModule> getButtonModule() {
        return buttonModule;
    }

    public Provider<TutorApiExtension> getTutorApiExtension() {
        return tutorApiExtension;
    }

    public Provider<DrawingModule> getDrawingModule() {
        return drawingModule;
    }

    public Provider<BonusModule> getBonusModule() {
        return bonusModule;
    }

    public Provider<ProgressBonusModule> getProgressBonusModule() {
        return progressBonusModule;
    }

    public Provider<VideoModule> getVideoModule() {
        return videoModule;
    }

    public Provider<AudioModule> getAudioModule(){
        return audioModule;
    }

    public Provider<DictionaryModule> getDictionaryModule() {
        return dictionaryModule;
    }

    public Provider<TextEditorModule> getTextEditorModule() {
        return textEditorModule;
    }

    public Provider<TestPageSubmitButtonModule> getTestPageSubmitButtonModule() {
        return testPageSubmitButtonModule;
    }

    public Provider<TestResetButtonModule> getTestResetButtonModule() {
        return testResetButtonModule;
    }

    public Provider<SpeechScoreModule> getSpeechScoreModule() {
        return speechScoreModule;
    }

    public Provider<ExternalInteractionModule> getExternalInteractionModule() {
        return externalInteractionModuleProvider;
    }

    public Provider<ExternalPresentationModule> getExternalPresentationModule() {
        return externalPresentationModuleProvider;
    }

    public Provider<InteractionMathJaxModule> getInteractionMathJaxModule() {
        return interactionMathJaxModule;
    }

    public Provider<AccordionModule> getAccordionModule() {
        return accordionModule;
    }

    public Provider<ReportModule> getReportModule() {
        return reportModuleProvider;
    }

    public Provider<MenuModule> getMenuModule() {
        return menuModule;
    }

    public Provider<IdentificationMathModule> getIdentificationMathModule() {
        return identificationMathModule;
    }
}
