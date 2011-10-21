package twitter;

import net.unto.twitter.Api;

import org.picocontainer.Startable;

import tasks.delegates.StartTaskData;
import tasks.delegates.StartTaskDelegate;
import basic.Delegate;

public class TwitterLogger implements Startable {
	
	private final StartTaskDelegate startTaskDelegate;
	private final TwitterOptions twitterConfig;

	public TwitterLogger(StartTaskDelegate startTaskDelegate, TwitterOptions twitterConfig) {
		this.startTaskDelegate = startTaskDelegate;
		this.twitterConfig = twitterConfig;
	}

	@Override
	public void start() {		
		startTaskDelegate.addListener(new Delegate.Listener<StartTaskData>(){
			@Override
			public void execute(final StartTaskData task) {
				onReceiveStartTaskData(task);
			}
		});
	}

	@Override
	public void stop() {
		// Nothing to do
	}

	private void enqueueTwitterUpdate(final String str, final String username, final String password) {
		new Thread(){
			@Override
			public void run() {
				Api api = Api.builder().username(username).password(password)
				.build();
				try{
					api.updateStatus(str).build().post();
				} catch (Exception ex){
					ex.printStackTrace();
				}

			}
		}.start();
	}

	void onReceiveStartTaskData(final StartTaskData task) {
		if (twitterConfig.isTwitterLoggingEnabled().currentValue().booleanValue())
			enqueueTwitterUpdate(task.taskData().getTaskName(), twitterConfig.username().currentValue(), twitterConfig.password().currentValue());
	}

}
