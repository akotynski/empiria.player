package eu.ydp.empiria.player.client.module.slideshow.structure;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "sound")
public class SoundBean {
	
	@XmlAttribute
	private String src;

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}
}
