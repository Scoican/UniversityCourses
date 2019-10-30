#include<QAbstractTableModel>
#include "Service.h"
#include <vector>
#include "Produs.h"

using namespace std;
class TableModel :public QAbstractTableModel {
	vector<Produs> products;
public:
	TableModel() {}

	int rowCount(const QModelIndex & parent = QModelIndex()) const override {
		return products.size();
	}
	int columnCount(const QModelIndex & parent = QModelIndex()) const override {
		return 5;
	}

	QVariant data(const QModelIndex & index, int role = Qt::DisplayRole) const override {
		if (role == Qt::DisplayRole) {
			Produs p = products[index.row()];
			if (index.column() == 0) {
				return QString::fromStdString(to_string(p.getId()));
			}
			else if (index.column() == 1) {
				return QString::fromStdString(p.getName());
			}
			else if (index.column() == 2) {
				return QString::fromStdString(p.getType());
			}
			else if (index.column() == 3) {
				return QString::fromStdString(to_string(p.getPrice()));
			}
			else if (index.column() == 4) {
				return QString::fromStdString(to_string(getVocale(p.getName())));
			}
			

		}
		return QVariant{};
	}

	int getVocale(string name) const {
		int total = 0;
		for (auto c : name) {
			if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
				total++;
			}
		}
		return total;
	}



	Produs getData(const QModelIndex & index) {
		return products[index.row()];
	}

	void setData(const vector<Produs>& newProducts) {
		this->products = newProducts;
		QModelIndex topLeft = createIndex(0, 0);
		QModelIndex bottomRight = createIndex(rowCount(), columnCount());
		emit dataChanged(topLeft, bottomRight);
		emit layoutChanged();
	}


};