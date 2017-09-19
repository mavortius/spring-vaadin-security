package com.company.app.operation

import com.company.app.Sections
import com.company.app.backend.MyBackend
import com.vaadin.server.FontAwesome
import com.vaadin.spring.annotation.SpringComponent
import com.vaadin.ui.Notification
import org.springframework.beans.factory.annotation.Autowired
import org.vaadin.spring.sidebar.annotation.FontAwesomeIcon
import org.vaadin.spring.sidebar.annotation.SideBarItem

/**
 * An operation that invokes a backend method that is available for admin users only. The operation is, however,
 * always visible in the side bar to demonstrate that the security checks on the backend layer are working.
 *
 */
@SpringComponent
@SideBarItem(sectionId = Sections.OPERATIONS, caption = 'Admin operation', order = 1)
@FontAwesomeIcon(FontAwesome.WRENCH)
class AdminOperation implements Runnable {

    private final MyBackend backend

    @Autowired
    AdminOperation(MyBackend backend) {
        this.backend = backend
    }

    @Override
    void run() {
        Notification.show backend.adminOnlyEcho('Hello Admin World')
    }
}
