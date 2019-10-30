#include "Console.h"

void Console::addUi()
{
	string name;
	string genre;
	int launchYear;
	string leadingActor;
	cout << "Name: ";
	cin >> name;
	cout << "Genre: ";
	cin >> genre;
	cout << "Launch year: ";
	cin >> launchYear;
	cout << "Leading actor: ";
	cin >> leadingActor;
	try {
		service.addMovie(name, genre, launchYear, leadingActor);
		cout << "Movie added with succes!" << endl;
	}
	catch (MovieRepositoryException& msg) {
		cout << msg << endl;
	}
	catch (ValidateException& msg) {
		cout << msg << endl;
	}
}

void Console::removeUi()
{
	string name;
	int launchYear;
	cout << "Name: ";
	cin >> name;
	cout << "Launch year: ";
	cin >> launchYear;
	try {
		service.removeMovie(name, launchYear);
		cout << "Movie removed with succes!" << endl;
	}
	catch (MovieRepositoryException& msg) {
		cout << msg << endl;
	}
	catch (ValidateException& msg) {
		cout << msg << endl;
	}
}

void Console::updateUi()
{
	string name;
	string genre;
	int launchYear;
	string leadingActor;
	cout << "Name: ";
	cin >> name;
	cout << "Genre: ";
	cin >> genre;
	cout << "Launch year: ";
	cin >> launchYear;
	cout << "Leading actor: ";
	cin >> leadingActor;
	try {
		service.updateMovie(name, genre, launchYear, leadingActor);
		cout << "Movie updated with succes!" << endl;
	}
	catch (MovieRepositoryException& msg) {
		cout << msg << endl;
	}
	catch (ValidateException& msg) {
		cout << msg << endl;
	}
}

void Console::findUi()
{
	string name;
	int launchYear;
	cout << "Name: ";
	cin >> name;
	cout << "Launch year: ";
	cin >> launchYear;
	try {
		cout << service.findMovie(name, launchYear);
	}
	catch (MovieRepositoryException& msg) {
		cout << msg << endl;
	}
	catch (ValidateException& msg) {
		cout << msg << endl;
	}
}

void Console::getAllUi()
{
	if (service.getAll().size() == 0) {
		cout << "There are no movies available!" << endl;
	}
	else {
		for (const Movie& m : service.getAll()) {
			cout << m;
		}
	}
}

void Console::filterByYear()
{
	int year;
	cout << "Year: ";
	cin >> year;
	for (const Movie& movie : service.filterByYear(year)) {
		cout << movie;
	}
}

void Console::filterByName()
{
	string name;
	cout << "Name: ";
	cin >> name;
	for (const Movie& movie : service.filterByName(name)) {
		cout << movie;
	}
}

void Console::sortByName()
{
	for (const Movie& movie : service.sortByName()) {
		cout << movie;
	}
}

void Console::sortByGenre()
{
	for (const Movie& movie : service.sortByGenre()) {
		cout << movie;
	}
}

void Console::sortByLaunchYear()
{
	for (const Movie& movie : service.sortByLaunchYear()) {
		cout << movie;
	}
}

void Console::sortByLeadingActor()
{
	for (const Movie& movie : service.sortByLeadingActor()) {
		cout << movie;
	}
}

void Console::addWishlistUi()
{
	cout << "Choose a movie to add to the wishlist from the following:" << endl;
	getAllUi();
	string name;
	int launchYear = 0;
	cout << "Name: ";
	cin >> name;
	cout << "Launch year: ";
	cin >> launchYear;
	try {
		wishlist.addWishlist(name, launchYear);
		cout << "Movie added with succes!" << endl;
	}
	catch (MovieRepositoryException& msg) {
		cout << msg << endl;
	}
	catch (ValidateException& msg) {
		cout << msg << endl;
	}
	catch (WishlistException& msg) {
		cout << msg << endl;
	}
}

void Console::deleteWishlistUi()
{
	wishlist.deleteWishlist();
	cout << "Wishlist emptied!" << endl;
}

void Console::populateWishlistUi()
{
	int nr;
	cout << "Insert a number of movies you wish to add:";
	cin >> nr;
	try {
		wishlist.populateWishlist(nr);
		viewWishlistUi();
	}
	catch (WishlistException& msg) {
		cout << msg << endl;
	}
}

void Console::viewWishlistUi() {
	if (wishlist.getWishlist().size() == 0) {
		cout << "There are no movies available!" << endl;
		return;
	}
	for (Movie m : wishlist.getWishlist()) {
		cout << m;
	}
}

void Console::printMenu()
{
	cout << "------------------------------------" << endl;
	cout << "-          Movie Rental            -" << endl;
	cout << "------------------------------------" << endl;
	cout << "- 1. Add a movie                   -" << endl;
	cout << "- 2. Delete a movie                -" << endl;
	cout << "- 3. Update a movie                -" << endl;
	cout << "- 4. Find a movie                  -" << endl;
	cout << "- 5. View all movies               -" << endl;
	cout << "- 6. Filter by launch year         -" << endl;
	cout << "- 7. Filter by movie title         -" << endl;
	cout << "- 8. Sort by movie title           -" << endl;
	cout << "- 9. Sort by movie genre           -" << endl;
	cout << "- 10. Sort by movie launch year    -" << endl;
	cout << "- 11. Sort by movie leading actor  -" << endl;
	cout << "- 12. Add movie to wish list       -" << endl;
	cout << "- 13. Remove movie from wish list  -" << endl;
	cout << "- 14. Populate wish list           -" << endl;
	cout << "- 15. View wish list               -" << endl;
	cout << "- 16. Undo                         -" << endl;
	cout << "- 0. Exit application              -" << endl;
	cout << "------------------------------------" << endl;
}

void Console::testingData()
{
	service.addMovie("TheAvengers", "Action", 2010, "Iron-Man");
	service.addMovie("TheAvengers:AgeOfUltron", "Action", 2015, "Ultron");
	service.addMovie("InfinityWar", "Action", 2018, "Thanos");
	service.addMovie("Thor:Ragnarok", "Comedy", 2017, "Thor");
	service.addMovie("LaLaLand", "Musical", 2017, "Some talented actress");
	service.addMovie("Mr.Nobody", "Sci-fi", 2005, "Nobody");
	service.addMovie("SomeSci-fiMovie", "Sci-fi", 2017, "Some talented actor");
	service.addMovie("TheComedyMovie", "Comedy", 2009, "A very funny actor");
}

void Console::run()
{
	//testingData();
	printMenu();
	int cmd;
	while (true) {
		cout << ">";
		cin >> cmd;
		try {
			switch (cmd) {
			case 1:
				addUi();
				break;
			case 2:
				removeUi();
				break;
			case 3:
				updateUi();
				break;
			case 4:
				findUi();
				break;
			case 5:
				getAllUi();
				break;
			case 6:
				filterByYear();
				break;
			case 7:
				filterByName();
				break;
			case 8:
				sortByName();
				break;
			case 9:
				sortByGenre();
				break;
			case 10:
				sortByLaunchYear();
				break;
			case 11:
				sortByLeadingActor();
				break;
			case 12:
				addWishlistUi();
				break;
			case 13:
				deleteWishlistUi();
				break;
			case 14:
				populateWishlistUi();
				break;
			case 15:
				viewWishlistUi();
				break;
			case 16:
				service.undo();
				break;
			case 0:
				return;
			default:
				cout << "Please enter a valid command!";
			}
		}
		catch (MovieRepositoryException& msg) {
			cout << msg << endl;
		}
	}
}