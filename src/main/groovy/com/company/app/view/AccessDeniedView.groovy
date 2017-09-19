package com.company.app.view

import com.vaadin.navigator.View
import com.vaadin.navigator.ViewChangeListener
import com.vaadin.spring.annotation.SpringComponent
import com.vaadin.spring.annotation.UIScope
import com.vaadin.ui.Label
import com.vaadin.ui.VerticalLayout
import com.vaadin.ui.themes.ValoTheme

/**
 * View that is shown when the user attempts to access a restricted view.
 *
 */
@SpringComponent
@UIScope
class AccessDeniedView extends VerticalLayout implements View {

    private Label message

    AccessDeniedView() {
        margin = true
        addComponent(message = new Label())
        message.setSizeUndefined()
        message.addStyleName(ValoTheme.LABEL_FAILURE)
    }

    @Override
    void enter(ViewChangeListener.ViewChangeEvent event) {
        message.value = "You do not have access to this view: ${event.viewName}"
    }
}
