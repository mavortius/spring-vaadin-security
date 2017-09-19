package com.company.app.view

import com.company.app.Sections
import com.vaadin.navigator.View
import com.vaadin.navigator.ViewChangeListener
import com.vaadin.server.FontAwesome
import com.vaadin.shared.ui.ContentMode
import com.vaadin.spring.annotation.SpringView
import com.vaadin.ui.Label
import com.vaadin.ui.VerticalLayout
import com.vaadin.ui.themes.ValoTheme
import org.vaadin.spring.sidebar.annotation.FontAwesomeIcon
import org.vaadin.spring.sidebar.annotation.SideBarItem

/**
 * When the user logs in and there is no view to navigate to, this view will be shown.
 *
 */
@SpringView(name = '')
@SideBarItem(sectionId = Sections.VIEWS, caption = 'Home', order = 0)
@FontAwesomeIcon(FontAwesome.HOME)
class HomeView extends VerticalLayout implements View {

    HomeView() {
        spacing = true
        margin = true
        Label header = new Label('Welcome to the Vaadin Managed Security Demo!')
        header.addStyleName(ValoTheme.LABEL_H1)
        addComponent(header)

        Label body = new Label(
                """
                <p>This application demonstrate how a Vaadin application can take care of security itself while still integrating with Spring Security.</p>
                <p>Please try it out by clicking and navigating around as different users. You can log in as <em>user/user</em> or <em>admin/admin</em>. Some of the protected
                features are hidden from the UI when you cannot access them, others are visible all the time.</p>
                <p>Also note that since we are using web socket based push, we do not have access to cookies and therefore cannot use Remember Me services.</p>
                """
        )
        body.contentMode = ContentMode.HTML
        addComponent(body)
    }

    @Override
    void enter(ViewChangeListener.ViewChangeEvent event) {
        // Nothing to do here
    }
}
