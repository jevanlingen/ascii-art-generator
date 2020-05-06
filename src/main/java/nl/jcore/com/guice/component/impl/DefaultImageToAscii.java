package nl.jcore.com.guice.component.impl;

import nl.jcore.com.guice.component.ImageToAscii;
import nl.jcore.com.guice.component.utils.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;

public class DefaultImageToAscii implements ImageToAscii {

    private final static AsciiImgCache CACHE = AsciiImgCache.create(new Font("Courier", Font.BOLD, 6));

    @Override
    public void print(final BufferedImage source) {
        // dimension of each tile
        Dimension tileSize = CACHE.getCharacterImageSize();

        // round the width and height so we avoid partial characters
        int outputImageWidth = (source.getWidth() / tileSize.width) * tileSize.width;
        int outputImageHeight = (source.getHeight() / tileSize.height) * tileSize.height;

        // extract pixels from source image
        int[] imagePixels = source.getRGB(0, 0, outputImageWidth, outputImageHeight, null, 0, outputImageWidth);

        // process the pixels to a grayscale matrix
        GrayscaleMatrix sourceMatrix = new GrayscaleMatrix(imagePixels, outputImageWidth, outputImageHeight);

        // divide matrix into tiles for easy processing
        TiledGrayscaleMatrix tiledMatrix = new TiledGrayscaleMatrix( sourceMatrix, tileSize.width, tileSize.height);

        StringBuffer output = new StringBuffer();

        // compare each tile to every character to determine best fit
        for (int i = 0; i < tiledMatrix.getTileCount(); i++) {
            GrayscaleMatrix tile = tiledMatrix.getTile(i);

            float minError = Float.MAX_VALUE;
            Map.Entry<Character, GrayscaleMatrix> bestFit = null;

            for (Map.Entry<Character, GrayscaleMatrix> charImage : CACHE) {
                GrayscaleMatrix charPixels = charImage.getValue();

                float error = this.calculateError(charPixels, tile);

                if (error < minError) {
                    minError = error;
                    bestFit = charImage;
                }
            }

            int tileX = ArrayUtils.convert1DtoX(i, tiledMatrix.getTilesX());
            int tileY = ArrayUtils.convert1DtoY(i, tiledMatrix.getTilesX());

            addCharacterToOutput(output, bestFit, imagePixels, tileX, tileY, outputImageWidth);
        }

        System.out.println(output);
    }

    public float calculateError(GrayscaleMatrix character, GrayscaleMatrix tile) {
        final float K1 = 0.01f;
        final float K2 = 0.03f;
        float L = 255f;

        float C1 = K1 * L;
        C1 *= C1;
        float C2 = K2 * L;
        C2 *= C2;

        final int imgLength = character.getData().length;

        float score = 0f;
        for (int i = 0; i < imgLength; i++) {
            float pixelImg1 = character.getData()[i];
            float pixelImg2 = tile.getData()[i];

            score += (2 * pixelImg1 * pixelImg2 + C1) * (2 + C2)
                    / (pixelImg1 * pixelImg1 + pixelImg2 * pixelImg2 + C1) / C2;
        }

        // average and convert score to error
        return 1 - (score / imgLength);
    }

    public void addCharacterToOutput(StringBuffer output, final Map.Entry<Character, GrayscaleMatrix> characterEntry,
            final int[] sourceImagePixels, final int tileX, final int tileY,
            final int imageWidth) {

        output.append(characterEntry.getKey());

        // append new line at the end of the row
        if ((tileX + 1) * this.CACHE.getCharacterImageSize().getWidth() == imageWidth) {
            output.append(System.lineSeparator());
        }
    }

}

