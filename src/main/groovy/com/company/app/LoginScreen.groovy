package com.company.app

import com.vaadin.event.ShortcutAction
import com.vaadin.spring.annotation.SpringComponent
import com.vaadin.ui.*
import com.vaadin.ui.themes.ValoTheme
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.vaadin.spring.annotation.PrototypeScope
import org.vaadin.spring.events.EventBus.SessionEventBus
import org.vaadin.spring.security.VaadinSecurity
import org.vaadin.spring.security.util.SuccessfulLoginEvent

/**
 * Full-screen UI component that allows the user to login.
 *
 */
@Slf4j
@PrototypeScope
@SpringComponent
class LoginScreen extends CustomComponent {

    private final VaadinSecurity security
    private final SessionEventBus eventBus

    private TextField username

    private PasswordField password

    private Button login

    private Label loginFailedLabel

    private Label loggedOutLabel

    @Autowired
    LoginScreen(VaadinSecurity security, SessionEventBus eventBus) {
        this.security = security
        this.eventBus = eventBus
        initLayout()
    }

    void setLoggedOut(boolean loggedOut) {
        loggedOutLabel.visible = loggedOut
    }

    private void initLayout() {
        FormLayout loginForm = new FormLayout()
        loginForm.setSizeUndefined()
        username = new TextField('Username')
        password = new PasswordField('Password')
        login = new Button('Login')

        loginForm.with {
            addComponent(username)
            addComponent(password)
            addComponent(login)
        }

        login.with {
            addStyleName(ValoTheme.BUTTON_PRIMARY)
            disableOnClick = true
            setClickShortcut(ShortcutAction.KeyCode.ENTER)
            addClickListener({ Button.ClickEvent event ->
                login()
            })
        }

        VerticalLayout loginLayout = new VerticalLayout()
        loginLayout.setSizeUndefined()

        loginFailedLabel = new Label()

        loginFailedLabel.with {
            setSizeUndefined()
            addStyleName(ValoTheme.LABEL_FAILURE)
            visible = true
        }
        loginLayout.addComponent(loginFailedLabel)
        loginLayout.setComponentAlignment(loginFailedLabel, Alignment.BOTTOM_CENTER)

        loggedOutLabel = new Label('Good bye!')

        loggedOutLabel.with {
            setSizeUndefined()
            addStyleName(ValoTheme.LABEL_SUCCESS)
            visible = false
        }

        loginLayout.with {
            addComponent(loggedOutLabel)
            setComponentAlignment(loggedOutLabel, Alignment.BOTTOM_CENTER)
            addComponent(loginForm)
            setComponentAlignment(loginForm, Alignment.TOP_CENTER)
        }

        VerticalLayout rootLayout = new VerticalLayout(loginLayout)

        rootLayout.with {
            setSizeFull()
            setComponentAlignment(loginLayout, Alignment.MIDDLE_CENTER)
        }

        compositionRoot = rootLayout
        setSizeFull()
    }

    private void login() {
        try {
            loggedOutLabel.visible = false
            String pwd = password.value
            password.value = ''
            final Authentication authentication = security.login(username.value, pwd)

            eventBus.publish(this, new SuccessfulLoginEvent(getUI(), authentication))
        } catch (AuthenticationException ex) {
            username.focus()
            username.selectAll()
            loginFailedLabel.value = "Login failed: ${ex.message}"
            loginFailedLabel.visible = true
        } catch (Exception ex) {
            Notification.show('An unexpected error occurred', ex.message, Notification.Type.ERROR_MESSAGE)
            log.error('Unexpected eror while logging in', ex)
        } finally {
            login.enabled = true
        }
    }
}
