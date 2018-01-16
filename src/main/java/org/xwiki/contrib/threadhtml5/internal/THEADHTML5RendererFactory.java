/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.xwiki.contrib.threadhtml5.internal;

import javax.inject.Named;
import javax.inject.Singleton;

import org.xwiki.component.annotation.Component;
import org.xwiki.rendering.internal.renderer.AbstractPrintRendererFactory;
import org.xwiki.rendering.syntax.Syntax;
import org.xwiki.rendering.syntax.SyntaxType;

/**
 * @version $Id: f26a2b5a3ea458b68602f31459e200070fb9d372 $
 */
@Component
@Named(THEADHTML5RendererFactory.SYNTAX_STRING)
@Singleton
public class THEADHTML5RendererFactory extends AbstractPrintRendererFactory
{
    /**
     * The name of renderer syntax type.
     */
    public static final String SYNTAXTYPE_NAME = "theadhtml";

    /**
     * The version of renderer syntax type.
     */
    public static final String SYNTAXTYPE_VERSION = "5.0";

    /**
     * The syntax.
     */
    public static final Syntax SYNTAX =
        new Syntax(new SyntaxType(SYNTAXTYPE_NAME, SYNTAXTYPE_NAME), SYNTAXTYPE_VERSION);

    /**
     * The Syntax as a String.
     */
    public static final String SYNTAX_STRING = SYNTAXTYPE_NAME + '/' + SYNTAXTYPE_VERSION;

    @Override
    public Syntax getSyntax()
    {
        return SYNTAX;
    }
}
