#include "array2d.h"
#include <iostream>
using namespace math;

template <class T>
const unsigned int Array2D<T>::getWidth() const {
	return width;
}//getWidth

template <class T>
const unsigned int Array2D<T>::getHeight() const {
	return height;
}//getHeight

template <class T>
T* Array2D<T>::getRawDataPtr() const {
	return (T*) buffer.data();
}//getRawDataPtr

template <class T>
void Array2D<T>::setData(const T * const  & data_ptr) {
	// This code clears all previous data.Thus, when setData is called, buffer becomes dataless
	if (buffer.data() != nullptr)
		buffer.clear(); 
	//gives a black picture width x height size
	if (data_ptr == 0 && (width != 0 || height != 0)) {
		for (unsigned int i = 0; i < width*height; i++) {
			buffer.push_back(0);
		}
		return;
	}
		

	if (width == 0 || height == 0)
		return;
	else
		for (unsigned int i = 0; i < width*height; i++) {
			buffer.push_back(data_ptr[i]); //Adding data to vector
		}
}//setData

template <class T>
T & Array2D<T>::operator () (unsigned int x, unsigned int y) {
	return buffer[x*width + y];
}//()

template <class T>
Array2D<T>::Array2D(unsigned int width, unsigned int height , const T * data_ptr): width(width), height(height) {
	setData(data_ptr);
}//Array2D

template <class T>
Array2D<T>::Array2D(const Array2D<T> &src) : width(src.getWidth()), height(src.getHeight()) {
	setData(src.getRawDataPtr());
}//Array2D


template <class T>
Array2D<T>::~Array2D() { buffer.clear(); }

template <class T>
Array2D<T> & Array2D<T>::operator = (const Array2D<T> & right) {
	if (&right == this)
		return *this;

	
	//Deep Copy of Data
	this->width = right.getWidth();
	this->height = right.getHeight();
	setData(right.getRawDataPtr()); 

	return *this;
}//=