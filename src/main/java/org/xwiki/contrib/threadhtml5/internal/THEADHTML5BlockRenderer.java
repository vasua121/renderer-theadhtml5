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

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.xwiki.component.annotation.Component;
import org.xwiki.rendering.internal.renderer.AbstractBlockRenderer;
import org.xwiki.rendering.renderer.PrintRendererFactory;

/**
 * @version $Id: 9078444012f0c6bbb59d1b58cd334c55c802ed7c $
 */
@Component
@Named(THEADHTML5RendererFactory.SYNTAX_STRING)
@Singleton
public class THEADHTML5BlockRenderer extends AbstractBlockRenderer
{
    /**
     * Factory to create Annotated HTML5 Print Renderers.
     */
    @Inject
    @Named(THEADHTML5RendererFactory.SYNTAX_STRING)
    private PrintRendererFactory theadHTML5RendererFactory;

    @Override
    protected PrintRendererFactory getPrintRendererFactory()
    {
        return this.theadHTML5RendererFactory;
    }
}
