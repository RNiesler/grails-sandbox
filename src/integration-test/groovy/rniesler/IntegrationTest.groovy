package rniesler

import grails.test.mixin.integration.Integration
import grails.transaction.Transactional
import org.springframework.dao.DuplicateKeyException
import spock.lang.Specification

@Integration
@Transactional
class IntegrationTest extends Specification {
  def "test"() {
    setup:
    Entity entity = new Entity(field1: 'TestValue')
    entity.save()
    expect:
    assert false, 'Implement'
  }
}
