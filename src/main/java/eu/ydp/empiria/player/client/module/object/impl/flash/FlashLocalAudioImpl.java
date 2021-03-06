package eu.ydp.empiria.player.client.module.object.impl.flash;

import com.google.gwt.core.client.GWT;
import com.google.gwt.media.client.MediaBase;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.module.object.impl.Audio;

public class FlashLocalAudioImpl extends FlashLocalMediaImpl implements Audio {

    public FlashLocalAudioImpl() {
        super("audio");
    }

    @Override
    protected native void loadFlvPlayerThroughSwfobject(String id, String swfSrc, String installSrc, String mediaSrc, int width, int height) /*-{
        var flashvars = {soundFile: mediaSrc, playerID: id, animation: "no", noinfo: "yes"};
        $wnd.swfobject.embedSWF(swfSrc, id, width, height, "9", installSrc, flashvars);

    }-*/;

    @Override
    protected String getSwfSrc() {
        return GWT.getModuleBaseURL() + "wpaudioplayer/wpaudioplayer.swf";
    }

    @Override
    public void addSrc(String src, String type) {
        if (this.src == null) {
            setSrc(src);
        }
    }

    @Override
    public void setShowNativeControls(boolean show) {
    }

    @Override
    public void setEventBusSourceObject(MediaWrapper<?> object) {
    }

    @Override
    public MediaBase getMedia() {
        return null;
    }

    @Override
    protected int getWidth() {
        return 160;
    }

    @Override
    protected int getHeight() {
        return 24;
    }

}
