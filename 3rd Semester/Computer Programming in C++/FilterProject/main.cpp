#include "Image.h"
#include "Filter.h"
#include "ppm.h"
#include <string>

using namespace std;
int main(int argc, char** argv) {
	unsigned int c = 1; //Argument counter (Starts at 1, whis is "-f"


	Image img = Image();
	string fileName = argv[argc - 1];
	string fileExtension;
	if(fileName.length() > 4)
		fileExtension = fileName.substr(fileName.length() - 4, 4);
	else {
		std::cout << "File is of invalid format or not given at all";
		exit(1);
	}
	bool loaded = img.load(fileName, fileExtension); //Load data of unfiltered image

	if (loaded) {
		if (strcmp(argv[c], "-f") == 0) {
			while (strcmp(argv[c], "-f") == 0) {
				c++; //Filter type argument

				bool applyGamma = false, applyLinear = false;//Checkers (Used to determine which filter will be used)

				if (strcmp(argv[c], "linear") == 0) //Apply Linear Filter
					applyLinear = true;

				else if (strcmp(argv[c], "gamma") == 0) //Apply Gamma Filter
					applyGamma = true;
				else {
					std::cout << "Filter invalid or not found. Exiting Program...\n";
					exit(1);
				}

				//Start to read Filter Parameters (either Gamma or Linear)
				c++;
				Filter *filter; //Make a Filter pointer. Will point to either LinearFilter or GammaFilter object, depending on arguments
				if (applyLinear) {
					float aR, aG, aB, cR, cG, cB; //Linear Filter Parameters
					aR = stof(argv[c]); c++;
					aG = stof(argv[c]); c++;
					aB = stof(argv[c]); c++;
					cR = stof(argv[c]); c++;
					cG = stof(argv[c]); c++;
					cB = stof(argv[c]);

					LinearParameter a(aR, aG, aB); //first parameter
					LinearParameter c(cR, cG, cB); //second parameter
					filter = new LinearFilter(a, c);
				}

				else {
					float gamma; //Gamma Filter Parameter
					gamma = stof(argv[c]);
					filter = new GammaFilter(gamma);
				}


				img = *filter << img;//Apply Gamma/Linear Filter
				delete filter; //Free memory
				c++;//Next filter (If there is one)
			}//while
		}//if (-f is found)
		else //else (-f is not found)
			std::cout << "first -f not found. Please don't forget to add it in.\n";
		string saveName = "filtered_" + fileName; //New name
		img.save(saveName, fileExtension);//Save filtered Image
		std::cout << "Image was filtered successfully";
	}//if (file was loaded)
	else //else (file was not loaded
		std::cout << "Image file is of invalid format.\n";

	return 0; //End of program
};
