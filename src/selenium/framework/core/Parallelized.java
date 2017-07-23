package selenium.framework.core;
// Inspired by http://hwellmann.blogspot.com/2009/12/running-parameterized-junit-tests-in.html


import org.junit.runners.model.RunnerScheduler;
import org.junit.runners.Parameterized;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Parallelized extends Parameterized
{
    
    private static class ThreadPoolScheduler implements RunnerScheduler
    {
        private ExecutorService executor; 
        
        public ThreadPoolScheduler()
        {
            executor = Executors.newCachedThreadPool();
        }
        
        @Override
        public void finished()
        {
            executor.shutdown();
            try
            {
                executor.awaitTermination(50, TimeUnit.HOURS);
            }
            catch (InterruptedException exc)
            {
                throw new RuntimeException(exc);
            }
        }

        @Override
        public void schedule(Runnable childStatement)
        {
            executor.submit(childStatement);
        }
    }

    @SuppressWarnings("unchecked")
	public Parallelized(Class klass) throws Throwable
    {
        super(klass);
        setScheduler(new ThreadPoolScheduler());
    }
}