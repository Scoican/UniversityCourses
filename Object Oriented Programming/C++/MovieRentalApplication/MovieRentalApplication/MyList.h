#pragma once
#pragma once
#include <memory>

// Implementation of list with linked list

template <typename T> class Iterator;
template <typename T> struct Node;

template <typename T>

class  Vector {
public:
	//constructor
	Vector() noexcept;
	//size of vector
	int size() const noexcept;
	//add an element at the end of the vector
	void add(const T &value);
	//remove an element from a given position
	void erase(int poz) noexcept;
	//returns the position of a given element
	int find(const T & element) noexcept;
	//override [] operator
	T &operator[](int poz);
	const T & operator[](int poz) const;
	//copiaza vector
	Vector(const Vector & v);
	//muta constuctor
	Vector(Vector && v)noexcept;
	//override = operator
	Vector & operator=(Vector && v) noexcept;
	//deepcopy
	Vector & operator=(const Vector & v);
	//destructor
	~Vector();
	friend class Iterator<T>;

	Iterator<T> begin() const noexcept;
	Iterator<T> last() const noexcept;
	Iterator<T> end() const noexcept;
private:
	void delete_list() noexcept;
	struct Node {
		T val;
		Node *next;
		Node(const T & value) noexcept : val{ value }, next{ nullptr } {}
	};
	Node * firstNode;
	Node * lastNode;
	int sizeList;
};

template <typename T>
Vector<T>::Vector() noexcept {
	sizeList = 0;
	firstNode = nullptr;
	lastNode = nullptr;
}

template <typename T>
Vector<T>::Vector(const Vector<T> & v) {
	sizeList = 0;
	firstNode = nullptr;
	lastNode = nullptr;
	for (Node * it = v.firstNode; it != nullptr; it = it->next) {
		this->add(it->val);
	}
}

template <typename T>
Vector<T>::Vector(Vector<T> && v) noexcept {
	sizeList = 0;
	firstNode = nullptr;
	lastNode = nullptr;
	if (this != nullptr) {
		this->firstNode = v.firstNode;
		this->sizeList = v.sizeList;
		this->lastNode = v.lastNode;
		v.firstNode = nullptr;
		v.sizeList = 0;
		v.lastNode = nullptr;
	}
}
template <typename T>
Vector<T> & Vector<T>::operator=(Vector<T> && v) noexcept
{
	if (this == &v) return *this;
	delete_list();
	this->firstNode = v.firstNode;
	this->sizeList = v.sizeList;
	this->lastNode = v.lastNode;
	v.firstNode = nullptr;
	v.sizeList = 0;
	v.lastNode = nullptr;
	return *this;
}

template <typename T>
Vector<T> & Vector<T>::operator=(const Vector<T> & v) {
	if (this == &v) return *this;
	delete_list();
	this->sizeList = 0;
	this->lastNode = nullptr;
	for (Node *it = v.firstNode; it != nullptr; it = it->next) {
		this->add(it->val);
	}
	return *this;
}

template <typename T>
Vector<T>::~Vector() {
	while (this->firstNode != nullptr) {
		Node * to_delete = this->firstNode;
		this->firstNode = this->firstNode->next;
		delete to_delete;
	}
}

template <typename T>
int Vector<T>::size() const noexcept {
	return this->sizeList;
}

template <typename T>
void Vector<T>::add(const T & value) {
	Node *tmp = new Node(value);
	if (this->firstNode == nullptr) {
		this->firstNode = this->lastNode = tmp;
	}
	else {
		this->lastNode->next = tmp;
		this->lastNode = this->lastNode->next;
	}
	++this->sizeList;
}

template <typename T>
void Vector<T>::erase(int poz) noexcept {
	if (poz < 0 || poz >= this->sizeList) return;
	if (poz == 0) {
		Node * to_delete = this->firstNode;
		this->firstNode = this->firstNode->next;
		delete to_delete;
	}
	else {
		Node * prev = this->firstNode;
		for (int i = 0; i < poz - 1; ++i, prev = prev->next);
		Node * to_delete = prev->next;
		prev->next = to_delete->next;
		if (poz == this->sizeList - 1) {
			this->lastNode = prev;
		}
		delete to_delete;
	}
	--this->sizeList;
}

