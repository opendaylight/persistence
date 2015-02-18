/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.persistence.util.test.easymock;

import javax.annotation.Nonnull;

import org.easymock.EasyMock;
import org.easymock.IArgumentMatcher;

/**
 * Argument matcher.
 * <p>
 * This argument matcher already verifies null-ability and type: If the argument is {@code null} or
 * of the wrong type the match fails. {@code null} is not a valid value, if {@code null} is expected
 * use {@link EasyMock#isNull(Class)}.
 * 
 * @param <T> type of the expected argument
 * @author Fabiel Zuniga
 * @author Nachiket Abhyankar
 */
public abstract class ArgumentMatcher<T> implements IArgumentMatcher {

    private String name;

    /**
     * Creates an argument matcher.
     *
     * @param name name to display when the argument does not match
     */
    public ArgumentMatcher(@Nonnull String name) {
        this.name = name;
    }

    @Override
    public void appendTo(StringBuffer buffer) {
        buffer.append("<" + this.name + ">");
    }

    @Override
    public boolean matches(Object arg) {
        if (arg == null) {
            return false;
        }

        try {
            @SuppressWarnings("unchecked")
            T argument = (T) arg;
            return verifyMatch(argument);
        }
        catch (ClassCastException e) {
            return false;
        }
    }

    /**
     * Verifies the argument matches.
     *
     * @param argument argument
     * @return {@code true} if {@code argument} matches expectations, {@code false} otherwise
     */
    public abstract boolean verifyMatch(@Nonnull T argument);
    
    /**
     * Verifies whether the properties match the expected values.
     * 
     * @param properties properties to verify
     * @return {@code true} if the property's value matches the expected, {@code false} otherwise
     */
    @SafeVarargs
    protected final boolean verify(@Nonnull Matchable<?>... properties) {
        for (Matchable<?> property : properties) {
            if (!property.matches()) {
                System.err.println("Property mismatch: " + getClass().getName() + " <" + this.name + ">");
                property.printMismatch(System.err);
                return false;
            }
        }
        return true;
    }

    /**
     * Registers this argument matcher to EasyMock.
     * <P>
     * Example:
     * 
     * <pre>
     * ArgumentMatcher&lt;MyArgumentType&gt; customArgumentMatcher = new ArgumentMatcher&lt;MyArgumentType&gt;() {
     *     ...
     * };
     * 
     * // For methods returning a value
     * EasyMock.expect(mock.someMethod(myArgumentMatcher.match())).andReturn(...);
     * 
     * // For void return methods
     * mock.someMethod(myArgumentMatcher.match());
     * </pre>
     * 
     * @return irrelevant value
     */
    public T match() {
        return match(this);
    }

    /**
     * Registers an {@link IArgumentMatcher} to EasyMock.
     * <p>
     * The preferred way (type-safe) is to extend {@link ArgumentMatcher} instead of directly
     * implementing {@link IArgumentMatcher}, and then use {@link #match()}.
     * <P>
     * Example:
     * 
     * <pre>
     * IArgumentMatcher customArgumentMatcher = new IArgumentMatcher() {
     *     ...
     * };
     * 
     * // For methods returning a value
     * EasyMock.expect(mock.someMethod(ArgumentMatcher.match(customArgumentMatcher))).andReturn(...);
     * 
     * // For void return methods
     * mock.someMethod(ArgumentMatcher.match(customArgumentMatcher));
     * </pre>
     * 
     * @param <T> type of the argument
     * @param matcher matcher to register
     * @return irrelevant value
     */
    public static <T> T match(@Nonnull IArgumentMatcher matcher) {
        if (matcher == null) {
            throw new IllegalArgumentException("matcher cannot be null");
        }
        EasyMock.reportMatcher(matcher);
        return null;
    }
}