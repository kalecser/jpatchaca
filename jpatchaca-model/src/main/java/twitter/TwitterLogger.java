package twitter;

import net.unto.twitter.Api;

import org.picocontainer.Startable;

import tasks.delegates.StartTaskDelegate;
import tasks.tasks.TaskView;
import ui.swing.mainScreen.Delegate;

public class TwitterLogger implements Startable {
	
	private final StartTaskDelegate startTaskDelegate;
	private final TwitterOptions twitterConfig;

	public TwitterLogger(StartTaskDelegate startTaskDelegate, TwitterOptions twitterConfig) {
		this.startTaskDelegate = startTaskDelegate;
		this.twitterConfig = twitterConfig;
	}

	@Override
	public void start() {		
		startTaskDelegate.addListener(new Delegate.Listener<TaskView>(){
			@Override
			public void execute(final TaskView task) {
				if (twitterConfig.isTwitterLoggingEnabled().currentValue())
					enqueueTwitterUpdate(task, twitterConfig.username().currentValue(), twitterConfig.password().currentValue());
			}
		});
	}

	@Override
	public void stop() {

	}

	private void enqueueTwitterUpdate(final TaskView task, final String username, final String password) {
		new Thread(){
			@Override
			public void run() {
				Api api = Api.builder().username(username).password(password)
				.build();
				try{
					api.updateStatus(task.name()).build().post();
				} catch (Exception ex){
					ex.printStackTrace();
				}

			}
		}.start();
	}

}
