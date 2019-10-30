#include "WishlistGUI.h"

void WishlistGUI::initGUICmps()
{
	QHBoxLayout* ly = new QHBoxLayout;
	setLayout(ly);

	QWidget *wishlistWidget = new QWidget;
	QVBoxLayout *wishlistLayout = new QVBoxLayout;
	wishlistWidget->setLayout(wishlistLayout);
	wishlistWidget->setFixedWidth(200);

	listView = new QListView;
	listView->setUniformItemSizes(true);
	listView->setModel(movieListModel);

	wishlistLayout->addWidget(new QLabel("Movies: "));
	wishlistLayout->addWidget(listView);

	ly->addWidget(wishlistWidget);

	QWidget *buttonsWidget = new QWidget;
	QVBoxLayout *buttonsLayout = new QVBoxLayout;
	buttonsWidget->setLayout(buttonsLayout);
	buttonsLayout->addWidget(buttonGenerate);
	spinner->setMaximum(movies.getAll().size());
	buttonsLayout->addWidget(spinner);
	buttonsLayout->addWidget(buttonAdd);
	buttonsLayout->addWidget(buttonEmpty);
	buttonsLayout->addWidget(buttonClose);
	ly->addWidget(buttonsWidget);

	QWidget *movieListWidget = new QWidget;
	QVBoxLayout *movieListLayout = new QVBoxLayout;
	movieListWidget->setLayout(movieListLayout);
	movieListLayout->addWidget(new QLabel("Wishlist: "));

	movieListView = new QListView;
	movieListView->setModel(wishlistModel);
	movieListLayout->addWidget(movieListView);
	ly->addWidget(movieListWidget);

	//total cos
	QWidget* widTotalMovies = new QWidget;
	QFormLayout *total = new QFormLayout;
	widTotalMovies->setLayout(total);
	int dim = wishlist.getWishlist().size();
	QLabel *totalText = new QLabel("Total movies:");
	totalDimension->setNum(dim);
	total->addRow(totalText, totalDimension);
	movieListLayout->addWidget(widTotalMovies);
}

void WishlistGUI::initSemnalSlot()
{
	//Add movie to wishlist
	QObject::connect(buttonAdd, &QPushButton::clicked, this, &WishlistGUI::addWishlist);
	
	//Movie list selection
	QObject::connect(listView->selectionModel(), &QItemSelectionModel::selectionChanged, this, &WishlistGUI::onMovieListSelectionChanged);

	//Movie list selection
	QObject::connect(movieListView->selectionModel(), &QItemSelectionModel::selectionChanged, this, &WishlistGUI::onWishlistSelectionChanged);

	//Empty button
	QObject::connect(buttonEmpty, &QPushButton::clicked, this, &WishlistGUI::emptyWishlist);

	//Close button
	QObject::connect(buttonClose, SIGNAL(clicked()), this, SLOT(close()));

	//Generate button
	QObject::connect(buttonGenerate, &QPushButton::clicked, this, &WishlistGUI::generateWishlist);

}

void WishlistGUI::setInitialState()
{
	loadMovieList(movies.getAll());
	loadWishlistList(wishlist.getWishlist());
	buttonAdd->setEnabled(false);
}

void WishlistGUI::loadWishlistList(const vector<Movie>& movies)
{
	wishlistModel->setMovies(movies);
}

void WishlistGUI::loadMovieList(const vector<Movie>& movies)
{
	movieListModel->setMovies(movies);
}

void WishlistGUI::onMovieListSelectionChanged()
{
	auto sel = listView->selectionModel()->selectedIndexes();
	if (sel.isEmpty()) {
		buttonAdd->setEnabled(false);
		return;
	}
	buttonAdd->setEnabled(true);
}

void WishlistGUI::onWishlistSelectionChanged()
{
	
}

void WishlistGUI::generateWishlist()
{
	try {
		wishlist.populateWishlist(spinner->value());
		totalDimension->setNum((int)wishlist.getWishlist().size());
		loadWishlistList(wishlist.getWishlist());
		wishlist.notifyObsss();
	}
	catch (WishlistException &ex) {}
}

void WishlistGUI::emptyWishlist()
{
	wishlist.deleteWishlist();
	totalDimension->setNum((int)wishlist.getWishlist().size());
	loadWishlistList(wishlist.getWishlist());
	wishlist.notifyObsss();
}

void WishlistGUI::addWishlist()
{
	auto sel = listView->selectionModel()->selectedIndexes();
	auto firstSel = sel.at(0);
	Movie movie = movies.findMovie(movieListModel->data(firstSel).toString().toStdString(),movieListModel->dataYear(firstSel).toInt());
	try {
		wishlist.addWishlist(movie.getName(), movie.getLaunchYear());
	}
	catch (WishlistException &ex) {
		QMessageBox::warning(this, "Warning!", QString::fromStdString(ex.error));
	}
	int dim = wishlist.getWishlist().size();
	totalDimension->setNum(dim);
	loadWishlistList(wishlist.getWishlist());
	wishlist.notifyObsss();
}

void WishlistGUI::update()
{
	vector<Movie> movieList;
	for (auto &m : wishlist.getWishlist()) {
		try {
			m = movies.findMovie(m.getName(),m.getLaunchYear());
			movieList.push_back(m);
		}
		catch (WishlistException &ex) {}
	}
	int dim = wishlist.getWishlist().size();
	totalDimension->setNum(dim);
	loadWishlistList(movieList);
}
