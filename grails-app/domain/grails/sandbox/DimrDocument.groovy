package grails.sandbox

class DimrDocument {
    DimrDocumentType type
    static belongsTo = [dimr: Dimr]
}

enum DimrDocumentType {
    WDP, OPTIMIZATION
}
