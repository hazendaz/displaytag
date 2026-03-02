/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.jsptests;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;

import org.displaytag.properties.SortOrderEnum;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.test.DisplaytagCase;
import org.displaytag.util.ParamEncoder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test for issue 773 - boolean/date column sorting broken when a non-HTML column precedes the sortable column.
 * <p>
 * When a column with {@code media="csv"} (or any non-HTML media) appears before a sortable boolean/date column, the
 * sort column index was incorrectly decremented by PR #661's column-visibility adjustment logic, causing the sort to
 * apply to the wrong column.
 * </p>
 */
class Displ773Test extends DisplaytagCase {

    /**
     * Gets the jsp name.
     *
     * @return the jsp name
     *
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    @Override
    public String getJspName() {
        return "DISPL-773.jsp";
    }

    /**
     * Sorting by a boolean column should work correctly even when a non-HTML column precedes it.
     * <p>
     * The table has: [csv-only name column, html name column (sortable), html active column (boolean, sortable)]. In
     * HTML, the visible columns are: [name, active]. Sorting by "active" (index 1) should sort by the boolean value,
     * not by "name" (index 0).
     * </p>
     *
     * @throws Exception
     *             any exception thrown during test.
     */
    @Override
    @Test
    public void doTest() throws Exception {
        final WebRequest request = new GetMethodWebRequest(this.getJspUrl(this.getJspName()));
        final ParamEncoder encoder = new ParamEncoder("table");

        // Sort by the "active" column (index 1 in HTML's visible list: [name=0, active=1])
        // ascending order (false < true)
        request.setParameter(encoder.encodeParameterName(TableTagParameters.PARAMETER_SORT), "1");
        request.setParameter(encoder.encodeParameterName(TableTagParameters.PARAMETER_ORDER),
                String.valueOf(SortOrderEnum.ASCENDING.getCode()));

        final WebResponse response = this.runner.getResponse(request);

        if (this.log.isDebugEnabled()) {
            this.log.debug(response.getText());
        }

        final WebTable[] tables = response.getTables();
        Assertions.assertEquals(1, tables.length, "Wrong number of tables.");
        Assertions.assertEquals(4, tables[0].getRowCount(), "Wrong number of rows (1 header + 3 data).");

        // Data: Charlie(false), Alice(true), Bob(false)
        // After ascending sort by 'active': false rows first (Charlie, Bob), then true rows (Alice)
        // The "active" column is index 1 in HTML's visible headerCellList (after csv-only column excluded)
        // Bug: sort index was incorrectly adjusted to 0 (name column), giving alphabetical order: Alice, Bob, Charlie
        // Fix: sort index stays 1 (active column), giving boolean order: false rows first
        final String firstRowActive = tables[0].getCellAsText(1, 1);
        Assertions.assertEquals("false", firstRowActive,
                "First data row after ascending sort by 'active' should show 'false'. "
                        + "If 'true', the sort is being applied to the wrong column (bug from PR #661).");
    }

}
