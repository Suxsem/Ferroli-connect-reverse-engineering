package org.eclipse.jetty.server.handler;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.continuation.Continuation;
import org.eclipse.jetty.continuation.ContinuationListener;
import org.eclipse.jetty.server.AsyncContinuation;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.util.statistic.CounterStatistic;
import org.eclipse.jetty.util.statistic.SampleStatistic;

public class StatisticsHandler extends HandlerWrapper {
    private final CounterStatistic _dispatchedStats = new CounterStatistic();
    private final SampleStatistic _dispatchedTimeStats = new SampleStatistic();
    /* access modifiers changed from: private */
    public final AtomicInteger _expires = new AtomicInteger();
    private final ContinuationListener _onCompletion = new ContinuationListener() {
        public void onComplete(Continuation continuation) {
            Request baseRequest = ((AsyncContinuation) continuation).getBaseRequest();
            long currentTimeMillis = System.currentTimeMillis() - baseRequest.getTimeStamp();
            StatisticsHandler.this._requestStats.decrement();
            StatisticsHandler.this._requestTimeStats.set(currentTimeMillis);
            StatisticsHandler.this.updateResponse(baseRequest);
            if (!continuation.isResumed()) {
                StatisticsHandler.this._suspendStats.decrement();
            }
        }

        public void onTimeout(Continuation continuation) {
            StatisticsHandler.this._expires.incrementAndGet();
        }
    };
    /* access modifiers changed from: private */
    public final CounterStatistic _requestStats = new CounterStatistic();
    /* access modifiers changed from: private */
    public final SampleStatistic _requestTimeStats = new SampleStatistic();
    private final AtomicInteger _responses1xx = new AtomicInteger();
    private final AtomicInteger _responses2xx = new AtomicInteger();
    private final AtomicInteger _responses3xx = new AtomicInteger();
    private final AtomicInteger _responses4xx = new AtomicInteger();
    private final AtomicInteger _responses5xx = new AtomicInteger();
    private final AtomicLong _responsesTotalBytes = new AtomicLong();
    private final AtomicInteger _resumes = new AtomicInteger();
    private final AtomicLong _statsStartedAt = new AtomicLong();
    /* access modifiers changed from: private */
    public final CounterStatistic _suspendStats = new CounterStatistic();

    public void statsReset() {
        this._statsStartedAt.set(System.currentTimeMillis());
        this._requestStats.reset();
        this._requestTimeStats.reset();
        this._dispatchedStats.reset();
        this._dispatchedTimeStats.reset();
        this._suspendStats.reset();
        this._resumes.set(0);
        this._expires.set(0);
        this._responses1xx.set(0);
        this._responses2xx.set(0);
        this._responses3xx.set(0);
        this._responses4xx.set(0);
        this._responses5xx.set(0);
        this._responsesTotalBytes.set(0);
    }

    public void handle(String str, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        long j;
        long currentTimeMillis;
        boolean isSuspended;
        this._dispatchedStats.increment();
        AsyncContinuation asyncContinuation = request.getAsyncContinuation();
        if (asyncContinuation.isInitial()) {
            this._requestStats.increment();
            j = request.getTimeStamp();
        } else {
            j = System.currentTimeMillis();
            this._suspendStats.decrement();
            if (asyncContinuation.isResumed()) {
                this._resumes.incrementAndGet();
            }
        }
        try {
            super.handle(str, request, httpServletRequest, httpServletResponse);
            if (!isSuspended) {
                if (asyncContinuation.isInitial()) {
                    this._requestStats.decrement();
                    this._requestTimeStats.set(currentTimeMillis);
                    updateResponse(request);
                }
            }
        } finally {
            currentTimeMillis = System.currentTimeMillis() - j;
            this._dispatchedStats.decrement();
            this._dispatchedTimeStats.set(currentTimeMillis);
            if (asyncContinuation.isSuspended()) {
                if (asyncContinuation.isInitial()) {
                    asyncContinuation.addContinuationListener(this._onCompletion);
                }
                this._suspendStats.increment();
            } else if (asyncContinuation.isInitial()) {
                this._requestStats.decrement();
                this._requestTimeStats.set(currentTimeMillis);
                updateResponse(request);
            }
        }
    }

    /* access modifiers changed from: private */
    public void updateResponse(Request request) {
        Response response = request.getResponse();
        int status = response.getStatus() / 100;
        if (status == 1) {
            this._responses1xx.incrementAndGet();
        } else if (status == 2) {
            this._responses2xx.incrementAndGet();
        } else if (status == 3) {
            this._responses3xx.incrementAndGet();
        } else if (status == 4) {
            this._responses4xx.incrementAndGet();
        } else if (status == 5) {
            this._responses5xx.incrementAndGet();
        }
        this._responsesTotalBytes.addAndGet(response.getContentCount());
    }

