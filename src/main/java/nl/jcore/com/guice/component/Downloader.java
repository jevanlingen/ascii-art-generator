package nl.jcore.com.guice.component;

import java.awt.image.BufferedImage;
import java.util.Optional;

public interface Downloader {

    Optional<BufferedImage> download(String url);

}
