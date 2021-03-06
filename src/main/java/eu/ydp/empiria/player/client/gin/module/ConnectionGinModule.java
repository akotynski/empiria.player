package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import eu.ydp.empiria.player.client.gin.factory.ConnectionItemsFactory;
import eu.ydp.empiria.player.client.gin.factory.ConnectionModuleFactory;
import eu.ydp.empiria.player.client.module.components.multiplepair.MultiplePairModuleView;
import eu.ydp.empiria.player.client.module.connection.ConnectionSurface;
import eu.ydp.empiria.player.client.module.connection.ConnectionSurfaceImpl;
import eu.ydp.empiria.player.client.module.connection.ConnectionSurfaceView;
import eu.ydp.empiria.player.client.module.connection.ConnectionSurfaceViewImpl;
import eu.ydp.empiria.player.client.module.connection.item.ConnectionItem;
import eu.ydp.empiria.player.client.module.connection.item.ConnectionItemImpl;
import eu.ydp.empiria.player.client.module.connection.presenter.ConnectionModulePresenter;
import eu.ydp.empiria.player.client.module.connection.presenter.ConnectionModulePresenterImpl;
import eu.ydp.empiria.player.client.module.connection.presenter.ConnectionModuleViewImpl;
import eu.ydp.empiria.player.client.module.connection.presenter.view.ConnectionView;
import eu.ydp.empiria.player.client.module.connection.presenter.view.ConnectionViewVertical;

public class ConnectionGinModule extends AbstractGinModule {

    @Override
    protected void configure() {
        bind(ConnectionModulePresenter.class).to(ConnectionModulePresenterImpl.class);
        bind(ConnectionView.class).to(ConnectionViewVertical.class);
        bind(MultiplePairModuleView.class).to(ConnectionModuleViewImpl.class);

        install(new GinFactoryModuleBuilder().implement(ConnectionItem.class, ConnectionItemImpl.class)
                .implement(ConnectionSurface.class, ConnectionSurfaceImpl.class).implement(MultiplePairModuleView.class, ConnectionModuleViewImpl.class)
                .implement(ConnectionView.class, ConnectionViewVertical.class).implement(ConnectionSurfaceView.class, ConnectionSurfaceViewImpl.class)
                .build(ConnectionModuleFactory.class));
        install(new GinFactoryModuleBuilder().build(ConnectionItemsFactory.class));
    }

}
