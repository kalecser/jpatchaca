package reactive;

import org.reactive.Receiver;
import org.reactive.Signal;
import org.reactive.Source;

public class Adapter<IN, OUT> {

	Source<OUT> output;
	public Adapter(Source<IN> in, final Functor<IN, OUT> functor){
		output = new Source<OUT>(functor.evaluate(in.currentValue()));
		
		in.addReceiver(new Receiver<IN>() {
			@Override
			public void receive(IN value) {
				output.supply(functor.evaluate(value));
			}
		});
	}

	public Signal<OUT> output() {
		return output;
	}

}
