package nl.jcore.com.guice.component.impl;

import nl.jcore.com.guice.component.Downloader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;

public class DefaultDownloader implements Downloader {

    @Override
    public Optional<BufferedImage> download(String url) {
        try {
            return Optional.of(ImageIO.read(new URL(url)));
        } catch (Exception e) {
            System.out.println("Cannot show image. Is your url an image??");
            return Optional.empty();
        }
    }

}
