package image.demo

import java.io.InputStream
import java.io.ByteArrayInputStream
import java.io.OutputStream
import java.io.ByteArrayOutputStream
import java.io.FileOutputStream
import java.io.FileInputStream
import javax.imageio.ImageIO
import java.awt.image.BufferedImage 
import org.im4java.core.*
import java.io.File

class ImageController {
	
	def imageService
	
	def upload = { 
		// pass through to upload.gsp	
	}
	
	def uploadImage = {
    
    setSessionValues(params);
    
		Image image = new Image(title: params.title, 
      photo: params.photo, 
      contentType: params?.photo?.getContentType(),
      caller: session.caller,
      xlimit: session.xlimit,
      ylimit: session.ylimit,
      slimit: session.slimit,
      format: session.format)
      
		if (image.photo && image.photo.length > 0 && image.validate()) {
			image.photo = imageService.getCompressedImage(image.photo)
			image.thumbnail = imageService.getThumbnail(image.photo)
			image.contentType = params?.photo?.getContentType()
		}
		
		if (image.save()) {
			redirect(action: 'crop', id: image.id)
		}
		else {
			render(view:'upload', model:[image: image])
		}
    
    redirect(action: "upload")
	}
	
  def setSessionValues = {
     if (params.caller)
    {
      session.caller = params.caller
    }
    if (params.xlimit)
    {
      session.xlimit = params.xlimit
    }
    if (params.ylimit)
    {
      session.ylimit = params.xlimit
    }
    if (params.slimit)
    {
      session.slimit = params.slimit
    }
    if (params.format)
    {
      session.format = params.format
    }
  }
  
  
  def crop = {
     // pass through to resize.gsp
  }
  
  def savePicture = {
    def image = Image.findById(params.id)
    def x1 = params.x1 as Integer
    def y1 = params.y1 as Integer
    def x2 = params.x2 as Integer
    def y2 = params.y2 as Integer
    def xlimit = session.xlimit as Integer
    def ylimit = session.ylimit as Integer
    def slimit = session.slimit as Integer
    
    InputStream imagein = new ByteArrayInputStream(image.photo)
    BufferedImage bufferedImage = ImageIO.read(imagein)
    
    if (!xlimit)
    {
       xlimit = bufferedImage.getWidth() 
    }
    if (!ylimit)
    {
        ylimit = bufferedImage.getHeight()
    }
    
    BufferedImage croppedImage = bufferedImage.getSubimage(x1, y1, x2 - x1, y2 - y1)
    File tmpFile = File.createTempFile("image",".jpg")    
    ConvertCmd cmd = new ConvertCmd();
    
    IMOperation op = new IMOperation();
    op.addImage();
    op.resize(xlimit,ylimit);
    op.addImage();
    cmd.run(op, croppedImage, tmpFile.getAbsolutePath());
    
    if (slimit)
    {
       def quality = 75;
       def length = tmpFile.length()
       File secondTmpFile
       while (length > slimit && quality > 0 )
       {
          secondTmpFile = File.createTempFile("image", ".jpg");
          reduceQuality(tmpFile, secondTmpFile, quality);
          quality = quality - 10;
          length = secondTmpFile.length()
       }
       tmpFile = secondTmpFile
        
       
    }
    
    InputStream fin = new FileInputStream(tmpFile);
    OutputStream finalout = new ByteArrayOutputStream();
    byte[] buffer = new byte[4096];
    for (int size; (size = fin.read(buffer)) != -1;)
    { 
      finalout.write(buffer,0, size);
    }
    
    image.photo = finalout.toByteArray();    
    
    redirect (action:'view', id: image.id)
 }

 def reduceQuality(File imagein, File imageout, quality){
      ConvertCmd cmd = new ConvertCmd();
       IMOperation op = new IMOperation();
          op.addImage();
          op.quality(quality);
          op.addImage();
          cmd.run(op, imagein.getAbsolutePath(), imageout.getAbsolutePath()) 
 }

 def downloadFile = {
        
        def image = Image.findById(params.id)
        if (image)
        {
          if (image.contentType == "image/jpeg")
          {
            response.setHeader "Content-Disposition","attachment; filename=unique-resized-image-filename.jpg"
            response.contentType = image.contentType
          }
          else if (image.contentType == "image/png")
          {
            response.setHeader "Content-Disposition","attachment; filename=unique-resized-image-filename.png"
            response.contentType = image.contentType
          }
          else if (image.contentType == "image/gif")
          {
            response.setHeader "Content-Disposition","attachment; filename=unique-resized-image-filename.gif"
            response.contentType = image.contentType
          }
          response.outputStream << image.photo
          response.outputStream.flush()
          
          
        }
        redirect (action:'view', id: image.id)
         
  }

  def test = {
      // pass through to test.gsp
  }
	def view = {
		// pass through to view.gsp
	}
	
	def renderImage = {
		def image = Image.findById(params.id)
		if (image) {
			response.setContentLength(image.photo.length)
			response.getOutputStream().write(image.photo)
		} else {
			response.sendError(404)
		}
	}
	
	def renderThumbnail = {
		def image = Image.findById(params.id)
		if (image) {
			response.setContentLength(image.thumbnail.length)
			response.getOutputStream().write(image.thumbnail)
		} else {
			response.sendError(404)
		}
	}
}
