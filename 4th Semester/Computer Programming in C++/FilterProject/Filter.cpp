#include "Filter.h"
//-----Filter Class Declarations-----//
Filter::Filter() {}

Filter::Filter(const Filter & filter) {}

Filter::~Filter() {}

//-----Linear Filter Class Declarations-----//
LinearFilter::LinearFilter(LinearParameter a, LinearParameter c) : a(a), c(c) {}

LinearFilter::LinearFilter(const LinearFilter & linearFilter) : a(linearFilter.a), c(linearFilter.c) {}

LinearFilter::~LinearFilter() {}

Image LinearFilter::operator << (const Image & image) {
	Image filteredImage = image;

	for (unsigned int i = 0; i < image.getHeight(); i++) 
		for(unsigned int j = 0; j < image.getWidth(); j++)
			filteredImage(i,j) = (filteredImage(i,j) * a + c).clampToLowerBound(0).clampToUpperBound(1);
	
	
		
	return filteredImage;
}//operator <<

//-----Gamma Filter Class Declarations-----//
GammaFilter::GammaFilter(float gamma) : gamma(gamma) {}

GammaFilter::GammaFilter(const GammaFilter & gammaFilter) : gamma(gammaFilter.gamma) {}

GammaFilter::~GammaFilter() {}

Image GammaFilter::operator << (const Image & image) {
	Image filteredImage = image;

	for (unsigned int i = 0; i < image.getHeight(); i++) 
		for (unsigned int j = 0; j < image.getWidth(); j++){
		filteredImage(i,j).r = powf(filteredImage(i, j).r, gamma);
		filteredImage(i,j).g = powf(filteredImage(i, j).g, gamma);
		filteredImage(i,j).b = powf(filteredImage(i, j).b, gamma);

		filteredImage(i,j) = filteredImage(i, j).clampToUpperBound(1.0);
		}

	return filteredImage;
}//operator <<
