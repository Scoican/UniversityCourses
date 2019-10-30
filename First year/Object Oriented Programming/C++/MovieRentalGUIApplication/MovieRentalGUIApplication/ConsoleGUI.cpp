#include "ConsoleGUI.h"




void ConsoleGUI::initGUICmps()
{
	QHBoxLayout* hbLayout = new QHBoxLayout;
	setLayout(hbLayout);

	//right
	QWidget* rightWidget = new QWidget;
	QVBoxLayout* rightVBLayout = new QVBoxLayout;
	rightWidget->setLayout(rightVBLayout);

	//Movie list
	QWidget* movieListWidget = new QWidget;
	QVBoxLayout* movieListVBLayout = new QVBoxLayout;
	movieListWidget->setLayout(movieListVBLayout);
	movieListVBLayout->addWidget(new QLabel("Movies"));

	listView = new QListView;
	listView->setUniformItemSizes(true);
	listView->setModel(listModel);
	movieListVBLayout->addWidget(listView);

	//Show total movies
	QWidget* widTotalMovies = new QWidget;
	QFormLayout *total = new QFormLayout;
	widTotalMovies->setLayout(total);
	int dim = movies.getAll().size();
	QLabel *totalText = new QLabel("Total movies:");
	totalDimension->setNum(dim);
	total->addRow(totalText, totalDimension);
	total->setFormAlignment(Qt::AlignLeft);
	movieListVBLayout->addWidget(widTotalMovies);
	rightVBLayout->addWidget(movieListWidget);

	//Button setup
	rightVBLayout->addWidget(new QLabel("Operations"));
	QHBoxLayout* buttonsHbLayout = new QHBoxLayout;

	QWidget* buttonsWidget = new QWidget;
	buttonsWidget->setLayout(buttonsHbLayout);

	buttonsHbLayout->addWidget(buttonRefresh);
	buttonsWidget->setLayout(buttonsHbLayout);
	buttonsHbLayout->addWidget(buttonSort);
	buttonsWidget->setLayout(buttonsHbLayout);
	buttonsHbLayout->addWidget(buttonFilter);
	buttonsWidget->setLayout(buttonsHbLayout);
	buttonsHbLayout->addWidget(readOnlyWishlist);
	buttonsWidget->setLayout(buttonsHbLayout);

	rightVBLayout->addWidget(buttonsWidget);

	//Wishlist
	rightVBLayout->addWidget(new QLabel("Movie wishlist"));
	QHBoxLayout *wishlistHBLayout = new QHBoxLayout;

	QWidget* wishlistWidget = new QWidget;
	wishlistWidget->setLayout(wishlistHBLayout);

	wishlistHBLayout->addWidget(buttonWishlist);
	wishlistWidget->setLayout(wishlistHBLayout);
	wishlistHBLayout->addWidget(buttonAddWishlist);
	wishlistWidget->setLayout(wishlistHBLayout);
	wishlistHBLayout->addWidget(buttonGenerateWishlist);
	wishlistWidget->setLayout(wishlistHBLayout);
	wishlistHBLayout->addWidget(buttonEmpty);
	wishlistWidget->setLayout(wishlistHBLayout);

	rightVBLayout->addWidget(wishlistWidget);

	//Left
	QWidget* leftWidget = new QWidget;
	QVBoxLayout* leftVBLayout = new QVBoxLayout;
	leftWidget->setLayout(leftVBLayout);
	QLabel* dataLabel = new QLabel("Movie manager app");
	//set font
	QFont font = dataLabel->font();
	font.setPointSize(20);
	font.setBold(true);
	dataLabel->setFont(font);
	leftVBLayout->addWidget(dataLabel);

	//Data
	QWidget *dataWidget = new QWidget;
	QFormLayout *dataForm = new QFormLayout;
	dataWidget->setLayout(dataForm);
	dataForm->addRow(new QLabel("Name:"), lineEditName);
	dataForm->addRow(new QLabel("Genre:"), lineEditGenre);
	dataForm->addRow(new QLabel("Launch year:"), lineEditLaunchYear);
	dataForm->addRow(new QLabel("Leading actor:"), lineEditLeadingActor);
	leftVBLayout->addWidget(dataWidget);

	//butoane optiuni
	dataWidget->setLayout(leftVBLayout);
	leftVBLayout->addWidget(buttonAdd);
	dataWidget->setLayout(leftVBLayout);
	leftVBLayout->addWidget(buttonDelete);
	dataWidget->setLayout(leftVBLayout);
	leftVBLayout->addWidget(buttonUpdate);
	dataWidget->setLayout(leftVBLayout);
	leftVBLayout->addWidget(buttonFind);

	//undo - close
	QWidget *undoWidget = new QWidget;
	QHBoxLayout* undoHBLayout = new QHBoxLayout;
	undoWidget->setLayout(undoHBLayout);
	undoHBLayout->addWidget(buttonUndo);
	undoHBLayout->addWidget(buttonClose);
	leftVBLayout->addWidget(undoWidget);

	//Sort
	QHBoxLayout *sortHBLayout = new QHBoxLayout;

	sortWidget->setLayout(sortHBLayout);

	sortHBLayout->addWidget(buttonSortByName);
	sortWidget->setLayout(sortHBLayout);
	sortHBLayout->addWidget(buttonSortByGenre);
	sortWidget->setLayout(sortHBLayout);
	sortHBLayout->addWidget(buttonSortByYear);
	sortWidget->setLayout(sortHBLayout);
	sortHBLayout->addWidget(buttonSortByActor);
	sortWidget->setLayout(sortHBLayout);

	leftVBLayout->addWidget(sortLabel);
	leftVBLayout->addWidget(sortWidget);

	sortLabel->hide();
	sortWidget->hide();

	//Filter
	QFormLayout *filterFormLayout = new QFormLayout;

	filterWidget->setLayout(filterFormLayout);

	filterFormLayout->addRow(filterYearLabel, lineEditFilterYear);
	filterFormLayout->addRow(filterNameLabel, lineEditFilterName);

	leftVBLayout->addWidget(filterLabel);
	leftVBLayout->addWidget(filterWidget);

	filterLabel->hide();
	filterWidget->hide();

	//Construction
	hbLayout->addWidget(rightWidget);
	hbLayout->addWidget(leftWidget);
}

