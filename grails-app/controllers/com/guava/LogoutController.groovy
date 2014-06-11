package com.guava

import grails.plugin.springsecurity.SpringSecurityUtils

class LogoutController {


    def index = {
        // TODO put any pre-logout code here
        redirect uri: SpringSecurityUtils.securityConfig.logout.filterProcessesUrl
    }
}

