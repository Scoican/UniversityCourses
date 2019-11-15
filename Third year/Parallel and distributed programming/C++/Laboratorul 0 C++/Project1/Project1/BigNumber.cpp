#include "BigNumber.h"
#include <thread>
#include <iostream>
#include <mutex>
#include <condition_variable>
#include "TxtOperations.h"

BigNumber::BigNumber()
{
	digits = vector<int>();
}

BigNumber::BigNumber(vector<int> digits)
{
	this->digits = digits;
}

BigNumber::BigNumber(int size)
{
	digits = vector<int>(size);
}

BigNumber::BigNumber(const BigNumber & otherNumber)
{
	digits = otherNumber.digits;
}

BigNumber::~BigNumber()
{
}

vector<int> BigNumber::getDigits()
{
	return this->digits;
}

int BigNumber::getDigit(int index)
{
	return this->digits.at(index);
}

int BigNumber::getSize()
{
	return this->digits.size();
}

void BigNumber::setSize(int size) {
	this->digits.resize(size);
}

void BigNumber::setDigit(int index, int value)
{
	this->digits.at(index) = value;
}

void BigNumber::addDigit(int value)
{
	this->digits.push_back(value);
}

BigNumber BigNumber::addSequential(BigNumber otherNumber)
{
	BigNumber largeNumber = BigNumber();
	BigNumber smallNumber = BigNumber();

	if (this->getSize() > otherNumber.getSize()) {
		largeNumber.digits = this->digits;
		smallNumber.digits = otherNumber.digits;
	}
	else
	{
		largeNumber.digits = otherNumber.digits;
		smallNumber.digits = this->digits;
	}

	int index, sumDigits, carry = 0;

	for (index = 0; index < smallNumber.getSize(); index++) {
		sumDigits = largeNumber.getDigit(index) + smallNumber.getDigit(index) + carry;
		largeNumber.setDigit(index, sumDigits % 10);
		carry = sumDigits / 10;
	}

	while (index < largeNumber.getSize() && carry == 1) {
		sumDigits = largeNumber.getDigit(index) + carry;
		largeNumber.setDigit(index, sumDigits % 10);
		carry = sumDigits / 10;
		index++;
	}

	if (carry == 1) {
		largeNumber.addDigit(1);
	}

	return largeNumber;
}

