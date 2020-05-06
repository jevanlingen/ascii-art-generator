package nl.jcore.com.guice.config;

import com.google.inject.AbstractModule;
import nl.jcore.com.guice.component.Downloader;
import nl.jcore.com.guice.component.ImageToAscii;
import nl.jcore.com.guice.component.impl.DefaultDownloader;
import nl.jcore.com.guice.component.impl.DefaultImageToAscii;

public class BasicModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Downloader.class).to(DefaultDownloader.class);
        bind(ImageToAscii.class).to(DefaultImageToAscii.class);
    }

}