package eu.ydp.empiria.player.client.module.dictionary.external.controller;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.dictionary.external.DictionaryMimeSourceProvider;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.callback.CallbackReceiver;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventTypes;

import java.util.Map;

public class DictionaryMediaWrapperCreator {

    @Inject
    private DictionaryMimeSourceProvider dictionaryMimeSourceProvider;

    @Inject
    private EventsBus eventsBus;

    public void create(String filePath, CallbackReceiver<MediaWrapper<Widget>> callback) {
        Map<String, String> sourcesWithTypes = dictionaryMimeSourceProvider.getSourcesWithTypes(filePath);

        BaseMediaConfiguration bmc = new BaseMediaConfiguration(sourcesWithTypes, false, true);
        eventsBus.fireEvent(new PlayerEvent(PlayerEventTypes.CREATE_MEDIA_WRAPPER, bmc, callback));
    }
}
