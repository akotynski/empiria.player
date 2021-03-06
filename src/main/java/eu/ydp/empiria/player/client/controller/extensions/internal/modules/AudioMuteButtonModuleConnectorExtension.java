package eu.ydp.empiria.player.client.controller.extensions.internal.modules;

import com.google.inject.Inject;
import com.google.inject.Provider;
import eu.ydp.empiria.player.client.module.core.creator.AbstractModuleCreator;
import eu.ydp.empiria.player.client.module.core.base.IModule;
import eu.ydp.empiria.player.client.module.core.creator.ModuleCreator;
import eu.ydp.empiria.player.client.module.ModuleTagName;
import eu.ydp.empiria.player.client.module.button.FeedbackAudioMuteButtonModule;

public class AudioMuteButtonModuleConnectorExtension extends ControlModuleConnectorExtension {

    @Inject
    private Provider<FeedbackAudioMuteButtonModule> provider;

    @Override
    public ModuleCreator getModuleCreator() {
        return new AbstractModuleCreator() {

            @Override
            public IModule createModule() {
                FeedbackAudioMuteButtonModule button = provider.get();
                initializeModule(button);
                return button;
            }
        };
    }

    @Override
    public String getModuleNodeName() {
        return ModuleTagName.AUDIO_MUTE_BUTTON.tagName();
    }

}
