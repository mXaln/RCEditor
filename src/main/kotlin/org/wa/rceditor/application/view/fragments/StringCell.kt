package org.wa.rceditor.application.view.fragments

import javafx.beans.property.StringProperty
import javafx.scene.layout.Priority
import org.wa.rceditor.application.Styles
import tornadofx.*

class StringCell: ListCellFragment<StringProperty>() {
    private val ivm = ItemViewModel(itemProperty = itemProperty)
    private val contributor = ivm.bind { item }

    override val root = hbox {
        addClass(Styles.itemRoot)

        label(contributor) {
            setId(Styles.contentLabel)
            hgrow = Priority.ALWAYS
            useMaxSize = true
            removeWhen { editingProperty }
        }
        textfield(contributor) {
            hgrow = Priority.ALWAYS
            removeWhen { editingProperty.not() }
            whenVisible { requestFocus() }
            action {
                if (text.trim().isNotEmpty()) {
                    ivm.commit()
                    commitEdit(item)
                }
            }
            required()
        }
        button(graphic = Styles.closeIcon()) {
            removeWhen { parent.hoverProperty().not().or(editingProperty) }
            action { cell?.listView?.items?.remove(item) }
        }
    }
}