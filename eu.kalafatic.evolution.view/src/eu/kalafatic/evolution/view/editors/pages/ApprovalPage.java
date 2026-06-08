package eu.kalafatic.evolution.view.editors.pages;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.MessageBox;
import java.io.File;
import java.util.List;

import eu.kalafatic.evolution.model.orchestration.Orchestrator;
import eu.kalafatic.evolution.model.orchestration.Task;
import eu.kalafatic.evolution.view.editors.MultiPageEditor;
import eu.kalafatic.evolution.view.editors.pages.approval.*;

public class ApprovalPage extends AEvoPage {

	private SummaryGroup summaryGroup;
	private SelfDevSessionGroup sessionGroup;
	private ReviewGroup reviewGroup;
	private FeedbackGroup feedbackGroup;
	private ProposedTasksGroup proposedTasksGroup;
	private ActionsGroup actionsGroup;

	private Adapter modelAdapter = new EContentAdapter() {
		@Override public void notifyChanged(Notification notification) {
			super.notifyChanged(notification);
			if (notification.isTouch()) return;
			scheduleRefresh();
		}
	};

	public ApprovalPage(Composite parent, MultiPageEditor editor, Orchestrator orchestrator) {
		super(parent, editor, orchestrator);
		createControl();
	}

	private void createControl() {
		this.setLayout(new GridLayout(1, false));

		// Summary Group
		Group summaryGroup = SWTFactory.createGroup(this, "Approval Summary", 2);
		SWTFactory.createLabel(summaryGroup, "Session ID:");
		sessionIdLabel = new Label(summaryGroup, SWT.NONE);
		sessionIdLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		SWTFactory.createLabel(summaryGroup, "Status:");
		statusLabel = new Label(summaryGroup, SWT.NONE);
		statusLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		SWTFactory.createLabel(summaryGroup, "Iterations:");
		iterationsLabel = new Label(summaryGroup, SWT.NONE);
		iterationsLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		SWTFactory.createLabel(summaryGroup, "Git Branch:");
		branchLabel = new Label(summaryGroup, SWT.NONE);
		branchLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		// Task Management Group
		Group taskGroup = SWTFactory.createGroup(this, "Proposed Tasks", 1);
		taskGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		((GridData)taskGroup.getLayoutData()).heightHint = 180;

		Composite taskTableComposite = new Composite(taskGroup, SWT.NONE);
		taskTableComposite.setLayout(new GridLayout(2, false));
		taskTableComposite.setLayoutData(new GridData(GridData.FILL_BOTH));

		tableViewer = new TableViewer(taskTableComposite, SWT.BORDER | SWT.FULL_SELECTION | SWT.V_SCROLL);
		Table table = tableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setLayoutData(new GridData(GridData.FILL_BOTH));

		createColumns();
		tableViewer.setContentProvider(ArrayContentProvider.getInstance());

		Composite taskActions = new Composite(taskTableComposite, SWT.NONE);
		taskActions.setLayout(new GridLayout(1, false));
		taskActions.setLayoutData(new GridData(SWT.BEGINNING, SWT.FILL, false, false));

		Button upBtn = SWTFactory.createButton(taskActions, "Move Up", 80);
		upBtn.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			@Override
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				handleMoveTask(-1);
			}
		});

		Button downBtn = SWTFactory.createButton(taskActions, "Move Down", 80);
		downBtn.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			@Override
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				handleMoveTask(1);
			}
		});

		Button deleteBtn = SWTFactory.createButton(taskActions, "Delete", 80);
		deleteBtn.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			@Override
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				handleDeleteTask();
			}
		});

		// Visualization Area
		Group vizGroup = SWTFactory.createGroup(this, "AI Network & Process Flow", 1);
		vizGroup.setLayoutData(new GridData(GridData.FILL_BOTH));
		vizGroup.setLayout(new FillLayout());

		browser = new Browser(vizGroup, SWT.NONE);
		browser.addProgressListener(new ProgressAdapter() {
			@Override
			public void completed(ProgressEvent event) {
				isLoaded = true;
				refreshBrowser();
			}
		});
		browser.addLocationListener(new LocationAdapter() {
			@Override
			public void changing(LocationEvent event) {
				if (event.location.startsWith("file://") || event.location.equals("about:blank")) {
					if (!event.location.equals("about:blank")) {
						event.doit = false;
						browser.setText(getHtmlTemplate());
					}
				}
			}
		});
		browser.setText(getHtmlTemplate());

		// Actions Area
		Group actionsGroup = SWTFactory.createGroup(this, "Review Actions", 2);
		Button approveBtn = SWTFactory.createButton(actionsGroup, "Approve & Apply", 150);
		approveBtn.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			@Override
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				handleApprove();
			}
		});

		Button rejectBtn = SWTFactory.createButton(actionsGroup, "Reject & Request Changes", 200);
		rejectBtn.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			@Override
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				handleReject();
			}
		});
	}

	@Override
	public void setOrchestrator(Orchestrator orchestrator) {
		if (this.orchestrator != null) this.orchestrator.eAdapters().remove(modelAdapter);
		super.setOrchestrator(orchestrator);
		if (this.orchestrator != null) this.orchestrator.eAdapters().add(modelAdapter);
	}

	public void createColumns(TableViewer tableViewer) {
		TableViewerColumn colName = new TableViewerColumn(tableViewer, SWT.NONE);
		colName.getColumn().setText("Task Name (Click to edit)"); colName.getColumn().setWidth(300);
		colName.setLabelProvider(new ColumnLabelProvider() { @Override public String getText(Object element) { return ((Task)element).getName(); } });
		colName.setEditingSupport(new org.eclipse.jface.viewers.EditingSupport(tableViewer) {
			@Override protected void setValue(Object element, Object value) { ((Task)element).setName(String.valueOf(value)); editor.setDirty(true); tableViewer.update(element, null); }
			@Override protected Object getValue(Object element) { return ((Task)element).getName(); }
			@Override protected org.eclipse.jface.viewers.CellEditor getCellEditor(Object element) { return new org.eclipse.jface.viewers.TextCellEditor(tableViewer.getTable()); }
			@Override protected boolean canEdit(Object element) { return true; }
		});
		TableViewerColumn colType = new TableViewerColumn(tableViewer, SWT.NONE);
		colType.getColumn().setText("Type"); colType.getColumn().setWidth(100);
		colType.setLabelProvider(new ColumnLabelProvider() { @Override public String getText(Object element) { return ((Task)element).getType(); } });
		TableViewerColumn colStatus = new TableViewerColumn(tableViewer, SWT.NONE);
		colStatus.getColumn().setText("Status"); colStatus.getColumn().setWidth(100);
		colStatus.setLabelProvider(new ColumnLabelProvider() { @Override public String getText(Object element) { return ((Task)element).getStatus().toString(); } });

		TableViewerColumn colIterative = new TableViewerColumn(tableViewer, SWT.NONE);
		colIterative.getColumn().setText("Iterative"); colIterative.getColumn().setWidth(70);
		colIterative.setLabelProvider(new ColumnLabelProvider() { @Override public String getText(Object element) { return ((Task)element).isIterativeMode() ? "YES" : "NO"; } });
		colIterative.setEditingSupport(new org.eclipse.jface.viewers.EditingSupport(tableViewer) {
			@Override protected void setValue(Object element, Object value) { ((Task)element).setIterativeMode((Boolean)value); editor.setDirty(true); tableViewer.update(element, null); }
			@Override protected Object getValue(Object element) { return ((Task)element).isIterativeMode(); }
			@Override protected org.eclipse.jface.viewers.CellEditor getCellEditor(Object element) { return new org.eclipse.jface.viewers.CheckboxCellEditor(tableViewer.getTable()); }
			@Override protected boolean canEdit(Object element) { return true; }
		});

		TableViewerColumn colSelfDev = new TableViewerColumn(tableViewer, SWT.NONE);
		colSelfDev.getColumn().setText("Self-Dev"); colSelfDev.getColumn().setWidth(70);
		colSelfDev.setLabelProvider(new ColumnLabelProvider() { @Override public String getText(Object element) { return ((Task)element).isSelfIterativeMode() ? "YES" : "NO"; } });
		colSelfDev.setEditingSupport(new org.eclipse.jface.viewers.EditingSupport(tableViewer) {
			@Override protected void setValue(Object element, Object value) { ((Task)element).setSelfIterativeMode((Boolean)value); editor.setDirty(true); tableViewer.update(element, null); }
			@Override protected Object getValue(Object element) { return ((Task)element).isSelfIterativeMode(); }
			@Override protected org.eclipse.jface.viewers.CellEditor getCellEditor(Object element) { return new org.eclipse.jface.viewers.CheckboxCellEditor(tableViewer.getTable()); }
			@Override protected boolean canEdit(Object element) { return true; }
		});

		TableViewerColumn colDarwin = new TableViewerColumn(tableViewer, SWT.NONE);
		colDarwin.getColumn().setText("Darwin"); colDarwin.getColumn().setWidth(70);
		colDarwin.setLabelProvider(new ColumnLabelProvider() { @Override public String getText(Object element) { return ((Task)element).isDarwinMode() ? "YES" : "NO"; } });
		colDarwin.setEditingSupport(new org.eclipse.jface.viewers.EditingSupport(tableViewer) {
			@Override protected void setValue(Object element, Object value) { ((Task)element).setDarwinMode((Boolean)value); editor.setDirty(true); tableViewer.update(element, null); }
			@Override protected Object getValue(Object element) { return ((Task)element).isDarwinMode(); }
			@Override protected org.eclipse.jface.viewers.CellEditor getCellEditor(Object element) { return new org.eclipse.jface.viewers.CheckboxCellEditor(tableViewer.getTable()); }
			@Override protected boolean canEdit(Object element) { return true; }
		});

		TableViewerColumn colAutoGit = new TableViewerColumn(tableViewer, SWT.NONE);
		colAutoGit.getColumn().setText("Auto-Git"); colAutoGit.getColumn().setWidth(70);
		colAutoGit.setLabelProvider(new ColumnLabelProvider() { @Override public String getText(Object element) { return ((Task)element).isGitAutomation() ? "YES" : "NO"; } });
		colAutoGit.setEditingSupport(new org.eclipse.jface.viewers.EditingSupport(tableViewer) {
			@Override protected void setValue(Object element, Object value) { ((Task)element).setGitAutomation((Boolean)value); editor.setDirty(true); tableViewer.update(element, null); }
			@Override protected Object getValue(Object element) { return ((Task)element).isGitAutomation(); }
			@Override protected org.eclipse.jface.viewers.CellEditor getCellEditor(Object element) { return new org.eclipse.jface.viewers.CheckboxCellEditor(tableViewer.getTable()); }
			@Override protected boolean canEdit(Object element) { return true; }
		});

		TableViewerColumn colMaxIter = new TableViewerColumn(tableViewer, SWT.NONE);
		colMaxIter.getColumn().setText("Max Iter"); colMaxIter.getColumn().setWidth(70);
		colMaxIter.setLabelProvider(new ColumnLabelProvider() { @Override public String getText(Object element) { return String.valueOf(((Task)element).getMaxIterations()); } });
		colMaxIter.setEditingSupport(new org.eclipse.jface.viewers.EditingSupport(tableViewer) {
			@Override protected void setValue(Object element, Object value) { try { ((Task)element).setMaxIterations(Integer.parseInt(String.valueOf(value))); editor.setDirty(true); tableViewer.update(element, null); } catch (Exception e) {} }
			@Override protected Object getValue(Object element) { return String.valueOf(((Task)element).getMaxIterations()); }
			@Override protected org.eclipse.jface.viewers.CellEditor getCellEditor(Object element) { return new org.eclipse.jface.viewers.TextCellEditor(tableViewer.getTable()); }
			@Override protected boolean canEdit(Object element) { return true; }
		});
	}

	public void handleMoveTask(int direction) {
		IStructuredSelection selection = (IStructuredSelection) proposedTasksGroup.getTableViewer().getSelection();
		if (selection.isEmpty()) return;
		Task task = (Task) selection.getFirstElement();
		int index = orchestrator.getTasks().indexOf(task);
		int newIndex = index + direction;
		if (newIndex >= 0 && newIndex < orchestrator.getTasks().size()) {
			orchestrator.getTasks().move(newIndex, index); editor.setDirty(true); proposedTasksGroup.getTableViewer().refresh();
		}
	}

	public void handleDeleteTask() {
		IStructuredSelection selection = (IStructuredSelection) proposedTasksGroup.getTableViewer().getSelection();
		if (selection.isEmpty()) return;
		Task task = (Task) selection.getFirstElement();
		orchestrator.getTasks().remove(task); editor.setDirty(true); proposedTasksGroup.getTableViewer().refresh();
	}

	@Override
	protected void refreshUI() {
		if (isDisposed()) return;
		summaryGroup.updateUI();
		sessionGroup.updateUI();
		reviewGroup.updateUI();
		feedbackGroup.updateUI();
		proposedTasksGroup.updateUI();
		this.reflow(true);
	}

	public void updateUI() {
		scheduleRefresh();
	}

	public void submitFeedback(int satisfaction, String comments) {
		if (orchestrator != null) {
			if (orchestrator.getSelfDevSession() != null && !orchestrator.getSelfDevSession().getIterations().isEmpty()) {
				eu.kalafatic.evolution.model.orchestration.Iteration last = orchestrator.getSelfDevSession().getIterations().get(orchestrator.getSelfDevSession().getIterations().size() - 1);
				last.setRating(satisfaction);
				last.setComments(comments);
			}

			// Centralized Feedback Persistence
			eu.kalafatic.evolution.controller.services.FeedbackService.getInstance().recordFeedback(orchestrator, "coding", satisfaction);

			eu.kalafatic.evolution.controller.manager.NeuronService.getInstance().train(orchestrator, comments, "coding", satisfaction);
			editor.setDirty(true);
			scheduleRefresh();
			MessageBox mb = new MessageBox(getShell(), SWT.ICON_INFORMATION | SWT.OK); mb.setText("Thank You"); mb.setMessage("Your feedback has been recorded and will be used to improve the AI."); mb.open();
		}
	}

	public void handleApprove() {
		if (editor.getCurrentContext() != null) {
			editor.getCurrentContext().provideApproval(true);
			MessageBox mb = new MessageBox(getShell(), SWT.ICON_INFORMATION | SWT.OK); mb.setText("Approval Confirmed"); mb.setMessage("Approval confirmed and orchestration will continue."); mb.open();
			editor.showAiChatPage();
		} else {
			MessageBox mb = new MessageBox(getShell(), SWT.ICON_INFORMATION | SWT.OK); mb.setText("Approval Confirmed"); mb.setMessage("Approval confirmed and changes applied to the system."); mb.open();
			editor.showAiChatPage();
		}
	}

	private void handleApprove() {
		if (editor.getCurrentContext() != null) {
			editor.getCurrentContext().setAutoApprove(true);
			editor.getCurrentContext().provideApproval(true);
			MessageBox mb = new MessageBox(getShell(), SWT.ICON_INFORMATION | SWT.OK); mb.setText("Auto-Approval Enabled"); mb.setMessage("All remaining tasks in the current orchestration loop will be automatically approved."); mb.open();
			editor.showAiChatPage();
		} else {
			handleApprove();
		}
	}

	public void handleReject() {
		if (editor.getCurrentContext() != null) {
			editor.getCurrentContext().provideApproval(false);
			MessageBox mb = new MessageBox(getShell(), SWT.ICON_WARNING | SWT.OK); mb.setText("Changes Requested"); mb.setMessage("Changes rejected. Orchestration aborted."); mb.open();
		} else {
			MessageBox mb = new MessageBox(getShell(), SWT.ICON_WARNING | SWT.OK); mb.setText("Changes Requested"); mb.setMessage("Changes rejected. Feedback sent to the autonomous agent for refinement."); mb.open();
		}
	}

	@Override
	public void dispose() {
		if (orchestrator != null) orchestrator.eAdapters().remove(modelAdapter);
		super.dispose();
	}
}
