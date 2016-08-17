package grails.sandbox
/**
 * @author rniesler
 * @since 17.08.16
 */
class Activity {
  Status status
  static hasMany = [ attachments: Attachment ]
  Attachment radZone

  static constraints = {
    radZone nullable: true
  }
}
