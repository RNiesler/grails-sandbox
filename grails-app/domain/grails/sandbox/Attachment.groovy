package grails.sandbox

class Attachment {
  String name

  static belongsTo = [activity: Activity]
}
