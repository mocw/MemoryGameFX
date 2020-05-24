package lodz.uni.math;

public class Image {
    private byte[] img;

    Image(byte[] image) {
        this.img = image;
    }

    public byte[] getImage() {
        return img;
    }
}
