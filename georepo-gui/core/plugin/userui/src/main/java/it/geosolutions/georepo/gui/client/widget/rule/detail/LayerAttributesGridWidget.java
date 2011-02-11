/*
 * $ Header: it.geosolutions.georepo.gui.client.widget.rule.detail.LayerAttributesGridWidget,v. 0.1 8-feb-2011 15.06.43 created by afabiani <alessio.fabiani at geo-solutions.it> $
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
import it.geosolutions.georepo.gui.client.widget.GeoRepoGridWidget;

import com.extjs.gxt.ui.client.widget.grid.ColumnModel;

// TODO: Auto-generated Javadoc
/**
 * The Class LayerAttributesGridWidget.
 */
public class LayerAttributesGridWidget extends GeoRepoGridWidget<Rule> {

    /** The rules service. */
    private RulesManagerServiceRemoteAsync rulesService;
    private Rule model;

    /**
     * Instantiates a new layer attributes grid widget.
     * @param model 
     * 
     * @param rulesService
     *            the rules service
     */
    public LayerAttributesGridWidget(Rule model, RulesManagerServiceRemoteAsync rulesService) {
        super();
        this.model = model;
        this.rulesService = rulesService;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see it.geosolutions.georepo.gui.client.widget.DGWATCHGridWidget#setGridProperties ()
     */
    @Override
    public void setGridProperties() {
        // grid.setAutoExpandColumn(BeanKeyValue.NAME.getValue());
        // grid.addPlugin(emailEnable);
        // grid.addPlugin(rssEnable);

        grid.setLoadMask(true);
        grid.setAutoWidth(true);
    }
    
    /* (non-Javadoc)
     * @see it.geosolutions.georepo.gui.client.widget.GeoRepoGridWidget#createStore()
     */
    @Override
    public void createStore() {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see it.geosolutions.georepo.gui.client.widget.GeoRepoGridWidget#prepareColumnModel()
     */
    @Override
    public ColumnModel prepareColumnModel() {
        // TODO Auto-generated method stub
        return null;
    }

}