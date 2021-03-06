#include "bgelim.h"
#include "logger.h"

bgElim::bgElim(cv::Mat *input)
{
	if (!input->data) {
		Logger::getLogger()->write(6, "Image sent to bgElim is not valid, EXITING!!!");
		exit(1);
	} else {
		image = *input;
		target[0] = 0;
		target[1] = 0;
		target[2] = 0;
        minDist = 150;
		process();
	}
}

bgElim::bgElim(cv::Mat *input, int distance)
{
	if (!input->data) {
		Logger::getLogger()->write(6, "Image sent to bgElim is not valid, EXITING!!!");
		exit(1);
	} else if (distance <= 0) {
		Logger::getLogger()->write(6, "Distance sent to bgElim must be greater than 0, EXITING!!!");
		exit(1);
	} else {
		image = *input;
		minDist = distance;
		createIterators();
	}
}

bgElim::bgElim(cv::Mat *input, int distance, cv::Vec3b colorTarget)
{
	if (!input->data) {
		Logger::getLogger()->write(6, "Image sent to bgElim is not valid, EXITING!!!");
		exit(1);
	} else if (distance <= 0) {
		Logger::getLogger()->write(6, "Distance sent to bgElim must be greater than 0, EXITING!!!");
		exit(1);
	} else if (colorTarget[0] < 0 || colorTarget[1] < 0 || colorTarget[2] < 0) {
		Logger::getLogger()->write(6, "Color sent to bgElim can't be NULL, EXITING!!!");
		exit(1);
	} else {
		image = *input;
		minDist = distance;
		target = colorTarget;
	}
}

void bgElim::createIterators()
{

}

void bgElim::iteratePixels()
{
    it = image.begin<cv::Vec3b>();
    itend = image.end<cv::Vec3b>();
    itout = newImage.begin<uchar>();

    minDist = 370;
	for (; it != itend; ++it, ++itout) {
		if (getDistance(*it) <= minDist)
                        *itout = 255;
		else
                        *itout = 0;
	}
}

int bgElim::getDistance(const cv::Vec3b& color) const
{
	if (color[0] < 0 || color[1] < 0 || color[2] < 0) {
		Logger::getLogger()->write(6, "Color passed to getDistance in bgElim must be greater than 0, EXITING!!!");
		exit(1);
	}
	return abs(color[0] - target[0]) + abs(color[1] - target[1]) + abs(color[2] - target[2]);
}

void bgElim::process()
{
    newImage.create(image.rows, image.cols, CV_8U);
	createIterators();
	iteratePixels();
    Logger::getLogger()->write(3,"Inside process");
}

cv::Mat bgElim::getbgElimImage()
{
	if (!newImage.data) {
		Logger::getLogger()->write(6, "newImage in bgElim is empty, EXITING!!!");
		exit(1);
	}
	return newImage;
}
