package eu.ydp.empiria.player.client.module;

public enum ModuleTagName {
	DIV("div"), GROUP("group"), SPAN("span"), TEXT_INTERACTION("textInteraction"), IMG("img"),
	CHOICE_INTERACTION("choiceInteraction"),
	SELECTION_INTERACTION("selectionInteraction"), IDENTYFICATION_INTERACTION("identificationInteraction"),
	TEXT_ENTRY_INTERACTION("textEntryInteraction"),	INLINE_CHOICE_INTERACTION("inlineChoiceInteraction"),
	SIMPLE_TEXT("simpleText"), AUDIO_PLAYER("audioPlayer"), MATH_TEXT("mathText"), MATH_INTERACTION("mathInteraction"),
	OBJECT("object"), SLIDESHOW_PLAYER("slideshowPlayer"), PROMPT("prompt"), TABLE("table"),
	PAGE_IN_PAGE("pageInPage"), SHAPE("shape"), INFO("info"), REPORT("report"), LINK("link"),
	NEXT_ITEM_NAVIGATION("nextItemNavigation"), PREV_ITEM_NAVIGATION("prevItemNavigation"), PAGES_SWITCH_BOX("pagesSwitchBox"),
	MARK_ALL_BUTTON("markAllButton"), SHOW_ANSWERS_BUTTON("showAnswersButton"), RESET_BUTTON("resetButton"), SUB("sub"), SUP("sup"), FLASH("flash"),
	AUDIO_MUTE_BUTTON("feedbackAudioMuteButton"),MEDIA_PLAY_PAUSE_BUTTON("mediaPlayPauseButton"),MEDIA_STOP_BUTTON("mediaStopButton"),MEDIA_MUTE_BUTTON("mediaMuteButton"),
	MEDIA_PROGRESS_BAR("mediaProgressBar"),MEDIA_VOLUME_BAR("mediaVolumeBar"),MEDIA_FULL_SCREEN_BUTTON("mediaFullScreenButton"),
	MEDIA_POSITION_IN_STREAM("mediaPositinInStream"),MEDIA_CURRENT_TIME("mediaCurrentTime"),MEDIA_TOTAL_TIME("mediaTotalTime"),
	MEDIA_TITLE("mediaTitle"), MEDIA_DESCRIPTION("mediaDescription"), MEDIA_SCREEN("mediaScreen"), SIMULATION_PLAYER("simulationPlayer"), MEDIA_TEXT_TRACK("mediaTextTrack"),
	MATH_GAP_TEXT_ENTRY_TYPE("gap_text-entry"), MATH_GAP_INLINE_CHOICE_TYPE("gap_inline-choice"),MATCH_INTERACTION("matchInteraction");
	String name = null;
	private ModuleTagName(String name){
		this.name = name;
	}

	public String tagName(){
		return name;
	}

	public static ModuleTagName getTag(String name){
		for(ModuleTagName tag : ModuleTagName.values()){
			if(tag.name.equals(name)){
				return tag;
			}
		}
		return null;
	}

	public static String getTagNameWithType(String tagName, String type) {
		String tagNameWithType = "";

		if ( tagName.equals("gap") && type.equals("text-entry") ) {
			tagNameWithType = MATH_GAP_TEXT_ENTRY_TYPE.toString();
		} else if ( tagName.equals("gap") && type.equals("inline-choice") ) {
			tagNameWithType = MATH_GAP_INLINE_CHOICE_TYPE.toString();
		}

		return tagNameWithType;
	}

	@Override
	public String toString() {
		return name;
	}


}
