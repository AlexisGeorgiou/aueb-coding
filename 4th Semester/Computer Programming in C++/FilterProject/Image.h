#ifndef _IMAGE_H_
#define _IMAGE_H_

#include "imageio.h"
#include "vec3.h"
#include "array2d.h"


typedef math::Vec3<float> Color;
class Image : public image::ImageIO, public math::Array2D<Color> {
public:

	Image(unsigned int width = 0, unsigned int height = 0, const Color * data_ptr = 0);

	Image(const Image &img);



	bool load(const std::string & filename, const std::string & format);

	bool save(const std::string & filename, const std::string & format);
};

#endif