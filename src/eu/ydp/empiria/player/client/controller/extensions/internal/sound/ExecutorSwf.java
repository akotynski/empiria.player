package eu.ydp.empiria.player.client.controller.extensions.internal.sound;

import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.gwtflashmedia.client.FlashMedia;
import eu.ydp.empiria.gwtflashmedia.client.event.FlashMediaCompleteEvent;
import eu.ydp.empiria.gwtflashmedia.client.event.FlashMediaCompleteHandler;
import eu.ydp.empiria.gwtflashmedia.client.event.FlashMediaLoadErrorEvent;
import eu.ydp.empiria.gwtflashmedia.client.event.FlashMediaLoadErrorHandler;
import eu.ydp.empiria.gwtflashmedia.client.event.FlashMediaLoadedEvent;
import eu.ydp.empiria.gwtflashmedia.client.event.FlashMediaLoadedHandler;
import eu.ydp.empiria.gwtflashmedia.client.event.FlashMediaMetadataEvent;
import eu.ydp.empiria.gwtflashmedia.client.event.FlashMediaMetadataHandler;
import eu.ydp.empiria.gwtflashmedia.client.event.FlashMediaMuteChangeEvent;
import eu.ydp.empiria.gwtflashmedia.client.event.FlashMediaMuteChangeHandler;
import eu.ydp.empiria.gwtflashmedia.client.event.FlashMediaPauseEvent;
import eu.ydp.empiria.gwtflashmedia.client.event.FlashMediaPauseHandler;
import eu.ydp.empiria.gwtflashmedia.client.event.FlashMediaPlayEvent;
import eu.ydp.empiria.gwtflashmedia.client.event.FlashMediaPlayHandler;
import eu.ydp.empiria.gwtflashmedia.client.event.FlashMediaPlayheadUpdateEvent;
import eu.ydp.empiria.gwtflashmedia.client.event.FlashMediaPlayheadUpdateHandler;
import eu.ydp.empiria.gwtflashmedia.client.event.FlashMediaStopEvent;
import eu.ydp.empiria.gwtflashmedia.client.event.FlashMediaStopHandler;
import eu.ydp.empiria.gwtflashmedia.client.event.FlashMediaVolumeChangeEvent;
import eu.ydp.empiria.gwtflashmedia.client.event.FlashMediaVolumeChangeHandler;
import eu.ydp.empiria.gwtflashmedia.client.event.HasFlashMediaHandlers;
import eu.ydp.empiria.player.client.PlayerGinjector;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.SourceUtil;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;

