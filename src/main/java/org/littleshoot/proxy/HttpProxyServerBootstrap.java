package org.littleshoot.proxy;

import org.littleshoot.proxy.impl.ThreadPoolConfiguration;
import org.littleshoot.proxy.monitoring.ProxyThreadPoolsObserver;
import org.littleshoot.proxy.ratelimit.RateLimiter;

import java.net.InetSocketAddress;

/**
 * Configures and starts an {@link HttpProxyServer}. The HttpProxyServer is
 * built using {@link #start()}. Sensible defaults are available for all
 * parameters such that {@link #start()} could be called immediately if you
 * wish.
 */
public interface HttpProxyServerBootstrap {

    /**
     * <p>
     * Give the server a name (used for naming threads, useful for logging).
     * </p>
     * 
     * <p>
     * Default = LittleProxy
     * </p>
     * 
     * @param name
     * @return
     */
    HttpProxyServerBootstrap withName(String name);

    /**
     * <p>
     * Specify the {@link TransportProtocol} to use for incoming connections.
     * </p>
     * 
     * <p>
     * Default = TCP
     * </p>
     * 
     * @param transportProtocol
     * @return
     */
    HttpProxyServerBootstrap withTransportProtocol(
            TransportProtocol transportProtocol);

    /**
     * <p>
     * Listen for incoming connections on the given address.
     * </p>
     * 
     * <p>
     * Default = [bound ip]:8080
     * </p>
     * 
     * @param address
     * @return
     */
    HttpProxyServerBootstrap withAddress(InetSocketAddress address);

    /**
     * <p>
     * Listen for incoming connections on the given port.
     * </p>
     * 
     * <p>
     * Default = 8080
     * </p>
     * 
     * @param port
     * @return
     */
    HttpProxyServerBootstrap withPort(int port);

    /**
     * <p>
     * Specify whether or not to only allow local connections.
     * </p>
     * 
     * <p>
     * Default = true
     * </p>
     * 
     * @param allowLocalOnly
     * @return
     */
    HttpProxyServerBootstrap withAllowLocalOnly(boolean allowLocalOnly);

    /**
     * This method has no effect and will be removed in a future release.
     * @deprecated use {@link #withNetworkInterface(InetSocketAddress)} to avoid listening on all local addresses
     */
    @Deprecated
    HttpProxyServerBootstrap withListenOnAllAddresses(boolean listenOnAllAddresses);

    /**
     * <p>
     * Specify an {@link SslEngineSource} to use for encrypting inbound
     * connections. Enabling this will enable SSL client authentication
     * by default (see {@link #withAuthenticateSslClients(boolean)})
     * </p>
     * 
     * <p>
     * Default = null
     * </p>
     * 
     * <p>
     * Note - This and {@link #withManInTheMiddle(MitmManagerFactory)} are
     * mutually exclusive.
     * </p>
     * 
     * @param sslEngineSource
     * @return
     */
    HttpProxyServerBootstrap withSslEngineSource(
            SslEngineSource sslEngineSource);

    /**
     * <p>
     * Specify whether or not to authenticate inbound SSL clients (only applies
     * if {@link #withSslEngineSource(SslEngineSource)} has been set).
     * </p>
     * 
     * <p>
     * Default = true
     * </p>
     * 
     * @param authenticateSslClients
     * @return
     */
    HttpProxyServerBootstrap withAuthenticateSslClients(
            boolean authenticateSslClients);

    /**
     * <p>
     * Specify a {@link ProxyAuthenticator} to use for doing basic HTTP
     * authentication of clients.
     * </p>
     * 
     * <p>
     * Default = null
     * </p>
     * 
     * @param proxyAuthenticator
     * @return
     */
    HttpProxyServerBootstrap withProxyAuthenticator(
            ProxyAuthenticator proxyAuthenticator);

    /**
     * <p>
     * Specify a {@link ChainedProxyManager} to use for chaining requests to
     * another proxy.
     * </p>
     * 
     * <p>
     * Default = null
     * </p>
     * 
     * @param chainProxyManager
     * @return
     */
    HttpProxyServerBootstrap withChainProxyManager(
            ChainedProxyManager chainProxyManager);

