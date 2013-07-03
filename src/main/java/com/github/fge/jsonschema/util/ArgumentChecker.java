package com.github.fge.jsonschema.util;

import com.github.fge.jsonschema.messages.JsonSchemaCoreMessageBundle;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.msgsimple.load.MessageBundles;
import com.google.common.annotations.Beta;

import javax.annotation.Nullable;

/**
 * An argument checker
 *
 * <p>The only method of this interface returns nothing; it is supposed to throw
 * an {@link IllegalArgumentException} or derivate if the argument supplied is
 * not legal.</p>
 *
 * <p>Note that the argument to check <b>may</b> be {@code null}.</p>
 *
 * @param <T> type of the argument to check
 *
 * @since 1.1.9
 */
@Beta
public abstract class ArgumentChecker<T>
{
    protected static final MessageBundle BUNDLE
        = MessageBundles.getBundle(JsonSchemaCoreMessageBundle.class);

    /**
     * As its name says
     *
     * @param <X> the type of the argument
     * @return an argument checker
     */
    public static <X> ArgumentChecker<X> anythingGoes()
    {
        return new ArgumentChecker<X>()
        {
            @Override
            public void check(@Nullable final X argument)
            {
            }
        };
    }

    /**
     * An argument checker refusing null arguments; fails with a standard
     * message
     *
     * @param <X> the type of the argument
     * @return an argument checker
     */
    public static <X> ArgumentChecker<X> notNull()
    {
        return new ArgumentChecker<X>()
        {
            @Override
            public void check(@Nullable final X argument)
            {
                BUNDLE.checkNotNull(argument, "argChecker.notNull");
            }
        };
    }

    /**
     * An argument checker refusing null arguments; fails with a customized
     * message
     *
     * @param message the message to fail with
     * @param <X> the type of the argument
     * @return an argument checker
     * @throws NullPointerException message is null
     */
    public static <X> ArgumentChecker<X> notNull(final String message)
    {
        BUNDLE.checkNotNull(message, "argChecker.nullMessage");
        return new ArgumentChecker<X>()
        {
            @Override
            public void check(@Nullable final X argument)
            {
                if (argument == null)
                    throw new NullPointerException(message);
            }
        };
    }

    /**
     * Combine this argument checker with another checker
     *
     * <p>This returns a <b>new</b> argument checker. Note that checkers are
     * called in order ({@code this}, then {@code other}).</p>
     *
     * @param other the other checker
     * @return a new argument checker combining this checker and the other
     * @throws NullPointerException other checker is null
     */
    public final ArgumentChecker<T> and(final ArgumentChecker<T> other)
    {
        BUNDLE.checkNotNull(other, "argChecker.nullChecker");
        return new CombinedArgumentChecker<T>(this, other);
    }

    /**
     * Check the sanity of an argument
     *
     * @param argument the argument
     * @throws IllegalArgumentException argument is not legal according to
     * checks
     */
    public abstract void check(@Nullable final T argument);

    private static final class CombinedArgumentChecker<T>
        extends ArgumentChecker<T>
    {
        private final ArgumentChecker<T> first;
        private final ArgumentChecker<T> second;

        private CombinedArgumentChecker(final ArgumentChecker<T> first,
            final ArgumentChecker<T> second)
        {
            this.first = first;
            this.second = second;
        }

        @Override
        public void check(@Nullable final T argument)
        {
            first.check(argument);
            second.check(argument);
        }
    }
}