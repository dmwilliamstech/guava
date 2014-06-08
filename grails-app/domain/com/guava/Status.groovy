package com.guava

class Status {

    String message
    Person author
    Date dateCreated

    static constraints = {
        message size:2..250, blank: false
    }
}
