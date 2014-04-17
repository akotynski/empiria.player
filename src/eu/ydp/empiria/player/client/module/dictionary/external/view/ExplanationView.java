package eu.ydp.empiria.player.client.module.dictionary.external.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.module.dictionary.external.controller.ExplanationListener;
import eu.ydp.empiria.player.client.module.dictionary.external.model.Entry;

public class ExplanationView extends Composite {

	private static ExplanationViewUiBinder uiBinder = GWT.create(ExplanationViewUiBinder.class);

	interface ExplanationViewUiBinder extends UiBinder<Widget, ExplanationView> {
	}

	@UiField
	Panel postPanel;
	@UiField
	Panel angPanel;
	@UiField
	Panel polPanel;
	@UiField
	Panel descPanel;

	@UiField
	Label postLabel;
	@UiField
	Label angLabel;
	@UiField
	InlineHTML polLabel;
	@UiField
	Label descLabel;

	@UiField
	PushButton backButton;
	@UiField
	PushButton playButton;

	private String descrSound;
	private boolean playingDescr;

	@Inject
	private Provider<ExplanationListener> listenerProvider;

	public ExplanationView() {
		initWidget(uiBinder.createAndBindUi(this));
		playingDescr = false;
		initJs();
	}

	@UiHandler("backButton")
	public void backButtonClick(ClickEvent event) {
		listenerProvider.get().onBackClick();
	}

	@UiHandler("descPanel")
	public void descPanelMouseUp(MouseUpEvent event) {
		onPlayDescrClick();
	}

	@UiHandler("playButton")
	public void playButtonClick(ClickEvent event) {
		onPlayDescrClick();
	}

	public void displayEntry(Entry entry, boolean isPlaySound) {
		if (entry != null) {
			postLabel.setText(entry.getPost());
			angLabel.setText(entry.getAng());
			polLabel.setHTML(entry.getPol());
			descLabel.setText(entry.getDesc());
			descrSound = entry.getDescrSound();
			if (isPlaySound) {
				playSound(entry.getAngSound());
			}
		}
	}

	public void show() {
		Style style = getElement().getStyle();
		style.setDisplay(Display.BLOCK);
	}

	public void hide() {
		Style style = getElement().getStyle();
		style.setDisplay(Display.NONE);
		stopDescrSound();
	}

	public void hideAndStopSound() {
		hide();
		stopDescrSound();
	}

	private native void initJs()/*-{
								var instance = this;
								$wnd.dictionarySoundFinished2 = function(){
								instance.@eu.ydp.empiria.player.client.module.dictionary.external.view.ExplanationView::soundDescrFinished()();
								}
								}-*/;

	private void onPlayDescrClick() {
		if (playingDescr) {
			stopDescrSound();
		} else {
			playDescrSound();
		}
	}

	private void playDescrSound() {
		if (descrSound != null && !descrSound.equals("")) {
			playSound(descrSound);
			playButton.setStylePrimaryName("dict-explanation-play-button-playing");
			playingDescr = true;
		}
	}

	// changed
	public void stopDescrSound() {
		playButton.setStylePrimaryName("dict-explanation-play-button");
		playingDescr = false;
		stopSoundJs();
	}

	private void soundDescrFinished() {
		playButton.setStylePrimaryName("dict-explanation-play-button");
		playingDescr = false;
	}

	private void playSound(String file) {
		String path = getMediaLinkJs(file);
		String cert = "";
		playSoundJs(path, cert);
	}

	private String getKey(String url) {
		String length = String.valueOf(url.length());
		if (url.length() + length.length() > 24) {
			url = url.substring(0, 24 - length.length());
		}
		while (url.length() + length.length() < 24) {
			length = "0" + length;
		}
		return url + length;
	}

	private native void playSoundJs(String file, String cert)/*-{
																if (typeof $wnd.dictionaryPlaySound == 'function'){
																$wnd.dictionaryPlaySound(file, cert);
																}
																}-*/;

	private native void stopSoundJs()/*-{
										if (typeof $wnd.dictionaryStopSound == 'function'){
										$wnd.dictionaryStopSound();
										}
										}-*/;

	private native String getMediaLinkJs(String file)/*-{
														if (typeof $wnd.dictionaryGetMediaLink == 'function'){
														return $wnd.dictionaryGetMediaLink(file);
														}
														return file;
														}-*/;
}
