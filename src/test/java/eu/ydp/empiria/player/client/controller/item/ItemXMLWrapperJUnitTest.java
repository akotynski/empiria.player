package eu.ydp.empiria.player.client.controller.item;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.google.inject.Binder;
import com.google.inject.Module;
import eu.ydp.empiria.player.client.AbstractTestBaseWithoutAutoInjectorInit;
import eu.ydp.empiria.player.client.GuiceModuleConfiguration;
import eu.ydp.empiria.player.client.gin.scopes.page.PageScoped;
import eu.ydp.empiria.player.client.util.file.xml.XmlData;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SuppressWarnings("PMD")
public class ItemXMLWrapperJUnitTest extends AbstractTestBaseWithoutAutoInjectorInit {

    private class CustomGinModule implements Module {
        @Override
        public void configure(Binder binder) {
            binder.bind(XmlData.class).annotatedWith(PageScoped.class).toInstance(xmlData);
        }
    }

    private final XmlData xmlData = mock(XmlData.class);
    private final Document document = mock(Document.class);
    private ItemXMLWrapper instance;

    @Before
    public void before() {
        setUp(new GuiceModuleConfiguration(), new CustomGinModule());
        NodeList nodeList = mock(NodeList.class);
        when(nodeList.item(Matchers.anyInt())).thenReturn(mock(Element.class));
        when(document.getElementsByTagName(Matchers.anyString())).thenReturn(nodeList);
        doReturn(document).when(xmlData).getDocument();
        doReturn("url").when(xmlData).getBaseURL();
        instance = injector.getInstance(ItemXMLWrapper.class);
    }

    @Test
    public void postConstruct() throws Exception {
        verify(xmlData).getDocument();
    }

    @Test
    public void getStyleDeclaration() throws Exception {
        assertThat(instance.getStyleDeclaration()).isNotNull();
        verify(document).getElementsByTagName(Matchers.eq("styleDeclaration"));
    }

    @Test
    public void getAssessmentItems() throws Exception {
        assertThat(instance.getAssessmentItems()).isNotNull();
        verify(document).getElementsByTagName(Matchers.eq("assessmentItem"));
    }

    @Test
    public void getItemBody() throws Exception {
        assertThat(instance.getItemBody()).isNotNull();
        verify(document).getElementsByTagName(Matchers.eq("itemBody"));
    }

    @Test
    public void getResponseDeclarations() throws Exception {
        assertThat(instance.getResponseDeclarations()).isNotNull();
        verify(document).getElementsByTagName(Matchers.eq("responseDeclaration"));
    }

    @Test
    public void getExpressions() throws Exception {
        assertThat(instance.getExpressions()).isNotNull();
        verify(document).getElementsByTagName(Matchers.eq("expressions"));
    }

    @Test
    public void getBaseURL() throws Exception {
        assertThat(instance.getBaseURL()).isEqualTo("url");
        verify(xmlData).getBaseURL();
    }

}
