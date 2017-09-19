package com.company.app

import com.vaadin.annotations.Push
import com.vaadin.annotations.Theme
import com.vaadin.annotations.Title
import com.vaadin.server.DefaultErrorHandler
import com.vaadin.server.ErrorEvent
import com.vaadin.server.VaadinRequest
import com.vaadin.spring.annotation.SpringUI
import com.vaadin.ui.Notification
import com.vaadin.ui.UI
import com.vaadin.ui.themes.ValoTheme
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.vaadin.spring.events.EventBus.SessionEventBus
import org.vaadin.spring.events.annotation.EventBusListenerMethod
import org.vaadin.spring.security.VaadinSecurity
import org.vaadin.spring.security.util.SecurityExceptionUtils
import org.vaadin.spring.security.util.SuccessfulLoginEvent

/**
 * Main application UI that shows either the {@link MainScreen} or
 * the {@link LoginScreen},
 * depending on whether there is an authenticated user or not. Also note that the UI is using web socket based push.
 */
@SpringUI
@Theme(ValoTheme.THEME_NAME)
@Title('Vaadin Managed Security Demo')
@Push
class SingleSecuredUI extends UI {

    @Autowired
    ApplicationContext context

    @Autowired
    VaadinSecurity security

    @Autowired
    SessionEventBus eventBus

    @Override
    protected void init(VaadinRequest request) {
        // Let's register a custom error handler to make the 'access denied' messages a bit friendlier.
        errorHandler = { ErrorEvent event ->
            if (SecurityExceptionUtils.isAccessDeniedException(event.throwable)) {
                Notification.show("Sorry, you don't have access to do that.")
            } else {
                super.error(event)
            }
        }

        if (security.authenticated) {
            showMainScreen()
        } else {
            showLoginScreen(request.getParameter('goodbye') != null)
        }
    }

    private void showLoginScreen(boolean loggedOut) {
        LoginScreen loginScreen = context.getBean(LoginScreen)
        loginScreen.loggedOut = loggedOut
        content = loginScreen
    }

    private void showMainScreen() {
        content = context.getBean(MainScreen)
    }

    @Override
    void attach() {
        super.attach()
        eventBus.subscribe(this)
    }

    @Override
    void detach() {
        eventBus.unsubscribe(this)
        super.detach()
    }

    @EventBusListenerMethod
    void onLogin(SuccessfulLoginEvent event) {
        if (event.source == this) {
            access { showMainScreen() }
        } else {
            // We cannot inject the Main Screen if the event was fired from another UI,
            // since that UI's scope would be active and the main screen for that UI would be injected.
            // Instead, we just reload the page and let the init(...) method do the work for us.
            page.reload()
        }
    }
}
