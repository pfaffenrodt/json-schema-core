/*
 * Copyright (c) 2013, Francis Galiegue <fgaliegue@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Lesser GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * Lesser GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.fge.jsonschema.util;

import javax.annotation.concurrent.Immutable;

/**
 * Interface for a frozen object
 *
 * <p>This interface is closely coupled with {@link Thawed}. The principle is
 * simple: you can obtain a thawed object from a frozen one, and a thawed object
 * can generate a frozen one.</p>
 *
 * <p>By contract, implementations of this interface must be immutable.</p>
 *
 * @param <T> the type of the thawed counterpart
 *
 * @deprecated use {@link com.github.fge.Frozen} instead
 */
@Immutable
@Deprecated
public interface Frozen<T extends Thawed<? extends Frozen<T>>>
{
    /**
     * Return a thawed representation of this frozen object.
     *
     * @return a thawed object
     */
    T thaw();
}
