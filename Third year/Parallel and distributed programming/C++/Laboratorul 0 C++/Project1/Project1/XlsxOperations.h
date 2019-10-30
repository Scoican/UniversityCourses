#pragma once
#include "FileOperations.h"

class XlsxOperations : public FileOperations
{
public:
	/**
	 * Method that generates random numbers and writes them in a given file
	 * @param fileName - File in which the numbers will be writen
	 * @param size - How many numbers to be writen
	 * @param min - Minimum size of number length
	 * @param max - Maximum size of number length
	 */
	void fileGenerator(string fileName, int size, int min, int max);
	/**
	 * Method that compares two given files and checks if the data inside it is the same
	 * @param fileName1 - Given file name
	 * @param fileName2 - Given file name
	 * @return  true    - If the containing data in both files are the same
	 *          false   - Otherwise
	 */
	bool fileComparator(string fileName1, string fileName2);

	/*
	* Constructor
	*/
	XlsxOperations();

	/*
	* Destructor
	*/
	~XlsxOperations();
};
