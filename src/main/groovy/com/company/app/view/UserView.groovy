package com.company.app.view

import com.company.app.Sections
import com.company.app.backend.MyBackend
import com.vaadin.navigator.View
import com.vaadin.navigator.ViewChangeListener
import com.vaadin.server.FontAwesome
import com.vaadin.spring.annotation.SpringView
import com.vaadin.ui.Button
import com.vaadin.ui.CustomComponent
import com.vaadin.ui.Notification
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.annotation.Secured
import org.vaadin.spring.sidebar.annotation.FontAwesomeIcon
import org.vaadin.spring.sidebar.annotation.SideBarItem

/**
 * View that is available for all users.
 *
 */
@Secured(['ROLE_USER', 'ROLE_ADMIN'])
@SpringView(name = 'user')
@SideBarItem(sectionId = Sections.VIEWS, caption = 'User View')
@FontAwesomeIcon(FontAwesome.ARCHIVE)
class UserView extends CustomComponent implements View {

    @Autowired
    UserView(MyBackend backend) {
        Button button = new Button('Call user backend', { Button.ClickEvent componentEvent ->
            Notification.show(backend.echo('Hello User World!'))
        })
        compositionRoot = button
    }

    @Override
    void enter(ViewChangeListener.ViewChangeEvent event) {
        // Nothing to do here
    }
}
