package nl.jcore.com.guice.service;

import com.google.inject.Inject;
import nl.jcore.com.guice.component.Downloader;
import nl.jcore.com.guice.component.ImageToAscii;

public class Communication {

    @Inject
    private Downloader downloader;

    @Inject
    private ImageToAscii toAscii;

    public void downloadAsAscii(String url) {
        downloader.download(url).ifPresent(toAscii::print);
    }

}