public abstract class ExecutorSwf implements SoundExecutor<Widget> {
	protected BaseMediaConfiguration baseMediaConfiguration;
	protected MediaWrapper<Widget> mediaWrapper = null;
	protected SoundExecutorListener soundExecutorListener;
	protected FlashMedia flashMedia;
	protected EventsBus eventsBus = PlayerGinjector.INSTANCE.getEventsBus();
	protected boolean pause;
	protected boolean playing = false;
	protected String source = null;
	protected boolean isFree = true;
	@Override
	public void init() {
		// Mapujemy wszystkie eventy flasha na mediaevent
		((HasFlashMediaHandlers) flashMedia).addFlashMediaPlayHandler(new FlashMediaPlayHandler() {
			@Override
			public void onFlashSoundPlay(FlashMediaPlayEvent event) {
				if (soundExecutorListener != null)
					soundExecutorListener.onPlay();
				playing = true;
				eventsBus.fireEventFromSource(new MediaEvent(MediaEventTypes.ON_PLAY, getMediaWrapper()), getMediaWrapper());
			}
		});

		((HasFlashMediaHandlers) flashMedia).addFlashMediaCompleteHandler(new FlashMediaCompleteHandler() {
			@Override
			public void onFlashSoundComplete(FlashMediaCompleteEvent event) {
				if (soundExecutorListener != null) {
					soundExecutorListener.onSoundFinished();
				}
				eventsBus.fireEventFromSource(new MediaEvent(MediaEventTypes.ENDED, getMediaWrapper()), getMediaWrapper());
			}
		});

		((HasFlashMediaHandlers) flashMedia).addFlashMediaLoadedHandler(new FlashMediaLoadedHandler() {
			@Override
			public void onFlashSoundLoaded(FlashMediaLoadedEvent event) {
				eventsBus.fireEventFromSource(new MediaEvent(MediaEventTypes.ON_DURATION_CHANGE, getMediaWrapper()), getMediaWrapper());
			}
		});

		((HasFlashMediaHandlers) flashMedia).addFlashMediaMuteChangeHandler(new FlashMediaMuteChangeHandler() {
			@Override
			public void onFlashSoundMuteChange(FlashMediaMuteChangeEvent event) {
				eventsBus.fireEventFromSource(new MediaEvent(MediaEventTypes.ON_VOLUME_CHANGE, getMediaWrapper()), getMediaWrapper());
			}
		});

		((HasFlashMediaHandlers) flashMedia).addFlashMediaVolumeChangeHandler(new FlashMediaVolumeChangeHandler() {
			@Override
			public void onFlashSoundVolumeChange(FlashMediaVolumeChangeEvent event) {
				eventsBus.fireEventFromSource(new MediaEvent(MediaEventTypes.ON_VOLUME_CHANGE, getMediaWrapper()), getMediaWrapper());
			}
		});
		((HasFlashMediaHandlers) flashMedia).addFlashMediaLoadErrorHandler(new FlashMediaLoadErrorHandler() {
			@Override
			public void onFlashSoundLoadError(FlashMediaLoadErrorEvent event) {
				eventsBus.fireEventFromSource(new MediaEvent(MediaEventTypes.ERROR, getMediaWrapper()), getMediaWrapper());
			}
		});
		((HasFlashMediaHandlers) flashMedia).addFlashMediaMetadataHandler(new FlashMediaMetadataHandler() {
			@Override
			public void onFlashMediaMetadataEvent(FlashMediaMetadataEvent event) {
				eventsBus.fireEventFromSource(new MediaEvent(MediaEventTypes.ON_DURATION_CHANGE, getMediaWrapper()), getMediaWrapper());
			}
		});

		((HasFlashMediaHandlers) flashMedia).addFlashMediaPauseHandler(new FlashMediaPauseHandler() {
			@Override
			public void onFlashSoundPause(FlashMediaPauseEvent event) {
				eventsBus.fireEventFromSource(new MediaEvent(MediaEventTypes.ON_PAUSE, getMediaWrapper()), getMediaWrapper());
			}
		});

		((HasFlashMediaHandlers) flashMedia).addFlashMediaStopHandler(new FlashMediaStopHandler() {
			@Override
			public void onFlashSoundStop(FlashMediaStopEvent event) {
				eventsBus.fireEventFromSource(new MediaEvent(MediaEventTypes.STOP, getMediaWrapper()), getMediaWrapper());
			}
		});
		((HasFlashMediaHandlers) flashMedia).addFlashMediaPositionChangeHandler(new FlashMediaPlayheadUpdateHandler() {

			@Override
			public void onFlashSoundPositionChange(FlashMediaPlayheadUpdateEvent event) {
				eventsBus.fireAsyncEventFromSource(new MediaEvent(MediaEventTypes.ON_TIME_UPDATE, getMediaWrapper(), event.getPlayheadTime() * .01f), getMediaWrapper());
			}
		});
		if (mediaWrapper != null) {
			((HasFlashMediaHandlers) flashMedia).addFlashMediaPositionChangeHandler((FlashMediaPlayheadUpdateHandler) mediaWrapper);
			((HasFlashMediaHandlers) flashMedia).addFlashMediaMetadataHandler((FlashMediaMetadataHandler) mediaWrapper);
			((HasFlashMediaHandlers) flashMedia).addFlashMediaMuteChangeHandler((FlashMediaMuteChangeHandler) mediaWrapper);
			((HasFlashMediaHandlers) flashMedia).addFlashMediaVolumeChangeHandler((FlashMediaVolumeChangeHandler) mediaWrapper);
		}
	}

	@Override
	public void setBaseMediaConfiguration(BaseMediaConfiguration baseMediaConfiguration) {
		this.baseMediaConfiguration = baseMediaConfiguration;
	}

	@Override
	public void setMediaWrapper(MediaWrapper<Widget> descriptor) {
		this.mediaWrapper = descriptor;
	}

	@Override
	public MediaWrapper<Widget> getMediaWrapper() {
		return mediaWrapper;
	}

	@Override
	public void setSoundFinishedListener(SoundExecutorListener listener) {
		this.soundExecutorListener = listener;
	}

	@Override
	public void setMuted(boolean mute) {
		flashMedia.setMute(mute);
	}

	@Override
	public void setVolume(double volume) {
		flashMedia.setVolume((int) (volume * 100));
	}

	@Override
	public void setCurrentTime(double time) {
		flashMedia.setPlayheadTime((int) (time * 1000));
	}

	@Override
	public void play(String src) {
		source = src;
		if (playing && !pause){
			stop();
		}
		if (flashMedia == null) {
			init();
			isFree = false;
		}
		if(isFree){
			flashMedia.setSrc(source);
			flashMedia.load();
		}
		flashMedia.play();
	}

	@Override
	public void pause() {
		pause = true;
		flashMedia.pause();
	}

	@Override
	public void play() {
		play(SourceUtil.getMpegSource(baseMediaConfiguration.getSources()));
	}

	public void free() {
		if (flashMedia != null) {
			flashMedia.free();
			isFree = true;
		}
	}
}