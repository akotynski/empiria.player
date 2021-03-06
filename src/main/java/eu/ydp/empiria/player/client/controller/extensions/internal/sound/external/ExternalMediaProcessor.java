package eu.ydp.empiria.player.client.controller.extensions.internal.sound.external;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;
import eu.ydp.empiria.player.client.controller.events.interaction.MediaInteractionSoundEventCallback;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.AbstractMediaProcessor;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.external.wrapper.ExternalMediaProxy;
import eu.ydp.empiria.player.client.controller.extensions.types.MediaProcessorExtension;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration.MediaType;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.events.internal.callback.CallbackReceiver;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;

import static com.google.common.base.Preconditions.checkArgument;

public class ExternalMediaProcessor extends AbstractMediaProcessor implements MediaProcessorExtension {

    @Inject
    private Provider<ExternalMediaProxy> proxyProvider;
    @Inject
    private ExternalMediaEngine engine;
    protected MediaInteractionSoundEventCallback callback;

    @Override
    public void initMediaProcessor() {
        super.initEvents();
    }

    @Override
    protected void createMediaWrapper(PlayerEvent event) {
        checkArgument(event.getValue() instanceof BaseMediaConfiguration, "Event value should be of type BaseMediaConfiguration");

        BaseMediaConfiguration bmc = (BaseMediaConfiguration) event.getValue();

        if (bmc.getMediaType() != MediaType.AUDIO) {
            throw new UnsupportedOperationException("Currently only audio is supported by ExternalMediaProcessor. Audio media type was expeced.");
        }

        ExternalMediaProxy proxy = proxyProvider.get();
        engine.addMediaProxy(proxy);
        initExecutor(proxy.getMediaExecutor(), bmc);

        @SuppressWarnings("unchecked")
        CallbackReceiver<MediaWrapper<Widget>> callbackReceiver = ((CallbackReceiver<MediaWrapper<Widget>>) event.getSource());
        callbackReceiver.setCallbackReturnObject(proxy.getMediaWrapper());
    }

    @Override
    public void pauseAllOthers(MediaWrapper<?> mediaWrapper) {
    }

    @Override
    protected void pauseAll() {
        engine.pauseCurrent();
    }
}
