/*
 *  Copyright (C) 2007 - 2011 GeoSolutions S.A.S.
 *  http://www.geo-solutions.it
 * 
 *  GPLv3 + Classpath exception
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package it.geosolutions.georepo.core.dao.impl;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.trg.dao.jpa.GenericDAOImpl;
import com.trg.search.jpa.JPASearchProcessor;

/**
 * 
 * The base DAO furnish a set of methods usually used
 * 
 * @author Tobia Di Pisa (tobia.dipisa@geo-solutions.it)
 */
@Repository
public class BaseDAO<T, ID extends Serializable> extends GenericDAOImpl<T, ID> {

    /**
     * EntityManager setting
     * 
     * @param entityManager
     *            the entity manager to set
     */
    @Override
    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        super.setEntityManager(entityManager);
    }

    /**
     * JPASearchProcessor setting
     * 
     * @param searchProcessor
     *            the search processor to set
     */
    @Override
    @Autowired
    public void setSearchProcessor(JPASearchProcessor searchProcessor) {
        super.setSearchProcessor(searchProcessor);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.trg.dao.jpa.JPABaseDAO#em()
     */
    @Override
    public EntityManager em() {
        // TODO Auto-generated method stub
        return super.em();
    }
}
