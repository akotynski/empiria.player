package eu.ydp.empiria.player.client.module.video.presenter;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.module.video.view.VideoPlayer;
import eu.ydp.empiria.player.client.module.video.view.VideoView;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventTypes;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class VideoPlayerReattacher {

    private final EventsBus eventsBus;
    private final VideoPlayerBuilder videoPlayerAttacher;
    private final PageScopeFactory pageScopeFactory;

    @Inject
    public VideoPlayerReattacher(EventsBus eventsBus, @ModuleScoped VideoPlayerBuilder videoPlayerBuilder, PageScopeFactory pageScopeFactory) {
        this.eventsBus = eventsBus;
        this.videoPlayerAttacher = videoPlayerBuilder;
        this.pageScopeFactory = pageScopeFactory;
    }

    public void registerReattachHandlerToView(final VideoView view) {
        eventsBus.addHandler(PlayerEvent.getType(PlayerEventTypes.TEST_PAGE_LOADED), new PlayerEventHandler() {
            @Override
            public void onPlayerEvent(PlayerEvent event) {
                VideoPlayer videoPlayer = videoPlayerAttacher.build();
                view.attachVideoPlayer(videoPlayer);
            }
        }, pageScopeFactory.getCurrentPageScope());
    }

}
