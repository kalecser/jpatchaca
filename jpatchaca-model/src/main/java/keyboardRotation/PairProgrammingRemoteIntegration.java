package keyboardRotation;

import org.picocontainer.Startable;
import org.reactive.Receiver;

import lang.Maybe;
import basic.Subscriber;
import tasks.taskName.ActiveTaskName;
import tasks.taskName.TaskName;

public class PairProgrammingRemoteIntegration implements Startable{

	private final PairProgrammingRemoteIntegrationGui gui;
	protected Maybe<String> remotePeer = null;
	private final ActiveTaskName task;
	private final Network net;
	private final KeyboardRotationOptions options;

	public PairProgrammingRemoteIntegration(TurnOnKeyboardRotationAlert turnOn, ActiveTaskName task, KeyboardRotationOptions options, PairProgrammingRemoteIntegrationGui gui){
		this(turnOn,task,options,gui, new NetworkImpl());
	}
	
	public PairProgrammingRemoteIntegration(TurnOnKeyboardRotationAlert turnOn, ActiveTaskName task, KeyboardRotationOptions options, PairProgrammingRemoteIntegrationGui gui, Network net) {
		this.gui = gui;
		this.task = task;
		this.net = net;
		this.options = options;
		turnOn.register(new Subscriber() {  @Override public void fire() {
				keyboardRotationWasTurnedOn();
		}});
		task.addReceiver(new Receiver<Maybe<TaskName>>() {  @Override public void receive(Maybe<TaskName> value) {
			startTaskOnRemotePeer(value);
		} });
	}

	protected void keyboardRotationWasTurnedOn() {
		if (!options.isRemoteIntegrationActive()) return;
		askForNewRemotePeer();
	}

	private void askForNewRemotePeer() {
		remotePeer = null;
		gui.requestRemotePeer(new PairProgrammingRemoteIntegrationGuiCallback() {  @Override public void onRemotePeer(String peer) {
				updatePeer(peer);
		}});
	}

	private void updatePeer(String peer) {
		remotePeer = Maybe.wrap(peer);
		Maybe<TaskName> activeTask = task.currentValue();
		startTaskOnRemotePeer(activeTask);
	}

	private void startTaskOnRemotePeer(Maybe<TaskName> activeTask) {
		if (activeTask == null) return;
		if (remotePeer == null) return;
		net.sendTo("starttask " +activeTask.unbox().toNonEmptyString().toString(), remotePeer.unbox());
	}

	@Override
	public void start() {}

	@Override
	public void stop() {}

}
