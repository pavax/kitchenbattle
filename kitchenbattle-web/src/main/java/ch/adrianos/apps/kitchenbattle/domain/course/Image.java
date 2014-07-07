package ch.adrianos.apps.kitchenbattle.domain.course;

import org.springframework.http.MediaType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.UUID;

@Entity
public class Image {

    @Id
    private String id;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @NotNull
    private byte[] content;

    @NotNull
    private String contentType;

    public Image(byte[] content, String contentType) {
        this.id = UUID.randomUUID().toString();
        this.content = content;
        this.contentType = contentType;
        MediaType mediaType = MediaType.parseMediaType(contentType);
        if (!mediaType.equals(MediaType.IMAGE_JPEG) && !mediaType.equals(MediaType.IMAGE_PNG) && !mediaType.equals(MediaType.IMAGE_GIF)) {
            throw new IllegalArgumentException("Image Type not supported: " + contentType);
        }
    }

    protected Image() {
    }

    public byte[] getContent() {
        return content;
    }

    public String getContentType() {
        return contentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Image image = (Image) o;

        if (!Arrays.equals(content, image.content)) return false;
        if (contentType != null ? !contentType.equals(image.contentType) : image.contentType != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = content != null ? Arrays.hashCode(content) : 0;
        result = 31 * result + (contentType != null ? contentType.hashCode() : 0);
        return result;
    }
}
