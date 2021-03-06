package eu.ydp.empiria.player.client.module.choice.structure;

import com.peterfranza.gwt.jaxb.client.parser.utils.XMLContent;
import eu.ydp.empiria.player.client.module.abstractmodule.structure.HasFixed;
import eu.ydp.empiria.player.client.structure.SimpleChoiceBaseBean;
import eu.ydp.empiria.player.module.abstractmodule.structure.XMLContentTypeAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "simpleChoice")
public class SimpleChoiceBean extends SimpleChoiceBaseBean implements HasFixed {

    @XmlAttribute
    private boolean fixed;
    @XmlValue
    @XmlJavaTypeAdapter(value = XMLContentTypeAdapter.class)
    private XMLContent content;
    private boolean multi;

    public SimpleChoiceBean() {
    }

    public XMLContent getContent() {
        return content;
    }

    public void setContent(XMLContent content) {
        this.content = content;
    }

    @Override
    public boolean isFixed() {
        return fixed;
    }

    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }

    public boolean isMulti() {
        return multi;
    }

    public void setMulti(boolean multi) {
        this.multi = multi;
    }

}