void BigNumber::addMPIParentReader(BigNumber otherNumber) {
	TxtOperations txtOperations;

	// pid -> process id
	// np -> no. of processes
	int pid;
	int number_of_processors;
	int elements_per_process;

	MPI_Status status;

	// Creation of parallel processes
	int rc = MPI_Init(NULL, NULL);

	if (rc != MPI_SUCCESS) {
		std::cout << "Err MPI init\n";
		MPI_Abort(MPI_COMM_WORLD, rc);
	}

	// find out process ID,
	// and how many processes were started
	MPI_Comm_rank(MPI_COMM_WORLD, &pid);
	MPI_Comm_size(MPI_COMM_WORLD, &number_of_processors);

	if (pid == 0) {
		vector<BigNumber> numbers = txtOperations.readNumbersFromFile("numbers.txt");
		BigNumber firstNumber = numbers.at(0);
		BigNumber secondNumber = numbers.at(1);
		BigNumber largeNumber = BigNumber();
		BigNumber smallNumber = BigNumber();

		if (firstNumber.getSize() > secondNumber.getSize()) {
			largeNumber.digits = firstNumber.digits;
			smallNumber.digits = secondNumber.digits;
		}
		else
		{
			largeNumber.digits = secondNumber.digits;
			smallNumber.digits = firstNumber.digits;
		}

		vector<int> result;
		vector<int> temp;

		auto begin = chrono::high_resolution_clock::now();
		int MAX = smallNumber.getSize();
		int rest = MAX % (number_of_processors - 1);
		elements_per_process = MAX / (number_of_processors - 1);
		int initRest = rest;
		int index = 0, i;

		for (i = 0; i < number_of_processors - 1; i++) {
			index = i * elements_per_process;
			int sendDestination = elements_per_process + 1;
			int start = index + initRest - rest;
			if (rest > 0) {
				MPI_Send(&sendDestination, 1, MPI_INT, i + 1, 0, MPI_COMM_WORLD);
				MPI_Send(&largeNumber.getDigits()[start], sendDestination, MPI_INT, i + 1, 0, MPI_COMM_WORLD);
				MPI_Send(&smallNumber.getDigits()[start], sendDestination, MPI_INT, i + 1, 0, MPI_COMM_WORLD);
				rest--;
			}
			else {
				MPI_Send(&elements_per_process, 1, MPI_INT, i + 1, 0, MPI_COMM_WORLD);
				MPI_Send(&largeNumber.getDigits()[start], elements_per_process, MPI_INT, i + 1, 0, MPI_COMM_WORLD);
				MPI_Send(&smallNumber.getDigits()[start], elements_per_process, MPI_INT, i + 1, 0, MPI_COMM_WORLD);
			}
		}

		int length, size = 0, p = 0;
		char carryFlag;

		for (int i = MAX; i < largeNumber.getSize(); i++) {
			result.insert(result.begin(), largeNumber.getDigit(i));
		}

		for (int i = number_of_processors - 1; i > 0; i--) {
			MPI_Recv(&length, 1, MPI_INT, i, 0, MPI_COMM_WORLD, &status);

			MPI_Recv(&carryFlag, 1, MPI_CHAR, i, 0, MPI_COMM_WORLD, &status);
			temp.resize(length);
			MPI_Recv(&temp[0], length, MPI_INT, i, 0, MPI_COMM_WORLD, &status);
			int resultLength = result.size() - 1;
			if (carryFlag == 'c') {
				result.insert(result.end(), temp.begin() + 1, temp.end());
				while (carryFlag == 'c'&& resultLength >= 0) {
					int digit = result[resultLength] + 1;
					result[resultLength] = digit % 10;
					if (digit > 9) {
						carryFlag = 'c';
					}
					else {
						carryFlag = 'n';
					}
					resultLength--;
				}
				if (carryFlag == 'c') {
					result.insert(result.begin(), 1);
				}
			}
			else {
				result.insert(result.end(), temp.begin(), temp.end());
			}
		}
		auto end = chrono::high_resolution_clock::now();
		cout << "Time:" << chrono::duration_cast<std::chrono::nanoseconds>(end - begin).count() << endl;

		reverse(result.begin(), result.end());

		txtOperations.writeNumberToFile(BigNumber(result), "MPISum.txt");
		txtOperations.writeNumberToFile(firstNumber.addSequential(secondNumber), "sequentialSum.txt");
	}
	else {
		int segment;
		vector<int> temp1;
		vector<int> temp2;
		MPI_Status status;
		char carry = 'c';
		char nonCarry = 'n';

		vector<int> result, aux, bux;
		BigNumber chunkFromFirstNumber, chunkFromSecondNumber;

		MPI_Recv(&segment, 1, MPI_INT, 0, 0, MPI_COMM_WORLD, &status);
		temp1.resize(segment);
		temp2.resize(segment);
		MPI_Recv(&temp1[0], segment, MPI_INT, 0, 0, MPI_COMM_WORLD, &status);
		MPI_Recv(&temp2[0], segment, MPI_INT, 0, 0, MPI_COMM_WORLD, &status);

		chunkFromFirstNumber = BigNumber(temp1);
		chunkFromSecondNumber = BigNumber(temp2);

		/*chunkFromFirstNumber = BigNumber(this->getSegment(start, start + segment));
		chunkFromSecondNumber = BigNumber(otherNumber.getSegment(start, start + segment));*/

		result = chunkFromFirstNumber.addSequential(chunkFromSecondNumber).getDigits();

		int sizeOfResult = result.size();

		reverse(result.begin(), result.end());

		MPI_Send(&sizeOfResult, 1, MPI_INT, 0, 0, MPI_COMM_WORLD);

		if (sizeOfResult > segment) {
			MPI_Send(&carry, 1, MPI_CHAR, 0, 0, MPI_COMM_WORLD);
		}
		else {
			MPI_Send(&nonCarry, 1, MPI_CHAR, 0, 0, MPI_COMM_WORLD);
		}
		MPI_Send(&result[0], sizeOfResult, MPI_INT, 0, 0, MPI_COMM_WORLD);
	}
	MPI_Finalize();
}

