package eu.ydp.empiria.player.client.module.video.presenter;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.video.view.VideoPlayer;
import eu.ydp.empiria.player.client.module.video.view.VideoView;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class VideoPresenter {

	private final VideoView view;
	private final VideoPlayerReattacher reAttachHack;
	private final VideoPlayerBuilder videoPlayerBuilder;

	@Inject
	public VideoPresenter(@ModuleScoped VideoView view, @ModuleScoped VideoPlayerBuilder videoPlayerAttacher,
			@ModuleScoped VideoPlayerReattacher reAttachHack) {
		this.view = view;
		this.videoPlayerBuilder = videoPlayerAttacher;
		this.reAttachHack = reAttachHack;
	}

	public void start() {
		view.createView();

		VideoPlayer videoPlayer = videoPlayerBuilder.build();
		view.attachVideoPlayer(videoPlayer);

		reAttachHack.registerReattachHandlerToView(view);
	}

	public Widget getView() {
		return view.asWidget();
	}
}
