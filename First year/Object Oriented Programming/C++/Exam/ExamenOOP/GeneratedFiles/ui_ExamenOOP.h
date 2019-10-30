/********************************************************************************
** Form generated from reading UI file 'ExamenOOP.ui'
**
** Created by: Qt User Interface Compiler version 5.10.1
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_EXAMENOOP_H
#define UI_EXAMENOOP_H

#include <QtCore/QVariant>
#include <QtWidgets/QAction>
#include <QtWidgets/QApplication>
#include <QtWidgets/QButtonGroup>
#include <QtWidgets/QHeaderView>
#include <QtWidgets/QMainWindow>
#include <QtWidgets/QMenuBar>
#include <QtWidgets/QStatusBar>
#include <QtWidgets/QToolBar>
#include <QtWidgets/QWidget>

QT_BEGIN_NAMESPACE

class Ui_ExamenOOPClass
{
public:
    QMenuBar *menuBar;
    QToolBar *mainToolBar;
    QWidget *centralWidget;
    QStatusBar *statusBar;

    void setupUi(QMainWindow *ExamenOOPClass)
    {
        if (ExamenOOPClass->objectName().isEmpty())
            ExamenOOPClass->setObjectName(QStringLiteral("ExamenOOPClass"));
        ExamenOOPClass->resize(600, 400);
        menuBar = new QMenuBar(ExamenOOPClass);
        menuBar->setObjectName(QStringLiteral("menuBar"));
        ExamenOOPClass->setMenuBar(menuBar);
        mainToolBar = new QToolBar(ExamenOOPClass);
        mainToolBar->setObjectName(QStringLiteral("mainToolBar"));
        ExamenOOPClass->addToolBar(mainToolBar);
        centralWidget = new QWidget(ExamenOOPClass);
        centralWidget->setObjectName(QStringLiteral("centralWidget"));
        ExamenOOPClass->setCentralWidget(centralWidget);
        statusBar = new QStatusBar(ExamenOOPClass);
        statusBar->setObjectName(QStringLiteral("statusBar"));
        ExamenOOPClass->setStatusBar(statusBar);

        retranslateUi(ExamenOOPClass);

        QMetaObject::connectSlotsByName(ExamenOOPClass);
    } // setupUi

    void retranslateUi(QMainWindow *ExamenOOPClass)
    {
        ExamenOOPClass->setWindowTitle(QApplication::translate("ExamenOOPClass", "ExamenOOP", nullptr));
    } // retranslateUi

};

namespace Ui {
    class ExamenOOPClass: public Ui_ExamenOOPClass {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_EXAMENOOP_H