    /**
     * <p>
     * Specify an {@link MitmManager} to use for making this proxy act as an SSL
     * man in the middle
     * </p>
     * 
     * <p>
     * Default = null
     * </p>
     * 
     * <p>
     * Note - This and {@link #withSslEngineSource(SslEngineSource)} are
     * mutually exclusive.
     * </p>
     * 
     * @param mitmManager
     * @return
     */
    HttpProxyServerBootstrap withManInTheMiddle(
            MitmManagerFactory mitmManager);

    /**
     * <p>
     * Specify an {@link ExceptionHandler} to handle client to proxy errors
     * </p>
     *
     * <p>
     * Default = null
     * </p>
     *
     * @param clientToProxyExHandler
     * @return exception handler
     */
    HttpProxyServerBootstrap withClientToProxyExHandler(
        ExceptionHandler clientToProxyExHandler);

    /**
     * <p>
     * Specify an {@link ExceptionHandler} to handle proxy to server errors
     * </p>
     *
     * <p>
     * Default = null
     * </p>
     *
     * @param proxyToServerExHandler
     * @return exception handler
     */
    HttpProxyServerBootstrap withProxyToServerExHandler(
        ExceptionHandler proxyToServerExHandler);


    /**
     * <p>
     * Specify a {@link RequestTracer} to trace proxy requests
     * </p>
     *
     * <p>
     * Default = null
     * </p>
     *
     * @param requestTracer
     * @return proxy server bootstrap
     */
    HttpProxyServerBootstrap withRequestTracer(RequestTracer requestTracer);

    /**
     * <p>
     * Specify an {@link GlobalStateHandler} to customize a global state based on channel attributes
     * </p>
     *
     * <p>
     * Default = null
     * </p>
     *
     * @param globalStateHandler
     * @return proxy server bootstrap
     */
    HttpProxyServerBootstrap withCustomGlobalState(
        GlobalStateHandler globalStateHandler);

    /**
     * <p>
     * Specify a {@link HttpFiltersSource} to use for filtering requests and/or
     * responses through this proxy.
     * </p>
     * 
     * <p>
     * Default = null
     * </p>
     * 
     * @param filtersSource
     * @return
     */
    HttpProxyServerBootstrap withFiltersSource(
            HttpFiltersSource filtersSource);

    /**
     * <p>
     * Specify a {@link FailureHttpResponseComposer} to use for composing
     * custom response message on unrecoverable failure
     * </p>
     *
     * <p>
     * Default = {@link DefaultFailureHttpResponseComposer}
     * </p>
     *
     * @param unrecoverableFailureHttpResponseComposer custom response message composer
     * @return
     */
    HttpProxyServerBootstrap withUnrecoverableFailureHttpResponseComposer(
        FailureHttpResponseComposer unrecoverableFailureHttpResponseComposer);

    /**
     * <p>
     * Specify whether or not to use secure DNS lookups for outbound
     * connections.
     * </p>
     * 
     * <p>
     * Default = false
     * </p>
     * 
     * @param useDnsSec
     * @return
     */
    HttpProxyServerBootstrap withUseDnsSec(
            boolean useDnsSec);

    /**
     * <p>
     * Specify whether or not to run this proxy as a transparent proxy.
     * </p>
     * 
     * <p>
     * Default = false
     * </p>
     * 
     * @param transparent
     * @return
     */
    HttpProxyServerBootstrap withTransparent(
            boolean transparent);

    /**
     * <p>
     * Specify the timeout after which to disconnect idle connections, in
     * seconds.
     * </p>
     * 
     * <p>
     * Default = 70
     * </p>
     * 
     * @param idleConnectionTimeout
     * @return
     */
    HttpProxyServerBootstrap withIdleConnectionTimeout(
            int idleConnectionTimeout);

    /**
     * <p>
     * Specify the timeout for connecting to the upstream server on a new
     * connection, in milliseconds.
     * </p>
     * 
     * <p>
     * Default = 40000
     * </p>
     * 
     * @param connectTimeout
     * @return
     */
    HttpProxyServerBootstrap withConnectTimeout(
            int connectTimeout);

