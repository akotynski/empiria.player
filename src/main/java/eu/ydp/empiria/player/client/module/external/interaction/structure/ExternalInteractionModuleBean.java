package eu.ydp.empiria.player.client.module.external.interaction.structure;

import eu.ydp.empiria.player.client.structure.ModuleBean;
import eu.ydp.gwtutil.client.StringUtils;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "externalInteraction")
public class ExternalInteractionModuleBean extends ModuleBean {

    @XmlAttribute(name = "src")
    private String src = StringUtils.EMPTY_STRING;

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }
}
