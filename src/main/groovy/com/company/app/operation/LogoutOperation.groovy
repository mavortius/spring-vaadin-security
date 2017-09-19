package com.company.app.operation

import com.company.app.Sections
import com.vaadin.server.FontAwesome
import com.vaadin.spring.annotation.SpringComponent
import org.springframework.beans.factory.annotation.Autowired
import org.vaadin.spring.security.managed.VaadinManagedSecurity
import org.vaadin.spring.sidebar.annotation.FontAwesomeIcon
import org.vaadin.spring.sidebar.annotation.SideBarItem

/**
 * Operation that logs the user out.
 *
 */
@SpringComponent
@SideBarItem(sectionId = Sections.OPERATIONS, caption = 'Logout')
@FontAwesomeIcon(FontAwesome.POWER_OFF)
class LogoutOperation implements Runnable {

    private final VaadinManagedSecurity security

    @Autowired
    LogoutOperation(VaadinManagedSecurity security) {
        this.security = security
    }

    @Override
    void run() {
        security.logout "?goodbye"
    }
}
