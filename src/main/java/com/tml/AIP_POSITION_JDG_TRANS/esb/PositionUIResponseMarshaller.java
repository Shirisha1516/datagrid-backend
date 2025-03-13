package com.tml.AIP_POSITION_JDG_TRANS.esb;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tml.AIP_POSITION_JDG_TRANS.esb.PositionUIResponseMarshaller;

import org.infinispan.protostream.MessageMarshaller;
import org.infinispan.protostream.MessageMarshaller.ProtoStreamReader;
import org.infinispan.protostream.MessageMarshaller.ProtoStreamWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PositionUIResponseMarshaller implements MessageMarshaller<PositionUIResponse> {
	
	private static Logger logger = LoggerFactory.getLogger(PositionUIResponseMarshaller.class);

    @Override
    public PositionUIResponse readFrom(ProtoStreamReader protoStreamReader) throws IOException {
        logger.info("Entering Method  PositionUIResponseMarshaller.readFrom");
        PositionUIResponse positionUIResponse = new PositionUIResponse();
        logger.info("Exiting Method  PositionUIResponseMarshaller.readFrom");
        return positionUIResponse;
    }

    @Override
    public void writeTo(ProtoStreamWriter protoStreamWriter, PositionUIResponse positionUIResponse) throws IOException {
        logger.info("Entering Method  PositionUIResponseMarshaller.readFrom");
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, String> positionUIResponseMap = objectMapper.readValue(positionUIResponse.toString(), HashMap.class);
        for (Map.Entry<String, String> entry : positionUIResponseMap.entrySet()) {
            protoStreamWriter.writeString(entry.getKey(), entry.getValue());
        }
        logger.info("Exiting Method  PositionUIResponseMarshaller.writeTo");
    }

    @Override
    public Class<? extends PositionUIResponse> getJavaClass() {
        return PositionUIResponse.class;
    }

    @Override
    public String getTypeName() {
        return "domain.positionUIResponse";
    }

}