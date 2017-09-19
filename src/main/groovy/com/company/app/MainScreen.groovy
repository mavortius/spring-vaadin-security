package com.company.app

import com.company.app.view.AccessDeniedView
import com.company.app.view.ErrorView
import com.vaadin.navigator.Navigator
import com.vaadin.spring.annotation.SpringComponent
import com.vaadin.spring.annotation.UIScope
import com.vaadin.spring.navigator.SpringViewProvider
import com.vaadin.ui.CssLayout
import com.vaadin.ui.CustomComponent
import com.vaadin.ui.HorizontalLayout
import com.vaadin.ui.UI
import org.springframework.beans.factory.annotation.Autowired
import org.vaadin.spring.security.VaadinSecurity
import org.vaadin.spring.sidebar.components.ValoSideBar
import org.vaadin.spring.sidebar.security.VaadinSecurityItemFilter

/**
 * Full-screen UI component that allows the user to navigate between views, and log out.
 *
 */
@UIScope
@SpringComponent
class MainScreen extends CustomComponent {

    @Autowired
    MainScreen(final VaadinSecurity security, SpringViewProvider viewProvider, ValoSideBar sideBar) {
        HorizontalLayout layout = new HorizontalLayout()
        layout.setSizeFull()
        compositionRoot = layout
        setSizeFull()

        // By adding a security item filter, only views that are accessible to the user will show up in the side bar.
        sideBar.itemFilter = new VaadinSecurityItemFilter(security)
        layout.addComponent(sideBar)

        CssLayout viewContainer = new CssLayout()
        viewContainer.setSizeFull()
        layout.addComponent(viewContainer)
        layout.setExpandRatio(viewContainer, 1f)

        Navigator navigator = new Navigator(UI.current, viewContainer)
        // Without an AccessDeniedView, the view provider would act like the restricted views did not exist at all.
        viewProvider.accessDeniedViewClass = AccessDeniedView
        navigator.addProvider(viewProvider)
        navigator.errorView = ErrorView
        navigator.navigateTo(navigator.state)
    }
}
