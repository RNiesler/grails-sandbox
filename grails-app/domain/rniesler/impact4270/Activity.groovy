package rniesler.impact4270

class Activity {

    static constraints = {
        name(validator: validatorClosure)
    }

    String name

    static validatorClosure = { String name, Activity activity ->
        return true
    }
}
