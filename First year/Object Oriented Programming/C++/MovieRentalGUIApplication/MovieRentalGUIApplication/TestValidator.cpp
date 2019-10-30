#include "TestValidator.h"

void callTestsValidator()
{
	MovieValidator validator;
	Movie movie = Movie("asd", "asd1", 1700, "asd1");
	try {
		validator.validate(movie);
	}
	catch (ValidateException& msg) {
		assert(msg.getMsg() == "Invalid Genre!Invalid name of Leading actor!Invalid launch year!");
	}
}