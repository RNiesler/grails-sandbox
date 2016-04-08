package rniesler.impact4270

import grails.test.mixin.integration.Integration
import grails.transaction.Transactional
import org.springframework.dao.DuplicateKeyException
import spock.lang.Specification

@Integration
@Transactional
class Impact4270Spec extends Specification {
  def "test merge"() {
    setup:
      Activity activity = new Activity()
      ActivityAttachment attachment = new ActivityAttachment()
      activity.addToAttachments(attachment)
      activity.save(flush: true, failOnError: true)
      activity.discard()
    when:
      Activity.withNewSession {
        activity.radiographyAffectedZone = attachment
        activity.merge(flush: true, failOnError: true, merge: true)
      }
    then:
     thrown(IllegalStateException)
  }


  def "test save"() {
    setup:
      Activity activity = new Activity()
      ActivityAttachment attachment = new ActivityAttachment()
      activity.addToAttachments(attachment)
      activity.save(flush: true, failOnError: true)
      activity.discard()
    when:
      Activity.withNewSession {
        activity.radiographyAffectedZone = attachment
        activity.save(flush: true, failOnError: true)
      }
    then:
      thrown(DuplicateKeyException)
  }
}
