#pragma once
#include <string>
#include <time.h>
#include <iostream>
using namespace std;

class FileOperations
{
public:
	FileOperations();
	~FileOperations();

	/**
	 * Method that generates a random number in the given range
	 * @param min - Minimum range
	 * @param max - Maximum range
	 * @return Generated number;
	 */
	int randomGenerator(int min, int max);

	/**
	* Method that generates a random digit
	* @return Generated digit;
	*/
	int randomDigit();

	/**
	 * Method that generates a random number with length equal to given size
	 * @param size - Size of number to be generated
	 * @return Generated number with length equal to size;
	 */
	string randomNumberGenerator(int size);

	/**
	 * Method that generates random numbers and writes them in a given file
	 * @param fileName - File in which the numbers will be writen
	 * @param size - How many numbers to be writen
	 * @param min - Minimum size of number length
	 * @param max - Maximum size of number length
	 */
	virtual void fileGenerator(string fileName, int size, int min, int max) = 0;

	/**
	 * Method that compares two given files and checks if the data inside it is the same
	 * @param fileName1 - Given file name
	 * @param fileName2 - Given file name
	 * @return  true    - If the containing data in both files are the same
	 *          false   - Otherwise
	 */
	virtual bool fileComparator(string fileName1, string fileName2) = 0;
};
