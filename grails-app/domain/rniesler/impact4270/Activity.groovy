package rniesler.impact4270

class Activity {

    static constraints = {
        radiographyAffectedZone(nullable: true)
    }

    static hasMany = [ attachments: ActivityAttachment ]

    ActivityAttachment radiographyAffectedZone
}
