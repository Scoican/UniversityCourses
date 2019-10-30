#include "Export.h"
#include <fstream>
#include <string>
#include <vector>
#include "MovieRepositoryException.h"

void exportToCVS(const std::string& fName, const std::vector<Movie>& movies) {
	std::ofstream out(fName, std::ios::trunc);
	if (!out.is_open()) {
		throw MovieRepositoryException("Unable to open file:" + fName);
	}
	for (const auto& movie : movies) {
		out << movie.getName() << ",";
		out << movie.getGenre() << ",";
		out << movie.getLaunchYear() << ",";
		out << movie.getLeadingActor() << std::endl;
	}
	out.close();
}

void exportToHTML(const std::string& fName, const std::vector<Movie>& movies) {
	std::ofstream out(fName, std::ios::trunc);
	if (!out.is_open()) {
		throw MovieRepositoryException("Unable to open file:" + fName);
	}
	out << "<html><body>" << std::endl;
	out << "<table border=\"1\" style=\"width:100 % \">" << std::endl;
	for (const auto& movie : movies) {
		out << "<tr>" << std::endl;
		out << "<td>" << movie.getName() << "</td>" << std::endl;
		out << "<td>" << movie.getGenre() << "</td>" << std::endl;
		out << "<td>" << movie.getLaunchYear() << "</td>" << std::endl;
		out << "<td>" << movie.getLeadingActor() << "</td>" << std::endl;
		out << "</tr>" << std::endl;
	}
	out << "</table>" << std::endl;
	out << "</body></html>" << std::endl;
	out.close();
}