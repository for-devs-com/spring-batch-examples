package com.fordevs.processor;

import com.fordevs.mysql.entity.OutputStudent;
import com.fordevs.postgresql.entity.InputStudent;
import org.springframework.stereotype.Component;

@Component
public class ItemProcessor implements org.springframework.batch.item.ItemProcessor<InputStudent, OutputStudent> {

	@Override
	public OutputStudent process(InputStudent item) throws Exception {

		// Inyectar KafkaTemplate
		// KafkaTemplate<String, OutputStudent> kafkaTemplate = null;
		
		System.out.println(item.getId());
		
		OutputStudent outputStudent = new
				OutputStudent();
		
		outputStudent.setId(item.getId());
		outputStudent.setFirstName(item.getFirstName());
		outputStudent.setLastName(item.getLastName());
		outputStudent.setEmail(item.getEmail());
		outputStudent.setDeptId(item.getDeptId());
		outputStudent.setIsActive(item.getIsActive() != null && Boolean.parseBoolean(item.getIsActive()));

		//afkaTemplate.send("student_topic", outputStudent);
		return outputStudent;
		
	}

}
