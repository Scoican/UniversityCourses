#include "TestDomain.h"
#include "TestRepository.h"
#include "TestService.h"
#include "TestValidator.h"
#include "TestMyList.h"
#include "Console.h"
#include "MyList.h"
#include <iostream>



int main() {
	callTestsDomain();
	callTestsMyList();
	callTestsRepository();
	callTestsService();
	callTestsValidator();
	MovieRepository repository;
	MovieValidator validator;
	MovieService service{ repository,validator };
	Console console{ service };
	console.run();
}