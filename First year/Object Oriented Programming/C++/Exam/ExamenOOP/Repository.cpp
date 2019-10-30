#include "Repository.h"

void Repository::store(const Produs& el) {
	elements.push_back(el);
	saveData();
}

bool Repository::find(const Produs& el) {
	for (const Produs& t : elements) {
		if (t == el) {
			return true;
		}
	}
	return false;
}
const vector<Produs>& Repository::getAll() const noexcept {
	return elements;
}
void Repository::loadData() {
	ifstream in(this->readFile);
	string id;
	string name;
	string type;
	string price;
	if (!in.is_open()) {
		cout << "error at load data";
	}
	while (!in.eof()) {
		getline(in, id);
		getline(in, name);
		getline(in, type);
		getline(in, price);
		if (in.eof()) {
			break;
		}
		Produs produs = Produs(stoi(id), name, type, stod(price));
		elements.push_back(produs);
	}
	in.close();
}
void Repository::saveData() {
	ofstream out(this->readFile);
	if (!out.is_open()) {
		cout << "error at save data";
	}
	for (Produs p : elements) {
		out << p.getId() << endl;
		out << p.getName() << endl;
		out << p.getType() << endl;
		out << p.getPrice() << endl;
	}
	out.close();
}