#pragma once
#include "MovieService.h"
#include "WishlistService.h"
#include "ReadOnlyWishlist.h"
#include <qwidget.h>
#include <qboxlayout.h>
#include <qlabel.h>
#include <qlistview.h>
#include <qformlayout.h>
#include <qpushbutton.h>
#include <qlineedit.h>
#include <qmessagebox.h>
#include "ListModel.h"
#include "WishlistGUI.h"
#include "Observer.h"
#include "Observabile.h"

class ConsoleGUI :public QWidget,public Obesevabile
{
	MovieService& movies;
	WishlistService& wishlist;
	vector<WishlistGUI*> wishlistWindows;
	vector<ReadOnlyWishlist*> drawWindows;

	//Views
	QListView* listView;
	ListModel* listModel = new ListModel;

	//Widgets
	QWidget* sortWidget = new QWidget;
	QWidget* filterWidget = new QWidget;

	//Labels
	QLabel *totalDimension = new QLabel;
	QLabel *sortLabel = new QLabel("Sort by:");
	QLabel *filterLabel = new QLabel("Filter by:");
	QLabel *filterYearLabel = new QLabel("Year:");
	QLabel *filterNameLabel = new QLabel("Name:");

	//Buttons
	QPushButton *buttonAdd = new QPushButton("Add");
	QPushButton *buttonDelete = new QPushButton("Delete");
	QPushButton *buttonUpdate = new QPushButton("Update");
	QPushButton *buttonFind = new QPushButton("Find");
	QPushButton *buttonRefresh = new QPushButton("Refresh");
	QPushButton *buttonFilter = new QPushButton("Filter");
	QPushButton *buttonSort = new QPushButton("Sort");
	QPushButton *buttonUndo = new QPushButton("Undo");
	QPushButton *buttonClose = new QPushButton("Close");
	QPushButton *buttonWishlist = new QPushButton("Wishlist");
	QPushButton *buttonSortByName = new QPushButton("Name");
	QPushButton *buttonSortByGenre = new QPushButton("Genre");
	QPushButton *buttonSortByYear = new QPushButton("Year");
	QPushButton *buttonSortByActor = new QPushButton("Actor");
	QPushButton *buttonFilterByYear = new QPushButton("Year");
	QPushButton *buttonFilterByName = new QPushButton("Name");
	QPushButton *readOnlyWishlist = new QPushButton("Draw wishlist");
	QPushButton *buttonAddWishlist = new QPushButton("Add wishlist");
	QPushButton *buttonGenerateWishlist = new QPushButton("Generate wishlist");
	QPushButton *buttonEmpty = new QPushButton("Empty wishlist");

	//Line editors
	QLineEdit* lineEditName = new QLineEdit;
	QLineEdit* lineEditGenre = new QLineEdit;
	QLineEdit* lineEditLaunchYear = new QLineEdit;
	QLineEdit* lineEditLeadingActor = new QLineEdit;
	QLineEdit* lineEditFilterName = new QLineEdit;
	QLineEdit* lineEditFilterYear = new QLineEdit;

	void initGUICmps();
	void initSemnalSlot();//conectez butoane
	void setInitialState();//interfata initiala a ferestrei
	void loadMovieList(const std::vector<Movie>& movies);
	void onSelectionChanged();
	void sortMovies();
	void filterMovies();
	void filterName();
	void filterYear();
	void refreshFunction();
	void addMovie();
	void deleteMovie();
	void updateMovie();
	void findMovie();
	void undoFunction();
	void lineEditNameFunction();
	void lineEditGenreFunction();
	void lineEditYearFunction();
	void lineEditActorFunction();
	void createWishlist();
	void drawWishlist();
	void addWishlist();
	void generateWishlist();
	void emptyWishlist();
public:
	ConsoleGUI(MovieService & movies, WishlistService &wishlist) :movies{ movies }, wishlist{ wishlist } {
		initGUICmps();
		initSemnalSlot();
		setInitialState();
	}
};


