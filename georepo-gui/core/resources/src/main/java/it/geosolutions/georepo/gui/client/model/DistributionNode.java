package it.geosolutions.georepo.gui.client.model;

import com.extjs.gxt.ui.client.data.BeanModel;

/**
 * The remote node to which content or member information are distributed.
 *
 * @author gmurray
 *
 */
public class DistributionNode extends BeanModel implements Comparable<DistributionNode> {

    /**
     * Key names for DistributionNode properties.
     */
    public static enum KeyValue {

        NODE_ID("id"),
        NODE_NAME("nodeName");

        private String value;

        KeyValue(String value) {
            this.value = value;
        }

        /**
         * @return the value
         */
        public String getValue() {
            return value;
        }

    }

	private static final long serialVersionUID = 5410505298544762818L;

	private String nodeName;
	private Integer id;

    public DistributionNode() {
    }

    public DistributionNode(Integer id, String name) {
        setId(id);
        setNodeName(name);
    }

    
	/**
	 * @return the node name
	 */
	public String getNodeName() {
		return nodeName;
	}

	/**
	 * @param name the node name to set
	 */
	public void setNodeName(String name) {
		this.nodeName = name;
		set(KeyValue.NODE_NAME.getValue(), this.nodeName);
	}

	/**
	 * @return the connectId
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the node id to set
	 */
	public void setId(Integer id) {
		this.id = id;
		set(KeyValue.NODE_ID.getValue(), this.id);
	}

    public int compareTo(DistributionNode n)  {
        return this.nodeName.compareTo(n.getNodeName());
    }

    @Override
    public String toString() {
        return "DistributionNode{" +
                "nodeName='" + nodeName + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }
}