    /* access modifiers changed from: protected */
    public void doStart() throws Exception {
        super.doStart();
        statsReset();
    }

    public int getRequests() {
        return (int) this._requestStats.getTotal();
    }

    public int getRequestsActive() {
        return (int) this._requestStats.getCurrent();
    }

    public int getRequestsActiveMax() {
        return (int) this._requestStats.getMax();
    }

    public long getRequestTimeMax() {
        return this._requestTimeStats.getMax();
    }

    public long getRequestTimeTotal() {
        return this._requestTimeStats.getTotal();
    }

    public double getRequestTimeMean() {
        return this._requestTimeStats.getMean();
    }

    public double getRequestTimeStdDev() {
        return this._requestTimeStats.getStdDev();
    }

    public int getDispatched() {
        return (int) this._dispatchedStats.getTotal();
    }

    public int getDispatchedActive() {
        return (int) this._dispatchedStats.getCurrent();
    }

    public int getDispatchedActiveMax() {
        return (int) this._dispatchedStats.getMax();
    }

    public long getDispatchedTimeMax() {
        return this._dispatchedTimeStats.getMax();
    }

    public long getDispatchedTimeTotal() {
        return this._dispatchedTimeStats.getTotal();
    }

    public double getDispatchedTimeMean() {
        return this._dispatchedTimeStats.getMean();
    }

    public double getDispatchedTimeStdDev() {
        return this._dispatchedTimeStats.getStdDev();
    }

    public int getSuspends() {
        return (int) this._suspendStats.getTotal();
    }

    public int getSuspendsActive() {
        return (int) this._suspendStats.getCurrent();
    }

    public int getSuspendsActiveMax() {
        return (int) this._suspendStats.getMax();
    }

    public int getResumes() {
        return this._resumes.get();
    }

    public int getExpires() {
        return this._expires.get();
    }

    public int getResponses1xx() {
        return this._responses1xx.get();
    }

    public int getResponses2xx() {
        return this._responses2xx.get();
    }

    public int getResponses3xx() {
        return this._responses3xx.get();
    }

    public int getResponses4xx() {
        return this._responses4xx.get();
    }

    public int getResponses5xx() {
        return this._responses5xx.get();
    }

    public long getStatsOnMs() {
        return System.currentTimeMillis() - this._statsStartedAt.get();
    }

    public long getResponsesBytesTotal() {
        return this._responsesTotalBytes.get();
    }

    public String toStatsHTML() {
        return "<h1>Statistics:</h1>\n" + "Statistics gathering started " + getStatsOnMs() + "ms ago" + "<br />\n" + "<h2>Requests:</h2>\n" + "Total requests: " + getRequests() + "<br />\n" + "Active requests: " + getRequestsActive() + "<br />\n" + "Max active requests: " + getRequestsActiveMax() + "<br />\n" + "Total requests time: " + getRequestTimeTotal() + "<br />\n" + "Mean request time: " + getRequestTimeMean() + "<br />\n" + "Max request time: " + getRequestTimeMax() + "<br />\n" + "Request time standard deviation: " + getRequestTimeStdDev() + "<br />\n" + "<h2>Dispatches:</h2>\n" + "Total dispatched: " + getDispatched() + "<br />\n" + "Active dispatched: " + getDispatchedActive() + "<br />\n" + "Max active dispatched: " + getDispatchedActiveMax() + "<br />\n" + "Total dispatched time: " + getDispatchedTimeTotal() + "<br />\n" + "Mean dispatched time: " + getDispatchedTimeMean() + "<br />\n" + "Max dispatched time: " + getDispatchedTimeMax() + "<br />\n" + "Dispatched time standard deviation: " + getDispatchedTimeStdDev() + "<br />\n" + "Total requests suspended: " + getSuspends() + "<br />\n" + "Total requests expired: " + getExpires() + "<br />\n" + "Total requests resumed: " + getResumes() + "<br />\n" + "<h2>Responses:</h2>\n" + "1xx responses: " + getResponses1xx() + "<br />\n" + "2xx responses: " + getResponses2xx() + "<br />\n" + "3xx responses: " + getResponses3xx() + "<br />\n" + "4xx responses: " + getResponses4xx() + "<br />\n" + "5xx responses: " + getResponses5xx() + "<br />\n" + "Bytes sent total: " + getResponsesBytesTotal() + "<br />\n";
    }
}
