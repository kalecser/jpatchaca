package twitter;

import net.unto.twitter.Api;

import org.picocontainer.Startable;

import basic.Delegate;
import basic.NonEmptyString;

import tasks.delegates.StartTaskData;
import tasks.delegates.StartTaskDelegate;

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
				if (twitterConfig.isTwitterLoggingEnabled().currentValue())
					enqueueTwitterUpdate(task.taskName(), twitterConfig.username().currentValue(), twitterConfig.password().currentValue());
			}
		});
	}

	@Override
	public void stop() {

	}

	private void enqueueTwitterUpdate(final NonEmptyString str, final String username, final String password) {
		new Thread(){
			@Override
			public void run() {
				Api api = Api.builder().username(username).password(password)
				.build();
				try{
					api.updateStatus(str.unbox()).build().post();
				} catch (Exception ex){
					ex.printStackTrace();
				}

			}
		}.start();
	}

}
