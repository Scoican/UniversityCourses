#pragma once
#include <qwidget.h>
#include <qboxlayout.h>
#include <qlistview.h>
#include <qpushbutton.h>
#include <qlabel.h>
#include <qtableview.h>
#include <qspinbox.h>
#include <qformlayout.h>
#include <qlabel.h>
#include <qmessagebox.h>

#include "WishlistService.h"
#include "MovieService.h"
#include "ListModel.h"

class WishlistGUI:public QWidget,public Observer
{
private:
	MovieService& movies;
	WishlistService& wishlist;

	QListView* listView;
	QListView* movieListView;
	ListModel* wishlistModel = new ListModel;
	ListModel* movieListModel = new ListModel;
	QSpinBox *spinner = new QSpinBox();

	QPushButton *buttonAdd = new QPushButton("Add");
	QPushButton *buttonGenerate = new QPushButton("Generate");
	QPushButton *buttonEmpty = new QPushButton("Empty");
	QPushButton *buttonClose = new QPushButton("Close");

	QLabel *totalDimension = new QLabel;

	void initGUICmps();
	void initSemnalSlot();//conectez butoane
	void setInitialState();//interfata initiala a ferestrei
	void loadWishlistList(const vector<Movie> &movies);
	void loadMovieList(const vector<Movie> &movies);
	void onMovieListSelectionChanged();
	void onWishlistSelectionChanged();
	void generateWishlist();
	void emptyWishlist();
	void addWishlist();
	void update() override;
public:
	WishlistGUI(MovieService & movies, WishlistService &wishlist) :movies{ movies }, wishlist{ wishlist } {
		initGUICmps();
		initSemnalSlot();
		setInitialState();
	}
};

