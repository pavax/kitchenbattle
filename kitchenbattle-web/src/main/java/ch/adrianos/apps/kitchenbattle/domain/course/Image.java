package ch.adrianos.apps.kitchenbattle.domain.course;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
    }

    protected Image() {
    }

    public byte[] getContent() {
        return content;
    }

    public String getContentType() {
        return contentType;
    }
}
