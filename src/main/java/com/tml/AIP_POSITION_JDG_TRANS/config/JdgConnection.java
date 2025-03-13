package com.tml.AIP_POSITION_JDG_TRANS.config;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ClientIntelligence;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.infinispan.client.hotrod.impl.ConfigurationProperties;
import org.infinispan.client.hotrod.marshall.ProtoStreamMarshaller;
import org.infinispan.commons.marshall.JavaSerializationMarshaller;
import org.infinispan.protostream.SerializationContext;
import org.infinispan.protostream.annotations.ProtoSchemaBuilder;
import org.infinispan.query.remote.client.ProtobufMetadataManagerConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tml.AIP_POSITION_JDG_TRANS.config.JdgConnection;
import com.tml.AIP_POSITION_JDG_TRANS.esb.PositionUIResponse;

import java.io.IOException;

@Component
public class JdgConnection {
	
	
	@Autowired
	JdgProperties jdgProperties;
	
	
	
	/*
	 * Connection references
	 */
	private static ConfigurationBuilder builder = new ConfigurationBuilder();
	private static RemoteCacheManager cacheManager;
	private static RemoteCache<Integer, Object> cache;
	private static Logger logger = LoggerFactory.getLogger(JdgConnection.class);

	/*
	 * Method to get Cache from Cache Manager JDG
	 */
	public RemoteCache<Integer, Object> getCache(String cacheName) throws Exception {

		logger.info("Entering method RemoteCache with cache Name:{}", cacheName);
		logger.info("Properties with values {} , {}", jdgProperties.getHostname(), jdgProperties.getPort());

		try {
         System.out.println("=============="+jdgProperties.getHostname()+"-------------"+jdgProperties.getPort());

			//builder.addServer().host(jdgProperties.getHostname()).port(Integer.parseInt(jdgProperties.getPort()));
			
       //  builder.addCluster("xsite").addClusterNodes("192.168.0.107:11222;192.168.0.117:11222")
       //  .security().authentication().username("myuser").password("changeme").realm("default") .saslMechanism("SCRAM-SHA-512");
			
		builder
		//.addCluster("DESKTOP-BUHROTA-8866")
		//.addClusterNode("192.168.0.107", 11222)
		//.addCluster("TMLTHNLTPB00559-38144")
		//.addClusterNode("192.168.0.117", 11222)
		
		.addServer()
				.host(jdgProperties.getHostname()) 
				.port(Integer.parseInt(jdgProperties.getPort()))
			//	.port(ConfigurationProperties.DEFAULT_HOTROD_PORT)
			//	.addServer().host("192.168.0.117")
			//	.port(Integer.parseInt(jdgProperties.getPort()))
			//	.port(ConfigurationProperties.DEFAULT_HOTROD_PORT)
				
				.security()
				.authentication()
				.username("developer")
				.password("NhXSxBfkLgxsBFWG")
				.realm("default")
				.saslMechanism("SCRAM-SHA-512");
			 
		    //  .clientIntelligence(ClientIntelligence.BASIC);
		
		//	cacheManager = new RemoteCacheManager(builder.build());
			cacheManager = new RemoteCacheManager(builder.marshaller(new ProtoStreamMarshaller()).build());
			
			logger.info("Cache Manager:{}", cacheManager);
			
			getCustomSerialiazedCache();
			cache = cacheManager.getCache(cacheName);
			System.out.println("----------"+cache);
			//cache.clear();
			RemoteCache<String, String> metadataCache = cacheManager.getCache(ProtobufMetadataManagerConstants.PROTOBUF_METADATA_CACHE_NAME);
			logger.info("Cache Name:{}", ProtobufMetadataManagerConstants.PROTOBUF_METADATA_CACHE_NAME);
			//metadataCache.clear();
			String errors = metadataCache.get(ProtobufMetadataManagerConstants.ERRORS_KEY_SUFFIX);
			logger.info("Cache Name:{}", ProtobufMetadataManagerConstants.OBJECT_NAME );
			logger.info("Cache Name:{}", ProtobufMetadataManagerConstants.PROTO_KEY_SUFFIX);
			logger.info("Cache Name:{}", ProtobufMetadataManagerConstants.PROTOBUF_METADATA_CACHE_NAME);
			logger.info("matadata Cache size:{}", metadataCache.size());
			
			
			if (errors != null) {
				throw new Exception("Error in proto " + errors);
			}

		} catch (Exception e) {
			logger.error("Exception occured in method RemoteCache with exception :{}", e);
			//throw new Exception("Exception occurred in method RemoteCache with exception fdge");
		}

		logger.info("Entering method RemoteCache with cache Name:{}", cacheName);
		
		return cache;
	}
	
	
	private static String getCustomSerialiazedCache() throws IOException {
		SerializationContext ctx = null;
		String generatedSchema = null;
		ProtoSchemaBuilder protoSchemaBuilder = null;
		try {
			ctx = ProtoStreamMarshaller.getSerializationContext(cacheManager);
			logger.info("ctx:{}", ctx);
			protoSchemaBuilder = new ProtoSchemaBuilder();
			generatedSchema = protoSchemaBuilder.fileName("positionUIResponse.proto").packageName("domain")
					.addClass(PositionUIResponse.class).build(ctx);
			logger.info("Schema:{}", generatedSchema);
			RemoteCache<String, String> metadataCache = cacheManager
					.getCache(ProtobufMetadataManagerConstants.PROTOBUF_METADATA_CACHE_NAME);
			logger.info("metadataCache:{}", metadataCache);
			metadataCache.put("positionUIResponse.proto", generatedSchema);

		} catch (Exception e1) {

			StringBuilder sb = new StringBuilder();
			sb.append("No schema generated because of Exception");
			logger.error(sb.toString(), e1);

		}
		return generatedSchema;
	}

}