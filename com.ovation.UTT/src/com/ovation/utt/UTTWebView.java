package com.ovation.utt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.ProgressEvent;
import org.eclipse.swt.browser.ProgressListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class UTTWebView extends ViewPart {

	private Browser brs=null;
	private boolean browserLoaded=false;
	public UTTWebView()
	{}
	@Override
	public void createPartControl(Composite parent) {
		// TODO Auto-generated method stub
		parent.setLayout(new GridLayout(1,true));
		brs = new Browser(parent, SWT.NONE);
		brs.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		brs.addProgressListener(new ProgressListener() {
			
			@Override
			public void completed(ProgressEvent arg0) {
				browserLoaded = true;
			}

			@Override
			public void changed(ProgressEvent arg0) {
				// TODO Auto-generated method stub
				browserLoaded=false;
			}
			
		
		});
		brs.setUrl(Activator.getDefault().getUTTWebURL());
	}
	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}
