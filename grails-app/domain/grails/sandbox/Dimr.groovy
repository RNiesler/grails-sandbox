package grails.sandbox

class Dimr {
    static hasMany = [
        versions: DimrVersion,
        rpAssessments: RPAssessment,
        documents: DimrDocument,
        comments: DimrComment
    ]

}