void ConsoleGUI::initSemnalSlot()
{
	//Item selection on list
	QObject::connect(listView->selectionModel(), &QItemSelectionModel::selectionChanged, this, &ConsoleGUI::onSelectionChanged);

	//Close app
	QObject::connect(buttonClose, SIGNAL(clicked()), this, SLOT(close()));

	//Show sort menu
	QObject::connect(buttonSort, &QPushButton::clicked, this, &ConsoleGUI::sortMovies);

	//Sorting options
	QObject::connect(buttonSortByName, &QPushButton::clicked, [this]() {
		loadMovieList(movies.sortByName());
	});
	QObject::connect(buttonSortByGenre, &QPushButton::clicked, [this]() {
		loadMovieList(movies.sortByGenre());
	});
	QObject::connect(buttonSortByYear, &QPushButton::clicked, [this]() {
		loadMovieList(movies.sortByLaunchYear());
	});
	QObject::connect(buttonSortByActor, &QPushButton::clicked, [this]() {
		loadMovieList(movies.sortByLeadingActor());
	});

	//Show filter menu
	QObject::connect(buttonFilter, &QPushButton::clicked, this, &ConsoleGUI::filterMovies);

	//Filter options
	QObject::connect(lineEditFilterName, &QLineEdit::textEdited, this, &ConsoleGUI::filterName);
	QObject::connect(lineEditFilterYear, &QLineEdit::textEdited, this, &ConsoleGUI::filterYear);

	//Refresh
	QObject::connect(buttonRefresh, &QPushButton::clicked, this, &ConsoleGUI::refreshFunction);

	//Add movie
	QObject::connect(buttonAdd, &QPushButton::clicked, this, &ConsoleGUI::addMovie);

	//Delete movie
	QObject::connect(buttonDelete, &QPushButton::clicked, this, &ConsoleGUI::deleteMovie);

	//Update movie
	QObject::connect(buttonUpdate, &QPushButton::clicked, this, &ConsoleGUI::updateMovie);

	//Find movie
	QObject::connect(buttonFind, &QPushButton::clicked, this, &ConsoleGUI::findMovie);

	//Undo
	QObject::connect(buttonUndo, &QPushButton::clicked, this, &ConsoleGUI::undoFunction);

	//LineEdits
	QObject::connect(lineEditName, &QLineEdit::textEdited, this, &ConsoleGUI::lineEditNameFunction);
	QObject::connect(lineEditGenre, &QLineEdit::textEdited, this, &ConsoleGUI::lineEditGenreFunction);
	QObject::connect(lineEditLaunchYear, &QLineEdit::textEdited, this, &ConsoleGUI::lineEditYearFunction);
	QObject::connect(lineEditLeadingActor, &QLineEdit::textEdited, this, &ConsoleGUI::lineEditActorFunction);

	//Wishlist
	QObject::connect(buttonWishlist, &QPushButton::clicked, this, &ConsoleGUI::createWishlist);
	QObject::connect(buttonAddWishlist, &QPushButton::clicked, this, &ConsoleGUI::addWishlist);
	QObject::connect(buttonGenerateWishlist, &QPushButton::clicked, this, &ConsoleGUI::generateWishlist);
	QObject::connect(buttonEmpty, &QPushButton::clicked, this, &ConsoleGUI::emptyWishlist);
	QObject::connect(readOnlyWishlist, &QPushButton::clicked, this, &ConsoleGUI::drawWishlist);
}

