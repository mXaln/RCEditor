package org.wa.rceditor.application.view.fragments

import com.jfoenix.controls.JFXButton
import com.jfoenix.controls.JFXDialog
import com.jfoenix.controls.JFXDialogLayout
import javafx.event.EventHandler
import javafx.geometry.Pos
import javafx.scene.layout.Priority
import javafx.scene.text.TextAlignment
import tornadofx.*
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent


class MessageDialog(type: MessageDialog.TYPE, message: String): JFXDialog() {
    init {
        val title = if (type == MessageDialog.TYPE.SUCCESS) "Success" else "An error occurred"
        val okButton = JFXButton("Okay")
        val tabKeyEventHandler = EventHandler<KeyEvent> { key ->
            if (key.code === KeyCode.TAB) key.consume()
        }

        content = JFXDialogLayout().apply {
            setHeading(label(title))
            setBody(vbox {
                prefWidth = 400.0
                minHeight = 100.0

                vboxConstraints {
                    padding = insets(10.0)
                    alignment = Pos.CENTER
                }

                label(message) {
                    vgrow = Priority.ALWAYS
                    isWrapText = true
                    textAlignment = TextAlignment.CENTER
                    maxHeight = Double.MAX_VALUE
                    style {
                        fontSize = 16.px
                        textFill = if (type == MessageDialog.TYPE.SUCCESS) c("#05CC0E") else c("#f00")
                    }
                    vboxConstraints {
                        marginBottom = 10.0
                    }
                }
            })
            setActions(okButton.apply {
                action { close() }
            })
        }

        setOnDialogOpened {
            okButton.requestFocus()
            scene.addEventHandler(KeyEvent.KEY_PRESSED, tabKeyEventHandler)
        }

        setOnDialogClosed {
            scene.removeEventHandler(KeyEvent.KEY_PRESSED, tabKeyEventHandler)
        }

        transitionType = JFXDialog.DialogTransition.CENTER
        isOverlayClose = true
    }

    enum class TYPE { SUCCESS, ERROR }
}