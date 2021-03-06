package eu.ydp.empiria.player.client.module;

import com.google.gwt.xml.client.Element;
import eu.ydp.empiria.player.client.resources.EmpiriaTagConstants;

public enum ModuleTagName {
    DIV("div"),
    GROUP("group"),
    SPAN("span"),
    TEXT_INTERACTION("textInteraction"),
    IMG("img"),
    CHOICE_INTERACTION("choiceInteraction"),
    SELECTION_INTERACTION("selectionInteraction"),
    IDENTYFICATION_INTERACTION("identificationInteraction"),
    IDENTYFICATION_MATH_INTERACTION("identificationMathInteraction"),
    TEXT_ENTRY_INTERACTION("textEntryInteraction"),
    INLINE_CHOICE_INTERACTION("inlineChoiceInteraction"),
    SIMPLE_TEXT("simpleText"),
    AUDIO_PLAYER("audioPlayer"),
    MATH_TEXT("mathText"),
    MATH_INTERACTION("mathInteraction"),
    OBJECT("object"),
    SLIDESHOW_PLAYER("slideshowPlayer"),
    PROMPT("prompt"),
    TABLE("table"),
    PAGE_IN_PAGE("pageInPage"),
    SHAPE("shape"),
    INFO("info"),
    REPORT("report"),
    LINK("link"),
    NEXT_ITEM_NAVIGATION("nextItemNavigation"),
    PREV_ITEM_NAVIGATION("prevItemNavigation"),
    PAGES_SWITCH_BOX("pagesSwitchBox"),
    MARK_ALL_BUTTON("markAllButton"),
    SHOW_ANSWERS_BUTTON("showAnswersButton"),
    RESET_BUTTON("resetButton"),
    SUB("sub"),
    SUP("sup"),
    FLASH("flash"),
    AUDIO_MUTE_BUTTON("feedbackAudioMuteButton"),
    MEDIA_PLAY_PAUSE_BUTTON("mediaPlayPauseButton"),
    MEDIA_PLAY_STOP_BUTTON("mediaPlayStopButton"),
    MEDIA_STOP_BUTTON("mediaStopButton"),
    MEDIA_MUTE_BUTTON("mediaMuteButton"),
    MEDIA_PROGRESS_BAR("mediaProgressBar"),
    MEDIA_VOLUME_BAR("mediaVolumeBar"),
    MEDIA_FULL_SCREEN_BUTTON("mediaFullScreenButton"),
    MEDIA_POSITION_IN_STREAM("mediaPositinInStream"),
    MEDIA_CURRENT_TIME("mediaCurrentTime"),
    MEDIA_TOTAL_TIME("mediaTotalTime"),
    MEDIA_TITLE("mediaTitle"),
    MEDIA_DESCRIPTION("mediaDescription"),
    MEDIA_SCREEN("mediaScreen"),
    SIMULATION_PLAYER("simulationPlayer"),
    MEDIA_TEXT_TRACK("mediaTextTrack"),
    MATH_GAP_TEXT_ENTRY_TYPE("gap_text-entry"),
    MATH_GAP_INLINE_CHOICE_TYPE("gap_inline-choice"),
    MATCH_INTERACTION("matchInteraction"),
    SOURCE_LIST("sourceList"),
    TEXT_FEEDBACK("textFeedback"),
    IMAGE_FEEDBACK("imageFeedback"),
    INLINE_CONTAINER_STYLE_STRONG("b"),
    LABELLING_INTERACTION("labellingInteraction"),
    ORDER_INTERACTION("orderInteraction"),
    COLORFILL_INTERACTION("colorfillInteraction"),
    DRAG_GAP("dragInteraction"),
    TUTOR("tutor"),
    BUTTON("button"),
    DRAWING("drawing"),
    BONUS("bonus"),
    PROGRESS_BONUS("progressBonus"),
    VIDEO("video"),
    DICTIONARY("dictionaryButton"),
    OPEN_QUESTION("openQuestion"),
    TEST_PAGE_SUBMIT("testPageSubmitButton"),
    TEST_RESET("testResetButton"),
    SPEECH_SCORE("speechScore"),
    EXTERNAL_INTERACTION("externalInteraction"),
    EXTERNAL_PRESENTATION("externalPresentation"),
    EXTERNAL_COMMON_PRESENTATION("externalCommonPresentation"),
    INLINE_MATH_JAX("inlineMathJax"),
    INTERACTION_MATH_JAX("interactionMathJax"),
    MATH_DRAG_GAP_TYPE("dragInteractionMath"),
    ACCORDION("accordion"),
    MENU("menu");

    String name = null;

    ModuleTagName(String name) {
        this.name = name;
    }

    public String tagName() {
        return name;
    }

    public static ModuleTagName getTag(String name) {
        ModuleTagName returnValue = null;
        for (ModuleTagName tag : ModuleTagName.values()) {
            if (tag.name.equals(name)) {
                returnValue = tag;
                break;
            }
        }
        return returnValue;
    }

    public static String getTagNameWithType(Element element) {
        String tagName = element.getTagName();
        String type = element.getAttribute(EmpiriaTagConstants.ATTR_TYPE);
        return getTagNameWithType(tagName, type);
    }

    private static String getTagNameWithType(String tagName, String type) {
        String tagNameWithType = "";
        if ("gap".equals(tagName)) {
            if ("text-entry".equals(type)) {
                tagNameWithType = MATH_GAP_TEXT_ENTRY_TYPE.toString();
            } else if ("inline-choice".equals(type)) {
                tagNameWithType = MATH_GAP_INLINE_CHOICE_TYPE.toString();
            } else if ("drag".equals(type)) {
                tagNameWithType = MATH_DRAG_GAP_TYPE.toString();
            } else if ("identification".equals(type)) {
                tagNameWithType = IDENTYFICATION_MATH_INTERACTION.toString();
            }
        }
        return tagNameWithType;
    }

    @Override
    public String toString() {
        return name;
    }

}