void ConsoleGUI::setInitialState()
{
	loadMovieList(movies.getAll());
	buttonDelete->setEnabled(false);
	buttonDelete->setToolTip("Delete a movie with the same name and launch year");
	buttonAdd->setEnabled(false);
	buttonAdd->setToolTip("Add a new movie");
	buttonUpdate->setEnabled(false);
	buttonUpdate->setToolTip("Update movie data");
	buttonFind->setEnabled(false);
	buttonFind->setToolTip("Find a movie with the same name and launch year");
	buttonUndo->setEnabled(false);
	buttonUndo->setToolTip("Undo last operation");
}

void ConsoleGUI::drawWishlist()
{
	ReadOnlyWishlist* readOnly = new ReadOnlyWishlist(wishlist);
	drawWindows.push_back(readOnly);
	wishlist.addObs(readOnly);
	movies.addObs(readOnly);
	drawWindows.back()->exec();

	notifyObsss();
}

void ConsoleGUI::loadMovieList(const std::vector<Movie>& movies)
{
	listModel->setMovies(movies);
}

void ConsoleGUI::onSelectionChanged()
{
	auto sel = listView->selectionModel()->selectedIndexes();
	if (sel.isEmpty()) {
		lineEditName->setText("");
		lineEditGenre->setText("");
		lineEditLaunchYear->setText("");
		lineEditLeadingActor->setText("");
		buttonDelete->setEnabled(false);
		buttonUpdate->setEnabled(false);
		lineEditName->setEnabled(true);
		buttonFind->setEnabled(true);
		return;
	}

	this->buttonAdd->setEnabled(false);
	this->buttonFind->setEnabled(false);
	this->buttonDelete->setEnabled(true);
	this->buttonUpdate->setEnabled(false);

	auto firstSel = sel.at(0);
	Movie m = listModel->getMovies(firstSel);
	lineEditName->setText(QString::fromStdString(m.getName()));
	lineEditName->setEnabled(false);
	lineEditGenre->setText(QString::fromStdString(m.getGenre()));
	lineEditLaunchYear->setText(QString::number(m.getLaunchYear()));
	lineEditLeadingActor->setText(QString::fromStdString(m.getLeadingActor()));
}

void ConsoleGUI::sortMovies()
{
	sortWidget->setVisible(!sortWidget->isVisible());
	sortLabel->setVisible(sortWidget->isVisible());
	filterWidget->setVisible(false);
	filterLabel->setVisible(false);
}

void ConsoleGUI::filterMovies()
{
	filterWidget->setVisible(!filterWidget->isVisible());
	filterLabel->setVisible(filterWidget->isVisible());
	sortWidget->setVisible(false);
	sortLabel->setVisible(false);
}

void ConsoleGUI::filterName()
{
	string caract = lineEditFilterName->text().toStdString();
	if (caract.size() > 0)
	{
		lineEditFilterYear->setEnabled(false);
	}
	else {
		lineEditFilterYear->setEnabled(true);
	}
	lineEditFilterName->setEnabled(true);
	try {
		listModel->setMovies(movies.filterByName(caract));
	}
	catch (MovieRepositoryException &ex) {}
	catch (ValidateException &ex) {}
}

void ConsoleGUI::filterYear()
{
	string caract = lineEditFilterYear->text().toStdString();
	if (caract.size() > 0)
	{
		lineEditFilterName->setEnabled(false);
	}
	else {
		lineEditFilterName->setEnabled(true);
	}
	lineEditFilterYear->setEnabled(true);
	try {
		listModel->setMovies(movies.filterByYear(stoi(caract)));
	}
	catch (MovieRepositoryException &ex) {}
	catch(invalid_argument &ex) {}
	catch (ValidateException &ex) {}
}

void ConsoleGUI::refreshFunction()
{
	loadMovieList(movies.getAll());
	lineEditName->setText("");
	lineEditGenre->setText("");
	lineEditLaunchYear->setText("");
	lineEditLeadingActor->setText("");
	lineEditName->setEnabled(true);
	buttonDelete->setEnabled(false);
	buttonAdd->setEnabled(false);
	buttonUpdate->setEnabled(false);
	buttonFind->setEnabled(false);
	buttonUndo->setEnabled(false);
}

