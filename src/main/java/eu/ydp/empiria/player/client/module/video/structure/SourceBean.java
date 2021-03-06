package eu.ydp.empiria.player.client.module.video.structure;

import eu.ydp.empiria.player.client.module.model.media.MimeType;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "source")
@XmlAccessorType(XmlAccessType.NONE)
public class SourceBean {

    @XmlAttribute(required = true)
    private String src;
    @XmlAttribute(name = "type", required = true)
    private String typeString;
    private MimeType mimeType;

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getTypeString() {
        return typeString;
    }

    public void setTypeString(String typeString) {
        mimeType = MimeType.fromValue(typeString);
        this.typeString = typeString;
    }

    public MimeType getMimeType() {
        return mimeType;
    }

    public void setMimeType(MimeType mime) {
        typeString = mime.value();
        this.mimeType = mime;
    }
}
