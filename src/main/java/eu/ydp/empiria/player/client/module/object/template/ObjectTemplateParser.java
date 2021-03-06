package eu.ydp.empiria.player.client.module.object.template;

import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.media.texttrack.TextTrackKind;
import eu.ydp.empiria.player.client.module.ModuleTagName;
import eu.ydp.empiria.player.client.module.media.MediaControllerFactory;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.module.media.button.MediaController;
import eu.ydp.empiria.player.client.module.media.button.MediaControllerWrapper;
import eu.ydp.empiria.player.client.module.media.button.VideoFullScreenMediaButton;
import eu.ydp.empiria.player.client.util.AbstractTemplateParser;
import eu.ydp.gwtutil.client.xml.XMLUtils;

public class ObjectTemplateParser extends AbstractTemplateParser {
    private Element fullScreenTemplate;

    private MediaWrapper<?> mediaWrapper;
    private MediaWrapper<?> fullScreenMediaWrapper;

    @Inject
    protected MediaControllerFactory mediaControllerFactory;

    @Inject
    private TemplateControllers templateControllers;

    private boolean isFullScreen = false;

    protected Widget getMediaObject() {
        Widget mediaObjectWidget;
        if (isFullScreen) {
            mediaObjectWidget = fullScreenMediaWrapper.getMediaObject();
        } else {
            FlowPanel videoContainer = new FlowPanel();
            videoContainer.add(mediaWrapper.getMediaObject());
            videoContainer.getElement().getStyle().setPosition(Position.RELATIVE);
            mediaObjectWidget = videoContainer;
        }
        return mediaObjectWidget;
    }

    @Override
    public void beforeParse(Node mainNode, Widget parent) {
        // kompatybilnosc wsteczna z szablonami bez media_screen
        if (isNotMediaScreenDefinedInTemplate()) {
            attachMediaScreenToRootOfTemplate(parent);
        }
    }

    private boolean isNotMediaScreenDefinedInTemplate() {
        return !isModuleInTemplate(ModuleTagName.MEDIA_SCREEN.tagName());
    }

    private void attachMediaScreenToRootOfTemplate(Widget parent) {
        if (isFullScreen) {
            attachMediaScreenToFullscreenTemplate(parent);
        } else {
            attachMediaScreenToNotFullscreenTemplate(parent);
        }
    }

    private void attachMediaScreenToFullscreenTemplate(Widget parent) {
        Widget parentWrapper = parent.getParent();
        if (parentWrapper instanceof HasWidgets) {
            HasWidgets parentPanel = (HasWidgets) parentWrapper;
            parentPanel.add(getMediaObject());
        }
    }

    private void attachMediaScreenToNotFullscreenTemplate(Widget parent) {
        if (parent instanceof HasWidgets) {
            ((HasWidgets) parent).add(getMediaObject());
        }
    }

    @Override
    public void parse(Node mainNode, Widget parent) {
        super.parse(mainNode, parent);

    }

    public void setMediaWrapper(MediaWrapper<?> mediaDescriptor) {
        this.mediaWrapper = mediaDescriptor;
    }

    public void setFullScreenMediaWrapper(MediaWrapper<?> fullScreenMediaWrapper) {
        this.fullScreenMediaWrapper = fullScreenMediaWrapper;
    }

    @Override
    protected MediaController getMediaControllerNewInstance(String moduleName, Node node) {
        MediaController controller;
        if (ModuleTagName.MEDIA_TEXT_TRACK.tagName().equals(moduleName)) {
            String kind = XMLUtils.getAttributeAsString((Element) node, "kind", TextTrackKind.SUBTITLES.name());
            TextTrackKind trackKind = TextTrackKind.SUBTITLES;
            try {
                trackKind = TextTrackKind.valueOf(kind.toUpperCase()); // NOPMD
            } catch (IllegalArgumentException exception) { // NOPMD

            }
            controller = mediaControllerFactory.get(ModuleTagName.MEDIA_TEXT_TRACK, trackKind);
        } else if (ModuleTagName.MEDIA_SCREEN.tagName().equals(moduleName)) {
            controller = new MediaControllerWrapper(getMediaObject());
        } else {
            controller = mediaControllerFactory.get(ModuleTagName.getTag(moduleName));
        }
        if (controller != null) {
            controller.setMediaDescriptor(mediaWrapper);
            controller.setFullScreen(isFullScreen);
        }
        if (controller instanceof VideoFullScreenMediaButton) {
            ((VideoFullScreenMediaButton) controller).setFullScreenTemplate(fullScreenTemplate);
            ((VideoFullScreenMediaButton) controller).setMediaWrapper(mediaWrapper);
            ((VideoFullScreenMediaButton) controller).setFullScreenMediaWrapper(fullScreenMediaWrapper);
        }

        return controller;
    }

    @Override
    protected boolean isModuleSupported(String moduleName) {
        return templateControllers.isControllerSupported(moduleName);
    }

    public void setFullScreenTemplate(Element fullScreenTemplate) {
        this.fullScreenTemplate = fullScreenTemplate;
    }

    public void setFullScreen(boolean fullScreen) {
        this.isFullScreen = fullScreen;
    }
}
