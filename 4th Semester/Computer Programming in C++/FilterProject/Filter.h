#ifndef _FILTER_H_
#define _FILTER_H_

#include <iostream>
#include "Image.h"

typedef math::Vec3<float> LinearParameter;

class Filter {
public:
	virtual Image operator << (const Image & image) = 0;

	Filter();

	Filter(const Filter & filter);

	~Filter();
};

class LinearFilter : public Filter {
private:
	LinearParameter a, c;
public:
	Image operator << (const Image & image);

	LinearFilter(LinearParameter a = 0, LinearParameter c = 0);

	LinearFilter(const LinearFilter & linearFilter);

	~LinearFilter();

	//Setters
	void setA(Color a) { this->a = a;}
	void setC(Color c) { this->c = c; }
};

class GammaFilter : public Filter {
private:
	float gamma;
public:
	Image operator << (const Image & image);

	GammaFilter(float gamma = 1.0);

	GammaFilter(const GammaFilter & gammaFilter);

	~GammaFilter();

	//Setters
	void setGamma(float gamma) { this->gamma = gamma; }
};

#endif