template <typename T>
int Vector<T>::find(const T & element) noexcept {
	int pos = 0;
	for (auto iter = this->begin(); iter != this->end(); ++iter) {
		if (*iter == element) return pos;
		++pos;
	}
	return -1;
}

template <typename T>
T & Vector<T>::operator []  (int poz)
{
	Iterator<T> iter = this->begin();
	for (int i = 0; i < poz && iter != this->end(); ++i)
		++iter;
	return *iter;
}

template <typename T>
const T & Vector<T>::operator[](int poz) const
{
	Iterator <T> iter = this->begin();
	for (int i = 0; i < poz && iter != this->end(); ++i)
		++iter;
	return *iter;
}

template<typename T>
Iterator<T> Vector<T>::begin() const noexcept {
	return Iterator<T>(this->firstNode);
}

template <typename T>
Iterator<T> Vector<T>::last() const noexcept {
	return Iterator<T>(this->lastNode);
}

template <typename T>
Iterator<T> Vector<T>::end() const noexcept {
	return Iterator<T>(nullptr);
}

template <typename T>
void Vector<T>::delete_list() noexcept
{
	while (this->firstNode != nullptr) {
		Node * to_delete = this->firstNode;
		this->firstNode = this->firstNode->next;
		delete to_delete;
	}
}

/////// Iteratorul
template <typename T>
class Iterator {
public:
	friend class Vector<T>;
	//dereference operator overload
	T & operator*() noexcept;

	//prefix increment operator
	Iterator & operator++() noexcept;

	//postfix increment operator
	Iterator operator++(int) noexcept;

	//equality operator for other iterator comparison
	bool operator==(const Iterator & rhs) const noexcept;

	//equality operator for ref comparison
	bool operator==(const typename Vector<T>::Node * ref) const noexcept;

	//inequality operator for other iterator comparison
	bool operator!=(const Iterator & rhs) const noexcept;

	//inequality operator for other ref comparison
	bool operator!=(const typename Vector<T>::Node * ref) const noexcept;

	//default constructor
	Iterator() noexcept : current_{ nullptr } {};

	//constructor overload for pointer parameter
	Iterator(typename Vector<T>::Node * other) noexcept;

	//copy constructor
	Iterator(const Iterator & rhs) noexcept;

	//copy assingment operator
	Iterator & operator=(const Iterator & rhs) = default;

	//default move constructor
	Iterator(Iterator && rhs) = default;

	//default move operator
	Iterator & operator=(Iterator && rhs) = default;

	//default destructor
	~Iterator();
private:
	typename Vector<T>::Node * current_;
};


template<typename T>
Iterator<T>::Iterator(typename Vector<T>::Node * other) noexcept : current_{ other } {

}

template<typename T>
Iterator<T>::Iterator(const Iterator<T> & rhs) noexcept : current_{ rhs.current_ } {

}

template<typename T>
Iterator<T>::~Iterator() {

}

template<typename T>
T & Iterator<T>::operator*() noexcept {
	return this->current_->val;
}

template<typename T>
Iterator<T> & Iterator<T>::operator++() noexcept {
	this->current_ = this->current_->next;
	return *this;
}

template<typename T>
Iterator<T> Iterator<T>::operator++(int) noexcept {
	Iterator<T> temp(*this);
	operator++();
	return temp;
}

template<typename T>
bool Iterator<T>::operator==(const Iterator<T> & rhs) const noexcept {
	return this->current_ == rhs.current_;
}

template<typename T>
bool Iterator<T>::operator==(const typename Vector<T>::Node * ref) const noexcept {
	return this->current_ == ref;
}

template<typename T>
bool Iterator<T>::operator!=(const Iterator<T> & rhs) const noexcept {
	return this->current_ != rhs.current_;
}

template<typename T>
bool Iterator<T>::operator!=(const typename Vector<T>::Node * ref) const noexcept {
	return this->current_ != ref;
}


