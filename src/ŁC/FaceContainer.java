package ŁC;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class FaceContainer implements Serializable {

    private transient List<BufferedImage> bufferedImages;
    private List<ByteArrayWrapper> wrappers = new ArrayList<>();

    public FaceContainer() {
        bufferedImages = new ArrayList<>();
    }

    public List<ByteArrayWrapper> getWrappers() {
        return wrappers;
    }

    public void setWrappers(List<ByteArrayWrapper> wrappers) {
        this.wrappers = wrappers;
    }

    public List<BufferedImage> getBufferedImages() {
        return bufferedImages;
    }

    public void setBufferedImages(List<BufferedImage> bufferedImages) {
        this.bufferedImages = bufferedImages;
    }

    public byte[] enbuffer(BufferedImage bufferedImage) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpg", baos);
        byte[] bytes = baos.toByteArray();
        return bytes;
    }

    public static BufferedImage debuffer(byte[] bytearray) throws IOException {
        InputStream in = new ByteArrayInputStream(bytearray);
        return ImageIO.read(in);
    }

    public void debufferAll() throws IOException {

        bufferedImages = new ArrayList<>();

        for (var wrapper : wrappers) {

            byte[] bytes = wrapper.getImage();

            BufferedImage bufferedImage = debuffer(bytes);

            bufferedImages.add(bufferedImage);

        }

    }

    public void enbufferAll() throws IOException {

        for (var bufferedImage : bufferedImages) {

            byte[] bytes = enbuffer(bufferedImage);

            ByteArrayWrapper wrappedImage = new ByteArrayWrapper(bytes);

            wrappers.add(wrappedImage);

        }

    }

}



