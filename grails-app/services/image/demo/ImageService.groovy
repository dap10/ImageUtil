package image.demo

class ImageService {
	
    static final int MAX_PHOTO_WIDTH = 800
    static final int MAX_PHOTO_HEIGHT = 600
    static final int MAX_THUMBNAIL_WIDTH = 120
    static final int MAX_THUMBNAIL_HEIGHT = 120

    static final float DEFAULT_COMPRESSION_QUALITY = 0.8f

    def imageCompressorService

    byte[] getCompressedImage(byte[] srcImageAsByteArray) {
        return imageCompressorService.compressAndScaleImage(srcImageAsByteArray, MAX_PHOTO_WIDTH, 
			MAX_PHOTO_HEIGHT, DEFAULT_COMPRESSION_QUALITY)
    }     

    byte[] getThumbnail(byte[] srcImageAsByteArray) {
        return imageCompressorService.compressAndScaleImage(srcImageAsByteArray, MAX_THUMBNAIL_WIDTH, 
			MAX_THUMBNAIL_HEIGHT, DEFAULT_COMPRESSION_QUALITY)
    }
}
