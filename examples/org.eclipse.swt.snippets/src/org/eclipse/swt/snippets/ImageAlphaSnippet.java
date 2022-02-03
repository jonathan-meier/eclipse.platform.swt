package org.eclipse.swt.snippets;

import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.internal.*;
import org.eclipse.swt.printing.*;
import org.eclipse.swt.widgets.*;

public class ImageAlphaSnippet {

	public static void main (String [] args) {
		int bpp = 4;
//		int bpp = 3;
		PaletteData paletteData = new PaletteData(0xFF00, 0xFF0000, 0xFF000000);
		ImageData testImageData = new ImageData(64, 64, bpp * 8, paletteData);
		testImageData.alphaData = new byte[64 * 64];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				for (int k = 0; k < 8; k++) {
					for (int l = 0; l < 8; l++) {
						int x = i * 8 + j;
						int y = k * 8 + l;
						int o = x * 64 + y;
						int a = 255 * i / 7;
						int r = 255 * j / 7;
						int g = 255 * k / 7;
						int b = 255 * l / 7;
						testImageData.data[bpp * o + 0] = (byte) r;
						testImageData.data[bpp * o + 1] = (byte) g;
						testImageData.data[bpp * o + 2] = (byte) b;
						testImageData.data[bpp * o + 3] = (byte) a;
						testImageData.alphaData[o] = (byte) a;
					}
				}
			}
		}
		ImageLoader imageLoader = new ImageLoader();
		imageLoader.data = new ImageData[] { testImageData };
		imageLoader.save("C:\\tmp\\images\\testImage.ico", SWT.IMAGE_ICO);
		imageLoader.save("C:\\tmp\\images\\testImage.bmp", SWT.IMAGE_BMP);
		imageLoader.save("C:\\tmp\\images\\testImage.png", SWT.IMAGE_PNG);

		Display display = new Display();

		Image testImage = new Image(display, (ImageDataProvider) (zoom -> testImageData));

		Image image = new Image(display, 64, 64);

//		ImageData imageData = new ImageData(64, 64, 24, paletteData);
//		imageData.alphaData = new byte[64 * 64];
//		Image image = new Image(display, (ImageDataProvider) (zoom -> imageData));

		boolean testPrinter = true;
		Printer printer = null;
		GC gc;
		if (testPrinter) {
			Shell shell = new Shell(display);
			PrintDialog printDialog = new PrintDialog(shell);
			PrinterData printerData = printDialog.open();
			printer = new Printer(printerData);
			printer.startJob("ImageAlphaSnippet");
			printer.startPage();
			gc = new GC(printer);
		}
		else {
			gc = new GC(image);
		}
//
//		gc.setBackground(new Color(0, 0, 0));
//		gc.fillRectangle(0, 0, 64, 64);
		gc.drawImage(testImage, 0, 0);
		gc.dispose();

		if (printer != null) {
			printer.endPage();
			printer.endJob();
			printer.dispose();
		}

		@SuppressWarnings("restriction")
		ImageData resultImageData = image.getImageData(DPIUtil.getDeviceZoom());
		imageLoader.data = new ImageData[] { resultImageData };
		imageLoader.save("C:\\tmp\\images\\resultImage.ico", SWT.IMAGE_ICO);
		imageLoader.save("C:\\tmp\\images\\resultImage.bmp", SWT.IMAGE_BMP);
		imageLoader.save("C:\\tmp\\images\\resultImage.png", SWT.IMAGE_PNG);

		image.dispose();
		display.dispose();

//		ImageData[] loadedImageDataArray = imageLoader.load("C:\\tmp\\icons\\testIcon.ico");
//		ImageData loadedImageData = loadedImageDataArray[0];
//		loadedImageData.toString();

//		shell.setText("Snippet 1");
//		shell.open ();
//		while (!shell.isDisposed ()) {
//			if (!display.readAndDispatch ()) display.sleep ();
//		}

		display.dispose();
	}

}