void BigNumber::addMPIChildReader(BigNumber otherNumber) {
	TxtOperations txtOperations;

	// pid -> process id
	// np -> no. of processes
	int pid;
	int number_of_processors;
	int elements_per_process;

	MPI_Status status;

	// Creation of parallel processes
	int rc = MPI_Init(NULL, NULL);

	if (rc != MPI_SUCCESS) {
		std::cout << "Err MPI init\n";
		MPI_Abort(MPI_COMM_WORLD, rc);
	}

	// find out process ID,
	// and how many processes were started
	MPI_Comm_rank(MPI_COMM_WORLD, &pid);
	MPI_Comm_size(MPI_COMM_WORLD, &number_of_processors);

	if (pid == 0) {
		vector<int> result;
		vector<int> temp;

		auto begin = chrono::high_resolution_clock::now();
		int MAX = 6;
		int rest = MAX % (number_of_processors - 1);
		elements_per_process = MAX / (number_of_processors - 1);
		int initRest = rest;
		int index = 0, i;

		for (i = 0; i < number_of_processors - 1; i++) {
			index = i * elements_per_process;
			int sendDestination = elements_per_process + 1;
			int start = index + initRest - rest;
			if (rest > 0) {
				MPI_Send(&start, 1, MPI_INT, i + 1, 0, MPI_COMM_WORLD);
				MPI_Send(&sendDestination, 1, MPI_INT, i + 1, 0, MPI_COMM_WORLD);
				rest--;
			}
			else {
				MPI_Send(&start, 1, MPI_INT, i + 1, 0, MPI_COMM_WORLD);
				MPI_Send(&elements_per_process, 1, MPI_INT, i + 1, 0, MPI_COMM_WORLD);
			}
		}

		int length, size = 0, p = 0;
		char carryFlag;

		for (int i = number_of_processors - 1; i > 0; i--) {
			MPI_Recv(&length, 1, MPI_INT, i, 0, MPI_COMM_WORLD, &status);

			MPI_Recv(&carryFlag, 1, MPI_CHAR, i, 0, MPI_COMM_WORLD, &status);
			temp.resize(length);
			MPI_Recv(&temp[0], length, MPI_INT, i, 0, MPI_COMM_WORLD, &status);
			int resultLength = result.size() - 1;
			if (carryFlag == 'c') {
				result.insert(result.end(), temp.begin() + 1, temp.end());
				while (carryFlag == 'c'&& resultLength >= 0) {
					int digit = result[resultLength] + 1;
					result[resultLength] = digit % 10;
					if (digit > 9) {
						carryFlag = 'c';
					}
					else {
						carryFlag = 'n';
					}
					resultLength--;
				}
				if (carryFlag == 'c') {
					result.insert(result.begin(), 1);
				}
			}
			else {
				result.insert(result.end(), temp.begin(), temp.end());
			}
		}
		auto end = chrono::high_resolution_clock::now();
		cout << "Time:" << chrono::duration_cast<std::chrono::nanoseconds>(end - begin).count() << endl;

		reverse(result.begin(), result.end());

		txtOperations.writeNumberToFile(BigNumber(result), "MPISum.txt");
	}
	else {
		int start;
		int segment;
		MPI_Status status;
		char carry = 'c';
		char nonCarry = 'n';

		vector<int> result, aux, bux;
		BigNumber chunkFromFirstNumber, chunkFromSecondNumber;

		MPI_Recv(&start, 1, MPI_INT, 0, 0, MPI_COMM_WORLD, &status);
		MPI_Recv(&segment, 1, MPI_INT, 0, 0, MPI_COMM_WORLD, &status);

		txtOperations.ReadVectorsFromFilesSpecificPoint("numbers.txt", "numbers.txt", start, start + segment, aux, bux);
		chunkFromFirstNumber = BigNumber(aux);
		chunkFromSecondNumber = BigNumber(bux);

		result = chunkFromFirstNumber.addSequential(chunkFromSecondNumber).getDigits();

		int sizeOfResult = result.size();

		reverse(result.begin(), result.end());

		MPI_Send(&sizeOfResult, 1, MPI_INT, 0, 0, MPI_COMM_WORLD);

		if (sizeOfResult > segment) {
			MPI_Send(&carry, 1, MPI_CHAR, 0, 0, MPI_COMM_WORLD);
		}
		else {
			MPI_Send(&nonCarry, 1, MPI_CHAR, 0, 0, MPI_COMM_WORLD);
		}
		MPI_Send(&result[0], sizeOfResult, MPI_INT, 0, 0, MPI_COMM_WORLD);
	}
	MPI_Finalize();
}

