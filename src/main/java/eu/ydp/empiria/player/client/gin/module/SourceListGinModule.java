package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.core.client.GWT;
import com.google.inject.Provider;
import com.google.inject.TypeLiteral;
import eu.ydp.empiria.player.client.gin.scopes.page.PageScopedProvider;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistLockingController;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistManager;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistManagerImpl;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistManagerModel;
import eu.ydp.empiria.player.client.module.sourcelist.presenter.SourceListPresenter;
import eu.ydp.empiria.player.client.module.sourcelist.presenter.SourceListPresenterImpl;
import eu.ydp.empiria.player.client.module.sourcelist.structure.SourceListJAXBParser;
import eu.ydp.empiria.player.client.module.sourcelist.view.SourceListView;
import eu.ydp.empiria.player.client.module.sourcelist.view.SourceListViewImpl;

public class SourceListGinModule extends BaseGinModule {
    public static class SourceListJAXBParserProvider implements Provider<SourceListJAXBParser> {
        @Override
        public SourceListJAXBParser get() {
            return GWT.create(SourceListJAXBParser.class);
        }

        ;
    }

    @Override
    protected void configure() {
        bind(SourceListView.class).to(SourceListViewImpl.class);
        bind(SourceListPresenter.class).to(SourceListPresenterImpl.class);
        bind(SourceListJAXBParser.class).toProvider(SourceListJAXBParserProvider.class);
        bind(SourcelistManager.class).to(SourcelistManagerImpl.class);

        bindPageScoped(SourcelistManager.class, new TypeLiteral<PageScopedProvider<SourcelistManager>>() {
        });
        bindPageScoped(SourcelistManagerModel.class, new TypeLiteral<PageScopedProvider<SourcelistManagerModel>>() {
        });
        bindPageScoped(SourcelistLockingController.class, new TypeLiteral<PageScopedProvider<SourcelistLockingController>>() {
        });
    }

}
