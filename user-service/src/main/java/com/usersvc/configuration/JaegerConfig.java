package com.usersvc.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.jaegertracing.internal.JaegerTracer;
import io.jaegertracing.Configuration.SamplerConfiguration;
import io.jaegertracing.Configuration.ReporterConfiguration;

// TODO: Auto-generated Javadoc
/**
 * JaegerConfig.
 *
 * @author : Kannappan
 * @version : 1.0
 */
@Configuration
public class JaegerConfig {
	
	/**
	 * Gets the tracer.
	 *
	 * @return the tracer
	 */
	@Bean
	public static JaegerTracer getTracer() {
	    SamplerConfiguration samplerConfig = SamplerConfiguration.fromEnv().withType("const").withParam(1);
	    ReporterConfiguration reporterConfig = ReporterConfiguration.fromEnv().withLogSpans(true);
	    io.jaegertracing.Configuration config = new io.jaegertracing.Configuration("jaeger user-service").withSampler(samplerConfig).withReporter(reporterConfig);
	    return config.getTracer();
	}

}