void BigNumber::addMPI(BigNumber otherNumber) {
	BigNumber largeNumber = BigNumber();
	BigNumber smallNumber = BigNumber();

	if (this->getSize() > otherNumber.getSize()) {
		largeNumber.digits = this->digits;
		smallNumber.digits = otherNumber.digits;
	}
	else
	{
		largeNumber.digits = otherNumber.digits;
		smallNumber.digits = this->digits;
	}

	TxtOperations txtOperations;
	txtOperations.writeNumberToFile(largeNumber, "number1.txt");
	txtOperations.writeNumberToFile(smallNumber, "number2.txt");

	// pid -> process id
	// np -> no. of processes
	int pid;
	int number_of_processors;
	int MAX = smallNumber.getSize();
	int elements_per_process;

	MPI_Status status;

	// Creation of parallel processes
	int rc = MPI_Init(NULL, NULL);

	if (rc != MPI_SUCCESS) {
		std::cout << "Err MPI init\n";
		MPI_Abort(MPI_COMM_WORLD, rc);
	}

	// find out process ID,
	// and how many processes were started
	MPI_Comm_rank(MPI_COMM_WORLD, &pid);
	MPI_Comm_size(MPI_COMM_WORLD, &number_of_processors);

	elements_per_process = MAX / (number_of_processors - 1);

	if (pid == 0) {
		vector<int> result;
		vector<int> temp;

		auto begin = chrono::high_resolution_clock::now();
		int rest = MAX % (number_of_processors - 1);
		int initRest = rest;
		int index = 0, i;

		for (i = 0; i < number_of_processors - 1; i++) {
			index = i * elements_per_process;
			int sendDestination = elements_per_process + 1;
			int start = index + initRest - rest;
			if (rest > 0) {
				MPI_Send(&start, 1, MPI_INT, i + 1, 0, MPI_COMM_WORLD);
				MPI_Send(&sendDestination, 1, MPI_INT, i + 1, 0, MPI_COMM_WORLD);
				rest--;
			}
			else {
				MPI_Send(&start, 1, MPI_INT, i + 1, 0, MPI_COMM_WORLD);
				MPI_Send(&elements_per_process, 1, MPI_INT, i + 1, 0, MPI_COMM_WORLD);
			}
		}

		int length, size = 0, p = 0;
		char carryFlag;

		for (int i = MAX; i < largeNumber.getSize(); i++) {
			result.insert(result.begin(), largeNumber.getDigit(i));
		}

		for (int i = number_of_processors - 1; i > 0; i--) {
			MPI_Recv(&length, 1, MPI_INT, i, 0, MPI_COMM_WORLD, &status);

			MPI_Recv(&carryFlag, 1, MPI_CHAR, i, 0, MPI_COMM_WORLD, &status);
			temp.resize(length);
			MPI_Recv(&temp[0], length, MPI_INT, i, 0, MPI_COMM_WORLD, &status);
			int resultLength = result.size() - 1;
			if (carryFlag == 'c') {
				result.insert(result.end(), temp.begin() + 1, temp.end());
				while (carryFlag == 'c'&& resultLength >= 0) {
					int digit = result[resultLength] + 1;
					result[resultLength] = digit % 10;
					if (digit > 9) {
						carryFlag = 'c';
					}
					else {
						carryFlag = 'n';
					}
					resultLength--;
				}
				if (carryFlag == 'c') {
					result.insert(result.begin(), 1);
				}
			}
			else {
				result.insert(result.end(), temp.begin(), temp.end());
			}
		}
		auto end = chrono::high_resolution_clock::now();
		cout << "Time:" << chrono::duration_cast<std::chrono::nanoseconds>(end - begin).count() << endl;

		TxtOperations txtFile;

		reverse(result.begin(), result.end());

		txtFile.writeNumberToFile(BigNumber(result), "MPISum.txt");
	}
	else {
		int start;
		int segment;
		MPI_Status status;
		char carry = 'c';
		char nonCarry = 'n';

		vector<int> result, aux, bux;
		BigNumber chunkFromFirstNumber, chunkFromSecondNumber;

		MPI_Recv(&start, 1, MPI_INT, 0, 0, MPI_COMM_WORLD, &status);
		MPI_Recv(&segment, 1, MPI_INT, 0, 0, MPI_COMM_WORLD, &status);

		txtOperations.ReadVectorsFromFilesSpecificPoint("number1.txt", "number2.txt", start, start + segment, aux, bux);
		chunkFromFirstNumber = BigNumber(aux);
		chunkFromSecondNumber = BigNumber(bux);

		/*chunkFromFirstNumber = BigNumber(this->getSegment(start, start + segment));
		chunkFromSecondNumber = BigNumber(otherNumber.getSegment(start, start + segment));*/

		result = chunkFromFirstNumber.addSequential(chunkFromSecondNumber).getDigits();

		int sizeOfResult = result.size();

		reverse(result.begin(), result.end());

		MPI_Send(&sizeOfResult, 1, MPI_INT, 0, 0, MPI_COMM_WORLD);

		if (sizeOfResult > segment) {
			MPI_Send(&carry, 1, MPI_CHAR, 0, 0, MPI_COMM_WORLD);
		}
		else {
			MPI_Send(&nonCarry, 1, MPI_CHAR, 0, 0, MPI_COMM_WORLD);
		}
		MPI_Send(&result[0], sizeOfResult, MPI_INT, 0, 0, MPI_COMM_WORLD);
	}
	MPI_Finalize();
}

