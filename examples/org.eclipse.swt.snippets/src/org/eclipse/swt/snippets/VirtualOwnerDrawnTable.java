package org.eclipse.swt.snippets;

import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

/**
 * @author Thomas Singer
 */
public class VirtualOwnerDrawnTable {

	public static void main(String[] args) {
		final Display display = new Display();

		ImageData srcImageData = new ImageData(32, 16, 32, new PaletteData(0xff, 0xff00, 0xff0000));
		srcImageData.alphaData = new byte[32 * 16];
//		Image srcImage = new Image(display, srcImageData);
		Image srcImage = new Image(display, 32, 16);
		GC gc = new GC(srcImage);
		gc.setAdvanced(true);
		gc.setBackground(new Color(255, 0, 0));
		gc.fillRectangle(0, 0, 16, 16);
		gc.setBackground(new Color(0, 255, 0));
		gc.fillRectangle(16, 0, 16, 16);
		gc.dispose();

		Shell shell = new Shell(display);

		Composite container = new Composite(shell, SWT.NONE);
		container.setLayout(new RowLayout(SWT.VERTICAL));

		Label label1 = new Label(container, SWT.NONE);
		label1.setText("Reference image should show a red and a green square: ");

		Canvas canvas1 = new Canvas(container, SWT.NONE);
		canvas1.setSize(32, 16);
		canvas1.addPaintListener(e -> e.gc.drawImage(srcImage, 0, 0));

		Label label2 = new Label(container, SWT.NONE);
		label2.setText("Control image should show a green square: ");

		Canvas canvas2 = new Canvas(container, SWT.NONE);
		canvas2.setSize(16, 16);
		canvas2.addPaintListener(e -> e.gc.drawImage(srcImage, 16, 0, 16, 16, 0, 0, 16, 16));

		container.pack();
		shell.setSize(400, 250);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}

		srcImage.dispose();
		display.dispose();
	}
}
