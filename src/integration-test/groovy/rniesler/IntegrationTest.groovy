package rniesler

import grails.test.mixin.integration.Integration
import grails.transaction.Transactional
import org.grails.datastore.mapping.query.Query
import grails.gorm.DetachedCriteria
import org.hibernate.exception.SQLGrammarException
import spock.lang.Specification

@Integration
@Transactional
class IntegrationTest extends Specification {

    def setup() {
        DetachedEntity.findAll().each { it.delete() }
        Entity.findAll().each { it.delete(flush: true) }
        final entity1 = new Entity(id: 1, field1: 'Correct').save()
        new Entity(id: 2, field1: 'Incorrect').save()
        new DetachedEntity(id: 1, entityId: entity1.id, field: 'abc').save()
        new DetachedEntity(id: 2, entityId: entity1.id, field: 'def').save()
    }

    def 'closure projection fails'() {
        setup:
        final detachedCriteria = new DetachedCriteria(DetachedEntity).build {
            projections {
                distinct 'entityId'
            }
            eq 'field', 'abc'
        }
        when:
        // will fail
        Entity.withCriteria {
            inList 'id', detachedCriteria
        }
        then:
        thrown(SQLGrammarException)

    }

    def 'closure projection manually'() {
        setup:
        final detachedCriteria = new DetachedCriteria(DetachedEntity).build {
            eq 'field', 'abc'
        }
        detachedCriteria.projections << new Query.DistinctPropertyProjection('entityId')
        expect:
        assert Entity.withCriteria {
            inList 'id', detachedCriteria
        }.collect { it.field1 }.contains('Correct')
    }

    def 'or fails in detached criteria'() {
        setup:
        final detachedCriteria = new DetachedCriteria(DetachedEntity).build {
            or {
                eq 'field', 'abc'
                eq 'field', 'def'
            }
        }
        detachedCriteria.projections << new Query.DistinctPropertyProjection('entityId')
        when:
        assert Entity.withCriteria {
            inList 'id', detachedCriteria
        }
        then:
        thrown(NullPointerException)
    }
}