vector<int> BigNumber::getSegment(int start, int end) {
	vector<int> destination;
	for (int i = start; i < end; i++) {
		destination.push_back(digits[i]);
	}
	return destination;
}

BigNumber BigNumber::addParallelClassification(BigNumber otherNumber, int no_threads)
{
	BigNumber largeNumber = BigNumber();
	BigNumber smallNumber = BigNumber();

	if (this->getSize() > otherNumber.getSize()) {
		largeNumber.digits = this->digits;
		smallNumber.digits = otherNumber.digits;
	}
	else
	{
		largeNumber.digits = otherNumber.digits;
		smallNumber.digits = this->digits;
	}

	int MAX = smallNumber.getSize();
	int start = 0;
	int finish = MAX / no_threads;
	int cat = MAX / no_threads;
	int rest = MAX % no_threads;
	vector<thread> threads = vector<thread>(no_threads);
	vector<int> carries = vector<int>(no_threads, -1);

	for (int i = 0; i < no_threads; i++) {
		if (rest > 0) {
			rest--;
			finish++;
		}

		threads[i] = thread(&BigNumber::computeClassification, this, start, finish, i, ref(largeNumber), ref(smallNumber), ref(carries));

		start = finish;
		finish = start + cat;
	}

	for (int i = 0; i < no_threads; i++) {
		threads[i].join();
	}

	start = 0;
	finish = MAX / no_threads;
	cat = MAX / no_threads;
	rest = MAX % no_threads;

	for (int i = 0; i < no_threads; i++) {
		if (rest > 0) {
			rest--;
			finish++;
		}

		if (i > 0 && carries[i] == 2) {
			carries[i] = carries[i - 1];
		}
		threads[i] = thread(&BigNumber::computeAdd, this, start, finish, i, ref(largeNumber), ref(smallNumber), ref(carries));

		start = finish;
		finish = start + cat;
	}

	for (int i = 0; i < no_threads; i++) {
		threads[i].join();
	}
	if (carries[no_threads - 1] == 1) {
		largeNumber.addDigit(1);
	}
	return largeNumber;
}

