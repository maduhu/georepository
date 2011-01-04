/*
 * $ Header: it.geosolutions.georepo.gui.client.model.DistributionNode,v. 0.1 3-gen-2011 17.06.10 created by afabiani <alessio.fabiani at geo-solutions.it> $
 * $ Revision: 0.1 $
 * $ Date: 3-gen-2011 17.06.10 $
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
package it.geosolutions.georepo.gui.client.model;

import com.extjs.gxt.ui.client.data.BeanModel;

// TODO: Auto-generated Javadoc
/**
 * The Class DistributionNode.
 */
public class DistributionNode extends BeanModel implements Comparable<DistributionNode> {

    /**
     * The Enum KeyValue.
     */
    public static enum KeyValue {

        /** The NOD e_ id. */
        NODE_ID("id"), /** The NOD e_ name. */
        NODE_NAME("nodeName");

        /** The value. */
        private String value;

        /**
         * Instantiates a new key value.
         * 
         * @param value
         *            the value
         */
        KeyValue(String value) {
            this.value = value;
        }

        /**
         * Gets the value.
         * 
         * @return the value
         */
        public String getValue() {
            return value;
        }

    }

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 5410505298544762818L;

    /** The node name. */
    private String nodeName;

    /** The id. */
    private Integer id;

    /**
     * Instantiates a new distribution node.
     */
    public DistributionNode() {
    }

    /**
     * Instantiates a new distribution node.
     * 
     * @param id
     *            the id
     * @param name
     *            the name
     */
    public DistributionNode(Integer id, String name) {
        setId(id);
        setNodeName(name);
    }

    /**
     * Gets the node name.
     * 
     * @return the node name
     */
    public String getNodeName() {
        return nodeName;
    }

    /**
     * Sets the node name.
     * 
     * @param name
     *            the new node name
     */
    public void setNodeName(String name) {
        this.nodeName = name;
        set(KeyValue.NODE_NAME.getValue(), this.nodeName);
    }

    /**
     * Gets the id.
     * 
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the id.
     * 
     * @param id
     *            the new id
     */
    public void setId(Integer id) {
        this.id = id;
        set(KeyValue.NODE_ID.getValue(), this.id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(DistributionNode n) {
        return this.nodeName.compareTo(n.getNodeName());
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.extjs.gxt.ui.client.data.BeanModel#toString()
     */
    @Override
    public String toString() {
        return "DistributionNode{" + "nodeName='" + nodeName + '\'' + ", id='" + id + '\'' + '}';
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DistributionNode other = (DistributionNode) obj;
        return id == other.id;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }
}