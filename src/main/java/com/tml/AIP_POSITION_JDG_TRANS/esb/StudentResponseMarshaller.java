package com.tml.AIP_POSITION_JDG_TRANS.esb;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tml.AIP_POSITION_JDG_TRANS.esb.StudentResponseMarshaller;

import org.infinispan.protostream.MessageMarshaller;
import org.infinispan.protostream.MessageMarshaller.ProtoStreamReader;
import org.infinispan.protostream.MessageMarshaller.ProtoStreamWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class StudentResponseMarshaller implements MessageMarshaller<StudentResponse> {
	
	private static Logger logger = LoggerFactory.getLogger(StudentResponseMarshaller.class);

    @Override
    public StudentResponse readFrom(ProtoStreamReader protoStreamReader) throws IOException {
        logger.info("Entering Method  StudentResponseMarshaller.readFrom");
        StudentResponse studentResponse = new StudentResponse();
        logger.info("Exiting Method  StudentResponseMarshaller.readFrom");
        return studentResponse;
    }

    @Override
    public void writeTo(ProtoStreamWriter protoStreamWriter, StudentResponse studentResponse) throws IOException {
        logger.info("Entering Method  StudentResponseMarshaller.readFrom");
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, String> studentResponseMap = objectMapper.readValue(studentResponse.toString(), HashMap.class);
        for (Map.Entry<String, String> entry : studentResponseMap.entrySet()) {
            protoStreamWriter.writeString(entry.getKey(), entry.getValue());
        }
        logger.info("Exiting Method  StudentResponseMarshaller.writeTo");
    }

    @Override
    public Class<? extends StudentResponse> getJavaClass() {
        return StudentResponse.class;
    }

    @Override
    public String getTypeName() {
        return "domain.StudentResponse";
    }

}
