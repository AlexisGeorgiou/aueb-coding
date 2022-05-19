#include "Image.h"
#include "ppm.h"



Image::Image(unsigned int width, unsigned int height, const Color * data_ptr): Array2D(width, height, data_ptr) {}

Image::Image(const Image &img) : Array2D(img) {};
	


bool Image::load(const std::string & filename, const std::string & format) {

		if (format.size() == 4 && (format[0] == '.'&& tolower(format[1]) == 'p' && tolower(format[2]) == 'p' && tolower(format[3]) == 'm')) {
			unsigned int *w = &width; //We do this to automatically resize the image object.
			unsigned int *h = &height;
			const char * filePointer = filename.c_str(); //Turn pointer to variable
			float *toLoad = image::ReadPPM(filePointer, (int*)w, (int*)h); //Data to load
			Color *pixels = new Color[width*height]; //Image pixels
			int j = 0;// toLoad Offset
			for (unsigned int i = 0; i < width*height; i++) {
				pixels[i].r = toLoad[j]; //Red Value loaded
				pixels[i].g = toLoad[j + 1]; //Green Value loaded
				pixels[i].b = toLoad[j + 2]; //Blue Value loaded
				j += 3;//Next pixel
			}
			
			setData(pixels);//Setting image data (Load complete)
			delete[] pixels; //Free memory

			return true;
		}
		else 
			return false;
		
}//load

bool Image::save(const std::string & filename, const std::string & format) {
	if (format.size() == 4 && (format[0] == '.', tolower(format[1]) == 'p' && tolower(format[2]) == 'p' && tolower(format[3]) == 'm')) {
		float *toSave = new float[width*height * 3];
		int j = 0; //buffer Offset
		for (unsigned int i = 0; i < width*height*3; i = i + 3) {
			toSave[i] = buffer[j].r; //Red Value saved
			toSave[i + 1] = buffer[j].g; //Green Value saved
			toSave[i + 2] = buffer[j].b; //Blue Value saved
			j++;//Next Vector in buffer
		}
		const char * filePointer = filename.c_str();
		image::WritePPM(toSave, width, height, filePointer); //Write file
		delete[] toSave;//Free Memory

		return true;
	}
	else
		return false;

}//save

