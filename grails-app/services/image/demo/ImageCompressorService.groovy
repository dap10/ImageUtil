package image.demo

import java.awt.Graphics2D 
import java.awt.geom.AffineTransform 
import java.awt.image.BufferedImage 
import javax.imageio.IIOImage 
import javax.imageio.ImageIO 
import javax.imageio.ImageWriteParam 
import javax.imageio.ImageWriter 
import javax.imageio.stream.MemoryCacheImageOutputStream 

class ImageCompressorService {

    static final double NO_CHANGE_SCALE_FACTOR = 1.0

    byte[] compressAndScaleImage(byte[] srcImageAsByteArray, int maxWidth,
                                        int maxHeight, float compressionQuality) throws IOException {
        BufferedImage srcImage = ImageIO.read(new ByteArrayInputStream(srcImageAsByteArray))
		double scaleFactor = calculateScaleFactorForImage(srcImage.getWidth(), srcImage.getHeight(),
			maxWidth, maxHeight)
		return compressAndScaleImage(srcImage, scaleFactor, compressionQuality)
    }

    byte[] compressAndScaleImage(BufferedImage srcImage, double scaleFactor, float compressionQuality) {
        int destWidth = srcImage.getWidth() * scaleFactor
        int destHeight = srcImage.getHeight() * scaleFactor
        BufferedImage destImage = new BufferedImage(destWidth, destHeight, BufferedImage.TYPE_INT_RGB)
        Graphics2D g = destImage.createGraphics()
        AffineTransform at = AffineTransform.getScaleInstance(scaleFactor, scaleFactor)
        g.drawRenderedImage(srcImage, at)
        return convertBufferedImageToByteArray(destImage, compressionQuality)
    }

    private double calculateScaleFactorForImage(int width, int height, int maxWidth, int maxHeight) {
        double scaleFactorForWidth = calculateScaleFactor(width, maxWidth)
        double scaleFactorForHeight = calculateScaleFactor(height, maxHeight)
        return scaleFactorForWidth < scaleFactorForHeight ? scaleFactorForWidth : scaleFactorForHeight
    }

    private double calculateScaleFactor(int size, int maxAllowedSize) {
        if (size > maxAllowedSize) {
            return maxAllowedSize / size
        }
        return NO_CHANGE_SCALE_FACTOR
    }

    private byte[] convertBufferedImageToByteArray(BufferedImage destImage, float compressionQuality) {
        Iterator iter = ImageIO.getImageWritersByFormatName("jpeg")
        ImageWriter writer = (ImageWriter) iter.next()
        ImageWriteParam iwp = writer.getDefaultWriteParam()
        iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT)
        iwp.setCompressionQuality(compressionQuality)
        ByteArrayOutputStream baos = new ByteArrayOutputStream()
        MemoryCacheImageOutputStream output = new MemoryCacheImageOutputStream(baos)
        writer.setOutput(output)
        IIOImage image = new IIOImage(destImage, null, null);
        writer.write(null, image, iwp)
        writer.dispose()
        return baos.toByteArray()
    }
}
