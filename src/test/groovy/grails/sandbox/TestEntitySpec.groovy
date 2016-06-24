package grails.sandbox

import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(TestEntity)
class TestEntitySpec extends Specification {

    void "test validate"() {
        setup:
            def testEntity = new TestEntity()
            testEntity.subEntities = [new TestSubEntity()]
        expect:
            testEntity.validate()
            testEntity.validate(deepValidate: true)
            testEntity.validate(deepValidate: false)

    }
    void "test validate with no validation"() {
        setup:
            def testEntity = new TestEntity()
            testEntity.subEntities = [new TestSubEntity()]
            testEntity.save(validate: false)
        expect:
            testEntity.validate()
        // the following ones are failing
            testEntity.validate(deepValidate: true)
            testEntity.validate(deepValidate: false)

    }
}
