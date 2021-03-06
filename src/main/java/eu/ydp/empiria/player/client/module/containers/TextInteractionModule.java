package eu.ydp.empiria.player.client.module.containers;

import com.google.gwt.user.client.Command;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.body.BodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.bookmark.BookmarkingHelper;
import eu.ydp.empiria.player.client.module.bookmark.IBookmarkable;
import eu.ydp.empiria.player.client.resources.TextStyleNameConstants;
import eu.ydp.gwtutil.client.geom.Rectangle;

public class TextInteractionModule extends BindingContainerModule implements IBookmarkable {

    private final BookmarkingHelper bookmarkingHelper;

    @Inject
    public TextInteractionModule(TextStyleNameConstants styleNames) {
        setContainerStyleName(styleNames.QP_TEXTINTERACTION());
        bookmarkingHelper = new BookmarkingHelper(getView());
    }

    @Override
    public void setBookmarkingStyleName(String styleName) {
        bookmarkingHelper.setBookmarkingStyleName(styleName);
    }

    @Override
    public void removeBookmarkingStyleName() {
        bookmarkingHelper.removeBookmarkingStyleName();
    }

    @Override
    public void setClickCommand(final Command command) {
        bookmarkingHelper.setClickCommand(command);
    }

    @Override
    public String getBookmarkHtmlBody() {
        return getView().getElement().getInnerText();
    }

    @Override
    public Rectangle getViewArea() {
        return bookmarkingHelper.getViewArea();
    }

    @Override
    public String getDefaultBookmarkTitle() {
        return BookmarkingHelper.getDefaultBookmarkTitle(getView().getElement().getInnerText());
    }
}
