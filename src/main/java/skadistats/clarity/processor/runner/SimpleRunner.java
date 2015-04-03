package skadistats.clarity.processor.runner;

import skadistats.clarity.decoder.DemoInputStream;

import java.io.IOException;
import java.io.InputStream;

public class SimpleRunner extends AbstractRunner<SimpleRunner> {

    public SimpleRunner(InputStream inputStream) throws IOException {
        DemoInputStream dis = new DemoInputStream(inputStream);
        dis.ensureDemHeader();
        cis = dis.newCodedInputStream();
    }

    @Override
    protected Source getSource() {
        return new AbstractSource() {
            @Override
            public LoopControlCommand doLoopControl(Context ctx, int upcomingTick) {
                processorTick = upcomingTick;
                if (processorTick != Integer.MAX_VALUE) {
                    endTicksUntil(ctx, processorTick - 1);
                    startNewTick(ctx);
                } else {
                    endTicksUntil(ctx, tick);
                }
                return LoopControlCommand.FALLTHROUGH;
            }
        };
    }

}
