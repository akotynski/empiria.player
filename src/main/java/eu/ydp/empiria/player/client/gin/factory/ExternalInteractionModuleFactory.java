package eu.ydp.empiria.player.client.gin.factory;

import com.google.gwt.user.client.ui.Widget;
import eu.ydp.empiria.player.client.module.external.sound.ExternalSoundInstance;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;

public interface ExternalInteractionModuleFactory {
	ExternalSoundInstance getExternalSoundInstance(MediaWrapper<Widget> audioWrapper);
}
