package com.guava

import grails.plugin.springsecurity.annotation.Secured
import org.apache.commons.logging.LogFactory

@Secured('IS_AUTHENTICATED_FULLY')

class AdminController {

        private static final log = LogFactory.getLog(this)
        def springSecurityService

        def index() {
            def msgs = currentUserTimeline()
            return [messages: msgs] // this is a map. key=>value
        }

        def follow = {
            def per = Person.get(params.id)
            if(per) {
                def currentUser = lookupPerson()
                currentUser.addToFollowed(per)
                currentUser.save()
            }
            redirect action: 'index'
        }

        def updateStatus = {
            def status = new Status(message: params.message)
            status.author = lookupPerson()
            status.save()
            def messages = currentUserTimeline()
            render template: 'messages', collection: messages, var: 'message'
        }

        private lookupPerson() {
            Person.get(springSecurityService.principal.id)
        }

        private currentUserTimeline() {
            def per = lookupPerson()
            def query = Status.whereAny {
                author { username == per.username }
                if(per.followed) author in per.followed
            }.order 'dateCreated', 'desc'
            def messages = query.list(max: 10)

            messages
        }

    }