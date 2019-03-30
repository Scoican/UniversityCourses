#include "TestDomain.h"
#include "TestRepository.h"
#include "TestService.h"
#include "Console.h"
#include <iostream>



int main() {
	callTestsDomain();
	callTestsRepository();
	callTestsService();
	MovieRepository repository;
	MovieService service{ repository };
	Console console{ service };
	console.run();
	
}