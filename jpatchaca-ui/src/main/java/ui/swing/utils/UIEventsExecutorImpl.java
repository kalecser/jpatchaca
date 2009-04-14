/*
 * Created on 14/04/2009
 */
package ui.swing.utils;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public final class UIEventsExecutorImpl implements UIEventsExecutor {

	final static class UncaughtExceptionHandlerDecorator implements Runnable {

		private final Runnable decorated;
		private final UncaughtExceptionHandler handler;

		public UncaughtExceptionHandlerDecorator(final Runnable decorated,
				final UncaughtExceptionHandler handler) {
			this.decorated = decorated;
			this.handler = handler;
		}

		@Override
		public void run() {
			Thread.setDefaultUncaughtExceptionHandler(handler);
			decorated.run();
		}

	}

	final static class UIEventsThreadFactory implements ThreadFactory {

		private final ThreadFactory delegate;
		private final PatchacaUncaughtExceptionHandler handler;

		public UIEventsThreadFactory(
				final PatchacaUncaughtExceptionHandler handler) {
			this.delegate = Executors.defaultThreadFactory();
			this.handler = handler;
		}

		@Override
		public Thread newThread(final Runnable r) {
			return delegate.newThread(new UncaughtExceptionHandlerDecorator(r,
					handler));
		}
	}

	private final Executor delegate;

	public UIEventsExecutorImpl(final PatchacaUncaughtExceptionHandler handler) {
		this.delegate = Executors
				.newSingleThreadExecutor(new UIEventsThreadFactory(handler));
	}

	@Override
	public void execute(final Runnable command) {
		delegate.execute(command);
	}

}
