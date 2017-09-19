package com.company.app.view

import com.vaadin.navigator.View
import com.vaadin.navigator.ViewChangeListener
import com.vaadin.ui.Label
import com.vaadin.ui.VerticalLayout
import com.vaadin.ui.themes.ValoTheme

/**
 * View that is shown when the user tries to navigate to a view that does not exist.
 * Please not this view is not Spring-managed; the Navigator will take care of instantiating it when needed.
 */
class ErrorView extends VerticalLayout implements View {

    private Label message

    ErrorView() {
        margin = true
        addComponent(message = new Label())
        message.setSizeUndefined()
        message.addStyleName(ValoTheme.LABEL_FAILURE)
    }

    @Override
    void enter(ViewChangeListener.ViewChangeEvent event) {
        message.value = "No such view: ${event.viewName}"
    }
}
