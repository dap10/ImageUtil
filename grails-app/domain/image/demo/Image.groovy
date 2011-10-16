package image.demo

class Image {
    byte[] photo
    byte[] thumbnail
    String title
    String contentType
    String caller
    BigInteger xlimit
    BigInteger ylimit
    BigInteger slimit
    String format
    
    static constraints = {
        title(maxSize: 50, nullable:true)
        photo(maxSize: 5242880 /* 5M */)
        thumbnail(maxSize: 102400 /* 100k */, nullable:true)
        contentType(matches: "image/(jpeg|png|gif)")
        caller(blank:true, nullable:true)
        xlimit(range:0..9223372036854775807, nullable:true)
        ylimit(range:0..9223372036854775807, nullable:true)
        slimit(range:0..9223372036854775807, nullable:true)
        format(blank:true, nullable:true)
        
    }
}
