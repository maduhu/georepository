/*
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

/**
 * @author Tobia Di Pisa
 *
 */
public class Member extends BeanModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5410505298544767218L;
	
	private String memberName;
	private String connectId;
	private String organization;
	
	/**
	 * @return the memberName
	 */
	public String getMemberName() {
		return memberName;
	}
	
	/**
	 * @param memberName the memberName to set
	 */
	public void setMemberName(String memberName) {
		this.memberName = memberName;
		set(MemberKeyValue.MEMBER_NAME.getValue(), this.memberName);
	}
	
	/**
	 * @return the connectId
	 */
	public String getConnectId() {
		return connectId;
	}
	
	/**
	 * @param connectId the connectId to set
	 */
	public void setConnectId(String connectId) {
		this.connectId = connectId;
		set(MemberKeyValue.MEMBER_UID.getValue(), this.connectId);
	}

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
		set(MemberKeyValue.MEMBER_ORG.getValue(), this.organization);
    }


    @Override
    public String toString() {
        return "Member [connectId=" + connectId + ", memberName=" + memberName + ", org="+organization+"]";
    }
	
}
