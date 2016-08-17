package grails.sandbox

import org.hibernate.event.internal.EntityCopyNotAllowedObserver
import org.hibernate.event.spi.EventSource
/**
 * Used in exceptional cases to allow to instances of the same entity in the graph to be merged.
 * See: https://hibernate.atlassian.net/browse/HHH-9106
 *
 */
class EntityCopyExceptionallyAllowedObserver extends EntityCopyNotAllowedObserver{
  @Override
  public void entityCopyDetected(
      Object managedEntity,
      Object mergeEntity1,
      Object mergeEntity2,
      EventSource session) {
    if(!(managedEntity instanceof Attachment)) {
      super.entityCopyDetected(managedEntity,mergeEntity1, mergeEntity2, session)
    }
  }
}
