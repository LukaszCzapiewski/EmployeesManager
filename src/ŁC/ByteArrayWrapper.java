package ŁC;

import java.io.Serializable;

public class ByteArrayWrapper implements Serializable {
    private byte[] image;

    public ByteArrayWrapper() {
    }

    public ByteArrayWrapper(byte[] image) {
        this.image = image;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}