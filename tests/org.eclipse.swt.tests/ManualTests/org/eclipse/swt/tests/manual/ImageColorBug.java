package org.eclipse.swt.tests.manual;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class ImageColorBug {

	public static void main(String[] args) {
        Display display = new Display();
        Shell shell = new Shell(display);
        shell.setText("Image Color Bug");
        shell.setSize(300, 200);
        shell.setLayout(new GridLayout());

        Image image = new Image(display, getImageData());

        shell.addPaintListener(e -> {
            e.gc.drawImage(image, 0, 0);
        });

        shell.open();

        while(!shell.isDisposed()) {
            if(!display.readAndDispatch()) display.sleep();
        }

        display.dispose();
        image.dispose();
    }

    static ImageData getImageData() {
        Image tmp = new Image(Display.getDefault(), 100, 100);

        GC gc = new GC(tmp);
        gc.setBackground(new Color(0, 0, 0));
        gc.fillRectangle(0, 0, 100, 100);
        gc.dispose();

        ImageData id = tmp.getImageData(100);
        tmp.dispose();

        makeOpaque(id);

        return id;
    }

    static void makeOpaque(ImageData imageData) {
    	if (imageData.depth != 32) {
    		return;
    	}
    	byte[] data = imageData.data;
    	for (int dp = 3; dp < data.length; dp += 4) {
    		data[dp] = (byte) 0xFF;
    	}
    }

}
