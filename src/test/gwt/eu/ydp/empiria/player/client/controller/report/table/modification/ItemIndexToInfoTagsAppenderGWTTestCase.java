package eu.ydp.empiria.player.client.controller.report.table.modification;

import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.XMLParser;
import eu.ydp.empiria.player.client.EmpiriaPlayerGWTTestCase;

public class ItemIndexToInfoTagsAppenderGWTTestCase extends EmpiriaPlayerGWTTestCase {

    private ItemIndexAppender testObj;

    //@formatter:off
    private final String INPUT = "" +
            "<rd>" +
            "<div class=\"emp_content_icon\">" +
            "<link>" +
            "<info itemIndex=\"1\" class=\"info_content_title\"/>" +
            "</link>" +
            "<link>" +
            "<info class=\"info_content_title\"/>" +
            "</link>" +
            "</div>" +
            "</rd>";

    private final String OUTPUT = "" +
            "<rd>" +
            "<div class=\"emp_content_icon\">" +
            "<link>" +
            "<info itemIndex=\"1\" class=\"info_content_title\"/>" +
            "</link>" +
            "<link>" +
            "<info class=\"info_content_title\" itemIndex=\"2\"/>" +
            "</link>" +
            "</div>" +
            "</rd>";

    private Element cellElement;

    //@formatter:on

    @Override
    protected void gwtSetUp() {
        testObj = new ItemIndexAppender();
    }

    public void testAppendToInfoTags() {
        // given
        int ITEM_INDEX = 2;
        cellElement = XMLParser.parse(INPUT).getDocumentElement();

        // when
        testObj.appendToInfoTags(ITEM_INDEX, cellElement);

        // then
        assertEquals(cellElement.toString(), OUTPUT);
    }
}
