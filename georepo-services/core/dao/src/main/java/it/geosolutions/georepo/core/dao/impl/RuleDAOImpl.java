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
import com.trg.search.Search;
import it.geosolutions.georepo.core.dao.RuleDAO;
import it.geosolutions.georepo.core.model.Rule;
import javax.persistence.Query;
import org.springframework.dao.DuplicateKeyException;

/**
 * Public implementation of the GSUserDAO interface
 * 
 * @author Emanuele Tajariol (etj at geo-solutions.it)
 */
@Transactional
public class RuleDAOImpl extends BaseDAO<Rule, Long>
// extends GenericDAOImpl<GSUser, Long>
        implements RuleDAO {

    final private static Logger LOGGER = Logger.getLogger(RuleDAOImpl.class);

    @Override
    public void persist(Rule... entities) {

        for (Rule rule : entities) {
            Search search = getDupSearch(rule);
            if(count(search)>0)
                throw new DuplicateKeyException("Duplicate Rule " + rule);
        }

        super.persist(entities);
    }


    private Search getDupSearch(Rule rule) {
        Search search = new Search(Rule.class);
        addSearchField(search, "gsuser", rule.getGsuser());
        addSearchField(search, "profile", rule.getProfile());
        addSearchField(search, "instance", rule.getInstance());
        addSearchField(search, "service", rule.getService());
        addSearchField(search, "request", rule.getRequest());
        addSearchField(search, "workspace", rule.getWorkspace());
        addSearchField(search, "layer", rule.getLayer());
        return search;
    }

    private void addSearchField(Search search, String field, Object o) {
        if( o == null)
            search.addFilterNull(field);
        else
            search.addFilterEqual(field, o);
    }


    @Override
    public List<Rule> findAll() {
        return super.findAll();
    }

    @Override
    public List<Rule> search(ISearch search) {
        return super.search(search);
    }

    @Override
    public Rule merge(Rule entity) {
        Search search = getDupSearch(entity);
        
        // check if we are dup'ing some other Rule.
        List<Rule> existent = search(search);
        switch(existent.size()) {
            case 0:
                break;

            case 1:
                // We may be updating some other fields in this Rule
                if(! existent.get(0).getId().equals(entity.getId()) )
                    throw new DuplicateKeyException("Duplicating Rule " + existent.get(0) + " with " + entity);
                break;

            default:
                throw new IllegalStateException("Too many rules duplicating " + entity);
        }

        return super.merge(entity);
    }

    @Override
    public int shift(long priorityStart, long offset) {
        if(offset <= 0)
            throw new IllegalArgumentException("Positive offset required");

//        Search search = new Search(Rule.class);
//        search.addFilterGreaterOrEqual("priority", priorityStart);

        String hql = "UPDATE Rule SET priority=priority+ :offset WHERE priority >= :priorityStart";

        Query query = em().createQuery(hql);
        query.setParameter("offset", offset);
        query.setParameter("priorityStart", priorityStart);

        return query.executeUpdate();
    }

    @Override
    public boolean remove(Rule entity) {
        return super.remove(entity);
    }

    @Override
    public boolean removeById(Long id) {
        return super.removeById(id);
    }


}
