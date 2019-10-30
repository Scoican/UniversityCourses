#pragma once
#include <QAbstractListModel>
#include <vector>
#include <Movie.h>
class ListModel :public QAbstractListModel {
private:
	std::vector<Movie> movies;
public:
	ListModel() {	}
	ListModel(const vector<Movie>& movies) :movies{ movies } {
	}

	int rowCount(const QModelIndex &parent = QModelIndex()) const override {
		return movies.size();
	}

	QVariant data(const QModelIndex & index, int role = Qt::DisplayRole) const override {
		if (role == Qt::DisplayRole) {
			auto m = movies[index.row()].getName();
			return QString::fromStdString(m);
		}
		return QVariant{};
	}

	QVariant dataYear(const QModelIndex & index, int role = Qt::DisplayRole) const {
		if (role == Qt::DisplayRole) {
			auto m = movies[index.row()].getLaunchYear();
			return QString::number(m);
		}

		return QVariant{};
	}

	Movie getMovies(const QModelIndex & index) {
		return movies[index.row()];
	}

	void setMovies(const vector<Movie>& movies) {
		this->movies = movies;
		QModelIndex topLeft = createIndex(0, 0);
		QModelIndex bottomRight = createIndex(rowCount(), 0);
		emit dataChanged(topLeft, bottomRight);
		emit layoutChanged();
	}
};



