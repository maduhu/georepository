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


import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.trg.search.ISearch;
import it.geosolutions.georepo.core.dao.LayerDetailsDAO;
import it.geosolutions.georepo.core.model.LayerDetails;
import java.util.Map;
import org.hibernate.Hibernate;

/**
 * Public implementation of the RuleLimitsDAO interface
 * 
 * @author Emanuele Tajariol (etj at geo-solutions.it)
 */
@Transactional
public class LayerDetailsDAOImpl extends BaseDAO<LayerDetails, Long>
// extends GenericDAOImpl<GSUser, Long>
        implements LayerDetailsDAO {

    final private static Logger LOGGER = Logger.getLogger(LayerDetailsDAOImpl.class);

    @Override
    public void persist(LayerDetails... entities) {
        super.persist(entities);
    }

//    @Override
//    public RuleLimits find(Long id) {
//        return super.find(id);
//    }
//
    @Override
    public List<LayerDetails> findAll() {
        return super.findAll();
    }

    @Override
    public List<LayerDetails> search(ISearch search) {
        return super.search(search);
    }

    @Override
    public LayerDetails merge(LayerDetails entity) {
        return super.merge(entity);
    }

    @Override
    public boolean remove(LayerDetails entity) {
        return super.remove(entity);
    }

    @Override
    public boolean removeById(Long id) {
        return super.removeById(id);
    }

    //==========================================================================

    @Override
    public Map<String, String> getCustomProps(Long id) {
        LayerDetails found = find(id);
        if (found != null) {
            Map<String, String> props = found.getCustomProps();

            if (props != null && !Hibernate.isInitialized(props)) {
                Hibernate.initialize(props); // fetch the props
            }
            return props;
        } else {
            throw new IllegalArgumentException("LayerDetails not found");
        }
    }

    @Override
    public void setCustomProps(Long id, Map<String, String> props) {
        LayerDetails found = find(id);
        if (found != null) {
            found.setCustomProps(props);
        } else {
            throw new IllegalArgumentException("LayerDetails not found");
        }
    }
}
