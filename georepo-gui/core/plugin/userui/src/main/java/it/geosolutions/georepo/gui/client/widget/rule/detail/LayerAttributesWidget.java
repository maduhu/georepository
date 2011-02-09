/*
 * $ Header: it.geosolutions.georepo.gui.client.widget.rule.detail.LayerAttributesWidget,v. 0.1 8-feb-2011 15.06.43 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 8-feb-2011 15.06.43 $
 *
 * ====================================================================
 *
 * Copyright (C) 2007 - 2011 GeoSolutions S.A.S.
 * http://www.geo-solutions.it
 *
 * GPLv3 + Classpath exception
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
package it.geosolutions.georepo.gui.client.widget.rule.detail;

import it.geosolutions.georepo.gui.client.model.Rule;
import it.geosolutions.georepo.gui.client.service.RulesManagerServiceRemoteAsync;

import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class LayerAttributesWidget.
 */
public class LayerAttributesWidget extends ContentPanel {
    
    /** The layer attributes info. */
    private LayerAttributesGridWidget layerAttributesInfo;
    private Rule model;

    /**
     * Instantiates a new layer attributes widget.
     * 
     * @param rulesService
     *            the rules service
     */
    public LayerAttributesWidget(Rule model, RulesManagerServiceRemoteAsync rulesService) {
        this.model = model;
        setHeaderVisible(false);
        setFrame(true);
        setHeight(400);
        setLayout(new FitLayout());

        setLayerAttributesInfo(new LayerAttributesGridWidget(model, rulesService));

        //add(getLayerCustomPropsInfo().getGrid());

        super.setMonitorWindowResize(true);

        setScrollMode(Scroll.NONE);

        //setBottomComponent(this.getRulesInfo().getToolBar());
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.extjs.gxt.ui.client.widget.Component#onWindowResize(int, int)
     */
    @Override
    protected void onWindowResize(int width, int height) {
        // TODO Auto-generated method stub
        super.setWidth(width - 5);
        super.layout();
    }
    
    /**
     * Sets the layer attributes info.
     * 
     * @param layerAttributesInfo
     *            the new layer attributes info
     */
    public void setLayerAttributesInfo(LayerAttributesGridWidget layerAttributesInfo) {
        this.layerAttributesInfo = layerAttributesInfo;
    }

    /**
     * Gets the layer attributes info.
     * 
     * @return the layer attributes info
     */
    public LayerAttributesGridWidget getLayerAttributesInfo() {
        return layerAttributesInfo;
    }
    
}
