#include "Validator.h"

void MovieValidator::validate(const Movie& m) {
	Vector <string> msgs;
	if (m.getGenre().find_first_of("0123456789") != std::string::npos) {
		msgs.add("Invalid Genre!");
	}
	if (m.getLeadingActor().find_first_of("0123456789") != std::string::npos) {
		msgs.add("Invalid name of Leading actor!");
	}
	if (m.getLaunchYear() < 1800 || m.getLaunchYear() > 2020) {
		msgs.add("Invalid launch year!");
	}
	if (msgs.size() > 0) {
		throw ValidateException(msgs);
	}
}

ostream& operator<<(ostream& out, const ValidateException& ex) {
	for (const auto& mesaj : ex.mesaje) {
		out << mesaj << endl;
	}
	return out;
}

