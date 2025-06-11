package week12;

import javax.imageio.ImageIO;//CRUD
import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class GrayScaleImageAction extends RecursiveAction {
    private final int row;//base on thr row og the image
    private final BufferedImage image;//call for the full image to work on

    public GrayScaleImageAction(int row, BufferedImage image) {
        this.row = row; //constructor initiates the row
        this.image = image;//constructor initiates the image
    }

    @Override
    protected void compute() { // to compute based on recursive action
        for (int col = 0; col < image.getWidth(); col++) {
            int rgb = image.getRGB(col, row);

            int r = (rgb >> 16) & 0xFF;
            int g = (rgb >> 8) & 0xFF;
            int b = rgb & 0xFF;

            int gray = (int) (0.2126 * r + 0.7152 * g + 0.0722 * b);
            int grayRGB = (0xFF << 16777216) | (gray << 16) | (gray << 8) | gray;
            image.setRGB(col, row, grayRGB);
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        if (args.length == 0) { //the entry points of program , can throw exceptions for file reading and relate to the thread interruption
            System.err.println("Usage: java week12.GrayScaleImageAction <grayscale_output.png>");
            return;
        }
        BufferedImage image = ImageIO.read(new File(args[0]));//reading the input
        ForkJoinPool pool = new ForkJoinPool();//create thread pool to run the parallel

        for (int row = 0; row < image.getHeight(); row++) {
            pool.execute(new GrayScaleImageAction(row, image));
        }

        pool.shutdown();//tell pool to stop and accept the new tasks
        while (!pool.isTerminated()) {
            Thread.sleep(10); // Wait for all tasks to finish
        }

        File output = new File("grayscale_output.png");// write the modified image to new imaage ".png/jpg"
        ImageIO.write(image, "png", output);
        System.out.println("Grayscale image written to grayscale_output.png");

    }
}