    /**
     * Specify a custom {@link HostResolver} for resolving server addresses.
     * 
     * @param serverResolver
     * @return
     */
    HttpProxyServerBootstrap withServerResolver(HostResolver serverResolver);

    /**
     * <p>
     * Add an {@link ActivityTracker} for tracking activity in this proxy.
     * </p>
     * 
     * @param activityTracker
     * @return
     */
    HttpProxyServerBootstrap plusActivityTracker(ActivityTracker activityTracker);

    /**
     * <p>
     * Specify the read and/or write bandwidth throttles for this proxy server. 0 indicates not throttling.
     * </p>
     * @param readThrottleBytesPerSecond
     * @param writeThrottleBytesPerSecond
     * @return
     */
    HttpProxyServerBootstrap withThrottling(long readThrottleBytesPerSecond, long writeThrottleBytesPerSecond);

    /**
     * All outgoing-communication of the proxy-instance is goin' to be routed via the given network-interface
     *
     * @param inetSocketAddress to be used for outgoing communication
     */
    HttpProxyServerBootstrap withNetworkInterface(InetSocketAddress inetSocketAddress);
    
    HttpProxyServerBootstrap withMaxInitialLineLength(int maxInitialLineLength);
    
    HttpProxyServerBootstrap withMaxHeaderSize(int maxHeaderSize);
    
    HttpProxyServerBootstrap withMaxChunkSize(int maxChunkSize);

    /**
     * When true, the proxy will accept requests that appear to be directed at an origin server (i.e. the URI in the HTTP
     * request will contain an origin-form, rather than an absolute-form, as specified in RFC 7230, section 5.3).
     * This is useful when the proxy is acting as a gateway/reverse proxy. <b>Note:</b> This feature should not be
     * enabled when running as a forward proxy; doing so may cause an infinite loop if the client requests the URI of the proxy.
     *
     * @param allowRequestToOriginServer when true, the proxy will accept origin-form HTTP requests
     */
    HttpProxyServerBootstrap withAllowRequestToOriginServer(boolean allowRequestToOriginServer);

    /**
     * Sets the alias to use when adding Via headers to incoming and outgoing HTTP messages. The alias may be any
     * pseudonym, or if not specified, defaults to the hostname of the local machine. See RFC 7230, section 5.7.1.
     *
     * @param alias the pseudonym to add to Via headers
     */
    HttpProxyServerBootstrap withProxyAlias(String alias);

    /**
     * <p>
     * Build and starts the server.
     * </p>
     *
     * @return the newly built and started server
     */
    HttpProxyServer start();

    /**
     * Set the configuration parameters for the proxy's thread pools.
     *
     * @param configuration thread pool configuration
     * @return proxy server bootstrap for chaining
     */
    HttpProxyServerBootstrap withThreadPoolConfiguration(ThreadPoolConfiguration configuration);

    /**
     * Set an observer instance to monitor internal thread pools.
     *
     * @param threadPoolObserver monitoring instance
     * @return proxy server bootstrap for chaining
     */
    HttpProxyServerBootstrap withThreadPoolObserver(ProxyThreadPoolsObserver threadPoolObserver);

    /**
     * <p>
     * Specify a {@link RateLimiter} that is used for rate limiting requests
     * </p>
     *
     * <p>
     * Default = null
     * </p>
     *
     * @param rateLimiter
     */
    HttpProxyServerBootstrap withRateLimiter(RateLimiter rateLimiter);

    /**
     * <p>
     * Specify whether to block http pipelining
     * </p>
     *
     * <p>
     * Default = false
     * </p>
     *
     * @param blockHttpPipelining
     */
    HttpProxyServerBootstrap withHttpPipeliningBlocked(boolean blockHttpPipelining);


    /**
     * <p>
     * Specify whether to enable logging in acceptor workers
     * </p>
     *
     * <p>
     * Default = false
     * </p> 
     * @param acceptorLoggingEnabled
     */
    HttpProxyServerBootstrap withAcceptorLoggingEnabled(boolean acceptorLoggingEnabled);
}