package eu.ydp.empiria.player.client.module.slideshow.structure;

import com.google.common.collect.Lists;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "slideshow")
public class SlideshowBean {

    @XmlElement
    private SlideshowTitleBean title;

    @XmlElement(name = "slide")
    private List<SlideBean> slideBeans = Lists.newArrayList();

    public SlideshowTitleBean getTitle() {
        return title;
    }

    public void setTitle(SlideshowTitleBean title) {
        this.title = title;
    }

    public List<SlideBean> getSlideBeans() {
        return slideBeans;
    }

    public void setSlideBeans(List<SlideBean> slideBeans) {
        this.slideBeans = slideBeans;
    }
}