BigNumber BigNumber::addParallelOptimised(BigNumber otherNumber, int numberOfThreads)
{
	BigNumber largeNumber = BigNumber();
	BigNumber smallNumber = BigNumber();

	if (this->getSize() > otherNumber.getSize()) {
		largeNumber.digits = this->digits;
		smallNumber.digits = otherNumber.digits;
	}
	else
	{
		largeNumber.digits = otherNumber.digits;
		smallNumber.digits = this->digits;
	}

	int MAX = smallNumber.getSize();
	int start = 0;
	int finish = MAX / numberOfThreads;
	int cat = MAX / numberOfThreads;
	int rest = MAX % numberOfThreads;
	vector<thread> threads = vector<thread>(numberOfThreads);
	vector<int> carries = vector<int>(numberOfThreads, -1);

	for (int i = 0; i < numberOfThreads; i++) {
		if (rest > 0) {
			rest--;
			finish++;
		}

		threads[i] = thread(&BigNumber::compute, this, start, finish, i, ref(largeNumber), ref(smallNumber), ref(carries));

		start = finish;
		finish = start + cat;
	}

	for (int i = 0; i < numberOfThreads; i++) {
		threads[i].join();
	}

	return largeNumber;
}

void BigNumber::printBigNumber()
{
	for (int index = 0; index < this->getSize(); index++) {
		cout << this->getDigit(index);
	}
	cout << endl;
}

void BigNumber::compute(int start, int end, int numberOfThreads, BigNumber & largeNumber, BigNumber & smallNumber, vector<int>& carries)
{
	int index, sumDigits, carry = 0;

	for (index = start; index < end; index++) {
		sumDigits = largeNumber.getDigit(index) + smallNumber.getDigit(index) + carry;
		largeNumber.setDigit(index, sumDigits % 10);
		carry = sumDigits / 10;
	}
	if (numberOfThreads == carries.size() - 1 && carry == 1) {
		index = end;
		end = largeNumber.getSize();
		while (carry == 1 && index < end) {
			sumDigits = largeNumber.getDigit(index) + carry;
			largeNumber.setDigit(index, sumDigits % 10);
			carry = sumDigits / 10;
			index++;
		}
		if (carry == 1) {
			largeNumber.addDigit(1);
		}
	}

	carries.at(numberOfThreads) = carry;

	wait(carries);

	if (numberOfThreads > 0 && carries[numberOfThreads - 1] == 1) {
		carry = carries[numberOfThreads - 1];
		index = start;
		while (carry == 1 && index < end) {
			sumDigits = largeNumber.getDigit(index) + carry;
			largeNumber.setDigit(index, sumDigits % 10);
			carry = sumDigits / 10;
			index++;
		}
	}
}

void BigNumber::wait(vector<int>& carries)
{
	while (find(carries.begin(), carries.end(), -1) != carries.end()) {
		continue;
	}
}

void BigNumber::computeAdd(int start, int end, int no_thread, BigNumber & largeNumber, BigNumber & smallNumber, vector<int>& carries)
{
	int index, sumDigits, carry = 0;
	if (no_thread > 0) {
		carry = carries[no_thread - 1];
	}

	for (index = start; index < end; index++) {
		sumDigits = largeNumber.getDigit(index) + smallNumber.getDigit(index) + carry;
		largeNumber.setDigit(index, sumDigits % 10);
		carry = sumDigits / 10;
	}

	if (no_thread == carries.size() - 1) {
		end = largeNumber.getSize();
		while (index < end && carry == 1) {
			sumDigits = largeNumber.getDigit(index) + carry;
			largeNumber.setDigit(index, sumDigits % 10);
			carry = sumDigits / 10;
			index++;
		}
	}
}

void BigNumber::computeClassification(int start, int end, int numberOfThreads, BigNumber & largeNumber, BigNumber & smallNumber, vector<int>& carries)
{
	int sum;
	for (int index = end - 1; index >= start; index--) {
		sum = largeNumber.getDigit(index) + smallNumber.getDigit(index);
		if (sum > 9) {
			carries[numberOfThreads] = 1;
			break;
		}
		else if (sum < 9) {
			carries[numberOfThreads] = 0;
			break;
		}
		else {
			carries[numberOfThreads] = 2;
		}
	}

	if (numberOfThreads == carries.size() - 1 && carries[numberOfThreads] != 0) {
		start = end;
		end = largeNumber.getSize();
		int carry = carries[numberOfThreads];
		for (int index = start; index < end; index++) {
			sum = largeNumber.getDigit(index);
			if (carry == 1) {
				sum++;
			}
			if (sum > 9) {
				carries[numberOfThreads] = 1;
				break;
			}
			else if (sum < 9) {
				carries[numberOfThreads] = 0;
				break;
			}
			else {
				carry = sum / 10;
				carries[numberOfThreads] = 2;
			}
		}
	}
}

