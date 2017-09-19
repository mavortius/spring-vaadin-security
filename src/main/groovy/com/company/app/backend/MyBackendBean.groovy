package com.company.app.backend

import org.springframework.stereotype.Service

/**
 * Implementation of {@link MyBackendBean}
 *
 */
@Service
class MyBackendBean implements MyBackend {

    @Override
    String adminOnlyEcho(String s) {
        "admin:$s"
    }

    @Override
    String echo(String s) {
        s
    }
}
