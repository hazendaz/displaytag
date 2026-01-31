/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.decorator.hssf;

import org.apache.poi.hssf.usermodel.HSSFSheet;

/**
 * An implementor of this interface decorates tables and columns appearing in an HSSF workbook.
 */
public interface DecoratesHssf {
    /**
     * Set the worksheet used to render a table model.
     *
     * @param sheet
     *            The worksheet used to render a table model.
     */
    void setSheet(HSSFSheet sheet);
}