void BigNumber::reverseNumber() {
	BigNumber number = BigNumber(this->digits);
	for (int i = 0; i < this->getSize(); i++) {
		this->setDigit(i, number.getDigit(number.getSize() - i - 1));
	}
}

BigNumber BigNumber::multiplySequential(BigNumber otherNumber)
{
	BigNumber largeNumber = BigNumber();
	BigNumber smallNumber = BigNumber();

	if (this->getSize() > otherNumber.getSize()) {
		largeNumber.digits = this->digits;
		smallNumber.digits = otherNumber.digits;
	}
	else
	{
		largeNumber.digits = otherNumber.digits;
		smallNumber.digits = this->digits;
	}
	vector<int> result(largeNumber.getSize() + smallNumber.getSize(), 0);
	BigNumber resultNumber = BigNumber(result);

	for (int i = 0; i < largeNumber.getSize(); i++) {
		int carry = 0;
		for (int j = 0; j < smallNumber.getSize(); j++) {
			int sum = largeNumber.getDigit(i)*smallNumber.getDigit(j) + resultNumber.getDigit(i + j) + carry;
			carry = sum / 10;
			resultNumber.setDigit(i + j, sum % 10);
		}
		if (carry > 0) {
			resultNumber.setDigit(i + smallNumber.getSize(), resultNumber.getDigit(i + smallNumber.getSize()) + carry);
		}
	}
	int i = resultNumber.getSize() - 1;
	while (i > 0 && resultNumber.getDigit(i) == 0) {
		i--;
	}
	if (i == -1) {
		return BigNumber(0);
	}
	else {
		resultNumber.setSize(i + 1);
	}

	return resultNumber;
}

BigNumber BigNumber::multiplyParallel(BigNumber otherNumber, int numberOfThreads)
{
	BigNumber largeNumber = BigNumber();
	BigNumber smallNumber = BigNumber();

	if (this->getSize() > otherNumber.getSize()) {
		largeNumber.digits = this->digits;
		smallNumber.digits = otherNumber.digits;
	}
	else
	{
		largeNumber.digits = otherNumber.digits;
		smallNumber.digits = this->digits;
	}

	int minSize = smallNumber.getSize();
	int maxSize = largeNumber.getSize();

	int start = 0;
	int dim = minSize / numberOfThreads;
	int end = minSize / numberOfThreads;
	int rest = minSize % numberOfThreads;

	vector<thread> threads = vector<thread>(numberOfThreads);
	vector<BigNumber> results = vector<BigNumber>(numberOfThreads);

	for (int index = 0; index < numberOfThreads; index++) {
		if (rest > 0) {
			end++;
			rest--;
		}
		results[index] = BigNumber(minSize + maxSize);

		threads[index] = thread(&BigNumber::computeMultiply, this, start, end, ref(smallNumber), ref(largeNumber), ref(results[index]));

		start = end;
		end = end + dim;
	}

	for (int index = 0; index < numberOfThreads; index++) {
		threads[index].join();
	}

	for (int i = 1; i < numberOfThreads; i++) {
		results[0] = results[0].addParallelClassification(results[i], numberOfThreads);
	}

	int i = results[0].getSize() - 1;
	while (i > 0 && results[0].getDigit(i) == 0) {
		i--;
	}
	if (i == -1) {
		return BigNumber(0);
	}
	else {
		results[0].setSize(i + 1);
	}

	return results[0];
}

void BigNumber::computeMultiply(int start, int end, BigNumber & smallNumber, BigNumber & largeNumber, BigNumber & result)
{
	int maxSize = largeNumber.getSize();
	int carry, product, multiplicand, multiplier, i, j;
	for (i = start; i < end; i++) {
		multiplier = smallNumber.getDigit(i);
		carry = 0;
		for (j = 0; j < maxSize; j++) {
			multiplicand = largeNumber.getDigit(j);
			product = multiplicand * multiplier + carry;
			carry = product / 10 + result.addDigit(j + i, product % 10);
		}
		if (carry != 0) {
			result.addDigit(j + i, carry % 10);
		}
	}
}

int BigNumber::addDigit(int position, int value)
{
	int sum = digits[position] + value;
	digits[position] = sum % 10;
	return sum / 10;
}