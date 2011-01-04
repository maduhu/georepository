/*
 * $ Header: it.geosolutions.georepo.gui.client.widget.DistributionNodeManagementWidget,v. 0.1 3-gen-2011 17.06.54 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 3-gen-2011 17.06.54 $
 *
 * ====================================================================
 *
 * Copyright (C) 2010 GeoSolutions S.A.S.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. 
 *
 * ====================================================================
 *
 * This software consists of voluntary contributions made by developers
 * of GeoSolutions.  For more information on GeoSolutions, please see
 * <http://www.geo-solutions.it/>.
 *
 */
package it.geosolutions.georepo.gui.client.widget;

import it.geosolutions.georepo.gui.client.i18n.I18nProvider;
import it.geosolutions.georepo.gui.client.model.DistributionNode;

import java.util.List;

import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.WidgetListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.StoreSorter;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.form.DualListField;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.ListField;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class DistributionNodeManagementWidget.
 */
public class DistributionNodeManagementWidget extends ContentPanel {

    /** The form panel. */
    private FormPanel formPanel;

    /** The node selector. */
    private DualListField<DistributionNode> nodeSelector;

    /** The source nodes. */
    private List<DistributionNode> sourceNodes;

    /** The destination nodes. */
    private List<DistributionNode> destinationNodes;

    /**
     * Instantiates a new distribution node management widget.
     */
    public DistributionNodeManagementWidget() {
        init();
    }

    /**
     * Inits the.
     */
    private void init() {
        setId("distributionNodeManagementWidget");
        setHeading(I18nProvider.getMessages().memberDistributionNodeManagement());
        setLayout(new FitLayout());

        setLayoutOnChange(true);

        this.formPanel = createFormPanel();
        add(this.formPanel);

        addWidgetListener(new WidgetListener() {
            @Override
            public void widgetResized(ComponentEvent ce) {
                formPanel.setHeight(getHeight());
            }
        });

        setScrollMode(Style.Scroll.AUTOY);
    }

    /**
     * Creates the form panel.
     * 
     * @return the form panel
     */
    private FormPanel createFormPanel() {
        FormPanel fp = new FormPanel();
        fp.setFrame(true);
        fp.setHeaderVisible(false);

        this.nodeSelector = new DualListField<DistributionNode>();
        this.nodeSelector.setFieldLabel("Nodes");
        ListField<DistributionNode> source = this.nodeSelector.getFromList();
        source.setDisplayField(DistributionNode.KeyValue.NODE_NAME.getValue());
        source.setValueField(DistributionNode.KeyValue.NODE_ID.getValue());

        // fill up the source list
        ListStore<DistributionNode> store = new ListStore<DistributionNode>();
        store.setStoreSorter(new StoreSorter<DistributionNode>());
        source.setStore(store);

        ListField<DistributionNode> destination = this.nodeSelector.getToList();
        destination.setDisplayField(DistributionNode.KeyValue.NODE_NAME.getValue());
        destination.setValueField(DistributionNode.KeyValue.NODE_ID.getValue());

        // empty destination by default
        // TODO: get these values from the server for the specified member
        store = new ListStore<DistributionNode>();
        store.setStoreSorter(new StoreSorter<DistributionNode>());
        destination.setStore(store);

        fp.add(this.nodeSelector);

        return fp;
    }

    /**
     * Sets the source nodes.
     * 
     * @param nodes
     *            the new source nodes
     */
    public void setSourceNodes(List<DistributionNode> nodes) {
        this.sourceNodes = nodes;
        fillNodeSelector();
    }

    /**
     * Gets the source nodes.
     * 
     * @return the source nodes
     */
    public List<DistributionNode> getSourceNodes() {
        return this.sourceNodes;
    }

    /**
     * Sets the destination nodes.
     * 
     * @param nodes
     *            the new destination nodes
     */
    public void setDestinationNodes(List<DistributionNode> nodes) {
        this.destinationNodes = nodes;
        fillNodeSelector();
    }

    /**
     * Gets the destination nodes.
     * 
     * @return the destination nodes
     */
    public List<DistributionNode> getDestinationNodes() {
        return this.destinationNodes;
    }

    /**
     * Gets the selected nodes.
     * 
     * @return the selected nodes
     */
    public List<DistributionNode> getSelectedNodes() {
        return this.nodeSelector.getToList().getStore().getModels();
    }

    /**
     * Fill node selector.
     */
    private void fillNodeSelector() {

        // make sure we have source and destination lists before filling the control - they may be
        // empty,
        // but cannot be null - null means they haven't been initialized yet
        ListField<DistributionNode> source = this.nodeSelector.getFromList();
        ListStore<DistributionNode> sourceStore = source.getStore();
        sourceStore.removeAll();

        if (this.sourceNodes != null) {
            // fill up the source list
            sourceStore.setStoreSorter(new StoreSorter<DistributionNode>());

            for (DistributionNode node : this.sourceNodes) {
                sourceStore.add(node);
            }
        }

        if ((this.sourceNodes != null) && (this.destinationNodes != null)) {

            // fill up the source list
            ListField<DistributionNode> destination = this.nodeSelector.getToList();
            ListStore<DistributionNode> destStore = destination.getStore();
            destStore.removeAll();
            destStore.setStoreSorter(new StoreSorter<DistributionNode>());

            for (DistributionNode node : this.sourceNodes) {
                if (this.destinationNodes.contains(node)) {
                    sourceStore.remove(node);
                }
            }

            // fill up the destination list
            destStore.add(this.destinationNodes);
        }
    }

}
