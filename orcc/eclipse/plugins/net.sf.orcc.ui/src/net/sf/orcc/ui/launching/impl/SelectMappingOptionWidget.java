package net.sf.orcc.ui.launching.impl;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import net.sf.orcc.plugins.Option;
import net.sf.orcc.ui.editor.FilteredRefinementDialog;
import net.sf.orcc.ui.launching.tabs.OrccAbstractSettingsTab;
import net.sf.orcc.util.OrccUtil;

public class SelectMappingOptionWidget extends TextBoxOptionWidget {

	/**
	 * Creates a new selectNetwork option widget.
	 */
	public SelectMappingOptionWidget(OrccAbstractSettingsTab tab,
			Option option, Composite parent) {
		super(tab, option, parent);
	}

	final protected boolean checkNetworkExists(IProject project, String name) {
		IResource file = OrccUtil.getFile(project, name,
				OrccUtil.MAPPING_SUFFIX);
		return (file != null && file.exists());
	}

	@Override
	protected Composite createControl(Composite parent) {
		final Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(3, false));

		GridData data = new GridData(SWT.FILL, SWT.TOP, true, false);
		data.horizontalSpan = 3;
		composite.setLayoutData(data);

		Font font = parent.getFont();

		Label lbl = new Label(composite, SWT.NONE);
		lbl.setFont(font);
		lbl.setText(option.getName() + ":");
		lbl.setToolTipText(option.getDescription());

		data = new GridData(SWT.LEFT, SWT.CENTER, false, false);
		lbl.setLayoutData(data);

		text = new Text(composite, SWT.BORDER | SWT.SINGLE);
		text.setFont(font);
		data = new GridData(SWT.FILL, SWT.CENTER, true, false);
		text.setLayoutData(data);
		text.addModifyListener(this);

		Button buttonBrowse = new Button(composite, SWT.PUSH);
		buttonBrowse.setFont(font);
		data = new GridData(SWT.FILL, SWT.CENTER, false, false);
		buttonBrowse.setLayoutData(data);
		buttonBrowse.setText("&Browse...");
		buttonBrowse.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				IProject project = launchConfigurationTab.getProjectFromText();
				String network = selectNetwork(project, composite.getShell());
				if (network == null) {
					network = "";
				}
				text.setText(network);
			}
		});

		return composite;
	}

	@Override
	public boolean isValid(ILaunchConfiguration launchConfig) {
		if (super.isValid(launchConfig)) {
			IProject project = launchConfigurationTab.getProjectFromText();
			String name = text.getText();
			if (checkNetworkExists(project, name)) {
				return true;
			}

			launchConfigurationTab.setErrorMessage("The mapping configuration \"" + name
					+ "\" specified by option \"" + option.getName()
					+ "\" does not exist");
		}
		return false;
	}

	/**
	 * Selects the qualified identifier of a network.
	 * 
	 * @param vertex
	 *            a vertex
	 * @param shell
	 *            shell
	 * @return the qualified identifier of a network
	 */
	final protected String selectNetwork(IProject project, Shell shell) {
		FilteredRefinementDialog dialog = new FilteredRefinementDialog(project,
				shell, OrccUtil.MAPPING_SUFFIX);
		dialog.setTitle("Select mapping configuration");
		dialog.setMessage("&Select existing mapping:");
		String refinement = text.getText();
		if (refinement != null) {
			dialog.setInitialPattern(refinement);
		}
		int result = dialog.open();
		if (result == Window.OK) {
			return (String) dialog.getFirstResult();
		}

		return null;
	}
}
