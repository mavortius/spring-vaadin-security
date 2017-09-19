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
 * An operation that invokes a backend method that is available for all users.
 *
 */
@SpringComponent
@SideBarItem(sectionId = Sections.OPERATIONS, caption = 'User operation', order = 0)
@FontAwesomeIcon(FontAwesome.ANCHOR)
class UserOperation implements Runnable {

    private final MyBackend backend

    @Autowired
    UserOperation(MyBackend backend) {
        this.backend = backend
    }

    @Override
    void run() {
        Notification.show backend.echo('Hello World')
    }
}
