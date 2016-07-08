package grails.sandbox

class RPAssessment {
    static belongsTo = [
        dimr: Dimr,
        activity: Activity
    ]
    static hasMany = [
        versions: RPAssessmentVersion
    ]
    static constraints = {
    }
}
