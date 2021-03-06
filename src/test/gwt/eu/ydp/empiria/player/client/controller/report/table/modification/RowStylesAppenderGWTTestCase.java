package eu.ydp.empiria.player.client.controller.report.table.modification;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.FlexTable;
import eu.ydp.empiria.player.client.EmpiriaPlayerGWTTestCase;
import eu.ydp.empiria.player.client.module.report.ReportStyleNameConstants;
import eu.ydp.empiria.player.client.controller.report.table.cell.CellCoords;

public class RowStylesAppenderGWTTestCase extends EmpiriaPlayerGWTTestCase {

    private RowStylesAppender testObj;

    private ReportStyleNameConstants styleNameConstants;

    //@formatter:off
    private final String OUTPUT = "" +
            "<colgroup>" +
            "<col></colgroup>" +
            "<tbody align=\"left\">" +
            "<tr></tr>" +
            "<tr></tr>" +
            "<tr class=\"qp-report-table-row qp-report-table-row-2\">" +
            "<td></td>" +
            "<td></td>" +
            "<td></td>" +
            "<td class=\"qp-report-table-cell qp-report-table-col-3\"></td>" +
            "</tr>" +
            "</tbody>";
    //@formatter:on

    @Override
    protected void gwtSetUp() throws Exception {
        styleNameConstants = GWT.create(ReportStyleNameConstants.class);
        testObj = new RowStylesAppender(styleNameConstants);
    }

    public void testShouldAddStylesToRow() {
        // given
        FlexTable flexTable = new FlexTable();
        CellCoords cellCoords = new CellCoords(2, 3);

        // when
        testObj.append(flexTable, cellCoords);

        // then
        String tabelHTML = flexTable.getElement().getInnerHTML();
        assertEquals(tabelHTML, OUTPUT);
    }
}
