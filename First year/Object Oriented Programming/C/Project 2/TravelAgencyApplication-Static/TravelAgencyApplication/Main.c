#pragma once
#include "TestPackage.h"
#include "TestRepository.h"
#include "TestService.h"
#include "TestVector.h"
#include "TestValidator.h"
#include "Console.h"
void tests() {
	callTestsPackage();
	callTestsVector();
	callTestsRepository();
	callTestsService();
	callTestsValidator();
}
int main()
{
	tests();
	run();
	return 0;
}