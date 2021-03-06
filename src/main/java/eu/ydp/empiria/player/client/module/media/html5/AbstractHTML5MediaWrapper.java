package eu.ydp.empiria.player.client.module.media.html5;

import com.google.gwt.dom.client.MediaElement;
import com.google.gwt.media.client.MediaBase;
import com.google.web.bindery.event.shared.HandlerRegistration;
import eu.ydp.empiria.player.client.controller.extensions.internal.media.html5.AbstractHTML5MediaExecutor;
import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.module.media.MediaAvailableOptions;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.module.object.impl.Media;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventTypes;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import static eu.ydp.empiria.player.client.util.events.internal.media.MediaEventTypes.ON_DURATION_CHANGE;

/**
 * Wrapper dla elemntow audio i elementow wspolnych audio i video html5 TODO: wydzielic HTML5AudioMediaWrapper a ten zostawic jako HTML5MediaWrapperBase
 */
public abstract class AbstractHTML5MediaWrapper implements MediaWrapper<MediaBase>, MediaEventHandler {
    private MediaBase mediaBase;
    private String uniqId = null;

    private final EventsBus eventsBus;
    private MediaAvailableOptions availableOptions = new HTML5MediaAvailableOptions();
    private boolean ready = false;
    private final Map<MediaEventTypes, HandlerRegistration> handlerRegistrations = new HashMap<MediaEventTypes, HandlerRegistration>();
    private AbstractHTML5MediaExecutor mediaExecutor;
    private final PageScopeFactory pageScopeFactory;

    public AbstractHTML5MediaWrapper(Media media, EventsBus eventBus, PageScopeFactory pageScopeFactory) {
        this.eventsBus = eventBus;
        this.pageScopeFactory = pageScopeFactory;
        setMediaBaseAndPreload(media.getMedia());
        registerEvents();
    }

    public void setMediaExecutor(AbstractHTML5MediaExecutor mediaExecutor) {
        this.mediaExecutor = mediaExecutor;
    }

    public AbstractHTML5MediaExecutor getMediaExecutor() {
        return mediaExecutor;
    }

    @Override
    public MediaAvailableOptions getMediaAvailableOptions() {
        return availableOptions;
    }

    public void setMediaAvailableOptions(MediaAvailableOptions options) {
        availableOptions = options;
    }

    @Override
    public MediaBase getMediaObject() {
        return mediaBase;
    }

    public void setMediaObject(MediaBase mediaBase) {
        setMediaBaseAndPreload(mediaBase);
    }

    private void setMediaBaseAndPreload(MediaBase mediaBase) {
        this.mediaBase = mediaBase;
        this.ready = false;
        mediaBase.setPreload(MediaElement.PRELOAD_METADATA);
    }

    private void registerEvents() {
        addHandlerRegistration(ON_DURATION_CHANGE,
                eventsBus.addAsyncHandlerToSource(MediaEvent.getType(ON_DURATION_CHANGE), this, this, pageScopeFactory.getCurrentPageScope()));
    }

    public void addHandlerRegistration(MediaEventTypes type, HandlerRegistration handlerRegistration) {
        handlerRegistrations.put(type, handlerRegistration);
    }

    @Override
    public String getMediaUniqId() {
        if (uniqId == null) {
            uniqId = String.valueOf(new StringBuilder().append(System.currentTimeMillis()).append(Math.random()).toString());
        }
        return uniqId;
    }

    @Override
    public double getCurrentTime() {
        return mediaBase.getCurrentTime();
    }

    @Override
    public double getDuration() {
        double duration = mediaBase.getDuration();
        return Double.isNaN(duration) ? 0 : duration;
    }

    @Override
    public boolean isMuted() {
        return mediaBase.isMuted();
    }

    @Override
    public double getVolume() {
        return mediaBase.getVolume();
    }

    @Override
    public boolean canPlay() {
        return ready || mediaBase.getReadyState() != MediaElement.HAVE_NOTHING;
    }

    @Override
    public void onMediaEvent(MediaEvent event) {
        MediaEventTypes eventType = event.getType();
        if (MediaEventTypes.ON_DURATION_CHANGE.equals(eventType)) {
            ready = true;
            removeHandler(eventType);
        }
    }

    protected void removeHandler(MediaEventTypes mediaEventType) {
        Set<Entry<MediaEventTypes, HandlerRegistration>> handleRegistrationsEntrySet = handlerRegistrations.entrySet();
        for (Entry<MediaEventTypes, HandlerRegistration> handlerRegistrationEntry : handleRegistrationsEntrySet) {
            MediaEventTypes entryMediaEventType = handlerRegistrationEntry.getKey();
            if (entryMediaEventType.equals(mediaEventType)) {
                HandlerRegistration handlerRegistration = handlerRegistrationEntry.getValue();
                handlerRegistration.removeHandler();
            }
        }
    }

    public MediaBase getMediaBase() {
        return mediaBase;
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + getMediaUniqId().hashCode();
        return result;
    }

    @Override
    @SuppressWarnings("PMD")
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        AbstractHTML5MediaWrapper other = (AbstractHTML5MediaWrapper) obj;
        if (getMediaUniqId() == null) {
            if (other.getMediaUniqId() != null) {
                return false;
            }
        } else if (!getMediaUniqId().equals(other.getMediaUniqId())) {
            return false;
        }
        return true;
    }

}
