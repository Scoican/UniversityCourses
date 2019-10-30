#pragma once
#include <Service.h>
#include <qboxlayout.h>
#include <qslider.h>
#include <qlineedit.h>
#include <qlabel.h>
#include <qpushbutton.h>
#include <qtableview.h>
#include <qformlayout.h>
#include "TableModel.h"
#include "Observable.h"

class Window:public QWidget, public Observer{
private:
	Service& service;

	QLabel* typeLabel = new QLabel("type");
	string type;
public:
	/*
	Constructor
	*/
	Window(Service& service, string type) :service{ service }, type{ type } {
		initGUI();
		setState();
	}
	/*
	Function that initializez the interface
	*/
	void initGUI();
	/*
	Function that initializez the start state of the interface
	*/
	void setState();
	/*
	Function that updates the content of the window
	*/
	virtual void update();
};

class Interface :public QWidget,public Observable
{
private:
	Service& service;

	vector<Window*> windows;
	QTableView* tableView;
	TableModel* tableModel = new TableModel;

	QLabel* idLabel = new QLabel("id");
	QLabel* nameLabel = new QLabel("name");
	QLabel* typeLabel = new QLabel("type");
	QLabel* priceLabel = new QLabel("price");
	QLabel* priceSliderLabel = new QLabel("Filter");

	QPushButton* addButton = new QPushButton("add");

	QLineEdit* idLine = new QLineEdit;
	QLineEdit* nameLine = new QLineEdit;
	QLineEdit* typeLine = new QLineEdit;
	QLineEdit* priceLine = new QLineEdit;
	QSlider* priceSlider = new QSlider;

public:
	/*
	Constructor
	*/
	Interface(Service& service) :service{ service } {
		initGUI();
		initSignals();
		setState();
		createWindows();
	}
	/*
	Function that initializez the interface
	*/
	void initGUI();
	/*
	Function that initializez the connections
	*/
	void initSignals();
	/*
	Function that initializez the start state of the interface
	*/
	void setState();
	/*
	Function that changes the QLines on a new selection in the table
	*/
	void onSelectionChanged();
	/*
	Function that adds a product in the table
	*/
	void addFunction();
	/*
	Function that filters the elements of the table
	*/
	void filterPrice();
	/*
	Function that creats windows for types
	*/
	void createWindows();
	/*
	Destructor
	*/
	~Interface();
};



