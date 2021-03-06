package eu.ydp.empiria.player.client.gin.factory;

import eu.ydp.empiria.player.client.module.model.image.ShowImageDTO;
import eu.ydp.empiria.player.client.module.tutor.TutorCommand;
import eu.ydp.empiria.player.client.module.tutor.TutorEndHandler;
import eu.ydp.empiria.player.client.module.tutor.view.TutorView;
import eu.ydp.gwtutil.client.animation.Animation;

import javax.inject.Named;

public interface TutorCommandsModuleFactory {
    @Named("animation")
    TutorCommand createAnimationCommand(Animation animation, TutorEndHandler handler);

    @Named("image")
    TutorCommand createShowImageCommand(TutorView moduleView, ShowImageDTO showImageDTO, TutorEndHandler handler);

}
