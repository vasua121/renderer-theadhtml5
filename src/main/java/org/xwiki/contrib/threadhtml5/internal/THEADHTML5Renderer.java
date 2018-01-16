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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.xwiki.component.annotation.Component;
import org.xwiki.component.annotation.InstantiationStrategy;
import org.xwiki.component.descriptor.ComponentInstantiationStrategy;
import org.xwiki.rendering.listener.QueueListener;
import org.xwiki.rendering.listener.WrappingListener;
import org.xwiki.rendering.renderer.PrintRenderer;
import org.xwiki.rendering.renderer.PrintRendererFactory;
import org.xwiki.rendering.renderer.printer.WikiPrinter;
import org.xwiki.rendering.renderer.printer.XHTMLWikiPrinter;

/**
 * @version $Id: 750d08f503da1c06925b75c21f930e6e6eb4374f $
 */
@Component
@Named(THEADHTML5RendererFactory.SYNTAX_STRING)
@InstantiationStrategy(ComponentInstantiationStrategy.PER_LOOKUP)
public class THEADHTML5Renderer extends WrappingListener implements PrintRenderer
{
    private static final String THEAD = "thead";

    private static final String TBODY = "tbody";

    @Inject
    @Named("html/5.0")
    private PrintRendererFactory html5RendererFactory;

    private PrintRenderer html5Renderer;

    private XHTMLWikiPrinter xhtmlWikiPrinter;

    private List<QueueListener> theadRows;

    private boolean tbodyOpen;

    @Override
    public WikiPrinter getPrinter()
    {
        return this.html5Renderer != null ? this.html5Renderer.getPrinter() : null;
    }

    @Override
    public void setPrinter(WikiPrinter printer)
    {
        if (this.html5Renderer == null) {
            this.html5Renderer = this.html5RendererFactory.createRenderer(printer);

            setWrappedListener(this.html5Renderer);
        } else {
            this.html5Renderer.setPrinter(printer);
        }
    }

    private XHTMLWikiPrinter getXHTMLWikiPrinter()
    {
        if (this.xhtmlWikiPrinter == null) {
            this.xhtmlWikiPrinter = new XHTMLWikiPrinter(getPrinter());
        }
        return this.xhtmlWikiPrinter;
    }

    @Override
    public void beginTable(Map<String, String> parameters)
    {
        this.theadRows = new ArrayList<>();

        super.beginTable(parameters);
    }

    @Override
    public void beginTableRow(Map<String, String> parameters)
    {
        if (this.theadRows != null) {
            // Start queuing current row events
            addRowQueue();
        }

        super.beginTableRow(parameters);
    }

    @Override
    public void beginTableCell(Map<String, String> parameters)
    {
        if (this.theadRows != null) {
            // The current row should not be part of the thead
            QueueListener currentRow = this.theadRows.remove(this.theadRows.size() - 1);

            // Print the thead
            releaseQueues();

            // Print the current line
            currentRow.consumeEvents(this.html5Renderer);
        }

        super.beginTableCell(parameters);
    }

    @Override
    public void endTable(Map<String, String> parameters)
    {
        // Flush any queued thead
        releaseQueues();

        // Print the end of the <tbody> if needed
        endTableBody();

        super.endTable(parameters);
    }

    private void addRowQueue()
    {
        // Create a new queue
        QueueListener queue = new QueueListener();

        // Set the queue as listener
        setWrappedListener(queue);

        // Add the queue to the thead
        this.theadRows.add(queue);
    }

    private void releaseQueues()
    {
        if (this.theadRows != null && !this.theadRows.isEmpty()) {
            // Print the start of the <thead>
            beginTableHead();

            // Print the children of the <thead>
            for (QueueListener queue : this.theadRows) {
                queue.consumeEvents(this.html5Renderer);
            }

            // Print the end of the </thead>
            endTableHead();

            // Print the start of the <tbody>
            beginTableBody();
        }

        // Put back html5 as current listener
        setWrappedListener(this.html5Renderer);

        // Reset thead queues
        this.theadRows = null;
    }

    private void beginTableHead()
    {
        getXHTMLWikiPrinter().printXMLStartElement(THEAD);
    }

    private void endTableHead()
    {
        getXHTMLWikiPrinter().printXMLEndElement(THEAD);
    }

    private void beginTableBody()
    {
        getXHTMLWikiPrinter().printXMLStartElement(TBODY);
        this.tbodyOpen = true;
    }

    private void endTableBody()
    {
        if (this.tbodyOpen) {
            getXHTMLWikiPrinter().printXMLEndElement(TBODY);

            this.tbodyOpen = false;
        }
    }
}
