#include "FileOperations.h"

FileOperations::FileOperations()
{
}

FileOperations::~FileOperations()
{
}

int FileOperations::randomGenerator(int min, int max)
{
	double f = (double)rand() / RAND_MAX;
	return (int)(min + f * (max - min));
}

int FileOperations::randomDigit()
{
	return (int)rand() % 10;
}

string FileOperations::randomNumberGenerator(int size)
{
	string number = "";
	for (int i = 0; i < size; i++) {
		int digit = randomDigit();
		if (i == 0) {
			while (digit == 0) {
				digit = randomDigit();
			}
		}
		number = number + to_string(digit);
	}
	return number;
}