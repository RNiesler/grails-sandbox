package grails.sandbox

import grails.test.mixin.integration.Integration
import grails.transaction.Transactional
import org.hibernate.Session
import org.junit.Before
import org.junit.Test

@Integration
@Transactional
class MergeProblemTest {
  Status status1
  Status status2
  Activity activity

  @Before
  void setup() {
    status1 = new Status(name: 'test-status1')
    status1.save()
    status2 = new Status(name: 'test-status2')
    status2.save()
    activity = new Activity();
    activity.status = status1
    def attachment = new Attachment(name: 'test-attachment')

    activity.radZone = attachment
    activity.addToAttachments(attachment)

    activity.save(flush: true)
    assert activity.attached
  }

  @Test
  void testMergeInEmptySession() {
    Activity.withSession { Session session ->
      session.clear()
      activity.status = status2
      def mergedActivity = activity.merge(flush: true, validate: false, merge: true)
      validateMerge(mergedActivity, activity)
    }
  }

  @Test
  void testMergeInNewSession() {
    Activity.withNewSession { Session session ->
      assert !activity.attached
      activity.status = status2
      def mergedActivity = activity.merge(flush: true, validate: false, merge: true)
      validateMerge(mergedActivity, activity)
    }
  }

  @Test
  void testMergeWithActivityInSession() {
    activity.attach()
    assert activity.attached
    activity.status = status2
    def mergedActivity = activity.merge(flush: true, validate: false, merge: true)
    validateMerge(mergedActivity, activity)
  }

  private void validateMerge(Activity merged, Activity old) {
    assert merged.status.id == old.status.id
    Activity.withNewSession {
      assert Activity.findById(merged.id)?.status.id == old.status.id
    }
  }
}