void ConsoleGUI::addMovie()
{
	try {
		movies.addMovie(lineEditName->text().toStdString(), lineEditGenre->text().toStdString(), lineEditLaunchYear->text().toInt(), lineEditLeadingActor->text().toStdString());
		int dim = movies.getAll().size();
		totalDimension->setNum(dim);
		loadMovieList(movies.getAll());
		buttonUndo->setEnabled(true);
	}
	catch (MovieRepositoryException &ex) {
		QMessageBox::warning(this, "Warning!", QString::fromStdString(ex.msg));
	}
	catch (ValidateException &ex) {
		QMessageBox::warning(this, "Warning!", QString::fromStdString(ex.getMsg()));
	}
}

void ConsoleGUI::deleteMovie()
{
	try {
		movies.removeMovie(lineEditName->text().toStdString(),lineEditLaunchYear->text().toInt());
		int dim = movies.getAll().size();
		totalDimension->setNum(dim);
		loadMovieList(movies.getAll());
		buttonUndo->setEnabled(true);
	}
	catch (MovieRepositoryException &ex) {
		QMessageBox::warning(this, "Warning!", QString::fromStdString(ex.msg));
	}
	catch(ValidateException &ex) {
		QMessageBox::warning(this, "Warning!", QString::fromStdString(ex.getMsg()));
	}
}

void ConsoleGUI::updateMovie()
{
	try {
		movies.updateMovie(lineEditName->text().toStdString(), lineEditGenre->text().toStdString(), lineEditLaunchYear->text().toInt(), lineEditLeadingActor->text().toStdString());
		int dim = movies.getAll().size();
		totalDimension->setNum(dim);
		loadMovieList(movies.getAll());
		buttonUndo->setEnabled(true);
	}
	catch (MovieRepositoryException &ex) {
		QMessageBox::warning(this, "Warning!", QString::fromStdString(ex.msg));
	}
	catch(ValidateException &ex) {
		QMessageBox::warning(this, "Warning!", QString::fromStdString(ex.getMsg()));
	}
}

void ConsoleGUI::findMovie()
{
	string movieName = lineEditName->text().toStdString();
	int movieYear = lineEditLaunchYear->text().toInt();
	try {
		Movie movie = movies.findMovie(movieName,movieYear);
		lineEditGenre->setText(QString::fromStdString(movie.getGenre()));
		lineEditLeadingActor->setText(QString::fromStdString(movie.getLeadingActor()));
	}
	catch (MovieRepositoryException &ex) {
		QMessageBox::warning(this, "Warning!", QString::fromStdString(ex.msg));
	}
	catch (ValidateException &ex) {
		QMessageBox::warning(this, "Warning!", QString::fromStdString(ex.getMsg()));
	}
}

void ConsoleGUI::undoFunction()
{
	try {
		movies.undo();
		loadMovieList(movies.getAll());
	}
	catch (MovieRepositoryException&ex) {
		buttonUndo->setEnabled(false);
	}
}

void ConsoleGUI::lineEditNameFunction()
{
	string text_input = lineEditName->text().toStdString();
	if (text_input.size() == 0) {
		this->buttonAdd->setEnabled(false);
		this->buttonDelete->setEnabled(false);
		this->buttonFind->setEnabled(false);
	}		 
	else {	 
		this->buttonAdd->setEnabled(true);
		this->buttonFind->setEnabled(true);
	}
}

void ConsoleGUI::lineEditGenreFunction()
{
	buttonUpdate->setEnabled(true);
	buttonUndo->setEnabled(true);
}

void ConsoleGUI::lineEditYearFunction()
{
	buttonUpdate->setEnabled(true);
	buttonUndo->setEnabled(true);
}

void ConsoleGUI::lineEditActorFunction()
{
	buttonUpdate->setEnabled(true);
	buttonUndo->setEnabled(true);
}

void ConsoleGUI::createWishlist()
{
	WishlistGUI* wishlistGui = new WishlistGUI{ movies,wishlist };
	wishlistWindows.push_back(wishlistGui);
	wishlist.addObs(wishlistGui);
	movies.addObs(wishlistGui);
	wishlistWindows.back()->show();
}

void ConsoleGUI::addWishlist()
{
	auto sel = listView->selectionModel()->selectedIndexes();
	auto firstSel = sel.at(0);
	Movie m = movies.findMovie(listModel->data(firstSel).toString().toStdString(), listModel->dataYear(firstSel).toInt());
	wishlist.addWishlist(m.getName(),m.getLaunchYear());
	wishlist.notifyObsss();
}

void ConsoleGUI::generateWishlist()
{
	try {
		wishlist.populateWishlist(rand() % (movies.getAll().size()));
		wishlist.notifyObsss();
		buttonEmpty->setEnabled(true);
	}
	catch (MovieRepositoryException &ex) {}
	catch (WishlistException &ex) {}
}

void ConsoleGUI::emptyWishlist()
{
	wishlist.deleteWishlist();
	wishlist.notifyObsss();
}
