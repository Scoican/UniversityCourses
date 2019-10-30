#include "BigNumber.h"
#include <thread>
#include <iostream>
#include <mutex>
#include <condition_variable>

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