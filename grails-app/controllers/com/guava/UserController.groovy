package com.guava

import org.springframework.dao.DataIntegrityViolationException

/**
 * UserController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
class UserController {

    def springSecurityService

    static allowedMethods = [save: "POST", update: "POST", delete: ["DELETE", "POST"]]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        def users = Person.all
        [userInstanceList: users, userInstanceTotal: users.size()]
    }

    def create() {
        def user = springSecurityService.getPrincipal()
        [userInstance: new Person(params)]
    }

    def save() {
        def map = params
        def userInstance = new Person(params)
        if (!userInstance.save(flush: true)) {
            render(view: "create", model: [userInstance: userInstance])
            return
        }

        if (!userInstance.authorities.contains(PersonAuthority.findByAuthority(params.role))) {
            PersonAuthority.create userInstance, PersonAuthority.findByAuthority(params.role)
        }

        redirect(action: "show", id: userInstance.id)
    }


    def delete() {
        try {
            Person.findById(params.id).delete()
        }
        catch (DataIntegrityViolationException e) {
            e.printStackTrace()
        }
    }
}