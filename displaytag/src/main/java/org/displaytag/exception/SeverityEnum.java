/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.exception;

/**
 * Enumeration for logging severities.
 */
public enum SeverityEnum {

    /** The fatal. */
    FATAL("fatal"),

    /** The error. */
    ERROR("error"),

    /** The warn. */
    WARN("warn"),

    /** The info. */
    INFO("info"),

    /** The debug. */
    DEBUG("debug");

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 899149338534L;

    /** The severity. */
    private String severity;

    /**
     * private constructor. Use only constants
     *
     * @param severity
     *            Severity name as String
     */
    SeverityEnum(final String severity) {
        this.severity = severity;
    }

    /**
     * Gets the severity.
     *
     * @return the severity
     */
    public String getSeverity() {
        return this.severity;
    }

}
