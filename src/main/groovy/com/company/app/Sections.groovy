package com.company.app

import org.springframework.stereotype.Component
import org.vaadin.spring.sidebar.annotation.SideBarSection
import org.vaadin.spring.sidebar.annotation.SideBarSections

/**
 * Component that is only used to declare the sections of the side bar.
 *
 */
@Component
@SideBarSections([
        @SideBarSection(id = Sections.VIEWS, caption = 'Views'),
        @SideBarSection(id = Sections.OPERATIONS, caption = 'Operations')
])
class Sections {
    static final String VIEWS = 'views'
    static final String OPERATIONS = 'operations'
}
