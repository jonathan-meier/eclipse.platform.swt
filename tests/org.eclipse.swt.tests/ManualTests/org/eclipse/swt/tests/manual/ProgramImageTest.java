package org.eclipse.swt.tests.manual;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class ProgramImageTest {

    public static void main(String[] args) {
        final Display display = new Display();

        final List<Image> images = createImages(display);

        final Shell shell = new Shell(display);

        shell.addListener(SWT.Paint, event -> {
            int x = 0;
            int y = 0;
            for(Image image : images) {
                event.gc.drawImage(image, x, y);
                x += image.getImageData().width;
                x += 10;
                if(x > 400) {
                    x = 0;
                    y += 24;
                }
            }
        });

//        shell.setBackground(new Color(255, 0, 0));
        shell.setSize(400, 300);
        shell.open();

        while(!shell.isDisposed()) {
            if(!display.readAndDispatch()) {
                display.sleep();
            }
        }

        for(Image image : images) {
            image.dispose();
        }

        display.dispose();
    }

    private static void maskColor(ImageData imageData) {
		byte[] maskData = imageData.maskData;
		if (maskData == null || imageData.depth != 32) {
			return;
		}

		byte[] data = imageData.data;
		int stride = maskData.length / imageData.height;
		for (int i = 0, dp = 0, mbp = 0; i < imageData.height; i++, mbp += stride) {
			for (int j = 0, mp = mbp; j < imageData.width; mp++) {
				for (int b = 7; b >= 0 && j < imageData.width; b--, j++, dp++) {
					int mask = (maskData[mp] >> b) & 0x1;
					for (int c = 0; c < 3; c++, dp++) {
						data[dp] = (byte) (data[dp] * mask);
					}
				}
			}
		}

    }

    private static List<Image> createImages(Display display) {
        final List<Image> images = new ArrayList<>();

        Program[] programs = Program.getPrograms();
        Program p = programs[624];
        ImageData imageData = p.getImageData();

        maskColor(imageData);

//		byte[] maskData = imageData.maskData;
//		if (maskData != null) {
//			int bbp = imageData.depth / 8;
//			byte[] data = imageData.data;
//			int stride = maskData.length / imageData.height;
//			for (int i = 0, dp = 0, mbp = 0; i < imageData.height; i++, mbp += stride) {
//				for (int j = 0, mp = mbp; j < imageData.width; mp++) {
//					for (int b = 7; b >= 0 && j < imageData.width; b--, j++) {
//						int mask = (maskData[mp] >> b) & 0x1;
//						for (int c = 0; c < bbp; c++, dp++) {
//							data[dp] = mask == 1 ? data[dp] : -128;
////							data[dp] = (byte) (data[dp] * mask);
//						}
////						if (data[dp] != (byte) (data[dp] * ((maskData[mp] >> b) & 0x1))) {
////							System.out.println("halt");
////						}
//					}
//				}
//			}
//		}

//        if (imageData.depth == 32 && imageData.alphaData == null) {
//        	int size = imageData.width * imageData.height;
//        	byte[] alphaData = imageData.alphaData = new byte[size];
//        	byte[] data = imageData.data;
//        	for (int ap = 0, dp = 3; ap < size; ap++, dp += 4) {
//        		alphaData[ap] = data[dp];
//        	}
//        }

        Image i = new Image(display, imageData);
        return List.of(i);
//        for(int i = 625; i < programs.length; i++) {
//        	Program p = programs[i];
//            final ImageData data = p.getImageData();
//            if(data == null) {
//                continue;
//            }
//
//            images.add(new Image(display, data));
//        }
//
//        return images;
    }
}
