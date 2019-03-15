package com.blah.crud.crudtest.services;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.blah.crud.crudtest.persistence.entity.Property;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.apache.lucene.search.Query;
import org.springframework.stereotype.Repository;
import org.hibernate.search.jpa.FullTextQuery;

@Repository
@Transactional
public class PropertySearchService {

    // Spring will inject here the entity manager object
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * A basic search for the entity User. The search is done by exact match per
     * keywords on fields name, city and email.
     *
     * @param text The query text.
     */
    public List search(String text) {

        // get the full text entity manager
        FullTextEntityManager fullTextEntityManager =
                org.hibernate.search.jpa.Search.
                        getFullTextEntityManager(entityManager);

        // create the query using Hibernate Search query DSL
        QueryBuilder queryBuilder =
                fullTextEntityManager.getSearchFactory()
                        .buildQueryBuilder().forEntity(Property.class).get();

        // a very basic query by keywords
        Query query =
                queryBuilder
                        .keyword().fuzzy().withEditDistanceUpTo(1).withPrefixLength(1)
                        .onField("propname").andField("address").andField("description")
                        .matching(text)
                        .createQuery();

        // wrap Lucene query in an Hibernate Query object
        FullTextQuery jpaQuery =
                fullTextEntityManager.createFullTextQuery(query, Property.class);

        // execute search and return results (sorted by relevance as default)
        @SuppressWarnings("unchecked")
        List results = jpaQuery.getResultList();

        return results;
    } // method search

}