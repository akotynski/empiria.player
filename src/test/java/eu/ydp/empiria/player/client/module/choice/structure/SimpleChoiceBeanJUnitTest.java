package eu.ydp.empiria.player.client.module.choice.structure;

import eu.ydp.empiria.player.client.AbstractJAXBTestBase;
import eu.ydp.gwtutil.client.StringUtils;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SimpleChoiceBeanJUnitTest extends AbstractJAXBTestBase<SimpleChoiceBean> {

    @Test
    public void shouldReturnSimpleChoice() {
        String nodeString = "<simpleChoice identifier=\"CHOICE_RESPONSE_1_2\">The Pyrenees</simpleChoice>";
        SimpleChoiceBean simpleChoice = createBeanFromXMLString(nodeString);

        assertThat(simpleChoice.getIdentifier(), is(equalTo("CHOICE_RESPONSE_1_2")));
    }

    @Test
    public void shouldReturnSimpleChoiceWhen_emptyValues() {
        String nodeString = "<simpleChoice/>";
        SimpleChoiceBean simpleChoice = createBeanFromXMLString(nodeString);

        assertThat(simpleChoice.getIdentifier(), is(equalTo(StringUtils.EMPTY_STRING)));
    }
}
