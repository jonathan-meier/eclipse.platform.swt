package org.eclipse.swt.tests.manual;

import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

/**
 * @author Thomas Singer
 */
public class VirtualOwnerDrawnTable {

	// Static =================================================================

	public static void main(String[] args) {
		final Display display = new Display();

		Image srcImage = new Image(display, 32, 16);
		GC gc = new GC(srcImage);

		Image srcImagePart1 = new Image(display, "/eclipse16.png");
		Image srcImagePart2 = new Image(display, "/warning.png");

		gc.drawImage(srcImagePart1, 0, 0);
		gc.drawImage(srcImagePart2, 16, 0);

		gc.dispose();

		Image destImage = new Image(display, 16, 16);
		gc = new GC(destImage);
		gc.drawImage(srcImage, 16, 0, 16, 16, 0, 0, 16, 16);
		gc.dispose();

		Shell shell = new Shell(display);

		Canvas canvas = new Canvas (shell, SWT.NONE);
		canvas.addPaintListener (e -> e.gc.drawImage (srcImage, 0, 0));

		shell.setSize(400, 500);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}

		srcImage.dispose();
		destImage.dispose();
		display.dispose();
	}
}
