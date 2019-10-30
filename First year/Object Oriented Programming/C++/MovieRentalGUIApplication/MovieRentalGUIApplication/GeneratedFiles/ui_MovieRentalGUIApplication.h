/********************************************************************************
** Form generated from reading UI file 'MovieRentalGUIApplication.ui'
**
** Created by: Qt User Interface Compiler version 5.10.1
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_MOVIERENTALGUIAPPLICATION_H
#define UI_MOVIERENTALGUIAPPLICATION_H

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

class Ui_MovieRentalGUIApplicationClass
{
public:
    QMenuBar *menuBar;
    QToolBar *mainToolBar;
    QWidget *centralWidget;
    QStatusBar *statusBar;

    void setupUi(QMainWindow *MovieRentalGUIApplicationClass)
    {
        if (MovieRentalGUIApplicationClass->objectName().isEmpty())
            MovieRentalGUIApplicationClass->setObjectName(QStringLiteral("MovieRentalGUIApplicationClass"));
        MovieRentalGUIApplicationClass->resize(600, 400);
        menuBar = new QMenuBar(MovieRentalGUIApplicationClass);
        menuBar->setObjectName(QStringLiteral("menuBar"));
        MovieRentalGUIApplicationClass->setMenuBar(menuBar);
        mainToolBar = new QToolBar(MovieRentalGUIApplicationClass);
        mainToolBar->setObjectName(QStringLiteral("mainToolBar"));
        MovieRentalGUIApplicationClass->addToolBar(mainToolBar);
        centralWidget = new QWidget(MovieRentalGUIApplicationClass);
        centralWidget->setObjectName(QStringLiteral("centralWidget"));
        MovieRentalGUIApplicationClass->setCentralWidget(centralWidget);
        statusBar = new QStatusBar(MovieRentalGUIApplicationClass);
        statusBar->setObjectName(QStringLiteral("statusBar"));
        MovieRentalGUIApplicationClass->setStatusBar(statusBar);

        retranslateUi(MovieRentalGUIApplicationClass);

        QMetaObject::connectSlotsByName(MovieRentalGUIApplicationClass);
    } // setupUi

    void retranslateUi(QMainWindow *MovieRentalGUIApplicationClass)
    {
        MovieRentalGUIApplicationClass->setWindowTitle(QApplication::translate("MovieRentalGUIApplicationClass", "MovieRentalGUIApplication", nullptr));
    } // retranslateUi

};

namespace Ui {
    class MovieRentalGUIApplicationClass: public Ui_MovieRentalGUIApplicationClass {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_MOVIERENTALGUIAPPLICATION_H
