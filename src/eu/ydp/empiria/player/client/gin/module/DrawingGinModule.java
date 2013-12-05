package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.inject.client.AbstractGinModule;

import eu.ydp.empiria.player.client.module.drawing.DrawingView;
import eu.ydp.empiria.player.client.module.drawing.DrawingViewImpl;
import eu.ydp.empiria.player.client.module.drawing.toolbox.ToolboxView;
import eu.ydp.empiria.player.client.module.drawing.toolbox.ToolboxViewImpl;

public class DrawingGinModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(DrawingView.class).to(DrawingViewImpl.class);
		bind(ToolboxView.class).to(ToolboxViewImpl.class);
	}

}